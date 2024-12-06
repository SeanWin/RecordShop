package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.Exception.AlbumNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @DisplayName("positive test for updateAlbumById method")
    @Test
    public void test_updateAlbum_positive(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(recordshopRepository.save(album)).willReturn(album);
        given(recordshopRepository.findById(album.getId())).willReturn(Optional.of(album));
        album.setStockCount(2);
        album.setPrice(29.99d);
        // when
        Album updatedAlbum = recordshopServiceImpl.updateAlbumById(album.getId(), album);

        // then
        assertThat(updatedAlbum.getStockCount()).isEqualTo(2);
        assertThat(updatedAlbum.getPrice()).isEqualTo(29.99d);
    }

    @DisplayName("Negative test for updateAlbumById method")
    @Test
    public void test_updateAlbumById_negative() {
        // given
        Long nonExistentAlbumId = 99L;
        Album updatedAlbum = Album.builder()
                .price(29.99d)
                .stockCount(2)
                .build();

        given(recordshopRepository.findById(nonExistentAlbumId)).willReturn(Optional.empty());

        // when
        assertThrows(AlbumNotFoundException.class, () -> {
            recordshopServiceImpl.updateAlbumById(nonExistentAlbumId, updatedAlbum);
        });

        // then
        verify(recordshopRepository, never()).save(any(Album.class));
    }

    @DisplayName("positive test for deleteAlbumById method")
    @Test
    public void test_deleteAlbumById_positive(){
        // given
        long id = 1L;
        given(recordshopRepository.existsById(id)).willReturn(true);

        // when
        recordshopServiceImpl.deleteAlbumById(id);

        // then
        verify(recordshopRepository, times(1)).existsById(id);
        verify(recordshopRepository, times(1)).deleteById(id);
    }

    @DisplayName("Negative test for deleteAlbumById method")
    @Test
    public void test_deleteAlbumById_negative() {
        // given
        long id = 1L;
        given(recordshopRepository.existsById(id)).willReturn(false);

        // when
        assertThrows(AlbumNotFoundException.class, () -> recordshopServiceImpl.deleteAlbumById(id));

        //then
        verify(recordshopRepository, times(1)).existsById(id);
        verify(recordshopRepository, never()).deleteById(id);
    }

}