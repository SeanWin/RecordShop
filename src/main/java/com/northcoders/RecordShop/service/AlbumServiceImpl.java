package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        albumRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album insertAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbumById(Long id, Album newAlbum) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("Album not found"));
        album.setPrice(newAlbum.getPrice());
        album.setStockCount(newAlbum.getStockCount());
        return albumRepository.save(album);
    }

    @Override
    public void deleteAlbumById(Long id) {
        if(!albumRepository.existsById(id)){
            throw new AlbumNotFoundException("Album not found");
        }
        albumRepository.deleteById(id);
    }
}
