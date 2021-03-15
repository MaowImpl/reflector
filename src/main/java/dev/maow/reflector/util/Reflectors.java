package dev.maow.reflector.util;

import dev.maow.reflector.api.access.AccessMode;
import dev.maow.reflector.api.access.AccessStrategy;
import dev.maow.reflector.api.factory.ReflectorFactory;
import dev.maow.reflector.impl.factory.ReflectorFactoryImpl;
import org.jetbrains.annotations.NotNull;

public final class Reflectors {
    private static AccessStrategy strategy = AccessMode.REFLECTION.getStrategy();

    private Reflectors() { throw new UnsupportedOperationException("Could not instantiate utility class 'dev.maow.reflector.util.Reflectors'"); }

    public static void mode(AccessMode mode) {
        Reflectors.strategy = mode.getStrategy();
    }

    public static void strategy(AccessStrategy strategy) {
        Reflectors.strategy = strategy;
    }

    public static @NotNull ReflectorFactory create(@NotNull Class<?> clazz) {
        return new ReflectorFactoryImpl(clazz, null);
    }

    public static @NotNull ReflectorFactory create(@NotNull Object obj) {
        return new ReflectorFactoryImpl(obj.getClass(), obj);
    }

    public static AccessStrategy getStrategy() {
        return strategy;
    }
}