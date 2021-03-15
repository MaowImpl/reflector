package dev.maow.reflector.api.access;

import dev.maow.reflector.impl.access.ReflectionAccessStrategy;

public enum AccessMode {
    REFLECTION(new ReflectionAccessStrategy()),
    ;

    private final AccessStrategy strategy;

    AccessMode(AccessStrategy strategy) {
        this.strategy = strategy;
    }

    public AccessStrategy getStrategy() {
        return strategy;
    }
}
