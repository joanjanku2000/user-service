package com.onlinecv.userservice.base.exceptions.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorFormat {
    private final String message;
    private final LocalDateTime time;
    private final String failingRequest;
    private String failingPayload;

    public ErrorFormat(String message, LocalDateTime time, String failingRequest, String failingPayload) {
        this.message = message;
        this.time = time;
        this.failingRequest = failingRequest;
        this.failingPayload = failingPayload;
    }

    public ErrorFormat(String message, LocalDateTime time, String failingRequest) {
        this.message = message;
        this.time = time;
        this.failingRequest = failingRequest;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getFailingRequest() {
        return failingRequest;
    }

    public String getFailingPayload() {
        return failingPayload;
    }
}
