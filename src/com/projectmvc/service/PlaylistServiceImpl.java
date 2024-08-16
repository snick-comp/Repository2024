package com.projectmvc.service;
import com.projectmvc.model.*;
import java.util.HashMap;
import java.util.Map;


public final class PlaylistServiceImpl implements PlaylistService {
	
    private final Map<Long, Playlist> playlists = new HashMap<>();

    private static final PlaylistServiceImpl INSTANCE = new PlaylistServiceImpl();

    private PlaylistServiceImpl() {}

    public static PlaylistServiceImpl getInstance() {
        return INSTANCE;
    }

    public void create(final Playlist playlist) {
        playlists.put(playlist.getId(), playlist);
    }

    public void update(final Playlist playlist) {
        if (playlists.containsKey(playlist.getId())) {
            playlists.put(playlist.getId(), playlist);
        }
    }

    public void delete(final long id) {
        playlists.remove(id);
    }

    public Playlist get(final long id) {
        return playlists.get(id);
    }

    public void addSongIntoPlaylist(long id, Song song) {
        Playlist playlist = playlists.get(id);
        if (playlist != null) {
            playlist.getSongs().add(song);
        }
    }
}