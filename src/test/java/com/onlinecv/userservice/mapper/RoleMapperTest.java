package com.onlinecv.userservice.mapper;

import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.model.entity.Role;
import com.onlinecv.userservice.model.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleMapperTest {

    private static final String TEST = "Test";
    private static final String DESCRIPTION = "Description";

    @Test
    public void roleMapperTest() {
        RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(TEST);
        roleDTO.setDescription(DESCRIPTION);

        Role role = roleMapper.roleDTOToRole(roleDTO);

        assertEquals(role.getName(), roleDTO.getName());
        assertEquals(role.getDescription(), roleDTO.getDescription());
    }
}
