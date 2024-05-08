package com.brigada.bloss.delegates.sendData;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.jms.Sender;
import com.brigada.bloss.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FilmDataFetcherSendData implements JavaDelegate {

    @Autowired
    FilmService filmService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Fetching data...");
        List<Film> films = filmService.getFilms();
        List<Film> toBeSend = new ArrayList<>();
        for (Film film : films) {
            boolean needToSend = film.getUpdateTime().compareTo(film.getLastViewedTime()) > 0;
            if (needToSend) {
                toBeSend.add(film);
            }
        }

        delegateExecution.setVariable("films_to_be_send", toBeSend);

    }

}
