package com.onlinecv.userservice.service.impl;

import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.model.entity.Role;
import com.onlinecv.userservice.model.mapper.RoleMapper;
import com.onlinecv.userservice.repository.RoleRepository;
import com.onlinecv.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Override
    public RoleDTO save(RoleDTO dto) {
        Role role = roleMapper.roleDTOToRole(dto);
        return roleMapper.roleToRoleDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO update(RoleDTO dto) {
        return null;
    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
