package com.onlinecv.userservice.online_cv.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRole extends BaseDTO {

    private UserDTO user;
    private RoleDTO role;
}
