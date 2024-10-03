package com.projectmvc.dao;
import com.projectmvc.model.Playlist;
import com.projectmvc.model.Song;
import java.util.Collection;


public interface PlaylistDAO {

    void addPlaylist(final Playlist playlist);

    Playlist getPlaylistById(final Long id);

    void addSongToPlaylist(final Long playlistId, final Song song);

    Collection<Song> getSongsInPlaylist(final Long playlistId);
}
