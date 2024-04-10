package com.brigada.bloss.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.service.FilmService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilmProducerJob implements Job {

    @Autowired
    FilmService filmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Film> films = (List<Film>) filmService.getFilms().getBody();

        log.info("Films:");
        for (Film film : films) {
            boolean needToSend = film.getUpdateTime().compareTo(film.getLastViewedTime()) > 0;

            if (needToSend) {
                log.info("Вот это отправлю:");
            } else {
                log.info("Не отправлю:");
            }

            log.info("------> " + film.getId());
            log.info("------> " + film.getName());
            log.info("------> " + film.getDescription());
            log.info("------> " + film.getAverageScore());
            log.info("------> " + film.getUpdateTime());
            log.info("------> " + film.getLastViewedTime());

            if (needToSend) {
                log.info("Отправляю...");
                filmService.jobViewedFilms(film.getId());
            }

        }

    }

}
