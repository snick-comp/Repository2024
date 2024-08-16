package com.projectmvc.controller;
import com.projectmvc.service.*;
import com.projectmvc.model.*;


public final class PlaylistControllerImpl implements PlaylistController {
	
    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    private static final PlaylistControllerImpl INSTANCE = new PlaylistControllerImpl();

    private PlaylistControllerImpl() {}

    public static PlaylistControllerImpl getInstance() {
        return INSTANCE;
    }

    public void create(final Playlist playlist) {
        playlistService.create(playlist);
    }

    public void update(final Playlist playlist) {
        playlistService.update(playlist);
    }

    public void delete(final long id) {
        playlistService.delete(id);
    }

    public Playlist get(final long id) {
        return playlistService.get(id);
    }

    public void addSongIntoPlaylist(final long id, final Song song) {
        playlistService.addSongIntoPlaylist(id, song);
    }
}

