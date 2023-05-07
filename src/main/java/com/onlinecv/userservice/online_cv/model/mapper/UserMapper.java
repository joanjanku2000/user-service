package com.onlinecv.userservice.online_cv.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.entity.AppUser;
import com.onlinecv.userservice.online_cv.model.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = BaseMapper.class)
public interface UserMapper {

    RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Named("rolesToString")
    static List<RoleDTO> inchToCentimeter(List<UserRole> roles) {
        return roles.stream().map(UserRole::getRole).map(roleMapper::roleToRoleDTO).collect(Collectors.toList());
    }

    AppUser toEntity(UserDTO userDTO);

    @Mapping(source = "userRoles", target = "roles", qualifiedByName = "rolesToString")
    UserDTO toDTO(AppUser user);

    // could add an integer limit to the times someone updates the personal information
    // and be managed here
    default AppUser toEntityForUpdate(AppUser user, UserDTO userDTO) {
        user.setName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthday(userDTO.getBirthday());
        return user;
    }
}
