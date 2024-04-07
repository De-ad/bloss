package com.brigada.bloss.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.brigada.bloss.entity.Role;
import com.brigada.bloss.entity.util.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.dao.ReviewRepository;
import com.brigada.bloss.dao.UserRepository;
import com.brigada.bloss.entity.Film;
import com.brigada.bloss.entity.Review;
import com.brigada.bloss.entity.User;
import com.brigada.bloss.entity.util.ReviewStatus;
import com.brigada.bloss.listening.MessageResponse;
import com.brigada.bloss.listening.ReviewRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    public ResponseEntity<Object> getRawReviews() {
        log.info("--> getting all reviews (including raw)...");
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.status(200).body(reviews);
    }

    public ResponseEntity<Object> getReviews() {
        log.info("--> getting all reviews (excluding raw)...");
        List<Review> reviews = reviewRepository.findAll();
        reviews = reviews.stream().filter(r -> r.getStatus().equals(ReviewStatus.APPROVED)).toList();
        return ResponseEntity.status(200).body(reviews);
    }

    public ResponseEntity<Object> getReview(Integer id) {
        log.info("--> getting review with id=" + id + "...");
        Optional<Review> optReview = reviewRepository.findById(id);
        if (!optReview.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Review with id=" + id + " does not exists"));
        }
        Review review = optReview.get();
        if (review.getStatus().equals(ReviewStatus.APPROVED)) {
            return ResponseEntity.status(200).body(review);
        } else {
            return ResponseEntity.status(404).body("Requested review cannot be obtained due to status: " + review.getStatus());
        }

    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> createReview(ReviewRequest reviewRequest, String username) {
        log.info("--> creating review to film with id=" + reviewRequest.getFilmId() + " and author='" + username + "'...");
        Optional<User> opAuthor = userRepository.findByUsername(username);
        if (!opAuthor.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("User with username=" + username + " does not exists"));
        }
        User author = opAuthor.get();

        Optional<Film> opFilm = filmRepository.findById(reviewRequest.getFilmId());
        if (!opFilm.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("Film with id=" + reviewRequest.getFilmId() + " does not exists"));
        }
        Film film = opFilm.get();

        Review review = new Review();
        review.setAuthor(author);
        review.setTargetFilm(film);
        review.setText(reviewRequest.getText());
        review.setScore(reviewRequest.getScore());

        review = reviewRepository.save(review);

        return ResponseEntity.status(201).body(review);
    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> editReview(Review editedReview) {
        log.info("--> editing review with id=" + editedReview.getId() + "...");
        Optional<Review> optReview = reviewRepository.findById(editedReview.getId());

        if (optReview.isEmpty()) {
            return ResponseEntity.status(404).body(new MessageResponse("Review with id=" + editedReview.getId() + " does not exists"));
        }

        Review review = optReview.get();

        review.setDate(new Date());
        review.setText(editedReview.getText());
        review.setScore(editedReview.getScore());
        review.setStatus(ReviewStatus.ON_REVIEW);
        review = reviewRepository.save(review);

        filmService.updateAverageScore(review.getTargetFilm().getId());

        return ResponseEntity.status(200).body(reviewRepository.findById(editedReview.getId()).get());

    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> deleteReview(Integer id) {
        log.info("--> deleting review with id=" + id + "...");
        Optional<Review> optReview = reviewRepository.findById(id);

        if (optReview.isEmpty()) {
            return ResponseEntity.status(404).body(new MessageResponse("Review with id=" + id + " does not exists"));
        }

        Review review = optReview.get();

        int filmId = review.getTargetFilm().getId();

        reviewRepository.deleteById(id);

        filmService.updateAverageScore(filmId);

        return ResponseEntity.status(204).body(null);
    }

    @Transactional(transactionManager = "blossTransactionManager", propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> approveReview(Integer id) {
        log.info("--> approving review with id=" + id + "...");
        Optional<Review> optReview = reviewRepository.findById(id);

        if (optReview.isEmpty()) {
            return ResponseEntity.status(404).body(new MessageResponse("Review with id=" + id + " does not exists"));
        }

        Review review = optReview.get();
        review.setStatus(ReviewStatus.APPROVED);
        review = reviewRepository.save(review);

        filmService.updateAverageScore(review.getTargetFilm().getId());

        return ResponseEntity.status(200).body(reviewRepository.findById(id).get());
    }

    public ResponseEntity<Object> rejectReview(Integer id) {
        log.info("--> rejecting review with id=" + id + "...");
        Optional<Review> optReview = reviewRepository.findById(id);

        if (optReview.isEmpty()) {
            return ResponseEntity.status(404).body(new MessageResponse("Review with id=" + id + " does not exists"));
        }

        Review review = optReview.get();
        review.setStatus(ReviewStatus.REJECTED);
        review = reviewRepository.save(review);

        return ResponseEntity.status(200).body(review);
    }

    public boolean checkTarget(String username, Integer requestedReviewId) {
        log.info("--> checking that user with username='" + username + "'  is equal to reviewer...");
        Optional<Review> optReview = reviewRepository.findById(requestedReviewId);
        if (optReview.isEmpty()) {
            return false;
        }
        Review review = optReview.get();

        if (username.equals(review.getAuthor().getUsername())) {
            return true;
        }

        for (Role role : userRepository.findByUsername(username).get().getRoles()) {
            if (role.getName().equals(Roles.ROLE_ADMIN.getTitle()) || role.getName().equals(Roles.ROLE_SUPER_ADMIN.getTitle())) {
                return true;
            }
        }

        return false;

    }

}
