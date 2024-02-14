package com.brigada.bloss.service;

import org.springframework.http.ResponseEntity;

import com.brigada.bloss.entity.Review;

public interface ReviewService {

    public ResponseEntity<Object> getReviews();

    public ResponseEntity<Object> createReview(Review review);
    
}
