package com.onlinecv.userservice.base.exceptions.constants;

public final class NotFoundMessage {
    private static final String ENTITY = "#ENTITY";
    private static final String NOT_FOUND = ENTITY + " with id %d not found";
    public static final String ROLE_NOT_FOUND = NOT_FOUND.replace(ENTITY,"Role");
}
