package com.northcoders.RecordShop.model;

import jakarta.validation.constraints.NotBlank;

public class ArtistDTO {

    @NotBlank(message = "The name is required.")
    private String name;

    @NotBlank(message = "The nationality is required.")
    private String nationality;

    public @NotBlank(message = "The name is required.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name is required.") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The nationality is required.") String getNationality() {
        return nationality;
    }

    public void setNationality(@NotBlank(message = "The nationality is required.") String nationality) {
        this.nationality = nationality;
    }

    public ArtistDTO() {
    }

    public Artist toArtist() {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setNationality(nationality);
        return artist;
    }
}
