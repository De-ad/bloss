package com.brigada.bloss.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brigada.bloss.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

}
