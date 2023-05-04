package com.onlinecv.userservice.online_cv.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDataKeyDTO extends BaseDTO {
    private String keyName;
}
