package com.avocado.ecomus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ColorNotFoundException extends RuntimeException{
    private String message;
}
