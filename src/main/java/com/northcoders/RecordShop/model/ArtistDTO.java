package com.northcoders.RecordShop.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArtistDTO {

    @NotBlank(message = "The name is required.")
    private String name;

    @NotBlank(message = "The nationality is required.")
    private String nationality;

    public Artist toArtist(){
        return new Artist()
                .setName(name)
                .setNationality(nationality);
    }
}
