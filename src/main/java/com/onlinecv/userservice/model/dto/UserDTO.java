package com.onlinecv.userservice.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private LocalDate birthday;
}
