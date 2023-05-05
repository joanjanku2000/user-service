package com.onlinecv.userservice.online_cv.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;


public class UserData extends BaseDTO {
    private UserDTO userDTO;
    private UserDataKeyDTO userDataKey;
    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDataKeyDTO getUserDataKey() {
        return userDataKey;
    }

    public void setUserDataKey(UserDataKeyDTO userDataKey) {
        this.userDataKey = userDataKey;
    }
}
