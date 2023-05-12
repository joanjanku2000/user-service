package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.repository.UserDataKeyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.onlinecv.userservice.mapper.RoleMapperTest.TEST;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserDataKeyControllerTest extends BaseTest {
    private static final String PATH = "/user-data-key";
    private static final String USER_DATA_KEY_URL = BASE_URL + PATH;
    private final UserDataKeyRepository userDataKeyRepository;

    @Autowired
    public UserDataKeyControllerTest(UserDataKeyRepository userDataKeyRepository) {
        this.userDataKeyRepository = userDataKeyRepository;
    }

    private UserDataKeyDTO getTestUserDataKeyDTO() {
        UserDataKeyDTO userDataKeyDTO = new UserDataKeyDTO();
        userDataKeyDTO.setKeyName(TEST);
        return userDataKeyDTO;
    }

    @Override
    <T extends BaseDTO> void assertObjectsEqual(T dto, T expectedDTO) {
        if (dto instanceof UserDataKeyDTO && expectedDTO instanceof UserDataKeyDTO) {
            assertEquals(((UserDataKeyDTO) dto).getKeyName(), ((UserDataKeyDTO) expectedDTO).getKeyName());
        }
    }

    @AfterEach
    protected void deleteDB() {
        userDataKeyRepository.deleteAll();
    }

    @Test
    void postUserDataKey() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = getTestUserDataKeyDTO();
        ResponseEntity<String> savedUserDataKey = postUserDataKey(userDataKeyDTO);
        log.info("Gotten response from server {} ", savedUserDataKey);
        assertSuccessfulAndCorrectResponse(userDataKeyDTO, savedUserDataKey);
    }

    @Test
    void postUserDataKey_Fail() throws JsonProcessingException {
        try {
            UserDataKeyDTO userDataKeyDTO = getTestUserDataKeyDTO();
            ResponseEntity<String> savedUserDataKey = postUserDataKey(userDataKeyDTO);
            log.info("Gotten response from server {} ", savedUserDataKey);
            assertSuccessfulAndCorrectResponse(userDataKeyDTO, savedUserDataKey);
            postUserDataKey(userDataKeyDTO);
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    void putUserDataKey_Pass() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = responseEntityToDTO(postUserDataKey(getTestUserDataKeyDTO()), UserDataKeyDTO.class);
        // modify
        userDataKeyDTO.setKeyName(userDataKeyDTO.getKeyName().concat(LocalDateTime.now().toString()));
        assertSuccessfulAndCorrectResponse(userDataKeyDTO, put(USER_DATA_KEY_URL, userDataKeyDTO));
    }

    @Test
    void putUserDataKey_Fail() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = responseEntityToDTO(postUserDataKey(getTestUserDataKeyDTO()), UserDataKeyDTO.class);
        try {
            put(USER_DATA_KEY_URL, userDataKeyDTO);
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @Test
    void putUserDataKey_Fail_Not_Found() throws JsonProcessingException {
        try {
            UserDataKeyDTO userDataKeyDTO = getTestUserDataKeyDTO();
            userDataKeyDTO.setId(nextLong());
            put(USER_DATA_KEY_URL, userDataKeyDTO);
            assertEquals(1, 2);
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e.getMessage());
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void getUserDataKey() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = responseEntityToDTO(postUserDataKey(getTestUserDataKeyDTO()), UserDataKeyDTO.class);
        assertSuccessfulAndCorrectResponse(userDataKeyDTO, get(USER_DATA_KEY_URL + SLASH + userDataKeyDTO.getId()));
    }

    @Test
    void getUserDataKey_Fail() throws JsonProcessingException {
        try {
            get(USER_DATA_KEY_URL + SLASH + nextInt());
            assertEquals(1, 2); // if request is successful , test must fail
        } catch (HttpClientErrorException e) {
            log.info("Message {}", e);
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void deleteUserDataKey() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = responseEntityToDTO(postUserDataKey(getTestUserDataKeyDTO()), UserDataKeyDTO.class);
        ResponseEntity<String> result = delete(USER_DATA_KEY_URL + SLASH + userDataKeyDTO.getId());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(userDataKeyRepository.findById(userDataKeyDTO.getId()), Optional.empty());
    }


    private ResponseEntity<String> postUserDataKey(UserDataKeyDTO userDataKeyDTO) throws JsonProcessingException {
        return post(USER_DATA_KEY_URL, userDataKeyDTO);
    }
}
