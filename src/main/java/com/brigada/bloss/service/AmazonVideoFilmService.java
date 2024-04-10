package com.brigada.bloss.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.AmazonVideoFilmRepository;
import com.brigada.bloss.entity.AmazonVideoFilm;
import com.brigada.bloss.entity.RottenFilms;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AmazonVideoFilmService {

    @Autowired
    private AmazonVideoFilmRepository filmRepository;

    public Iterable<AmazonVideoFilm> getFilms() {
        log.info("--> reading amazon video films from db...");
        List<AmazonVideoFilm> films = filmRepository.findAll();
        return films;
    }

    public void processMappedData(Map<String, String> data) {
        AmazonVideoFilm nFilm = AmazonVideoFilm.fromMap(data);
            
            if (RottenFilms.getSet().contains(nFilm.getName())) {

                nFilm.setLastReceiveTime(Timestamp.valueOf(LocalDateTime.now()));
                filmRepository.save(nFilm);

            }

    }

}
