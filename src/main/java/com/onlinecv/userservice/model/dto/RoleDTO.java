package com.onlinecv.userservice.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;

public class RoleDTO extends BaseDTO {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
