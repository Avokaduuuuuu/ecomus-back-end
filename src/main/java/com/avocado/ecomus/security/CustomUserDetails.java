package com.avocado.ecomus.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@Getter
public class CustomUserDetails {
    private int userId;
    private String password;


    public int getUserId() {
        return userId;
    }


    public String getPassword() {
        return password;
    }

}
