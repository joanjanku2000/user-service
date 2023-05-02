package com.onlinecv.userservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class UserData {
    private Long id;
    private UserDTO userDTO;
    private UserDataKey userDataKey;

}
