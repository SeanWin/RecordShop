package com.northcoders.RecordShop.service;

import com.northcoders.RecordShop.exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Artist;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.repository.AlbumRepository;
import com.northcoders.RecordShop.repository.ArtistRepository;
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

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private AlbumServiceImpl albumServiceImpl;

    @Test
    @DisplayName("getAllAlbums responds with a list of all albums")
    void test_GetAllAlbums() {
        // given
        List<Album> albums = new ArrayList<>();
        Artist artist1 = new Artist();
        artist1.setId(1L);
        artist1.setName("The Beatles");
        artist1.setNationality("British");

        Artist artist2 = new Artist();
        artist2.setId(2L);
        artist2.setName("Beethoven");
        artist2.setNationality("German");

        Album album1 = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist1).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        Album album2 = new Album.Builder().setId(2L).setName("Symphony No. 9").setArtist(artist2).setGenre(Genre.CLASSICAL)
                .setReleaseDate(LocalDate.of(2010, 8, 22)).setStockCount(2).setPrice(29.99).build();

        albums.add(album1);
        albums.add(album2);
        when(albumRepository.findAll()).thenReturn(albums);

        //when
        List<Album> result = albumServiceImpl.getAllAlbums();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(albums);
    }

    @DisplayName("positive test for getAlbumById method")
    @Test
    public void test_getAlbumById_positive(){
        // given
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        given(albumRepository.findById(1L)).willReturn(Optional.of(album));

        // when
        Album savedAlbum = albumServiceImpl.getAlbumById(album.getId()).get();

        // then
        assertThat(savedAlbum).isNotNull();
        assertThat(savedAlbum.getId()).isEqualTo(1L);
        assertThat(savedAlbum.getName()).isEqualTo("Abbey Road");

    }

    @DisplayName("Negative test for getAlbumById method")
    @Test
    public void test_getAlbumById_negative() {
        // given
        Long albumId = 1L;

        // when
        Optional<Album> result = albumServiceImpl.getAlbumById(albumId);

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("Test for insertAlbum method when artist exists in db")
    @Test
    public void test_insertAlbum(){
        // given
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        when(artistRepository.findByName(artist.getName())).thenReturn(artist);
        when(albumRepository.save(album)).thenReturn(album);

        //when
        Album result = albumServiceImpl.insertAlbum(album);

        //then
        assertThat(result).isEqualTo(album);
        verify(artistRepository, times(1)).findByName("The Beatles");
        verify(artistRepository, never()).save(artist);
        verify(albumRepository, times(1)).save(album);
    }

    @DisplayName("Test for insertAlbum method when artist doesn't exists in db")
    @Test
    public void test_insertAlbum2(){
        // given
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();
        when(artistRepository.findByName(artist.getName())).thenReturn(null);
        when(albumRepository.save(album)).thenReturn(album);

        //when
        Album result = albumServiceImpl.insertAlbum(album);

        //then
        assertThat(result).isEqualTo(album);
        verify(artistRepository, times(1)).findByName("The Beatles");
        verify(artistRepository, times(1)).save(artist);
        verify(albumRepository, times(1)).save(album);
    }

    @DisplayName("positive test for updateAlbumById method")
    @Test
    public void test_updateAlbum_positive(){
        // given
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

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
        Long id = 1L;
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album updatedAlbum = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        given(albumRepository.findById(id)).willReturn(Optional.empty());

        // when
        assertThrows(AlbumNotFoundException.class, () -> {
            albumServiceImpl.updateAlbumById(id, updatedAlbum);
        });

        // then
        verify(albumRepository, never()).save(updatedAlbum);
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