package com.avocado.ecomus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandNotFoundException extends RuntimeException {
    private String message;
}
