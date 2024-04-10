package com.brigada.bloss.entity;

import java.sql.Timestamp;
import java.util.Map;

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
@Table(name = "netflix_films")
public class NetflixFilm {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "average_score")
    private Double averageScore = 0d;

    @Column(name = "last_receive_time")
    private Timestamp lastReceiveTime;

    public static NetflixFilm fromMap(Map<String, String> data) {
        System.out.println(data);
        return new NetflixFilm(
            Integer.parseInt(data.get("id")),
            data.get("name"),
            data.get("description"),
            Double.parseDouble(data.get("average_score")),
            null
        );
    }

}
