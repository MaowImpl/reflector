package dev.maow.reflector.impl;

import dev.maow.reflector.api.Reflector;
import dev.maow.reflector.util.Reflectors;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodReflector implements Reflector {
    private final Class<?> clazz;
    private final Object target;
    private final String name;
    private final Class<?>[] parameters;

    private boolean accessible;
    private Method cached;

    public MethodReflector(Class<?> clazz, Object target, String name, Class<?>[] parameters) {
        this.clazz = clazz;
        this.target = target;
        this.name = name;
        this.parameters = parameters;
    }

    public <T> T cast(Class<T> type, Object... args) {
        return type.cast(invokeSafe(args));
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(Object... args) {
        return (T) invokeSafe(args);
    }

    public Object invokeSafe(Object... args) {
        return Reflectors.getStrategy()
                .methodInvoke(clazz, target, name, parameters, accessible, args);
    }

    public void call(Object... args) {
        Reflectors.getStrategy()
                .methodInvoke(clazz, target, name, parameters, accessible, args);
    }

    @Override
    public int modifiers() {
        return method().getModifiers();
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull MethodReflector accessible() {
        accessible = true;
        return this;
    }

    @Override
    public <V extends Annotation> V annotation(@NotNull Class<V> clazz) {
        if (method().isAnnotationPresent(clazz))
            return method().getAnnotation(clazz);
        return null;
    }

    public Class<?> clazz() {
        return clazz;
    }

    public Object target() {
        return target;
    }

    public Class<?>[] parameters() {
        return parameters;
    }

    public Method method() {
        if (cached == null) {
            try {
                cached = clazz.getDeclaredMethod(name, parameters);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return cached;
    }
}