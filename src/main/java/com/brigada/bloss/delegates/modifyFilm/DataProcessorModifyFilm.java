package com.brigada.bloss.delegates.modifyFilm;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class DataProcessorModifyFilm implements JavaDelegate {

    @Autowired
    FilmService filmService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> groups = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getGroupIds();
        if (!groups.contains("moderators") && !groups.contains("camunda-admin")) {
            delegateExecution.setVariable("changed_status", "не изменили");
            delegateExecution.setVariable("changed_film_textarea", "Вам отказано в доступе, вы не админ и не модератор! Не тратьте ресурсы сервера пожалуйста!");
            return;
        }
        Integer filmId = (Integer) delegateExecution.getVariable("modify_film_id_number");
        String filmName = delegateExecution.getVariable("modify_film_textfield").toString();
        String filmDescription = delegateExecution.getVariable("modify_film_film_description_textarea").toString();
        String operation = delegateExecution.getVariable("modify_film_selector_operation") == null ? null : delegateExecution.getVariable("modify_film_selector_operation").toString();

        if (operation == null) {
            delegateExecution.setVariable("changed_status", "не изменили");
            delegateExecution.setVariable("changed_film_textarea", "Зачем тогда запустили процесс? Не тратьте ресурсы сервера пожалуйста!");
            return;
        }

        Film film = null;
        switch (Objects.requireNonNull(operation)) {
            case "create":
                delegateExecution.setVariable("changed_status", "создали");
                film = new Film();
                film.setName(filmName);
                film.setDescription(filmDescription);
                delegateExecution.setVariable("changed_film_textarea", filmService.createFilm(film).toString());
                break;
            case "update":
                film = new Film();
                film.setId(filmId);
                film.setName(filmName);
                film.setDescription(filmDescription);
                film = filmService.editFilm(film);
                if (film == null) {
                    delegateExecution.setVariable("changed_status", "попробовали изменить");
                    delegateExecution.setVariable("changed_film_textarea", "Такого фильма нет!");
                } else {
                    delegateExecution.setVariable("changed_status", "изменили");
                    delegateExecution.setVariable("changed_film_textarea", film.toString());
                }
                break;
            case "delete":
                delegateExecution.setVariable("changed_status", "удалили");
                delegateExecution.setVariable("changed_film_textarea", filmService.deleteFilm(filmId).toString());
                break;
        }
    }
}
