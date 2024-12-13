package com.northcoders.RecordShop.repository;

import com.northcoders.RecordShop.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Artist findByName (String name);
}