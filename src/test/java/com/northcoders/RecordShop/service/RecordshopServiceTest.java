package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.repository.RecordshopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class RecordshopServiceTest {

    @Mock
    private RecordshopRepository recordshopRepository;

    @InjectMocks
    private RecordshopServiceImpl recordshopServiceImpl;

    @Test
    @DisplayName("getAllAlbums responds with a list of all albums")
    void test_GetAllAlbums() {
        //arrange
        List<Album> albums = new ArrayList<>();
        Album album1 = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseYear(2000).stockCount(1).price(19.99d).build();
        Album album2 = Album.builder().id(2L).name("name2").artist("artist2").genre(Genre.CLASSICAL).releaseYear(2000).stockCount(2).price(29.99d).build();
        albums.add(album1);
        albums.add(album2);
        when(recordshopRepository.findAll()).thenReturn(albums);

        //act
        List<Album> result = recordshopServiceImpl.getAllAlbums();

        //assert
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(albums);
    }

}