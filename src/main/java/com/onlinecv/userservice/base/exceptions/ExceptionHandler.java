package com.onlinecv.userservice.base.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorFormat handleNotFoundEexception(HttpServletRequest httpServletRequest, NotFoundException e) {
        ErrorFormat errorFormat = null;
        try {
            errorFormat = ErrorFormat.builder().message(e.getMessage()).time(LocalDateTime.now()).failingRequest(httpServletRequest.getRequestURI()).failingPayload(extractBytesToString(httpServletRequest.getInputStream().readAllBytes())).build();
        } catch (IOException ex) {
            errorFormat = ErrorFormat.builder().message(e.getMessage()).time(LocalDateTime.now()).failingRequest(httpServletRequest.getRequestURI()).build();
        }
        return errorFormat;
    }

    private String extractBytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
