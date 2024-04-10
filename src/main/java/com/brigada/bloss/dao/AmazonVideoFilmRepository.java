package com.brigada.bloss.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brigada.bloss.entity.AmazonVideoFilm;

@Repository
public interface AmazonVideoFilmRepository extends JpaRepository<AmazonVideoFilm, Integer> {

}
