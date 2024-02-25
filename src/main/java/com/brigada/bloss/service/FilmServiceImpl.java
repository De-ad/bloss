package com.brigada.bloss.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.entity.Film;
import com.brigada.bloss.listening.MessageResponse;

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
    public ResponseEntity<Object> getFilm(Integer filmId) {
        Optional<Film> optFilm = filmRepository.findById(filmId);
        if (!optFilm.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Film with id=" + filmId + " does not exists"));
        }
        return ResponseEntity.status(200).body(optFilm.get());
    }

    @Override
    public ResponseEntity<Object> createFilm(Film film) {
        film = filmRepository.save(film);
        return ResponseEntity.status(201).body(film);
    }

    @Override
    public ResponseEntity<Object> editFilm(Film filmRequest) {
        Optional<Film> optFilm = filmRepository.findById(filmRequest.getId());
        if (!optFilm.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Film with id=" + filmRequest.getId() + " does not exists"));
        }
        Film film = optFilm.get();
        film.setName(filmRequest.getName());
        film.setDescription(filmRequest.getDescription());
        filmRepository.save(film);
        return ResponseEntity.status(200).body(film);
    }

    @Override
    public ResponseEntity<Object> deleteFilm(Integer id) {
        filmRepository.deleteById(id);
        return ResponseEntity.status(204).body(null);
    }
}
