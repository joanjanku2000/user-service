package com.onlinecv.userservice.online_cv.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserData extends BaseDTO {
    private UserDTO userDTO;
    private UserDataKeyDTO userDataKey;

}
