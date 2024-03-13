package com.brigada.bloss.controller;

import com.brigada.bloss.listening.MessageResponse;
import com.brigada.bloss.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping()
    public ResponseEntity<Object> createReview(@RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal UserDetails userDetails) {
        return reviewService.createReview(reviewRequest, userDetails.getUsername());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping()
    public ResponseEntity<Object> editReview(@RequestBody Review review, @AuthenticationPrincipal UserDetails userDetails) {
        return reviewService.checkTarget(userDetails.getUsername(), review.getId())
                ? reviewService.editReview(review)
                : ResponseEntity.status(403).body(new MessageResponse("Access denied"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        return reviewService.checkTarget(userDetails.getUsername(), id)
                ? reviewService.deleteReview(id)
                : ResponseEntity.status(403).body(new MessageResponse("Access denied"));
    }

    @GetMapping("/raw")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getRawReviews() {
        return reviewService.getRawReviews();
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> approveReview(@PathVariable  Integer id) {
        return reviewService.approveReview(id);
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> rejectReview(@PathVariable  Integer id) {
        return reviewService.rejectReview(id);
    }


}
