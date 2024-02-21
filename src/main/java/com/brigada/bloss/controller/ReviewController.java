package com.brigada.bloss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @GetMapping()
    public ResponseEntity<Object> getReview(@RequestParam("id") Integer reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PostMapping()
    public ResponseEntity<Object> createReview(@RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReview(reviewRequest);
    }

    @PutMapping()
    public ResponseEntity<Object> editReview(@RequestBody ReviewRequest reviewRequest){
        return reviewService.editReview(reviewRequest);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteReview(@RequestParam("id") Integer reviewId){
        return reviewService.deleteReview(reviewId);
    }


}
