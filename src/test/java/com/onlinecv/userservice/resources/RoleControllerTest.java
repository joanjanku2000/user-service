package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecv.userservice.model.dto.RoleDTO;
import com.onlinecv.userservice.model.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestPropertySource("/application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoleControllerTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final Logger log = LoggerFactory.getLogger(RoleControllerTest.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ROLE_URL = BASE_URL + "/role";

    private static HttpEntity<String> toRequest(RoleDTO roleDTO) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(toJson(roleDTO),headers);
    }

    private static String toJson(RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleDTO);
    }

    @Test
    @DisplayName("Test - POST /role")
    void postRole() throws JsonProcessingException {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("Test");
        roleDTO.setDescription("Description");
        ResponseEntity<String> savedRole = restTemplate.postForEntity(ROLE_URL, toRequest(roleDTO), String.class);
        log.info("Gotten response from server {} ",savedRole);
        assertEquals(savedRole.getStatusCode(), HttpStatus.OK);
        assertTrue(Objects.requireNonNull(savedRole.getBody()).contains(roleDTO.getName()));
    }
}
