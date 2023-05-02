package com.onlinecv.userservice.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTO {
    private String username;
    private String name;
    private String lastName;
    private LocalDate birthday;
}
