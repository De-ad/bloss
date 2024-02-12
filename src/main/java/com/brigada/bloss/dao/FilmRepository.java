package com.brigada.bloss.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brigada.bloss.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {

}
