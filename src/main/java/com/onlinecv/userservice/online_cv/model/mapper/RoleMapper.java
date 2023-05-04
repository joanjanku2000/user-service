package com.onlinecv.userservice.online_cv.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper(uses = BaseMapper.class)
public interface RoleMapper {
    Role roleDTOToRole(RoleDTO roleDTO);

    RoleDTO roleToRoleDTO(Role role);

    default Role toEntityForUpdate(RoleDTO roleDTO, Role role) {
        role.setDescription(roleDTO.getDescription());
        role.setName(roleDTO.getName());
        return role;
    }
}
