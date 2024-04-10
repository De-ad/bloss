package com.brigada.bloss.controller;

import com.brigada.bloss.entity.AmazonVideoFilm;

import com.brigada.bloss.service.AmazonVideoFilmService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amazon-video-films")
@Slf4j
public class AmazonVideoFilmController {

    @Autowired
    private AmazonVideoFilmService filmService;

    @GetMapping()
    public Iterable<AmazonVideoFilm> getFilms() {
        log.info("-> got GET at /amazon-video-films");
        return filmService.getFilms();
    }

}
