package dev.maow.reflector.test;

import dev.maow.reflector.api.factory.ReflectorFactory;
import dev.maow.reflector.impl.FieldReflector;
import dev.maow.reflector.util.Reflectors;

public class Test {
    public static void main(String[] args) {
        final ReflectorFactory factory = Reflectors.create(Target.class);
        final String memeSchool = factory
                .field("memeSchool")
                .get();
        factory
                .method("staticPrint", String.class)
                .call(memeSchool);
        final Target target = new Target();
        final ReflectorFactory instanceFactory = Reflectors.create(target);
        final FieldReflector willSmith = instanceFactory
                .field("thisFieldWontBeFinalAnymoreHehe")
                .accessible();
        instanceFactory
                .method("print", String.class)
                .call(willSmith.getSafe());
        willSmith.set("Will Smith has been assassinated.");
        instanceFactory
                .method("privatePrint", String.class)
                .accessible()
                .call(willSmith.getSafe());
    }
}