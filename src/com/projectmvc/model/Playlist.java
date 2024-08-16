package com.projectmvc.model;
import java.util.Collection;


public class Playlist {
    private long id;
    private String name;
    private Collection<Song> songs;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(final Collection<Song> songs) {
        this.songs = songs;
    }
}
