package com.northcoders.RecordShop.controller;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.service.RecordshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
