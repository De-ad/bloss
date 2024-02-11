package com.brigada.bloss.controller;

import com.brigada.bloss.model.Film;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    public FilmController() {
        films.add(new Film("Чевовек павук", "Фильм про чевовека павука"));
        films.add(new Film("Женщина кошка", "Фильм про женщину кошку"));
        films.add(new Film("Бэтмон", "Фильм про бэтмона"));
        films.add(new Film("Супермэн", "Фильм про супермэна"));
    }

    @GetMapping("/films")
    Iterable<Film> getFilms() {
        return films;
    }


}
