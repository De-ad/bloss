package com.brigada.bloss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brigada.bloss.listening.ReviewRequest;
import com.brigada.bloss.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<Object> getReviews() {
        return reviewService.getReviews();
    }

    @PostMapping()
    public ResponseEntity<Object> createReview(@RequestBody ReviewRequest revieRequest) {
        return reviewService.createReview(revieRequest);
    }

}
