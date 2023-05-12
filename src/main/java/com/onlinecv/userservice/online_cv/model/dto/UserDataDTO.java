package com.onlinecv.userservice.online_cv.model.dto;

import com.onlinecv.userservice.base.dto.BaseDTO;


public class UserDataDTO extends BaseDTO {
    private UserDTO userDTO;
    private UserDataKeyDTO userDataKey;
    private String keyValue;

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

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
