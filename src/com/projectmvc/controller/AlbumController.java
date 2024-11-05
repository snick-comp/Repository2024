package com.projectmvc.controller;
import com.projectmvc.dao.AlbumDAO;
import com.projectmvc.model.Album;
import com.projectmvc.exception.CustomDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumDAO albumDAO;

    @Autowired
    public AlbumController(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    @PostMapping
    public String addAlbum(@RequestBody Album album) {
        try {
            albumDAO.addAlbum(album);
            return "Album added successfully.";
        } catch (CustomDatabaseException e) {
            return "Error adding album: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable Long id) {
        try {
            return albumDAO.getAlbumById(id);
        } catch (CustomDatabaseException e) {
            throw new RuntimeException("Error retrieving album by ID: " + e.getMessage());
        }
    }

    @GetMapping
    public Collection<Album> getAllAlbums() {
        try {
            return albumDAO.getAllAlbums();
        } catch (CustomDatabaseException e) {
            throw new RuntimeException("Error retrieving all albums: " + e.getMessage());
        }
    }
}
