package com.brigada.bloss.listening;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsResponse {

    private Integer id;
    private String username;
    private String token;
    
}
