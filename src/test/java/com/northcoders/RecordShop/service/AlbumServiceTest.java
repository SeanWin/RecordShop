package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.repository.AlbumRepository;
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
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumServiceImpl albumServiceImpl;

    @Test
    @DisplayName("getAllAlbums responds with a list of all albums")
    void test_GetAllAlbums() {
        //arrange
        List<Album> albums = new ArrayList<>();
        Album album1 = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        Album album2 = Album.builder().id(2L).name("name2").artist("artist2").genre(Genre.CLASSICAL).releaseDate(LocalDate.of(2010, 8, 22)).stockCount(2).price(29.99d).build();
        albums.add(album1);
        albums.add(album2);
        when(albumRepository.findAll()).thenReturn(albums);

        //act
        List<Album> result = albumServiceImpl.getAllAlbums();

        //assert
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(albums);
    }

    @DisplayName("positive test for getAlbumById method")
    @Test
    public void test_getAlbumById_positive(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(albumRepository.findById(1L)).willReturn(Optional.of(album));

        // when
        Album savedAlbum = albumServiceImpl.getAlbumById(album.getId()).get();

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
        given(albumRepository.findById(albumId)).willReturn(Optional.empty());

        // when
        Optional<Album> result = albumServiceImpl.getAlbumById(albumId);

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("Test for insertAlbum method")
    @Test
    public void test_insertAlbum(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(albumRepository.save(album)).willReturn(album);

        //when
        Album result = albumServiceImpl.insertAlbum(album);

        //then
        assertThat(result).isEqualTo(album);
    }

    @DisplayName("positive test for updateAlbumById method")
    @Test
    public void test_updateAlbum_positive(){
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(albumRepository.save(album)).willReturn(album);
        given(albumRepository.findById(album.getId())).willReturn(Optional.of(album));
        album.setStockCount(2);
        album.setPrice(29.99d);
        // when
        Album updatedAlbum = albumServiceImpl.updateAlbumById(album.getId(), album);

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

        given(albumRepository.findById(nonExistentAlbumId)).willReturn(Optional.empty());

        // when
        assertThrows(AlbumNotFoundException.class, () -> {
            albumServiceImpl.updateAlbumById(nonExistentAlbumId, updatedAlbum);
        });

        // then
        verify(albumRepository, never()).save(any(Album.class));
    }

    @DisplayName("positive test for deleteAlbumById method")
    @Test
    public void test_deleteAlbumById_positive(){
        // given
        long id = 1L;
        given(albumRepository.existsById(id)).willReturn(true);

        // when
        albumServiceImpl.deleteAlbumById(id);

        // then
        verify(albumRepository, times(1)).existsById(id);
        verify(albumRepository, times(1)).deleteById(id);
    }

    @DisplayName("Negative test for deleteAlbumById method")
    @Test
    public void test_deleteAlbumById_negative() {
        // given
        long id = 1L;
        given(albumRepository.existsById(id)).willReturn(false);

        // when
        assertThrows(AlbumNotFoundException.class, () -> albumServiceImpl.deleteAlbumById(id));

        //then
        verify(albumRepository, times(1)).existsById(id);
        verify(albumRepository, never()).deleteById(id);
    }

}