package com.onlinecv.userservice.online_cv.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.online_cv.model.dto.UserDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.model.entity.AppUser;
import com.onlinecv.userservice.online_cv.model.entity.UserData;
import com.onlinecv.userservice.online_cv.model.entity.UserDataKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper(uses = BaseMapper.class)
public interface UserDataMapper {


    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    UserDataKeyMapper userDataKeyMapper = Mappers.getMapper(UserDataKeyMapper.class);

    @Named("userDTO")
    static UserDTO toUserDTO(AppUser appUser) {
        return userMapper.toDTO(appUser);
    }

    @Named("dataKeyDTO")
    static UserDataKeyDTO toDataKeyDTO(UserDataKey userDataKey) {
        return userDataKeyMapper.toDTO(userDataKey);
    }

    default UserData toEntity(UserDataDTO dto, AppUser appUser, UserDataKey userDataKey) {
        UserData userData = new UserData();
        userData.setKeyValue(dto.getKeyValue());
        userData.setUser(appUser);
        userData.setUserDataKey(userDataKey);
        return userData;
    }

    @Mapping(source = "user", target = "userDTO", qualifiedByName = "userDTO")
    @Mapping(source = "userDataKey", target = "userDataKey", qualifiedByName = "dataKeyDTO")
    UserDataDTO toDTO(UserData userData);

    default UserData toEntityForUpdate(UserData userData, UserDataDTO userDataDTO) {
        userData.setKeyValue(userDataDTO.getKeyValue());
        return userData;
    }
}
