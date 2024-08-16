package com.projectmvc.service;
import java.util.Collection;
import com.projectmvc.model.*;


public interface SongService {
	
    Collection<Song> getAll();
}