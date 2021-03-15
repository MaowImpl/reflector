package dev.maow.reflector.api.factory;

import dev.maow.reflector.impl.ConstructorReflector;
import dev.maow.reflector.impl.FieldReflector;
import dev.maow.reflector.impl.MethodReflector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@ApiStatus.NonExtendable
public interface ReflectorFactory {
    ConstructorReflector constructor(@NotNull Class<?>... parameters);

    void constructors(Consumer<ConstructorReflector> consumer);

    MethodReflector method(@NotNull String name, @NotNull Class<?>... parameters);

    void methods(Consumer<MethodReflector> consumer);

    FieldReflector field(@NotNull String name);

    void fields(Consumer<FieldReflector> consumer);
}
