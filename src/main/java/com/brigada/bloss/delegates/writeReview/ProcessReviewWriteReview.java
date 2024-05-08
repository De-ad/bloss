package com.brigada.bloss.delegates.writeReview;

import com.brigada.bloss.entity.Review;
import com.brigada.bloss.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessReviewWriteReview implements JavaDelegate {

    @Autowired
    ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer filmId = (Integer) delegateExecution.getVariable("target_film_id");
        String reviewText = delegateExecution.getVariable("review_text_create_review_textarea").toString();
        Integer rating = (Integer) delegateExecution.getVariable("review_rating_create_review_number");
        Integer reviewId = reviewService.createReview(filmId, reviewText, rating, delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId());

        delegateExecution.setVariable("new_review_id", reviewId);
        delegateExecution.setVariable("a_r_write_review_textarea", reviewService.getReview(reviewId).toString());
    }
}
