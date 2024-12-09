package com.northcoders.RecordShop.model;

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

    @Column(nullable = false)
    public String artist;

    @Enumerated
    public Genre genre;

    @Column(nullable = false)
    public LocalDate releaseDate;

    @Column(nullable = false)
    public Integer stockCount;

    @Column(nullable = false)
    public Double price;
}
