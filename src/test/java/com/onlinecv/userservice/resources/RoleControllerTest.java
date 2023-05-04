package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    private static void assertSuccessfulResponse(RoleDTO roleDTO, ResponseEntity<String> savedRole) throws JsonProcessingException {
        assertEquals(savedRole.getStatusCode(), HttpStatus.OK);
        assertEquals(objectMapper.readValue(savedRole.getBody(), RoleDTO.class).getName(), roleDTO.getName());
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
        assertSuccessfulResponse(roleDTO, savedRole);
        roleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test - POST /role - Non Unique")
    void postRole_Fail() throws JsonProcessingException {
        RoleDTO roleDTO = getTestRole();
        ResponseEntity<String> savedRole = postRole(roleDTO);
        log.info("Gotten response from server {} ", savedRole);
        assertSuccessfulResponse(roleDTO, savedRole);
        assertThrows(RuntimeException.class, () -> postRole(roleDTO));
        roleRepository.deleteAll();

    }

    @Test
    @DisplayName("Test - POST /role - Non Unique - in row")
    void postRole_Pass_Multiple() throws JsonProcessingException {
        RoleDTO roleDTO = getTestRole();
        ResponseEntity<String> savedRole = postRole(roleDTO);
        log.info("Gotten response from server {} ", savedRole);
        assertSuccessfulResponse(roleDTO, savedRole);
        roleDTO.setName("Another One");
        assertDoesNotThrow(() -> postRole(roleDTO));
        roleRepository.deleteAll();
    }
}
