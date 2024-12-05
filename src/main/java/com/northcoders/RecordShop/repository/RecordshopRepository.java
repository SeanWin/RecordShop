package com.northcoders.RecordShop.repository;

import com.northcoders.RecordShop.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordshopRepository extends CrudRepository<Album, Long> {
}
