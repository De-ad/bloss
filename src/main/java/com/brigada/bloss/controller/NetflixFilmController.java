package com.brigada.bloss.controller;

import com.brigada.bloss.entity.NetflixFilm;

import com.brigada.bloss.service.NetflixFilmService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/netflix-films")
@Slf4j
public class NetflixFilmController {

    @Autowired
    private NetflixFilmService filmService;

    @GetMapping()
    public Iterable<NetflixFilm> getFilms() {
        log.info("-> got GET at /netflix-films");
        return filmService.getFilms();
    }

}
