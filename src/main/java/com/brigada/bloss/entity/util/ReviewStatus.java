package com.brigada.bloss.entity.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum ReviewStatus {
    
    APPROVED("approved"),

    ON_REVIEW("on_review"),

    REJECTED("rejected");

    private final String title;

}
