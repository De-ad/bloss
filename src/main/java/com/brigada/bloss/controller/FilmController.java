package com.brigada.bloss.controller;

import com.brigada.bloss.entity.Film;

import com.brigada.bloss.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFilm(@PathVariable Integer id) {
        return filmService.getFilm(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Object> createFilm(@RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<Object> editFilm(@RequestBody Film film) {
        return filmService.editFilm(film);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable Integer id) {
        return filmService.deleteFilm(id);
    }

}
