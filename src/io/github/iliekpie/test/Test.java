package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.util.NativeLoader;

public class Test {
    public static void main(String... args) {
        NativeLoader.load();
        new Test();
    }

    public Test() {
        GameScreen screen = new GameScreen(800, 600);
        screen.run();
    }
}
