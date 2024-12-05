package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.repository.RecordshopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
        Album album1 = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        Album album2 = Album.builder().id(2L).name("name2").artist("artist2").genre(Genre.CLASSICAL).releaseDate(LocalDate.of(2010, 8, 22)).stockCount(2).price(29.99d).build();
        albums.add(album1);
        albums.add(album2);
        when(recordshopRepository.findAll()).thenReturn(albums);

        //act
        List<Album> result = recordshopServiceImpl.getAllAlbums();

        //assert
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(albums);
    }

    @DisplayName("positive test for getAlbumById method")
    @Test
    public void test_getAlbumById_positive(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(recordshopRepository.findById(1L)).willReturn(Optional.of(album));

        // when
        Album savedAlbum = recordshopServiceImpl.getAlbumById(album.getId()).get();

        // then
        assertThat(savedAlbum).isNotNull();
        assertThat(savedAlbum.getId()).isEqualTo(1L);
        assertThat(savedAlbum.getName()).isEqualTo("name");

    }

    @DisplayName("Negative test for getAlbumById method")
    @Test
    public void test_getAlbumById_negative() {
        // given
        Long albumId = 1L;
        given(recordshopRepository.findById(albumId)).willReturn(Optional.empty());

        // when
        Optional<Album> result = recordshopServiceImpl.getAlbumById(albumId);

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("Test for insertAlbum method")
    @Test
    public void test_insertAlbum(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(recordshopRepository.save(album)).willReturn(album);

        //when
        Album result = recordshopServiceImpl.insertAlbum(album);

        //then
        assertThat(result).isEqualTo(album);
    }

}