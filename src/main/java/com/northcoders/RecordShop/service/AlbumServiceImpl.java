package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Artist;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.repository.AlbumRepository;
import com.northcoders.RecordShop.repository.ArtistRepository;
import com.northcoders.RecordShop.search.AlbumSearchParameters;
import com.northcoders.RecordShop.search.AlbumSearchSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

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
    @Transactional
    public Album insertAlbum(Album album) {
        Artist artist = artistRepository.findByName(album.getArtist().getName());
        if(artist==null){
            artist = artistRepository.save(album.getArtist());
        }
        album.setArtist(artist);
        return albumRepository.save(album);
    }

    @Override
    public Album updateAlbumById(Long id, Album newAlbum) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("Album not found"));
        album.setName(newAlbum.getName());
        album.setGenre(newAlbum.getGenre());
        album.setReleaseDate(newAlbum.getReleaseDate());
        album.setPrice(newAlbum.getPrice());
        album.setStockCount(newAlbum.getStockCount());

        Artist artist = artistRepository.findByName(newAlbum.getArtist().getName());
        if(artist==null){
            artist = artistRepository.save(newAlbum.getArtist());
        }
        album.setArtist(artist);
        return albumRepository.save(album);
    }

    @Override
    public void deleteAlbumById(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new AlbumNotFoundException("Album not found");
        }
        albumRepository.deleteById(id);
    }

    @Override
    public List<Album> searchAlbums(String artistName, Integer releaseYear, Genre genre, String albumName) {
        AlbumSearchParameters searchParams = new AlbumSearchParameters();
        searchParams.setArtistName(artistName);
        searchParams.setReleaseYear(releaseYear);
        searchParams.setGenre(genre);
        searchParams.setAlbumName(albumName);

        AlbumSearchSpecification specification = new AlbumSearchSpecification(searchParams);
        return albumRepository.findAll(specification);
    }
}