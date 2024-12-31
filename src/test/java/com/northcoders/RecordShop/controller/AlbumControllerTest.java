package com.northcoders.RecordShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.RecordShop.exception.AlbumNotFoundException;
import com.northcoders.RecordShop.model.*;
import com.northcoders.RecordShop.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class AlbumControllerTest {

    @MockBean
    private AlbumService albumService;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("GET all albums")
    void test_getAllAlbums() throws Exception {
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

        given(albumService.getAllAlbums()).willReturn(albums);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop/albums"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(albums.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Abbey Road"))
                .andExpect(jsonPath("$[0].releaseDate", is("2000-05-15")))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Symphony No. 9"))
                .andExpect(jsonPath("$[1].releaseDate", is("2010-08-22")));
    }

    @Test
    @DisplayName("GET album by id positive")
    void test_getAlbumById_positive() throws Exception {
        // given
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();
        given(albumService.getAlbumById(1L)).willReturn(Optional.of(album));

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop/albums/{id}", 1L));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(album.getName())))
                .andExpect(jsonPath("$.genre", is(String.valueOf(album.getGenre()))));
    }

    @Test
    @DisplayName("GET album by id negative")
    void test_getAlbumById_negative() throws Exception {
        // given
        given(albumService.getAlbumById(1L)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop/albums/{id}", 1L));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("POST album positive")
    void test_createAlbum_positive() throws Exception {
        //given
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("The Beatles");
        artistDTO.setNationality("British");

        AlbumDTO albumDTO = new AlbumDTO.Builder().setName("Abbey Road").setArtist(artistDTO).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album createdAlbum = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        //when
        when(albumService.insertAlbum(any(Album.class))).thenReturn(createdAlbum);

        this.mockMvc.perform(
                        post("/api/v1/recordshop/albums")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(albumDTO)))
                .andExpect(status().isCreated());

        //then
        verify(albumService, times(1)).insertAlbum(any(Album.class));
    }

    @Test
    @DisplayName("POST album negative")
    void test_createAlbum_negative() throws Exception {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("The Beatles");
        artistDTO.setNationality("British");
        // AlbumDTO with invalid fields

        AlbumDTO albumDTO = new AlbumDTO.Builder().setName("").setArtist(artistDTO).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2030, 5, 15)).setStockCount(-1).setPrice(-19.99).build();

        // when
        this.mockMvc.perform(
                        post("/api/v1/recordshop/albums")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(albumDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        // then
        verify(albumService, times(0)).insertAlbum(any(Album.class));
    }

    @Test
    @DisplayName("PUT album positive")
    public void test_updateAlbum_positive() throws Exception {
        // given
        long id = 1L;

        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("The Beatles");
        artistDTO.setNationality("British");

        AlbumDTO updatedAlbumDTO = new AlbumDTO.Builder().setName("Abbey Road").setArtist(artistDTO).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(10).setPrice(29.99).build();

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album savedAlbum = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();

        Album updatedAlbum = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(10).setPrice(29.99).build();

        given(albumService.updateAlbumById(eq(id), any(Album.class))).willReturn(updatedAlbum);

        // when
        ResultActions response = mockMvc.perform(
                put("/api/v1/recordshop/albums/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedAlbumDTO))
        );

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedAlbum.getName())))
                .andExpect(jsonPath("$.stockCount", is(updatedAlbum.getStockCount())))
                .andExpect(jsonPath("$.price", is(updatedAlbum.getPrice())));
    }

    @DisplayName("PUT album negative")
    @Test
    public void test_updateAlbum_negative() throws Exception {
        // given
        long id = 1L;

        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("The Beatles");
        artistDTO.setNationality("British");
        AlbumDTO updatedAlbumDTO = new AlbumDTO.Builder().setName("Abbey Road").setArtist(artistDTO).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(10).setPrice(29.99).build();

        given(albumService.updateAlbumById(eq(id), any(Album.class)))
                .willThrow(new AlbumNotFoundException("Album not found"));

        // when
        ResultActions response = mockMvc.perform(
                put("/api/v1/recordshop/albums/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedAlbumDTO))
        );

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("DELETE album positive")
    @Test
    public void test_deleteAlbum_positive() throws Exception{
        //given
        long id= 1L;

        willDoNothing().given(albumService).deleteAlbumById(id);

       //when
        ResultActions response = mockMvc.perform(delete("/api/v1/recordshop/albums/{id}", id));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("DELETE album negative")
    @Test
    public void test_deleteAlbumById_negative() throws Exception {
        // given
        long id = 1L;
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("The Beatles");
        artist.setNationality("British");

        Album album = new Album.Builder().setId(1L).setName("Abbey Road").setArtist(artist).setGenre(Genre.BLUES)
                .setReleaseDate(LocalDate.of(2000, 5, 15)).setStockCount(1).setPrice(19.99).build();
        doThrow(new AlbumNotFoundException("Album not found")).when(albumService).deleteAlbumById(id);

        //when
        ResultActions response = mockMvc.perform(delete("/api/v1/recordshop/albums/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(album)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
        verify(albumService, times(1)).deleteAlbumById(id);
    }
}
