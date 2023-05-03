package com.onlinecv.userservice.model.mapper;

import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    Role roleDTOToRole(RoleDTO roleDTO);
}
