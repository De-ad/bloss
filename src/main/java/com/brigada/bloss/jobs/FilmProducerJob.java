package com.brigada.bloss.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.jms.MapSenderJms;
import com.brigada.bloss.service.FilmService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilmProducerJob implements Job {

    @Autowired
    FilmService filmService;

    @Autowired
    MapSenderJms mapSenderJms;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        
        List<Film> films = (List<Film>) filmService.getFilms().getBody();

        for (Film film : films) {
            boolean needToSend = film.getUpdateTime().compareTo(film.getLastViewedTime()) > 0;

            if (needToSend) {
                mapSenderJms.send(film.toMap());
                filmService.jobViewedFilms(film.getId());
            }

        }

    }

}
