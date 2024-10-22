package com.projectmvc.dao;
import com.projectmvc.model.Artist;
import java.util.Collection;


public interface ArtistDAO {
	
    void addArtist(final Artist artist);
    Artist getArtistById(final Long id);
    Collection<Artist> getAllArtists();
}
