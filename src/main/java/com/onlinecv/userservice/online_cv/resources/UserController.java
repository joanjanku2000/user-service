package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.base.resources.BaseController;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController implements BaseController<UserDTO> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
}
