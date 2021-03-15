package dev.maow.reflector.api.access;

import org.jetbrains.annotations.NotNull;

public interface AccessStrategy {
    Object constructorInvoke(
            @NotNull Class<?> clazz,
            @NotNull Class<?>[] parameters,
            boolean accessible,
            @NotNull Object... args
    );

    Object methodInvoke(
            @NotNull Class<?> clazz,
            @NotNull Object target,
            @NotNull String name,
            @NotNull Class<?>[] parameters,
            boolean accessible,
            @NotNull Object... args
    );

    Object fieldGet(
            @NotNull Class<?> clazz,
            @NotNull Object target,
            @NotNull String name,
            boolean accessible
    );

    void fieldSet(
            @NotNull Class<?> clazz,
            @NotNull Object target,
            @NotNull String name,
            @NotNull Object value,
            boolean accessible
    );
}