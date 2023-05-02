package com.onlinecv.userservice.model.dto;

import lombok.Data;

@Data
public class UserRole {
    private Long id;
    private UserDTO user;
    private RoleDTO role;
}
