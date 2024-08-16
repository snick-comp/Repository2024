package com.projectmvc.controller;
import com.projectmvc.model.*;


public interface PlaylistController {
	
    void create(final Playlist playlist);
    void update(final Playlist playlist);
    void delete(final long id);
    Playlist get(final long id);
    void addSongIntoPlaylist(final long id, final Song song);
}
