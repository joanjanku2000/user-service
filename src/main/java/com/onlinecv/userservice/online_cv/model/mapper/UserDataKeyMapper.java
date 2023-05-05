package com.onlinecv.userservice.online_cv.model.mapper;

import com.onlinecv.userservice.base.dto.BaseMapper;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.model.entity.UserDataKey;
import org.mapstruct.Mapper;

@Mapper(uses = BaseMapper.class)
public interface UserDataKeyMapper {

    UserDataKey toEntity(UserDataKeyDTO userDataKeyDTO);

    UserDataKeyDTO toDTO(UserDataKey userDataKey);

    default UserDataKey toEntityForUpdate(UserDataKeyDTO userDataKeyDTO, UserDataKey userDataKey) {
        userDataKey.setKeyName(userDataKeyDTO.getKeyName());
        return userDataKey;
    }
}
