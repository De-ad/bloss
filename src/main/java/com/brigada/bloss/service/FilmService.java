package com.brigada.bloss.service;

import java.util.List;

import com.brigada.bloss.entity.Film;

public interface FilmService {
    
    public List<Film> getFilms();

    public Film createFilm(Film film);

}
