package com.onlinecv.userservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record RoleDTO(Long id, String name, String description) {
}
