package com.brigada.bloss.controller;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.service.FilmService;

import jakarta.websocket.OnClose;

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

    public FilmController() {}

    @GetMapping()
    public ResponseEntity<Object> getFilms() {
        return ResponseEntity.ok().body(filmService.getFilms());
    }

    @PostMapping()
    public ResponseEntity<Object> createFilm(@RequestBody Film film) {
        return ResponseEntity.ok().body(filmService.createFilm(film));
    }

}
