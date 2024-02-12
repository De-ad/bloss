package com.brigada.bloss.controller;

import com.brigada.bloss.model.Film;
import com.brigada.bloss.model.Review;
import com.brigada.bloss.services.FilmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.ok().body(new Film(1, "Super man", "supman"));
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<Object> getFilmInfo(@PathVariable("filmId") Integer filmId) {
        return ResponseEntity.ok().body(new Film(filmId, "Spider-Man", "pavuk"));
    }

    @GetMapping("/reviews/add")
    public ResponseEntity<Object> changeStatus(@RequestBody Review review) {
        return filmService.addReview(review);
    }


}
