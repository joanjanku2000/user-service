package com.onlinecv.userservice.online_cv.validations;

import com.onlinecv.userservice.base.repository.BaseRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class ValidationAspect {

    private final BaseRepository baseRepository;

    @Autowired
    public ValidationAspect(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Around("@annotation(com.onlinecv.userservice.online_cv.validations.Validations)")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Validations validationAnnotation = Objects.requireNonNull(signature.getMethod().getAnnotation(Validations.class));
        for (Validate validateAnnotation : Arrays.stream(validationAnnotation.validations()).collect(Collectors.toList())) {
            String fieldName = validateAnnotation.field();
            String entityClassName = validateAnnotation.entity().getSimpleName();
            Object o = proceedingJoinPoint.getArgs()[validateAnnotation.argumentPos()];

            if (validateAnnotation.validation().equals(Validation.UNIQUE)) {
                if (baseRepository.findBy(entityClassName, fieldName, readValue(o, fieldName)).size() > 0) {
                    // stop execution
                    throw new RuntimeException();
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    private Object readValue(Object o, String fieldName) throws IllegalAccessException {
        Field f = Objects.requireNonNull(Arrays.stream(o.getClass().getDeclaredFields()).filter(t -> t.getName().equals(fieldName)).findFirst().orElse(null));
        f.setAccessible(true);
        return f.get(o);
    }
}
