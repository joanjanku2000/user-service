package com.onlinecv.userservice.online_cv.service.impl;

import com.onlinecv.userservice.base.exceptions.BadRequestException;
import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.entity.AppUser;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.entity.UserRole;
import com.onlinecv.userservice.online_cv.model.mapper.UserMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import com.onlinecv.userservice.online_cv.service.UserService;
import com.onlinecv.userservice.online_cv.validations.Validate;
import com.onlinecv.userservice.online_cv.validations.Validation;
import com.onlinecv.userservice.online_cv.validations.Validations;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.onlinecv.userservice.online_cv.constants.BadRequestExceptionMessages.USER_EXISTS;
import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.*;
import static java.util.Objects.requireNonNull;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USERNAME = "username";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Validations(validations = {@Validate(validation = Validation.UNIQUE, field = "userName", entity = AppUser.class, message = USER_EXISTS), @Validate(validation = Validation.UNIQUE, field = "email", entity = AppUser.class, message = USER_EXISTS)})
    @Override
    public UserDTO save(UserDTO dto) {
        AppUser user = userMapper.toEntity(dto);
        List<UserRole> userRoleList = requireNonNull(dto).getRoles().stream().map(role -> toUserRoleEntity(user, roleRepository.findById(role.getId()).orElseThrow(() -> new NotFoundException(String.format(ROLE_NOT_FOUND, role.getId()))))).collect(Collectors.toList());
        user.setUserRoles(userRoleList);
        return userMapper.toDTO(userRepository.save(user));
    }

    private UserRole toUserRoleEntity(AppUser user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole;
    }


    @Override
    public UserDTO update(UserDTO dto) {
        log.info("DTO {} ", dto.getId());
        AppUser user = userRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, dto.getId())));
        if (userRepository.existsAppUsersByEmailOrUserNameButIsNotUserWithId(dto.getEmail(), dto.getUserName(), user.getId())) {
            throw new BadRequestException(USER_EXISTS);
        }
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
        return userRepository.findByUserName(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, USERNAME, username)));
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return userMapper.toDTO((AppUser) loadUserByUsername(username));
    }
}
