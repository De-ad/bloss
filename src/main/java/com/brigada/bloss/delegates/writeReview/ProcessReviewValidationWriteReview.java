package com.brigada.bloss.delegates.writeReview;

import com.brigada.bloss.entity.Review;
import com.brigada.bloss.entity.util.ReviewStatus;
import com.brigada.bloss.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessReviewValidationWriteReview implements JavaDelegate {

    @Autowired
    ReviewService reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer newReviewId = (Integer) delegateExecution.getVariable("new_review_id");
        String reviewStatus = delegateExecution.getVariable("write_review_a_r_selector").toString();
        String cause = delegateExecution.getVariable("rejection_cause_write_review_textarea").toString();
        if (reviewStatus.equals("approve")) {
            reviewService.approveReview(newReviewId, cause);
            delegateExecution.setVariable("approved", "принято");
        } else {
            reviewService.rejectReview(newReviewId, cause);
            delegateExecution.setVariable("approved", "отклонено");
        }
        delegateExecution.setVariable("review_validation_result_textarea", cause);
    }
}
