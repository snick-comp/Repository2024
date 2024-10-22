package com.projectmvc.model;


public class Artist {

    private long id;
    private String name;
    private String genre;

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }
}
