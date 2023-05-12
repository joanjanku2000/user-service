package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.base.resources.BaseController;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-data")
public class UserDataController implements BaseController<UserDataDTO> {

    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public ResponseEntity<UserDataDTO> save(UserDataDTO dto) {
        return ResponseEntity.ok(userDataService.save(dto));
    }

    @Override
    public ResponseEntity<UserDataDTO> update(UserDataDTO dto) {
        return ResponseEntity.ok(userDataService.update(dto));
    }

    @Override
    public ResponseEntity<UserDataDTO> findById(Long id) {
        return ResponseEntity.ok(userDataService.findById(id));
    }

    @Override
    public void delete(Long id) {
        userDataService.delete(id);
    }
}
