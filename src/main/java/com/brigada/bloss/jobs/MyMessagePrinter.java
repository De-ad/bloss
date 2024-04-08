package com.brigada.bloss.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.brigada.bloss.entity.Review;
import com.brigada.bloss.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyMessagePrinter implements Job {

    @Autowired
    ReviewService reviewService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Review> reviews = (List<Review>) reviewService.getReviews().getBody();
        log.info("Reviews:");
        for (Review review : reviews) {
            log.info("------ " + review.getId() + " -> " + review.getText());
        }
    }
    
}
