package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.dao.PlaylistDAO;
import com.projectmvc.model.Playlist;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public final class PlaylistDAOImpl implements PlaylistDAO {

    private static final PlaylistDAOImpl INSTANCE = new PlaylistDAOImpl();

    private PlaylistDAOImpl() {}

    public static PlaylistDAOImpl getInstance() {
        return INSTANCE;
    }

    public void save(final Playlist playlist) {
        final String query = "INSERT INTO Playlists (name) VALUES (?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playlist.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving playlist", e);
        }
    }

    public Optional<Playlist> findById(final long id) {
        final String query = "SELECT * FROM Playlists WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Playlist playlist = new Playlist();
                    playlist.setId(resultSet.getLong("id"));
                    playlist.setName(resultSet.getString("name"));
                    return Optional.of(playlist);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding playlist by id", e);
        }
        return Optional.empty();
    }

    public void update(final Playlist playlist) {
        final String query = "UPDATE Playlists SET name = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playlist.getName());
            statement.setLong(2, playlist.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating playlist", e);
        }
    }

    public void delete(final long id) {
        final String query = "DELETE FROM Playlists WHERE id = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting playlist", e);
        }
    }
}
