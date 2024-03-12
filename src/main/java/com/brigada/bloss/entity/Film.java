package com.brigada.bloss.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "average_score", nullable = false)
    private Double averageScore = 0d;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetFilm")
    @JsonIgnore
    private List<Review> reviews;

    public void addReviewToFilm(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
        review.setTargetFilm(this);
    }

    public void updateAverageScore() {

        if (this.reviews == null) return;

        this.averageScore = this.reviews.stream()
                //  TODO .filter(review -> review.getStatus() == ReviewStatus.APPROVED)
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0d);

    }

}
