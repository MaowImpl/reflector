package dev.maow.reflector.impl;

import dev.maow.reflector.api.Reflector;
import dev.maow.reflector.util.Reflectors;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorReflector implements Reflector {
    private final Class<?> clazz;
    private final Class<?>[] parameters;

    private boolean accessible;
    private Constructor<?> cached;

    public ConstructorReflector(Class<?> clazz, Class<?>[] parameters) {
        this.clazz = clazz;
        this.parameters = parameters;
    }

    public <T> T cast(Class<T> type, Object... args) {
        return type.cast(instanceSafe(args));
    }

    @SuppressWarnings("unchecked")
    public <T> T instance(Object... args) {
        return (T) instanceSafe(args);
    }

    public Object instanceSafe(Object... args) {
        return Reflectors.getStrategy()
                .constructorInvoke(clazz, parameters, accessible, args);
    }

    @Override
    public int modifiers() {
        return constructor().getModifiers();
    }

    @Override
    public String name() {
        return constructor().getName();
    }

    @Override
    public @NotNull ConstructorReflector accessible() {
        accessible = true;
        return this;
    }

    @Override
    public <V extends Annotation> V annotation(@NotNull Class<V> clazz) {
        if (constructor().isAnnotationPresent(clazz))
            return constructor().getAnnotation(clazz);
        return null;
    }

    public Class<?> clazz() {
        return clazz;
    }

    public Class<?>[] parameters() {
        return parameters;
    }

    public Constructor<?> constructor() {
        if (cached == null) {
            try {
                cached = clazz.getDeclaredConstructor(parameters);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return cached;
    }
}