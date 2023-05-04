package com.onlinecv.userservice.online_cv.validations;

import com.onlinecv.userservice.base.repository.BaseRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class ValidationAspect {

    private static final String REPOSITORY = "Repository";
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BaseRepository baseRepository;

    @Around("@annotation(com.onlinecv.userservice.online_cv.validations.Validate)")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws IllegalAccessException {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Validate validate = Objects.requireNonNull(signature.getMethod().getAnnotation(Validate.class));
        String fieldName = validate.field();
        Class<?> entity = validate.entity();
        String entityClassName = entity.getSimpleName();
        Object o = proceedingJoinPoint.getArgs()[validate.argumentPos()];

        if (validate.value().equals(Validation.UNIQUE)) {
            baseRepository.findBy(entityClassName, fieldName, readValue(o, fieldName));
        }
        return null;
    }

    private Object readValue(Object o, String fieldName) throws IllegalAccessException {
        Field f = Arrays.stream(o.getClass().getDeclaredFields()).filter(t -> t.getName().equals(fieldName)).findFirst().orElse(null);
        f.setAccessible(true);
        Object s = f.get(o);
        return s;
    }
}
