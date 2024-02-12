package com.brigada.bloss.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {
    private Integer score;
    private String text;
    private Date date;
    private Integer id;
    private Integer filmId;
    private Integer userId;
    
}
