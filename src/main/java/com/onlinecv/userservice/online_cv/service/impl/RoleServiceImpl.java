package com.onlinecv.userservice.online_cv.service.impl;

import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.mapper.RoleMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.service.RoleService;
import com.onlinecv.userservice.online_cv.validations.Validate;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import static com.onlinecv.userservice.base.exceptions.constants.NotFoundMessage.ROLE_NOT_FOUND;
import static com.onlinecv.userservice.online_cv.validations.Validation.UNIQUE;
import static java.util.Objects.requireNonNull;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Validate(value = UNIQUE, field = "name", entity = Role.class)
    @Override
    public RoleDTO save(RoleDTO dto) {
        Role role = roleMapper.roleDTOToRole(dto);
        return roleMapper.roleToRoleDTO(roleRepository.save(role));
    }

    @Validate(value = UNIQUE, field = "name", entity = Role.class)
    @Override
    public RoleDTO update(RoleDTO dto) {
        Role role = roleMapper.toEntityForUpdate(dto, roleRepository.findById(requireNonNull(dto.getId())).orElseThrow(() -> new NotFoundException(String.format(ROLE_NOT_FOUND, dto.getId()))));
        return roleMapper.roleToRoleDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.roleToRoleDTO(roleRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(ROLE_NOT_FOUND, id))));
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
