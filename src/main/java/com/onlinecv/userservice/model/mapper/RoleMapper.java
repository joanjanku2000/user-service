package com.onlinecv.userservice.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper(uses = BaseMapper.class)
public interface RoleMapper {
    Role roleDTOToRole(RoleDTO roleDTO);
    RoleDTO roleToRoleDTO(Role role);
}
