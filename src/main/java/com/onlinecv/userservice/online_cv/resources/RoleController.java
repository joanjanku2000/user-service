package com.onlinecv.userservice.online_cv.resources;

import com.onlinecv.userservice.base.resources.BaseController;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController implements BaseController<RoleDTO> {


    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.save(roleDTO));
    }

    @PutMapping
    public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO dto) {
        return ResponseEntity.ok(roleService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        roleService.delete(id);
    }
}
