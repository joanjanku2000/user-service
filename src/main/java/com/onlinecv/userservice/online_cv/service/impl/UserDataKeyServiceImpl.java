package com.onlinecv.userservice.online_cv.service.impl;

import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.online_cv.model.dto.UserDataKeyDTO;
import com.onlinecv.userservice.online_cv.model.entity.UserDataKey;
import com.onlinecv.userservice.online_cv.model.mapper.UserDataKeyMapper;
import com.onlinecv.userservice.online_cv.repository.UserDataKeyRepository;
import com.onlinecv.userservice.online_cv.service.UserDataKeyService;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.USER_DATA_KEY_NOT_FOUND;


@Service
public class UserDataKeyServiceImpl implements UserDataKeyService {
    private final UserDataKeyRepository userDataKeyRepository;
    private final UserDataKeyMapper userDataKeyMapper = Mappers.getMapper(UserDataKeyMapper.class);

    public UserDataKeyServiceImpl(UserDataKeyRepository userDataKeyRepository) {
        this.userDataKeyRepository = userDataKeyRepository;
    }

    @Override
    public UserDataKeyDTO save(UserDataKeyDTO dto) {
        UserDataKey userDataKey = userDataKeyMapper.toEntity(dto);
        return userDataKeyMapper.toDTO(userDataKeyRepository.save(userDataKey));
    }

    @Override
    public UserDataKeyDTO update(UserDataKeyDTO dto) {
        UserDataKey userDataKey = userDataKeyMapper.toEntityForUpdate(dto, userDataKeyRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(String.format(USER_DATA_KEY_NOT_FOUND, dto.getId()))));
        return userDataKeyMapper.toDTO(userDataKeyRepository.save(userDataKey));
    }

    @Override
    public UserDataKeyDTO findById(Long id) {
        return userDataKeyMapper.toDTO(userDataKeyRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(USER_DATA_KEY_NOT_FOUND, id))));
    }

    @Override
    public void delete(Long id) {
        userDataKeyRepository.deleteById(id);
    }
}
