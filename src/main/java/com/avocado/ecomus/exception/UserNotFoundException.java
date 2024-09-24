package com.avocado.ecomus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private String message;
}
