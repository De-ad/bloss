package com.brigada.bloss.controller;

import com.brigada.bloss.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brigada.bloss.entity.Review;
import com.brigada.bloss.listening.ReviewRequest;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<Object> getReviews() {
        return reviewService.getReviews();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReview(@PathVariable Integer id) {
        return reviewService.getReview(id);
    }

    @PostMapping()
    public ResponseEntity<Object> createReview(@RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReview(reviewRequest);
    }

    @PutMapping()
    public ResponseEntity<Object> editReview(@RequestBody Review review){
        return reviewService.editReview(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable Integer id){
        return reviewService.deleteReview(id);
    }


}
