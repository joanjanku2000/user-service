package com.onlinecv.userservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public record UserDTO(Long id, String username, String name, String lastName, LocalDate birthday) {
}
