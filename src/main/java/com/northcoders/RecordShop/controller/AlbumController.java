package com.northcoders.RecordShop.controller;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.AlbumDTO;
import com.northcoders.RecordShop.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/recordshop/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable("id") long id) {
        return albumService.getAlbumById(id)
                .map(album -> new ResponseEntity<>(album, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody @Valid AlbumDTO albumDTO){
        Album newAlbum = albumService.insertAlbum(albumDTO.toAlbum());
        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable("id") long id, @RequestBody @Valid AlbumDTO albumDTO){
        Album updatedAlbum = albumService.updateAlbumById(id, albumDTO.toAlbum());
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable("id") long id){
        albumService.deleteAlbumById(id);
        return new ResponseEntity<>("Album deleted successfully!.", HttpStatus.NO_CONTENT);
    }

}
