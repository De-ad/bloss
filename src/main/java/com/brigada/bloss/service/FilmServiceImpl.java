package com.brigada.bloss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.entity.Film;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    
    
}
