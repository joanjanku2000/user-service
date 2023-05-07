package com.onlinecv.userservice.online_cv.service;

import com.onlinecv.userservice.base.service.BaseService;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<UserDTO> {
    UserDTO findUserByUsername(String username);
}
