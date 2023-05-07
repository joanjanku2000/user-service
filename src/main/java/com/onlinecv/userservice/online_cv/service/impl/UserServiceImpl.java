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
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.ROLE_NOT_FOUND;
import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.USER_NOT_FOUND;
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

    @Override
    public UserDTO update(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, USERNAME, username)));
    }
}
