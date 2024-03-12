package com.brigada.bloss.listening;

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
public class ReviewRequest {

    private Integer authorId;
    private Integer filmId;
    private String text;
    private Integer score;

}
