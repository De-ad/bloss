package com.brigada.bloss.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private String name;
    private String surname;
    private String id;
    private String login;
    private String password;
    
}
