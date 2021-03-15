package dev.maow.reflector.impl;

import dev.maow.reflector.api.Reflector;
import dev.maow.reflector.util.Reflectors;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldReflector implements Reflector {
    private final Class<?> clazz;
    private final Object target;
    private final String name;

    private boolean accessible;
    private Field cached;

    public FieldReflector(Class<?> clazz, Object target, String name) {
        this.clazz = clazz;
        this.target = target;
        this.name = name;
    }

    public <T> T cast(Class<T> type) {
        return type.cast(getSafe());
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) getSafe();
    }

    public Object getSafe() {
        return Reflectors.getStrategy()
                .fieldGet(clazz, target, name, accessible);
    }

    public void set(Object value) {
        Reflectors.getStrategy()
                .fieldSet(clazz, target, name, value, accessible);
    }

    @Override
    public int modifiers() {
        return field().getModifiers();
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull FieldReflector accessible() {
        accessible = true;
        return this;
    }

    @Override
    public <V extends Annotation> V annotation(@NotNull Class<V> clazz) {
        if (field().isAnnotationPresent(clazz))
            return field().getAnnotation(clazz);
        return null;
    }

    public Class<?> clazz() {
        return clazz;
    }

    public Object target() {
        return target;
    }

    public Class<?> type() {
        return field().getType();
    }

    public Field field() {
        if (cached == null) {
            try {
                cached = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return cached;
    }
}