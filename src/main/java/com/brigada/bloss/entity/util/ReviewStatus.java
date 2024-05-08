package com.brigada.bloss.entity.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public enum ReviewStatus implements Serializable {

    APPROVED("approved"),

    ON_REVIEW("on_review"),

    REJECTED("rejected");

    private final String title;

}
