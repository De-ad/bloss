package com.brigada.bloss.entity;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum RottenFilms {

    BATMAN("Бэтмен"),

    SUPERMAN("Супермэн"),

    AQUAMAN("Аквамен");

    private final String title;

    public static Set<String> getSet() {
        return Arrays
        .stream(RottenFilms.values())
        .map(RottenFilms::getTitle)
        .collect(Collectors.toSet());
    }

}
