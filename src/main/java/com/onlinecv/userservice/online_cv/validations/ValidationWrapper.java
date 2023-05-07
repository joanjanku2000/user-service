package com.onlinecv.userservice.online_cv.validations;

public class ValidationWrapper {
    Validation validation;
    String field;

    public ValidationWrapper(Validation validation, String field) {
        this.validation = validation;
        this.field = field;
    }

    public static ValidationWrapper getValidationWrapper(Validation validation, String field) {
        return new ValidationWrapper(validation, field);
    }
}
