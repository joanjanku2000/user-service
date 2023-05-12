package com.onlinecv.userservice.online_cv.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.model.entity.AppUser;
import com.onlinecv.userservice.online_cv.model.entity.UserData;
import com.onlinecv.userservice.online_cv.model.entity.UserDataKey;
import org.mapstruct.Mapper;


@Mapper(uses = BaseMapper.class)
public interface UserDataMapper {


    default UserData toEntity(UserDataDTO dto, AppUser appUser, UserDataKey userDataKey) {
        UserData userData = new UserData();
        userData.setKeyValue(dto.getKeyValue());
        userData.setUser(appUser);
        userData.setUserDataKey(userDataKey);
        return userData;
    }

    UserDataDTO toDTO(UserData userData);

    default UserData toEntityForUpdate(UserData userData, UserDataDTO userDataDTO) {
        userData.setKeyValue(userDataDTO.getKeyValue());
        return userData;
    }
}
