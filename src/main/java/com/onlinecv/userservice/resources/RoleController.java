package com.onlinecv.userservice.resources;

import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.save(roleDTO));
    }
}
