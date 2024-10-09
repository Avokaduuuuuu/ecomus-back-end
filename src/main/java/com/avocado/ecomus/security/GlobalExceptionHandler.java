package com.avocado.ecomus.security;

import com.avocado.ecomus.payload.resp.BaseResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BaseResp resp = new BaseResp();

        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            resp.setMsg(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        resp.setMsg("Validation Failed");
        resp.setCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentTypeMismatchException ex) {
        BaseResp resp = new BaseResp();

        String errorMessage = "Invalid value for parameter '" + ex.getName() + "': " + ex.getValue();
        resp.setMsg(errorMessage);
        resp.setCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
