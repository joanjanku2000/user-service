package com.onlinecv.userservice.online_cv.service.impl;

import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.entity.User;
import com.onlinecv.userservice.online_cv.model.entity.UserRole;
import com.onlinecv.userservice.online_cv.model.mapper.UserMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import com.onlinecv.userservice.online_cv.service.UserService;
import com.onlinecv.userservice.online_cv.validations.Validate;
import com.onlinecv.userservice.online_cv.validations.Validation;
import com.onlinecv.userservice.online_cv.validations.Validations;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.*;
import static java.util.Objects.requireNonNull;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final String USERNAME = "username";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Validations(validations = {@Validate(validation = Validation.UNIQUE, field = "username", entity = User.class), @Validate(validation = Validation.UNIQUE, field = "email", entity = User.class)})
    @Override
    public UserDTO save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        List<UserRole> userRoleList = requireNonNull(dto).getRoles().stream().map(role -> toUserRoleEntity(user, roleRepository.findById(role.getId()).orElseThrow(() -> new NotFoundException(String.format(ROLE_NOT_FOUND, role.getId()))))).collect(Collectors.toList());
        user.setUserRoles(userRoleList);
        return userMapper.toDTO(userRepository.save(user));
    }

    private UserRole toUserRoleEntity(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole;
    }

    @Validations(validations = {@Validate(validation = Validation.UNIQUE, field = "username", entity = User.class), @Validate(validation = Validation.UNIQUE, field = "email", entity = User.class)})
    @Override
    public UserDTO update(UserDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, dto.getId())));
        return userMapper.toDTO(userRepository.save(userMapper.toEntityForUpdate(user, dto)));
    }

    @Override
    public UserDTO findById(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id))));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, USERNAME, username)));
    }
}
