package com.enigma.warungmakanbahari.controller;

import com.enigma.warungmakanbahari.model.response.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .message(e.getReason())
                .build();

        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();;

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
