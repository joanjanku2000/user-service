package com.onlinecv.userservice.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor
public class RoleDTO extends BaseDTO {
    private String name;
    private String description;
}
