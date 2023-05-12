package com.onlinecv.userservice.online_cv.service.impl;

import com.onlinecv.userservice.base.exceptions.BadRequestException;
import com.onlinecv.userservice.base.exceptions.NotFoundException;
import com.onlinecv.userservice.online_cv.model.dto.UserDataDTO;
import com.onlinecv.userservice.online_cv.model.entity.UserData;
import com.onlinecv.userservice.online_cv.model.mapper.UserDataMapper;
import com.onlinecv.userservice.online_cv.repository.UserDataKeyRepository;
import com.onlinecv.userservice.online_cv.repository.UserDataRepository;
import com.onlinecv.userservice.online_cv.repository.UserRepository;
import com.onlinecv.userservice.online_cv.service.UserDataService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.onlinecv.userservice.online_cv.constants.NotFoundExceptionMessages.*;
import static java.util.Objects.requireNonNull;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final UserDataKeyRepository userDataKeyRepository;
    private final UserDataMapper userDataMapper = Mappers.getMapper(UserDataMapper.class);

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository, UserRepository userRepository, UserDataKeyRepository userDataKeyRepository) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.userDataKeyRepository = userDataKeyRepository;
    }

    @Override
    public UserDataDTO save(UserDataDTO dto) {
        UserData userData = userDataMapper.toEntity(dto, userRepository.findById(requireNonNull(dto.getUserDTO()).getId()).orElseThrow(() -> new BadRequestException(String.format(USER_NOT_FOUND, requireNonNull(dto.getUserDTO()).getId()))), userDataKeyRepository.findById(requireNonNull(dto.getUserDataKey()).getId()).orElseThrow(() -> new BadRequestException(String.format(USER_DATA_KEY_NOT_FOUND, requireNonNull(dto.getUserDataKey()).getId()))));
        return userDataMapper.toDTO(userDataRepository.save(userData));
    }

    @Override
    public UserDataDTO update(UserDataDTO dto) {
        UserData userData = userDataMapper.toEntityForUpdate(userDataRepository.findById(requireNonNull(dto.getId())).orElseThrow(() -> new NotFoundException(String.format(USER_DATA_NOT_FOUND, dto.getId()))), dto);
        return userDataMapper.toDTO(userDataRepository.save(userData));
    }

    @Override
    public UserDataDTO findById(Long id) {
        return userDataMapper.toDTO(userDataRepository.findById(requireNonNull(id)).orElseThrow(() -> new NotFoundException(String.format(USER_DATA_NOT_FOUND, id))));
    }

    @Override
    public void delete(Long id) {
        userDataRepository.deleteById(id);
    }
}
