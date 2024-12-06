package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.Exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.repository.RecordshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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

    @Override
    public Album updateAlbumById(Long id, Album newAlbum) {
        Album album = recordshopRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("Album not found"));
        album.setPrice(newAlbum.getPrice());
        album.setStockCount(newAlbum.getStockCount());
        return recordshopRepository.save(album);
    }
}
