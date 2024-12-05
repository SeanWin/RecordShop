package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.repository.RecordshopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RecordshopServiceImpl implements RecordshopService{

    @Autowired
    RecordshopRepository recordshopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        recordshopRepository.findAll().forEach(albums::add);
        return albums;
    }
}
