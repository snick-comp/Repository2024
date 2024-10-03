package com.projectmvc.dao;
import com.projectmvc.model.Song;
import java.util.Collection;


public interface SongDAO {

    void addSong(final Song song);

    Song getSongById(final Long id);

    Collection<Song> getAllSongs();
}
