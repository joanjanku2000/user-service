package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.base.resources.BaseController;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-data")
public class UserDataController implements BaseController<UserDataDTO> {

    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping
    public ResponseEntity<UserDataDTO> save(@RequestBody UserDataDTO dto) {
        return ResponseEntity.ok(userDataService.save(dto));
    }

    @PutMapping
    public ResponseEntity<UserDataDTO> update(@RequestBody UserDataDTO dto) {
        return ResponseEntity.ok(userDataService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userDataService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userDataService.delete(id);
    }
}
