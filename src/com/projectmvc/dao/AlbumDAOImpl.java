package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.exception.CustomDatabaseException;
import com.projectmvc.model.Album;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * AlbumDAOImpl is the implementation of the AlbumDAO interface.
 * It provides methods to interact with the 'albums' table in the database.
 */
public final class AlbumDAOImpl implements AlbumDAO {

    /**
     * Adds a new album to the database.
     * 
     * @param album The Album object to be added.
     */
    public void addAlbum(final Album album) {
    	
        final String query = "INSERT INTO albums (id, title, genre, release_date) VALUES (?, ?, ?, ?)";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, album.getId());
                statement.setString(2, album.getTitle());
                statement.setString(3, album.getGenre());
                statement.setDate(4, new java.sql.Date(album.getReleaseDate().getTime()));
                statement.executeUpdate();
            }
            connection.commit();
            
        } catch (final SQLException e) {
            if (connection != null) {
            	
                try {
                    connection.rollback();
                    
                } catch (final SQLException rollbackEx) {
                    throw new CustomDatabaseException("Error rolling back transaction", rollbackEx);
                }
            }
            throw new CustomDatabaseException("Error adding album", e);
            
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    /**
     * Retrieves an album by its ID from the database.
     * 
     * @param id The ID of the album to be retrieved.
     * @return The Album object if found, null otherwise.
     */
    public Album getAlbumById(final Long id) {
       
    	final String query = "SELECT * FROM albums WHERE id = ?";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
            
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        final Album album = new Album();
                        album.setId(resultSet.getLong("id"));
                        album.setTitle(resultSet.getString("title"));
                        album.setGenre(resultSet.getString("genre"));
                        album.setReleaseDate(resultSet.getDate("release_date"));
                        return album;
                    }
                }
            }
       
        } catch (final SQLException e) {
          
        	throw new CustomDatabaseException("Error getting album by ID", e);
        
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return null;
    }

    /**
     * Retrieves all albums from the database.
     * 
     * @return A Collection of Album objects representing all albums in the database.
     */
    public Collection<Album> getAllAlbums() {
        
    	final Collection<Album> albums = new ArrayList<>();
        final String query = "SELECT * FROM albums";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final Album album = new Album();
                    album.setId(resultSet.getLong("id"));
                    album.setTitle(resultSet.getString("title"));
                    album.setGenre(resultSet.getString("genre"));
                    album.setReleaseDate(resultSet.getDate("release_date"));
                    albums.add(album);
                }
            }
        
        } catch (final SQLException e) {
           
        	throw new CustomDatabaseException("Error getting all albums", e);
        
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return albums;
    }
}
