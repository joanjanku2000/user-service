package com.onlinecv.userservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UserRole(Long id, UserDTO user, RoleDTO role) {
}
