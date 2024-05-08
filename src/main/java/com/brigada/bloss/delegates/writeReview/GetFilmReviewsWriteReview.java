package com.brigada.bloss.delegates.writeReview;

import com.brigada.bloss.entity.Film;
import com.brigada.bloss.entity.Review;
import com.brigada.bloss.service.FilmService;
import com.brigada.bloss.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GetFilmReviewsWriteReview implements JavaDelegate {

    @Autowired
    ReviewService reviewService;
    @Autowired
    FilmService filmService;
    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userId = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();
        if (userId.equals("guest")) {
            runtimeService.deleteProcessInstance(delegateExecution.getProcessInstanceId(), "Guest cannot write reviews!");
            return;
        }
        Integer filmId = (Integer) delegateExecution.getVariable("film_id_write_review_number");
        Film targetFilm = filmService.getFilm(filmId);
        if (targetFilm == null) {
            runtimeService.deleteProcessInstance(delegateExecution.getProcessInstanceId(), "There is no film with id="+filmId);
            return;
        }

        delegateExecution.setVariable("target_film_id", targetFilm.getId());
        delegateExecution.setVariable("film_name", targetFilm.getName());

        List<Review> reviews = reviewService.getReviews();
        StringBuilder stringBuilder = new StringBuilder();
        for (Review review : reviews) {
            if (review.getTargetFilm().getId().equals(filmId)) {
                stringBuilder.append(review).append("\n").append("-------------------------").append("\n");
            }
        }
        delegateExecution.setVariable("reviews_write_review_textarea", stringBuilder.toString());
    }
}
