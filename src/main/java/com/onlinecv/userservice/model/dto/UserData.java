package com.onlinecv.userservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UserData(Long id, UserDTO userDTO, UserDataKey userDataKey) {

}
