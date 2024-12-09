package com.northcoders.RecordShop.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlbumDTO {

    @NotEmpty(message = "Name is required")
    public String name;

    @NotEmpty(message = "Artist is required")
    public String artist;

    @NotNull(message = "Genre is required")
    public Genre genre;

    @NotNull(message = "The release date is required.")
    @Past(message = "The release date must be in the past.")
    private LocalDate releaseDate;

    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count must be at least 0")
    public Integer stockCount;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    public Double price;

    public Album toAlbum(){
        return new Album()
                .setName(name)
                .setArtist(artist)
                .setGenre(genre)
                .setReleaseDate(releaseDate)
                .setStockCount(stockCount)
                .setPrice(price);
    }
}
