package com.brigada.bloss.service;

import org.springframework.http.ResponseEntity;

import com.brigada.bloss.entity.Review;
import com.brigada.bloss.listening.ReviewRequest;

public interface ReviewService {

    public ResponseEntity<Object> getReviews();
    
    public ResponseEntity<Object> getReview(Integer id);

    public ResponseEntity<Object> createReview(ReviewRequest review);

    public ResponseEntity<Object> editReview(Review review);

    public ResponseEntity<Object> deleteReview(Integer id);
    
}
