package com.brigada.bloss.controller;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.service.FilmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping()
    public ResponseEntity<Object> getFilms() {
        return filmService.getFilms();
    }

    @PostMapping()
    public ResponseEntity<Object> createFilm(@RequestBody Film film) {
        return filmService.createFilm(film);
    }

}
