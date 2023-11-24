package com.trip.penguin.interceptor.annotation;

import lombok.NonNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionCheck {
    @NonNull String versionKey();
    @NonNull String versionValue();
}
