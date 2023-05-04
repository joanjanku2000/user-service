package com.onlinecv.userservice.base.resources;

import com.onlinecv.userservice.base.dto.BaseDTO;
import org.springframework.http.ResponseEntity;

public interface BaseController<T extends BaseDTO> {

    ResponseEntity<T> save(T dto);

    ResponseEntity<T> update(T dto);

    ResponseEntity<T> findById(Long id);

    void delete(Long id);
}
