package com.onlinecv.userservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UserDataKey (Long id, String keyName) {
}
