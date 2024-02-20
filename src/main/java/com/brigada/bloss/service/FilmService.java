package com.brigada.bloss.service;

import org.springframework.http.ResponseEntity;

import com.brigada.bloss.entity.Film;

public interface FilmService {
    
    public ResponseEntity<Object> getFilms();

    public ResponseEntity<Object> createFilm(Film film);

}
