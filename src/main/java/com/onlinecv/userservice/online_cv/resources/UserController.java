package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.base.resources.BaseController;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController implements BaseController<UserDTO> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> save(UserDTO dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @Override
    public ResponseEntity<UserDTO> update(UserDTO dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @Override
    public ResponseEntity<UserDTO> findById(Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }
}
