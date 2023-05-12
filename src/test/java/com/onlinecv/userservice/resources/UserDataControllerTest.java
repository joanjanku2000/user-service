package com.onlinecv.userservice.resources;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.onlinecv.userservice.base.dto.BaseDTO;
import com.onlinecv.userservice.online_cv.model.dto.RoleDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.model.entity.Role;
import com.onlinecv.userservice.online_cv.model.mapper.RoleMapper;
import com.onlinecv.userservice.online_cv.model.mapper.UserDataKeyMapper;
import com.onlinecv.userservice.online_cv.model.mapper.UserDataMapper;
import com.onlinecv.userservice.online_cv.model.mapper.UserMapper;
import com.onlinecv.userservice.online_cv.repository.RoleRepository;
import com.onlinecv.userservice.online_cv.repository.UserDataRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import com.onlinecv.userservice.online_cv.service.UserDataKeyService;
import com.onlinecv.userservice.online_cv.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.onlinecv.userservice.mapper.RoleMapperTest.TEST;
import static com.onlinecv.userservice.mapper.RoleMapperTest.getTestRole;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserDataControllerTest extends BaseTest {

    private static final String PATH = "/user-data";
    private static final String USER_DATA_URL = BASE_URL + PATH;
    private static final UserDataKeyMapper userDataKeyMapper = Mappers.getMapper(UserDataKeyMapper.class);
    private static final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);
    private static final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDataRepository userDataRepository;
    private final UserDataKeyService userDataKeyService;
    private final RoleRepository roleRepository;
    private Long roleID;
    private UserDTO userDTO;
    private List<UserDataKeyDTO> userDataKeyDTOS;


    @Autowired
    public UserDataControllerTest(UserRepository userRepository, UserDataKeyService userDataKeyService, UserDataRepository userDataRepository, RoleRepository roleRepository, UserService userService) {
        this.userDataRepository = userDataRepository;
        this.userDataKeyService = userDataKeyService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @BeforeEach
    void saveUsersAndDataKeys() {
        Role role = roleRepository.save(roleMapper.roleDTOToRole(getTestRole()));
        this.roleID = role.getId();
        RoleDTO roleDTO = getTestRole();
        roleDTO.setId(roleID);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Joan");
        userDTO.setUserName("jjanku");
        userDTO.setLastName("Janku");
        userDTO.setEmail("jjanku@jjanku.com");
        userDTO.setRoles(List.of(roleDTO));
        userDTO.setUserPassword("PASSWORD");
        this.userDTO = userService.save(userDTO);

        userDataKeyDTOS = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UserDataKeyDTO userDataKeyDTO = new UserDataKeyDTO();
            userDataKeyDTO.setKeyName(TEST + i);
            userDataKeyDTOS.add(userDataKeyService.save(userDataKeyDTO));
        }
    }

    @AfterEach
    void deleteEverything() {
        userDataKeyDTOS.clear();
        userRepository.deleteAll();
        userDataRepository.deleteAll();
        userDataRepository.deleteAll();
    }

    @Override
    <T extends BaseDTO> void assertObjectsEqual(T dto, T expectedDTO) {
        if (dto instanceof UserDataDTO && expectedDTO instanceof UserDataDTO) {
            assertEquals(((UserDataDTO) dto).getKeyValue(), ((UserDataDTO) expectedDTO).getKeyValue());
            assertEquals(((UserDataDTO) dto).getUserDTO().getId(), ((UserDataDTO) expectedDTO).getUserDTO().getId());
            assertEquals(((UserDataDTO) dto).getUserDataKey().getId(), ((UserDataDTO) expectedDTO).getUserDataKey().getId());
        }
    }

    private UserDataDTO responseEntityToDTO(ResponseEntity<String> responseEntity) {
        try {
            return responseEntityToDTO(responseEntity, UserDataDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void postUserData() {
        List<UserDataDTO> userDataDTOList = getUserTestData();
        List<UserDataDTO> userDataDTOResponseList = userDataDTOList.stream().map(this::postUserData).map(this::responseEntityToDTO).collect(Collectors.toList());
        assertEquals(userDataDTOList.size(), userDataDTOResponseList.size());
        for (Iterator<UserDataDTO> udI = userDataDTOList.listIterator(), udRespI = userDataDTOResponseList.listIterator(); udI.hasNext() && udRespI.hasNext(); ) {
            UserDataDTO expectedUserDataDTO = udI.next();
            UserDataDTO actualUserDataDTO = udRespI.next();
            assertSuccessfulAndCorrectResponse(expectedUserDataDTO,actualUserDataDTO);
        }
    }


    private ResponseEntity<String> postUserData(UserDataDTO userDataDTO) {
        try {
            ResponseEntity<String> resp = post(USER_DATA_URL, userDataDTO);
            assertEquals(resp.getStatusCode(), HttpStatus.OK);
            log.info("Gotten response from server {} ", resp);
            return resp;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UserDataDTO> getUserTestData() {
        return userDataKeyDTOS.stream().map(udk -> dataKeyToUserData(udk, userDTO, getRandomString(TEST))).collect(Collectors.toList());
    }

    private UserDataDTO dataKeyToUserData(UserDataKeyDTO userDataKeyDTO, UserDTO userDTO, String value) {
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setUserDataKey(userDataKeyDTO);
        userDataDTO.setUserDTO(userDTO);
        userDataDTO.setKeyValue(value);
        return userDataDTO;
    }

    private String getRandomString(String random) {
        return random.concat(Instant.now().toString());
    }


}
