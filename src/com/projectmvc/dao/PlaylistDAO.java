package com.projectmvc.dao;
import com.projectmvc.model.Playlist;
import java.util.Optional;


public interface PlaylistDAO {
    void save(Playlist playlist);
    Optional<Playlist> findById(long id);
    void update(Playlist playlist);
    void delete(long id);
}
