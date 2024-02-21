package com.brigada.bloss.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.dao.ReviewRepository;
import com.brigada.bloss.dao.UserRepository;
import com.brigada.bloss.entity.Film;
import com.brigada.bloss.entity.Review;
import com.brigada.bloss.entity.User;
import com.brigada.bloss.listening.MessageResponse;
import com.brigada.bloss.listening.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public ResponseEntity<Object> getReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ResponseEntity.status(200).body(reviews);
    }

    @Override
    public ResponseEntity<Object> getReview(Integer reviewId) {   
        Review review = reviewRepository.findById(reviewId).get();
        return ResponseEntity.status(200).body(review);
    }

    @Override
    public ResponseEntity<Object> createReview(ReviewRequest reviewRequest) {
        
        Optional<User> opAuthor = userRepository.findById(reviewRequest.getAuthorId());
        if (!opAuthor.isPresent()) {
            return ResponseEntity.status(404).body(new MessageResponse("User with id=" + reviewRequest.getAuthorId() + " does not exists"));
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
        review.getTargetFilm().updateAverageScore();

        return ResponseEntity.status(201).body(review);
    }

    @Override
    public ResponseEntity<Object> editReview(ReviewRequest reviewRequest) {

        Optional<Review> optReview = reviewRepository.findById(reviewRequest.getAuthorId());
        Review review = optReview.get();

        review.setDate(new Date());
        review.setText(reviewRequest.getText());
        review.setScore(reviewRequest.getScore());
        // review.setStatus(Status.onreview);
        review = reviewRepository.save(review);
        review.getTargetFilm().updateAverageScore();

        return ResponseEntity.status(200).body(review);

    }

    @Override
    public ResponseEntity<Object> deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
        reviewRepository.findById(reviewId).get().getTargetFilm().updateAverageScore();
        return ResponseEntity.status(204).body(null);
    }
}
