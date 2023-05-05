package com.onlinecv.userservice.online_cv.constants;

import com.onlinecv.userservice.base.exceptions.constants.ExceptionMessages;

public final class NotFoundExceptionMessages extends ExceptionMessages {
    public static final String ROLE_NOT_FOUND = NOT_FOUND.replace(ENTITY, "Role");
    public static final String USER_DATA_KEY_NOT_FOUND = NOT_FOUND.replace(ENTITY, "UserDataKey");
}
