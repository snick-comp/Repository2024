package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.exception.CustomDatabaseException;
import com.projectmvc.model.Artist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public final class ArtistDAOImpl implements ArtistDAO {

    public void addArtist(final Artist artist) {
        
    	final String query = "INSERT INTO artists (id, name, genre) VALUES (?, ?, ?)";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
           
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, artist.getId());
                statement.setString(2, artist.getName());
                statement.setString(3, artist.getGenre());
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
           
            throw new CustomDatabaseException("Error adding artist", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    public Artist getArtistById(final Long id) {
        
    	final String query = "SELECT * FROM artists WHERE id = ?";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
           
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
              
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        final Artist artist = new Artist();
                        artist.setId(resultSet.getLong("id"));
                        artist.setName(resultSet.getString("name"));
                        artist.setGenre(resultSet.getString("genre"));
                        return artist;
                    }
                }
            }
       
        } catch (final SQLException e) {
            throw new CustomDatabaseException("Error getting artist by ID", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return null;
    }

    public Collection<Artist> getAllArtists() {
       
    	final Collection<Artist> artists = new ArrayList<>();
        final String query = "SELECT * FROM artists";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
           
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final Artist artist = new Artist();
                    artist.setId(resultSet.getLong("id"));
                    artist.setName(resultSet.getString("name"));
                    artist.setGenre(resultSet.getString("genre"));
                    artists.add(artist);
                }
            }
       
        } catch (final SQLException e) {
            throw new CustomDatabaseException("Error getting all artists", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return artists;
    }
}
