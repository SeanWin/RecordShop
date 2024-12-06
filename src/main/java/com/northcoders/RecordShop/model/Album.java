package com.northcoders.RecordShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    public Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    public String name;

    @Column(nullable = false)
    @NotBlank(message = "Artist is required")
    public String artist;

    @Column(nullable = false)
    @NotNull(message = "Genre is required")
    @Enumerated(EnumType.STRING)
    public Genre genre;

    @Column(nullable = false)
    @NotNull(message = "Release date is required")
    public LocalDate releaseDate;

    @Column(nullable = false)
    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count must be at least 0")
    public Integer stockCount;;

    @Column(nullable = false)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    public Double price;
}
