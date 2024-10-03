package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.exception.CustomDatabaseException;
import com.projectmvc.model.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public final class SongDAOImpl implements SongDAO {

    public void addSong(final Song song) {
        final String query = "INSERT INTO songs (name) VALUES (?)";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, song.getName());
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
            throw new CustomDatabaseException("Error adding song", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    public Song getSongById(final Long id) {
        final String query = "SELECT * FROM songs WHERE id = ?";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        final Song song = new Song();
                        song.setId(resultSet.getLong("id"));
                        song.setName(resultSet.getString("name"));
                        return song;
                    }
                }
            }
        } catch (final SQLException e) {
            throw new CustomDatabaseException("Error getting song by ID", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return null;
    }

    public Collection<Song> getAllSongs() {
        final Collection<Song> songs = new ArrayList<>();
        final String query = "SELECT * FROM songs";
        final ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = null;

        try {
            connection = connectionManager.borrowConnection();
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final Song song = new Song();
                    song.setId(resultSet.getLong("id"));
                    song.setName(resultSet.getString("name"));
                    songs.add(song);
                }
            }
        } catch (final SQLException e) {
            throw new CustomDatabaseException("Error getting all songs", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return songs;
    }
}
