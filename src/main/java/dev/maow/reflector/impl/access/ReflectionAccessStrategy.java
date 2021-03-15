package dev.maow.reflector.impl.access;

import dev.maow.reflector.api.access.AccessStrategy;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;

public class ReflectionAccessStrategy implements AccessStrategy {
    private static Field modifiers;

    @Override
    public Object constructorInvoke(
            @NotNull Class<?> clazz, Class<?>[] parameters, boolean accessible, @NotNull Object... args
    ) {
        try {
            final Constructor<?> constructor =
                    clazz.getDeclaredConstructor(parameters);
            if (accessible) constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object methodInvoke(
            @NotNull Class<?> clazz, @NotNull Object target,
            @NotNull String name, @NotNull Class<?>[] parameters,
            boolean accessible,
            @NotNull Object... args
    ) {
        try {
            final Method method = clazz.getDeclaredMethod(name, parameters);
            if (accessible) method.setAccessible(true);
            return method.invoke(target, args);
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object fieldGet(@NotNull Class<?> clazz, @NotNull Object target, @NotNull String name, boolean accessible) {
        try {
            final Field field = clazz.getDeclaredField(name);
            if (accessible) field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void fieldSet(
            @NotNull Class<?> clazz, @NotNull Object target,
            @NotNull String name, @NotNull Object value,
            boolean accessible
    ) {
        try {
            final Field field = clazz.getDeclaredField(name);
            if (accessible) field.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers())) {
                finalFieldSet(target, field, value);
                return;
            }
            field.set(target, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void finalFieldSet(@NotNull Object target, @NotNull Field field, @NotNull Object value) {
        try {
            if (modifiers == null) {
                modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);
            }
            modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}