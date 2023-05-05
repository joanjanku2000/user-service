package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.service.UserDataKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-data-key")
public class UserDataKeyController {

    private final UserDataKeyService userDataKeyService;

    public UserDataKeyController(UserDataKeyService userDataKeyService) {
        this.userDataKeyService = userDataKeyService;
    }

    @PostMapping
    public ResponseEntity<UserDataKeyDTO> save(@RequestBody UserDataKeyDTO userDataKeyDTO) {
        return ResponseEntity.ok(userDataKeyService.save(userDataKeyDTO));
    }

    @PutMapping
    public ResponseEntity<UserDataKeyDTO> update(@RequestBody UserDataKeyDTO dto) {
        return ResponseEntity.ok(userDataKeyService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataKeyDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userDataKeyService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userDataKeyService.delete(id);
    }
}

