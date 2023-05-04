package com.onlinecv.userservice.base.repository;

import com.onlinecv.userservice.base.entity.BaseEntity;

import java.util.List;

public interface BaseRepository {
    List<BaseEntity> findBy(String entityName, String columnName, Object value);
}
