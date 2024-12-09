package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.model.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumService {
    List<Album> getAllAlbums();

    Optional<Album> getAlbumById(Long id);

    Album insertAlbum(Album album);

    Album updateAlbumById(Long id, Album newAlbum);

    void deleteAlbumById(Long id);
}
