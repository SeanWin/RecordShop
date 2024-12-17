package com.northcoders.RecordShop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;


@Table(name="albums")
@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    @JsonManagedReference
    private Artist artist;

    @Enumerated
    public Genre genre;

    @Column(nullable = false)
    public LocalDate releaseDate;

    @Column(nullable = false)
    public Integer stockCount;

    @Column(nullable = false)
    public Double price;
}
