package com.avocado.ecomus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class FileBrokenException extends RuntimeException{
    private String message;
}
