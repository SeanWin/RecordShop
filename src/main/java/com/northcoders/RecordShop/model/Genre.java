package com.northcoders.RecordShop.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Genre {
    ROCK,
    POP,
    JAZZ,
    CLASSICAL,
    HIPHOP,
    ELECTRONIC,
    COUNTRY,
    REGGAE,
    BLUES,
    OTHER;

    public static List<String> getAllowedValues() {
        return Arrays.stream(Genre.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
