package com.onlinecv.userservice.base.dto;

import com.onlinecv.userservice.base.entity.BaseEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BaseMapper {

    BaseEntity dtoToEntity(BaseDTO baseDto);

    BaseDTO entityToDto(BaseEntity baseEntity);
}
