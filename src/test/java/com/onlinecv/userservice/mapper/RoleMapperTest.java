package com.onlinecv.userservice.mapper;

import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleMapperTest {

    public static final String TEST = "Test";
    private static final String DESCRIPTION = "Description";
    private static RoleDTO roleDTO;

    public static RoleDTO getTestRole() {
        if (isNull(roleDTO)) {
            roleDTO = new RoleDTO();
            roleDTO.setName(TEST);
            roleDTO.setDescription(DESCRIPTION);
        }

        return roleDTO;
    }

    @Test
    public void roleMapperTest() {
        RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
        RoleDTO roleDTO = getTestRole();

        Role role = roleMapper.roleDTOToRole(roleDTO);

        assertEquals(role.getName(), roleDTO.getName());
        assertEquals(role.getDescription(), roleDTO.getDescription());
    }
}
