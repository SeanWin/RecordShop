package com.northcoders.RecordShop.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AlbumDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @Valid
    @NotNull(message = "Artist is required")
    private ArtistDTO artist;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "The release date is required.")
    @Past(message = "The release date must be in the past.")
    private LocalDate releaseDate;

    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count must be at least 0")
    private Integer stockCount;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is required") String name) {
        this.name = name;
    }

    public @Valid @NotNull(message = "Artist is required") ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(@Valid @NotNull(message = "Artist is required") ArtistDTO artist) {
        this.artist = artist;
    }

    public @NotNull(message = "Genre is required") Genre getGenre() {
        return genre;
    }

    public void setGenre(@NotNull(message = "Genre is required") Genre genre) {
        this.genre = genre;
    }

    public @NotNull(message = "The release date is required.") @Past(message = "The release date must be in the past.") LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@NotNull(message = "The release date is required.") @Past(message = "The release date must be in the past.") LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public @NotNull(message = "Stock count is required") @Min(value = 0, message = "Stock count must be at least 0") Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(@NotNull(message = "Stock count is required") @Min(value = 0, message = "Stock count must be at least 0") Integer stockCount) {
        this.stockCount = stockCount;
    }

    public @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") Double price) {
        this.price = price;
    }

    public AlbumDTO() {
    }

    public AlbumDTO(String name, ArtistDTO artist, Genre genre, LocalDate releaseDate, Integer stockCount, Double price) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.stockCount = stockCount;
        this.price = price;
    }

    public Album toAlbum() {
        Album album = new Album();
        album.setName(name);
        album.setArtist(artist.toArtist());
        album.setGenre(genre);
        album.setReleaseDate(releaseDate);
        album.setStockCount(stockCount);
        album.setPrice(price);
        return album;
    }

    public static class Builder {
        private String name;
        private ArtistDTO artist;
        private Genre genre;
        private LocalDate releaseDate;
        private Integer stockCount;
        private Double price;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setArtist(ArtistDTO artist) {
            this.artist = artist;
            return this;
        }

        public Builder setGenre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder setReleaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setStockCount(Integer stockCount) {
            this.stockCount = stockCount;
            return this;
        }

        public Builder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public AlbumDTO build() {
            return new AlbumDTO(name, artist, genre, releaseDate, stockCount, price);
        }
    }
}
