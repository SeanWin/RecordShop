package com.northcoders.RecordShop.controller;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.service.RecordshopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/recordshop")
public class RecordshopController {

    @Autowired
    RecordshopService recordshopService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(recordshopService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable("id") long id) {
        return recordshopService.getAlbumById(id)
                .map(album -> new ResponseEntity<>(album, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Album> createAlbum(@Valid @RequestBody Album album){
        Album newAlbum = recordshopService.insertAlbum(album);
        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable("id") long id, @RequestBody Album album){
        Album updatedAlbum = recordshopService.updateAlbumById(id, album);
        return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);

    }

}
