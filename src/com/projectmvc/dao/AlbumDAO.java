package com.projectmvc.dao;
import com.projectmvc.model.Album;
import java.util.Collection;


public interface AlbumDAO {
	
    void addAlbum(final Album album);
    Album getAlbumById(final Long id);
    Collection<Album> getAllAlbums();
}
