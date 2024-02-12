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

    private Integer id;
    private Integer userId;
    private Integer filmId;
    private String text;
    private Integer score;
    private Date date;
    
}
