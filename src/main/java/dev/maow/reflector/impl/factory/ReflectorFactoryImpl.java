package dev.maow.reflector.impl.factory;

import dev.maow.reflector.api.factory.ReflectorFactory;
import dev.maow.reflector.impl.ConstructorReflector;
import dev.maow.reflector.impl.FieldReflector;
import dev.maow.reflector.impl.MethodReflector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class ReflectorFactoryImpl implements ReflectorFactory {
    private final Class<?> clazz;
    private final Object obj;

    public ReflectorFactoryImpl(Class<?> clazz, Object obj) {
        this.clazz = clazz;
        this.obj = obj;
    }

    @Override
    public ConstructorReflector constructor(@NotNull Class<?>... parameters) {
        return new ConstructorReflector(clazz, parameters);
    }

    @Override
    public void constructors(Consumer<ConstructorReflector> consumer) {
        Arrays
                .stream(clazz.getDeclaredConstructors())
                .map(constructor ->
                        new ConstructorReflector(clazz,
                                constructor.getParameterTypes()))
                .forEach(consumer);
    }

    @Override
    public MethodReflector method(@NotNull String name, @NotNull Class<?>... parameters) {
        return new MethodReflector(clazz, obj, name, parameters);
    }

    @Override
    public void methods(Consumer<MethodReflector> consumer) {
        Arrays
                .stream(clazz.getDeclaredMethods())
                .map(method -> new MethodReflector(
                        clazz, obj,
                        method.getName(), method.getParameterTypes()
                ))
                .forEach(consumer);
    }

    @Override
    public FieldReflector field(@NotNull String name) {
        return new FieldReflector(clazz, obj, name);
    }

    @Override
    public void fields(Consumer<FieldReflector> consumer) {
        Arrays
                .stream(clazz.getDeclaredFields())
                .map(field -> new FieldReflector(clazz, obj, field.getName()))
                .forEach(consumer);
    }
}