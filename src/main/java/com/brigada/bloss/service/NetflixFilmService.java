package com.brigada.bloss.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.NetflixFilmRepository;
import com.brigada.bloss.entity.NetflixFilm;
import com.brigada.bloss.entity.RottenFilms;

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

    public void processMappedData(Map<String, String> data) {
        NetflixFilm nFilm = NetflixFilm.fromMap(data);
            
            if (RottenFilms.getSet().contains(nFilm.getName())) {

                nFilm.setLastReceiveTime(Timestamp.valueOf(LocalDateTime.now()));
                filmRepository.save(nFilm);

            }

    }

}
