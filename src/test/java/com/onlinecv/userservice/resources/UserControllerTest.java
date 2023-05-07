package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.mapper.RoleMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest extends BaseTest {

    private static final String PATH = "/use";
    private static final String USER_URL = BASE_URL + PATH;
    private static final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Long roleID;

    @Autowired
    public UserControllerTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDTO getTestUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Joan");
        userDTO.setUsername("jjanku");
        userDTO.setLastName("Janku");
        userDTO.setEmail("jjanku@jjanku.com");
        userDTO.setRoles(List.of(getTestRole()));
        return userDTO;
    }

    public RoleDTO getTestRole() {
        RoleDTO roleDTO = com.onlinecv.userservice.mapper.RoleMapperTest.getTestRole();
        roleDTO.setId(roleID);
        return roleDTO;
    }

    @BeforeAll
    public void initializeRoles() {
        Role role = roleRepository.save(roleMapper.roleDTOToRole(getTestRole()));
        this.roleID = role.getId();
    }

    @Override
    <T extends BaseDTO> void assertRolesEqual(T dto, T expectedDTO) {
        if (dto instanceof UserDTO && expectedDTO instanceof UserDTO) {
            assertEquals(((UserDTO) dto).getName(), ((UserDTO) expectedDTO).getName());
            assertEquals(((UserDTO) dto).getBirthday(), ((UserDTO) expectedDTO).getBirthday());
            assertEquals(((UserDTO) dto).getLastName(), ((UserDTO) expectedDTO).getLastName());
            assertEquals(((UserDTO) dto).getUsername(), ((UserDTO) expectedDTO).getUsername());
            assertEquals(((UserDTO) dto).getEmail(), ((UserDTO) expectedDTO).getEmail());
            assertEquals(((UserDTO) dto).getRoles().size(), ((UserDTO) expectedDTO).getRoles().size());
            for (int i = 0; i < ((UserDTO) dto).getRoles().size(); i++) {
                assertEquals(((UserDTO) dto).getRoles().get(i).getName(), ((UserDTO) expectedDTO).getRoles().get(i).getName());
            }
        }
    }

    @Test
    void postUser() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {} ", savedUser);
        assertSuccessfulAndCorrectResponse(userDTO, savedUser);
    }

    private ResponseEntity<String> postUser(UserDTO userDTO) throws JsonProcessingException {
        return post(USER_URL, userDTO);
    }
}
