package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.mapper.RoleMapper;
import com.onlinecv.userservice.online_cv.model.mapper.UserMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import com.onlinecv.userservice.online_cv.repository.UserRoleRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest extends BaseTest {

    private static final String PATH = "/user";
    private static final String USER_URL = BASE_URL + PATH;
    private static final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private Long roleID;

    @Autowired
    public UserControllerTest(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @BeforeEach
    public void initializeRoles() {
        Role role = roleRepository.save(roleMapper.roleDTOToRole(getTestRole()));
        this.roleID = role.getId();
    }

    @AfterEach
    public void deleteRole() {
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    public UserDTO getTestUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Joan");
        userDTO.setUserName("jjanku");
        userDTO.setLastName("Janku");
        userDTO.setEmail("jjanku@jjanku.com");
        userDTO.setRoles(List.of(getTestRole()));
        userDTO.setUserPassword("PASSWORD");
        return userDTO;
    }

    public RoleDTO getTestRole() {
        RoleDTO roleDTO = com.onlinecv.userservice.mapper.RoleMapperTest.getTestRole();
        roleDTO.setId(roleID);
        return roleDTO;
    }

    @Override
    <T extends BaseDTO> void assertRolesEqual(T dto, T expectedDTO) {
        if (dto instanceof UserDTO && expectedDTO instanceof UserDTO) {
            assertEquals(((UserDTO) dto).getFirstName(), ((UserDTO) expectedDTO).getFirstName());
            assertEquals(((UserDTO) dto).getBirthday(), ((UserDTO) expectedDTO).getBirthday());
            assertEquals(((UserDTO) dto).getLastName(), ((UserDTO) expectedDTO).getLastName());
            assertEquals(((UserDTO) dto).getUserName(), ((UserDTO) expectedDTO).getUserName());
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

    @Test
    void postUser_Fail_DuplicateEmail() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        userDTO.setUserName("Different"); // change username to assure fails because of same email only
        log.info("Gotten response from server {} ", savedUser);
        assertThrows(RuntimeException.class, () -> postUser(userDTO));
    }

    @Test
    void postUser_Fail_DuplicateUsername() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        userDTO.setEmail("Different"); // change email to assure fails because of same username only
        log.info("Gotten response from server {} ", savedUser);
        assertThrows(RuntimeException.class, () -> postUser(userDTO));
    }

    @Test
    void postUser_Fail_DuplicateUsernameAndEmail() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {} ", savedUser);
        assertThrows(RuntimeException.class, () -> postUser(userDTO));
    }

    @Test
    void putUser() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {}", savedUser);
        UserDTO dtoToSendAgain = responseEntityToDTO(savedUser, UserDTO.class);
        dtoToSendAgain.setUserName("NewUsername");
        assertSuccessfulAndCorrectResponse(userDTO, put(USER_URL, dtoToSendAgain));
    }

    @Test
    void putUser_Fail() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        userRepository.save(userMapper.toEntity(userDTO));
        log.info("Gotten response from server {}", savedUser);
        assertThrows(RuntimeException.class, () -> put(USER_URL, responseEntityToDTO(savedUser, UserDTO.class)));
    }

    @Test
    void getUser() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {}", savedUser);
        assertSuccessfulAndCorrectResponse(userDTO, savedUser);
        ResponseEntity<String> gottenUser = get(USER_URL + SLASH + responseEntityToDTO(savedUser, UserDTO.class).getId());
        assertSuccessfulAndCorrectResponse(responseEntityToDTO(savedUser, UserDTO.class), gottenUser);
    }

    @Test
    void getUser_FAIL() {
        try {
            get(USER_URL + SLASH + RandomUtils.nextInt());
            assertEquals(1, 2);  // if request is successful , test must fail
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void deleteUser() throws JsonProcessingException {
        // save a user first
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {}", savedUser);
        assertSuccessfulAndCorrectResponse(userDTO, savedUser);

        Long userId = responseEntityToDTO(savedUser, UserDTO.class).getId();
        // delete the user
        ResponseEntity<String> resp = delete(USER_URL + SLASH + userId);
        assertEquals(resp.getStatusCode(), HttpStatus.OK);

        // check the db for user and USER ROLE (remember it should cascade to delete the user_role as well)

        assertEquals(Optional.empty(),userRepository.findById(userId));
        assertEquals(0,userRoleRepository.findAllByUserIdAndDeletedFalse(userId).size());
    }
    private static final String USERNAME = "username";
    @Test
    void getUserByUsername() throws JsonProcessingException {
        UserDTO userDTO = getTestUser();
        ResponseEntity<String> savedUser = postUser(userDTO);
        log.info("Gotten response from server {}", savedUser);
        assertSuccessfulAndCorrectResponse(userDTO, savedUser);
        ResponseEntity<String> gottenUser = get(USER_URL + SLASH + USERNAME + SLASH + responseEntityToDTO(savedUser, UserDTO.class).getUserName());
        assertSuccessfulAndCorrectResponse(responseEntityToDTO(savedUser, UserDTO.class), gottenUser);
    }


    @Test
    void getUserByUsername_Fail() throws JsonProcessingException {
        try {
            get(USER_URL + SLASH + USERNAME + SLASH + USERNAME + LocalDateTime.now().toString());
            assertEquals(1, 2);  // if request is successful , test must fail
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

    }

    private ResponseEntity<String> postUser(UserDTO userDTO) throws JsonProcessingException {
        return post(USER_URL, userDTO);
    }
}
