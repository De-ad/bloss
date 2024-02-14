package com.brigada.bloss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.ReviewRepository;
import com.brigada.bloss.entity.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ResponseEntity<Object> getReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.status(200).body(reviews);
    }

    @Override
    public ResponseEntity<Object> createReview(Review review) {
        review = reviewRepository.save(review);
        return ResponseEntity.status(201).body(review);
    }

    

}
