package com.brigada.bloss.entity.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Roles {

    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", 0),

    ROLE_ADMIN("ROLE_ADMIN", 1),

    ROLE_USER("ROLE_USER", 2);

    private final String title;
    private final Integer value;

}
