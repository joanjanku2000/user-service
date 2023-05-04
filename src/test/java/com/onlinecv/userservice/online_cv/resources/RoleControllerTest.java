package com.onlinecv.userservice.online_cv.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.onlinecv.userservice.mapper.RoleMapperTest.getTestRole;
import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource("/application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoleControllerTest {
    private static final String BASE_URL = "http://localhost:9090";
    private static final String PATH = "/role";
    private static final Logger log = LoggerFactory.getLogger(RoleControllerTest.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ROLE_URL = BASE_URL + PATH;

    private static HttpEntity<String> toRequest(RoleDTO roleDTO) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(toJson(roleDTO), headers);
    }

    private static String toJson(RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleDTO);
    }

    @Test
    @DisplayName("Test - POST /role")
    void postRole() throws JsonProcessingException {
        RoleDTO roleDTO = getTestRole();
        ResponseEntity<String> savedRole = restTemplate.postForEntity(ROLE_URL, toRequest(roleDTO), String.class);
        log.info("Gotten response from server {} ", savedRole);
        assertEquals(savedRole.getStatusCode(), HttpStatus.OK);
        assertEquals(objectMapper.readValue(savedRole.getBody(), RoleDTO.class).getName(), roleDTO.getName());
    }
}
