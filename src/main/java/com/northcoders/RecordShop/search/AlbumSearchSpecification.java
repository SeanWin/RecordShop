package com.northcoders.RecordShop.search;

import com.northcoders.RecordShop.model.Album;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AlbumSearchSpecification implements Specification<Album> {

    private final AlbumSearchParameters params;

    public AlbumSearchSpecification(AlbumSearchParameters params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Album> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (params.getArtistName() != null) {
            predicates.add( builder.like(builder.lower(root.get("artist").get("name")),
                    "%" + params.getArtistName().toLowerCase() + "%"));
        }
        if (params.getReleaseYear() != null) {
            predicates.add(builder.equal(builder.function("YEAR", Integer.class, root.get("releaseDate")), params.getReleaseYear()));
        }
        if (params.getGenre() != null) {
            predicates.add(builder.equal(root.get("genre"), params.getGenre()));
        }
        if (params.getAlbumName() != null) {
            predicates.add(builder.like(builder.lower(root.get("name")),
                    "%" + params.getAlbumName().toLowerCase() + "%"));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}