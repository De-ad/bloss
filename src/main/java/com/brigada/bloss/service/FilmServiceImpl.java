package com.brigada.bloss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.entity.Film;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public ResponseEntity<Object> getFilms() {
        List<Film> films = filmRepository.findAll();
        return ResponseEntity.status(200).body(films);
    }

    @Override
    public ResponseEntity<Object> createFilm(Film film) {
        film = filmRepository.save(film);
        return ResponseEntity.status(201).body(film);
    }

    
    
}
