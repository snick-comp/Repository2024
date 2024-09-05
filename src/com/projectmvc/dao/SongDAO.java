package com.projectmvc.dao;
import com.projectmvc.model.Song;
import java.util.Collection;


public interface SongDAO {
    void save(Song song);
    Collection<Song> findAll();
}
