package com.dmdev.junit.service.extension;

import com.dmdev.junit.service.UserService;
import lombok.Getter;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class PostProcEx implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.out.println("postProcessTestInstance");
        Field[] declaredFields = testInstance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Getter.class)) {
                field.set(testInstance, new UserService(null));
            }
        }
    }
}
