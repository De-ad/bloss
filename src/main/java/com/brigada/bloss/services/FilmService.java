package com.brigada.bloss.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brigada.bloss.model.Review;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FilmService {
    
    public ResponseEntity<Object> getFilmInfo(Integer filmId) {
        return null;
    }

    public ResponseEntity<Object> getFilms() {
        return null;
    }

    public ResponseEntity<Object> addReview(Review review) {
        return null;
    }

}
