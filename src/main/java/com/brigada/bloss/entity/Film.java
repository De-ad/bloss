package com.brigada.bloss.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brigada.bloss.entity.util.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
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

    @Column(name = "update_time", nullable = false)
    private Timestamp updateTime;

    @Column(name = "last_viewed_time", nullable = false)
    private Timestamp lastViewedTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetFilm", fetch = FetchType.EAGER)
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
                .filter(review -> review.getStatus() == ReviewStatus.APPROVED)
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0d);

    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.id));
        map.put("name", this.name);
        map.put("description", this.description);
        map.put("average_score", String.valueOf(this.averageScore));
        return map;
    }

    public String __repr__(boolean moderator) {
        StringBuilder sB = new StringBuilder();
        sB.append(id).append(") ").append(name).append("\n\n").append("- 💬[").append(description).append("]").append("\n\n").append("- ⨏ = ").append(averageScore).append("\n\n").append("- 🔄 reviews summy: ").append(reviews.size()).append("\n\n");

        for (Review review: reviews) {
            if (!moderator && !review.getStatus().equals(ReviewStatus.APPROVED))
                continue;
            if (!review.getStatus().equals(ReviewStatus.APPROVED))
                sB.append("----> ").append(review.getScore()).append("️❌ : ").append(review.getText()).append("\n\n");
            else
                sB.append("----> ").append(review.getScore()).append("️⭐️ : ").append(review.getText()).append("\n\n");
        }

        return sB.toString();
    }

}
