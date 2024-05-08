package com.brigada.bloss.delegates.modifyFilm;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FilmFetcherModifyFilm implements JavaDelegate {

    @Autowired
    FilmService filmService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Film> films = filmService.getFilms();
        StringBuilder builder = new StringBuilder();
        for (Film film : films) {
            builder.append(film).append("\n").append("-------------------------").append("\n");
        }
        delegateExecution.setVariable("modify_film_all_films_textarea", builder.toString());
    }
}
