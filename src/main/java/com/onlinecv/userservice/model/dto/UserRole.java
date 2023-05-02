package com.onlinecv.userservice.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRole extends BaseDTO {

    private UserDTO user;
    private RoleDTO role;
}
