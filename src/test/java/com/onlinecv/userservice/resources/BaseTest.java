package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecv.userservice.base.dto.BaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseTest {
    protected static final String BASE_URL = "http://localhost:9090";
    protected static final String SLASH = "/";
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected static final RestTemplate restTemplate = new RestTemplate();
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected static <T extends BaseDTO> HttpEntity<String> toRequest(T dto) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(toJson(dto), headers);
    }

    protected static <T extends BaseDTO> String toJson(T dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    protected static <T extends BaseDTO> ResponseEntity<String> post(String url, T dto) throws JsonProcessingException {
        return restTemplate.postForEntity(url, toRequest(dto), String.class);
    }

    protected static <T extends BaseDTO> ResponseEntity<String> put(String url, T dto) throws JsonProcessingException {
        return restTemplate.exchange(url, HttpMethod.PUT, toRequest(dto), String.class);
    }


    protected static <T extends BaseDTO> ResponseEntity<String> get(String url) {
        return restTemplate.getForEntity(url, String.class);
    }

    protected static <T extends BaseDTO> ResponseEntity<String> delete(String url) {
        return restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    protected static <T extends BaseDTO> T responseEntityToDTO(ResponseEntity<String> responseEntity, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.readValue(Objects.requireNonNull(responseEntity.getBody()), tClass);
    }

    protected <T extends BaseDTO> void assertSuccessfulAndCorrectResponse(T dto, ResponseEntity<String> entity) throws JsonProcessingException {
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        assertRolesEqual(dto, responseEntityToDTO(entity, dto.getClass()));
    }

    abstract <T extends BaseDTO> void assertRolesEqual(T dto, T expectedDTO);

}
