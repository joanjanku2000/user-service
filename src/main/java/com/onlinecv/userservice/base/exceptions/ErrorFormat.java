package com.onlinecv.userservice.base.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorFormat {
    private String message;
    private LocalDateTime time;
    private String failingRequest;
    private String failingPayload;
}
