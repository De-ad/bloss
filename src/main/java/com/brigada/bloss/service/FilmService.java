package com.brigada.bloss.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.entity.Film;
import com.brigada.bloss.listening.MessageResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public ResponseEntity<Object> getFilms() {
        log.info("--> reading films from db...");
        List<Film> films = filmRepository.findAll();
        return ResponseEntity.status(200).body(films);
    }

    public ResponseEntity<Object> getFilm(Integer filmId) {
        log.info("--> reading film with id=" + filmId + " from db...");
        Optional<Film> optFilm = filmRepository.findById(filmId);
        if (!optFilm.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Film with id=" + filmId + " does not exists"));
        }
        return ResponseEntity.status(200).body(optFilm.get());
    }

    public ResponseEntity<Object> createFilm(Film film) {
        log.info("--> creatig film with name='" + film.getName() + "'...");
        film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        film.setLastViewedTime(Timestamp.valueOf(LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.MIN)));
        film = filmRepository.save(film);
        return ResponseEntity.status(201).body(film);
    }

    public ResponseEntity<Object> editFilm(Film filmRequest) {
        log.info("--> editing film with name='" + filmRequest.getName() + "'...");
        Optional<Film> optFilm = filmRepository.findById(filmRequest.getId());
        if (!optFilm.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Film with id=" + filmRequest.getId() + " does not exists"));
        }
        Film film = optFilm.get();
        film.setName(filmRequest.getName());
        film.setDescription(filmRequest.getDescription());
        film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        filmRepository.save(film);
        return ResponseEntity.status(200).body(film);
    }

    public ResponseEntity<Object> deleteFilm(Integer id) {
        log.info("--> deleting film with id=" + id + "...");
        filmRepository.deleteById(id);
        return ResponseEntity.status(204).body(null);
    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public Film updateAverageScore(Integer filmId) {
        log.info("--> updating avg score for film with id=" + filmId + "...");
        Optional<Film> optFilm = filmRepository.findById(filmId);
        Film film = optFilm.get();
        film.updateAverageScore();
        film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        return filmRepository.save(film);
    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public void jobViewedFilms(Integer filmId) {
        log.info("--> updating last viewed time for film with id=" + filmId + "...");
        Optional<Film> optFilm = filmRepository.findById(filmId);
        Film film = optFilm.get();
        film.setLastViewedTime(Timestamp.valueOf(LocalDateTime.now()));
        filmRepository.save(film);
    }

}
