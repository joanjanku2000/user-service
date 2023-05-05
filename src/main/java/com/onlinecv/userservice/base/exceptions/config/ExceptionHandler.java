package com.onlinecv.userservice.base.exceptions.config;

import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.base.exceptions.model.ErrorFormat;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorFormat> handleNotFoundEexception(HttpServletRequest httpServletRequest, NotFoundException e) {
        ErrorFormat errorFormat = null;
        try {
            errorFormat = new ErrorFormat(e.getMessage(), LocalDateTime.now(), httpServletRequest.getRequestURI(), extractBytesToString(httpServletRequest.getInputStream().readAllBytes()));
        } catch (IOException ex) {
            errorFormat = new ErrorFormat(e.getMessage(), LocalDateTime.now(), httpServletRequest.getRequestURI());
        }
        return new ResponseEntity<>(errorFormat,HttpStatus.NOT_FOUND);
    }

    private String extractBytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
