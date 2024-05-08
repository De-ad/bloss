package com.brigada.bloss.delegates.viewFilms;

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
public class AllFilmsSneakyFetcher implements JavaDelegate {

    @Autowired
    FilmService filmService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Film> films = filmService.getFilms();
        StringBuilder sB = new StringBuilder();

        List<String> groups = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getGroupIds();
        boolean moderator = groups.contains("camunda-admin") || groups.contains("moderators");

        for (Film film: films) {
            sB.append(film.__repr__(moderator)).append("〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️").append("\n");
        }

        delegateExecution.setVariable("film_viewer_textarea", sB.toString());
    }
}
