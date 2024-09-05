package com.projectmvc.dao;
import com.projectmvc.connection.ConnectionManager;
import com.projectmvc.dao.SongDAO;
import com.projectmvc.model.Song;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public final class SongDAOImpl implements SongDAO {

    private static final SongDAOImpl INSTANCE = new SongDAOImpl();

    private SongDAOImpl() {}

    public static SongDAOImpl getInstance() {
        return INSTANCE;
    }

    public void save(final Song song) {
        final String query = "INSERT INTO Songs (name) VALUES (?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, song.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving song", e);
        }
    }

    public Collection<Song> findAll() {
        final String query = "SELECT * FROM Songs";
        Collection<Song> songs = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Song song = new Song();
                song.setId(resultSet.getLong("id"));
                song.setName(resultSet.getString("name"));
                songs.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving songs", e);
        }
        return songs;
    }
}
