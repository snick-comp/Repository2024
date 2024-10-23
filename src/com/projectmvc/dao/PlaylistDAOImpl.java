package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.exception.CustomDatabaseException;
import com.projectmvc.model.Playlist;
import com.projectmvc.model.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public final class PlaylistDAOImpl implements PlaylistDAO {

    public void addPlaylist(final Playlist playlist) {
       
    	final String query = "INSERT INTO playlists (id, name) VALUES (?, ?)";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
          
        	connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
           
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, playlist.getId());
                statement.setString(2, playlist.getName());
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
          
            throw new CustomDatabaseException("Error adding playlist", e);
      
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    public Playlist getPlaylistById(final Long id) {
      
    	final String query = "SELECT * FROM playlists WHERE id = ?";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
           
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        final Playlist playlist = new Playlist();
                        playlist.setId(resultSet.getLong("id"));
                        playlist.setName(resultSet.getString("name"));
                        return playlist;
                    }
                }
            }
        
        } catch (final SQLException e) {
           
        	throw new CustomDatabaseException("Error getting playlist by ID", e);
        
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return null;
    }

    public void addSongToPlaylist(final Long playlistId, final Song song) {
        
    	final String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, playlistId);
                statement.setLong(2, song.getId());
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
         
            throw new CustomDatabaseException("Error adding song to playlist", e);
       
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    public Collection<Song> getSongsInPlaylist(final Long playlistId) {
        
    	final Collection<Song> songs = new ArrayList<>();
        final String query = "SELECT s.id, s.name FROM songs s " +
                "JOIN playlist_songs ps ON s.id = ps.song_id " +
                "WHERE ps.playlist_id = ?";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, playlistId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        final Song song = new Song();
                        song.setId(resultSet.getLong("id"));
                        song.setName(resultSet.getString("name"));
                        songs.add(song);
                    }
                }
            }
       
        } catch (final SQLException e) {
           
        	throw new CustomDatabaseException("Error getting songs in playlist", e);
        
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return songs;
    }
}
