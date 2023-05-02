package com.onlinecv.userservice.base.service;

import com.onlinecv.userservice.base.dto.BaseDTO;

public interface BaseService<T extends BaseDTO> {
    void save(T dto);
    void update(T dto);
    T findById(Long id);
    void delete(Long id);
}
