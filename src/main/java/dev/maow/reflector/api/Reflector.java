package dev.maow.reflector.api;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface Reflector {
    int modifiers();

    String name();

    @NotNull Reflector accessible();

    <T extends Annotation> T annotation(@NotNull Class<T> clazz);
}
