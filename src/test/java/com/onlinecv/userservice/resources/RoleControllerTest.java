package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.base.exceptions.BadRequestException;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.onlinecv.userservice.mapper.RoleMapperTest.getTestRole;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoleControllerTest extends BaseTest {
    private static final String PATH = "/role";
    private static final String ROLE_URL = BASE_URL + PATH;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleControllerTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    <T extends BaseDTO> void assertObjectsEqual(T roleDTO, T expectedRoleDto) {
        if (roleDTO instanceof RoleDTO && expectedRoleDto instanceof RoleDTO) {
            assertEquals(((RoleDTO) roleDTO).getName(), ((RoleDTO) expectedRoleDto).getName());
            assertEquals(((RoleDTO) roleDTO).getDescription(), ((RoleDTO) expectedRoleDto).getDescription());
        }
    }

    @AfterEach
    protected void deleteDB() {
        roleRepository.deleteAll();
    }

    private ResponseEntity<String> postRole(RoleDTO roleDTO) throws JsonProcessingException {
        return post(ROLE_URL, roleDTO);
    }

    @Test
    @DisplayName("Test - POST /role")
    void postRole() throws JsonProcessingException {
        RoleDTO roleDTO = getTestRole();
        ResponseEntity<String> savedRole = postRole(roleDTO);
        log.info("Gotten response from server {} ", savedRole);
        assertSuccessfulAndCorrectResponse(roleDTO, savedRole);
    }

    @Test
    @DisplayName("Test - POST /role - Non Unique")
    void postRole_Fail() throws JsonProcessingException {
        try {
            RoleDTO roleDTO = getTestRole();
            ResponseEntity<String> savedRole = postRole(roleDTO);
            log.info("Gotten response from server {} ", savedRole);
            assertSuccessfulAndCorrectResponse(roleDTO, savedRole);
            postRole(roleDTO);
        } catch (HttpClientErrorException e){
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("Test - POST /role - Non Unique - in row")
    void postRole_Pass_Multiple() throws JsonProcessingException {
        RoleDTO roleDTO = getTestRole();
        ResponseEntity<String> savedRole = postRole(roleDTO);
        log.info("Gotten response from server {} ", savedRole);
        assertSuccessfulAndCorrectResponse(roleDTO, savedRole);
        roleDTO.setName("Another One");
        assertDoesNotThrow(() -> postRole(roleDTO));
    }

    @Test
    @DisplayName("Test - PUT /role")
    void putRole_Pass() throws JsonProcessingException {
        RoleDTO createdRole = responseEntityToDTO(postRole(getTestRole()), RoleDTO.class);
        // modify name
        createdRole.setName(createdRole.getName().concat(LocalDateTime.now().toString()));
        assertSuccessfulAndCorrectResponse(createdRole, put(ROLE_URL, createdRole));
    }

    @Test
    @DisplayName("Test - PUT /role - Must fail - Non Unique")
    void putRole_Fail() throws JsonProcessingException {
        RoleDTO createdRole = responseEntityToDTO(postRole(getTestRole()), RoleDTO.class);
        try {
            put(ROLE_URL, createdRole);
        } catch (HttpClientErrorException e){
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("Test - GET /role/{id} ")
    void getRole_Pass() throws JsonProcessingException {
        RoleDTO roleDTO = responseEntityToDTO(postRole(getTestRole()), RoleDTO.class);
        assertSuccessfulAndCorrectResponse(roleDTO, get(ROLE_URL + SLASH + roleDTO.getId()));
    }

    @Test
    @DisplayName("Test - GET /role/{id} - FAIL")
    void getRole_Fail() throws JsonProcessingException {
        try {
            get(ROLE_URL + SLASH + RandomUtils.nextInt());
            assertEquals(1, 2);  // if request is successful , test must fail
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    @DisplayName("Test - DELETE /role/{id} ")
    void deleteRole_Pass() throws JsonProcessingException {
        RoleDTO roleDTO = responseEntityToDTO(postRole(getTestRole()), RoleDTO.class);
        ResponseEntity<String> result = delete(ROLE_URL + SLASH + roleDTO.getId());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(roleRepository.findById(roleDTO.getId()), Optional.empty());
    }

}
