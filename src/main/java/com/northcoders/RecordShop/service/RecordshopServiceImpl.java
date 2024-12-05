package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.repository.RecordshopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordshopServiceImpl implements RecordshopService{

    @Autowired
    RecordshopRepository recordshopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        recordshopRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return recordshopRepository.findById(id);
    }

    @Override
    public Album insertAlbum(Album album) {
        return recordshopRepository.save(album);
    }
}
