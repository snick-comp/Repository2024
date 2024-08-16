package com.projectmvc.service;
import com.projectmvc.model.*;
import java.util.ArrayList;
import java.util.Collection;


public final class SongServiceImpl implements SongService {
	
    private final Collection<Song> songs = new ArrayList<>();

    private static final SongServiceImpl INSTANCE = new SongServiceImpl();

    private SongServiceImpl() {}

    public static SongServiceImpl getInstance() {
        return INSTANCE;
    }

    public Collection<Song> getAll() {
        return new ArrayList<>(songs);
    }
}
