package com.brigada.bloss.service;

import org.springframework.http.ResponseEntity;

import com.brigada.bloss.listening.ReviewRequest;

public interface ReviewService {

    public ResponseEntity<Object> getReviews();

    public ResponseEntity<Object> createReview(ReviewRequest review);

    public ResponseEntity<Object> editReview(ReviewRequest review);

    public ResponseEntity<Object> deleteReview(ReviewRequest review);
    
}
