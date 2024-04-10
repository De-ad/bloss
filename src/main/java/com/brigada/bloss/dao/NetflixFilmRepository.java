package com.brigada.bloss.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brigada.bloss.entity.NetflixFilm;

@Repository
public interface NetflixFilmRepository extends JpaRepository<NetflixFilm, Integer> {

}
