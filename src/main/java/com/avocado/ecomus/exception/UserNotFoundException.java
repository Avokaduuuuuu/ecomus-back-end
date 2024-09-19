package com.avocado.ecomus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private String mess;
}
