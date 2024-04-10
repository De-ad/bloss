package com.brigada.bloss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.NetflixFilmRepository;
import com.brigada.bloss.entity.NetflixFilm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NetflixFilmService {

    @Autowired
    private NetflixFilmRepository filmRepository;

    public Iterable<NetflixFilm> getFilms() {
        log.info("--> reading netflix films from db...");
        List<NetflixFilm> films = filmRepository.findAll();
        return films;
    }

}
