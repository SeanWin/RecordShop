package com.northcoders.RecordShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.northcoders.RecordShop.model.Album;
import com.northcoders.RecordShop.model.Genre;
import com.northcoders.RecordShop.service.RecordshopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class RecordshopControllerTest {

    @MockBean
    private RecordshopService recordshopService;

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
        Album album1 = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99).build();
        Album album2 = Album.builder().id(2L).name("name2").artist("artist2").genre(Genre.CLASSICAL).releaseDate(LocalDate.of(2010, 8, 22)).stockCount(2).price(29.99).build();
        albums.add(album1);
        albums.add(album2);
        given(recordshopService.getAllAlbums()).willReturn(albums);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(albums.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[0].releaseDate", is("2000-05-15")))
                .andExpect(jsonPath("$[1].releaseDate", is("2010-08-22")));

    }

    @Test
    @DisplayName("GET album by id positive")
    void test_getAlbumById_positive() throws Exception {
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(recordshopService.getAlbumById(1L)).willReturn(Optional.of(album));

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop/{id}", 1L));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(album.getName())))
                .andExpect(jsonPath("$.artist", is(album.getArtist())));
    }

    @Test
    @DisplayName("GET album by id negative")
    void test_getAlbumById_negative() throws Exception {
        // given
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();
        given(recordshopService.getAlbumById(1L)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/recordshop/{id}", 1L));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("POST album positive")
    void test_createAlbum_positive() throws Exception {
        Album album = Album.builder().id(1L).name("name").artist("artist").genre(Genre.BLUES).releaseDate(LocalDate.of(2000, 5, 15)).stockCount(1).price(19.99d).build();

        when(recordshopService.insertAlbum(any(Album.class))).thenReturn(album);

        this.mockMvc.perform(
                post("/api/v1/recordshop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(album)))
                .andExpect(status().isCreated());

        verify(recordshopService,times(1)).insertAlbum(any(Album.class));
    }
}
