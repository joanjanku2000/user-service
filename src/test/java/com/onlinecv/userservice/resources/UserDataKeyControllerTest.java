package com.onlinecv.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.repository.UserDataKeyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static com.onlinecv.userservice.mapper.RoleMapperTest.TEST;
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
    <T extends BaseDTO> void assertRolesEqual(T dto, T expectedDTO) {
        if (dto instanceof UserDataKeyDTO && expectedDTO instanceof UserDataKeyDTO) {
            assertEquals(((UserDataKeyDTO) dto).getKeyName(), ((UserDataKeyDTO) expectedDTO).getKeyName());
        }
    }

    @AfterEach
    protected void deleteDB() {
        userDataKeyRepository.deleteAll();
    }

    @Test
    @DisplayName("Test - POST /user-data-key")
    void postUserDataKey() throws JsonProcessingException {
        UserDataKeyDTO userDataKeyDTO = getTestUserDataKeyDTO();
        ResponseEntity<String> savedUserDataKey = postUserDataKey(userDataKeyDTO);
        log.info("Gotten response from server {} ", savedUserDataKey);
        assertSuccessfulResponse(userDataKeyDTO, savedUserDataKey);
    }

    private ResponseEntity<String> postUserDataKey(UserDataKeyDTO userDataKeyDTO) throws JsonProcessingException {
        return post(USER_DATA_KEY_URL, userDataKeyDTO);
    }
}
