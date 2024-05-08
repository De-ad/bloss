package com.brigada.bloss.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.brigada.bloss.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.brigada.bloss.dao.FilmRepository;
import com.brigada.bloss.dao.ReviewRepository;
import com.brigada.bloss.entity.Review;
import com.brigada.bloss.entity.util.ReviewStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;


    @Autowired
    @Qualifier("transactionTemplateRepeatableRead")
    private TransactionTemplate repeatableReadTransactionTemplate;
    @Autowired
    @Qualifier("transactionTemplateReadCommitted")
    private TransactionTemplate readOnlyTransactionTemplate;

    public List<Review> getRawReviews() {
        log.info("--> getting all reviews (including raw)...");
        return readOnlyTransactionTemplate.execute(status -> reviewRepository.findAll());
    }

    public List<Review> getReviews() {
        log.info("--> getting all reviews (excluding raw)...");
        return readOnlyTransactionTemplate.execute(status -> {
            List<Review> reviews = reviewRepository.findAll();
            return reviews.stream().filter(r -> r.getStatus().equals(ReviewStatus.APPROVED)).toList();
        });
    }

    public Review getReview(Integer id) {
        log.info("--> getting review with id=" + id + "...");
        return readOnlyTransactionTemplate.execute(status -> {
            Optional<Review> optReview = reviewRepository.findById(id);
            return optReview.get();
        });
    }

    public Integer createReview(Integer targetFilmId, String reviewText, Integer rating, String userId) {
        log.info("--> creating review to film with id=" + targetFilmId + " and author='" + userId + "'...");
        return repeatableReadTransactionTemplate.execute(status -> {
            Film film = filmRepository.findById(targetFilmId).get();
            Review review = new Review();
            review.setAuthorUserId(userId);
            review.setTargetFilm(film);
            review.setText(reviewText);
            review.setScore(rating);
            return reviewRepository.save(review).getId();
        });
    }

    public String approveReview(Integer id, final String cause) {
        log.info("--> approving review with id=" + id + "...");
        return repeatableReadTransactionTemplate.execute(status -> {
            Review review = reviewRepository.findById(id).get();
            review.setStatus(ReviewStatus.APPROVED);
            review = reviewRepository.save(review);
            filmService.updateAverageScore(review.getTargetFilm().getId());
            return cause;
        });
    }

    public String rejectReview(Integer id, final String cause) {
        log.info("--> rejecting review with id=" + id + "...");
        return repeatableReadTransactionTemplate.execute(status -> {
            Review review = reviewRepository.findById(id).get();
            review.setStatus(ReviewStatus.REJECTED);
            reviewRepository.save(review);
            return cause;
        });

    }

}

