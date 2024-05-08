package com.brigada.bloss.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.entity.Film;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    @Qualifier("transactionTemplateRepeatableRead")
    private TransactionTemplate repeatableReadTransactionTemplate;
    @Autowired
    @Qualifier("transactionTemplateReadCommitted")
    private TransactionTemplate readOnlyTransactionTemplate;
    @Autowired
    @Qualifier("transactionTemplateRepeatableReadSupports")
    private TransactionTemplate supportingRepeatableReadTemplate;

    public List<Film> getFilms() {
        log.info("--> reading films from db...");
        return readOnlyTransactionTemplate.execute(status -> filmRepository.findAll());
    }

    public Film getFilm(Integer filmId) {
        log.info("--> reading film with id=" + filmId + " from db...");
        return readOnlyTransactionTemplate.execute(status -> {
            return filmRepository.findById(filmId).get();
        });
    }

    public Film createFilm(final Film film) {
        log.info("--> creatig film with name='" + film.getName() + "'...");
        return repeatableReadTransactionTemplate.execute(status -> {
            film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            film.setLastViewedTime(Timestamp.valueOf(LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.MIN)));
            return filmRepository.save(film);
        });
    }

    public Film editFilm(final Film filmRequest) {
        log.info("--> editing film with name='" + filmRequest.getName() + "'...");
        return repeatableReadTransactionTemplate.execute(status -> {
            Optional<Film> optFilm = filmRepository.findById(filmRequest.getId());
            if (!optFilm.isPresent()) {
                return null;
            }
            Film film = optFilm.get();
            film.setName(filmRequest.getName());
            film.setDescription(filmRequest.getDescription());
            film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            return filmRepository.save(film);
        });

    }

    public Film deleteFilm(Integer id) {
        log.info("--> deleting film with id=" + id + "...");
        return repeatableReadTransactionTemplate.execute(status -> {
            Film deletedFilm = filmRepository.getReferenceById(id);
            filmRepository.deleteById(id);
            return deletedFilm;
        });
    }

    public Film updateAverageScore(Integer filmId) {
        log.info("--> updating avg score for film with id=" + filmId + "...");
        return supportingRepeatableReadTemplate.execute(status -> {
            Optional<Film> optFilm = filmRepository.findById(filmId);
            Film film = optFilm.get();
            film.updateAverageScore();
            film.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            return filmRepository.save(film);
        });
    }

    public void jobViewedFilms(Integer filmId) {
        log.info("--> updating last viewed time for film with id=" + filmId + "...");
        readOnlyTransactionTemplate.execute(status -> {
            Optional<Film> optFilm = filmRepository.findById(filmId);
            Film film = optFilm.get();
            film.setLastViewedTime(Timestamp.valueOf(LocalDateTime.now()));
            return filmRepository.save(film);
        });
    }

}
