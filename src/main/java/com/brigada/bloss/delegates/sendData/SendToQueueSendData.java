package com.brigada.bloss.delegates.sendData;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.jms.Sender;
import com.brigada.bloss.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SendToQueueSendData implements JavaDelegate {

    @Autowired
    FilmService filmService;
    @Autowired
    Sender sender;
    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Film> films = (List<Film>) delegateExecution.getVariable("films_to_be_send");
        if (films.isEmpty()) {
            log.info("There is nothing we can do. Sleep 10 sec...");
            runtimeService.deleteProcessInstance(delegateExecution.getProcessInstanceId(), "There is no changed films...");
            return;
        }
        for (Film film: films) {
            log.info("Sending film...");
            sender.send(film.toMap());
            filmService.jobViewedFilms(film.getId());
        }

    }

}
