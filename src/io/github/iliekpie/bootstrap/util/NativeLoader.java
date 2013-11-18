package io.github.iliekpie.bootstrap.util;

public class NativeLoader {
    public static void load() {
        System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "\\lib\\native\\" + getOS());
    }

    private static String getOS() {
        String rawOS = System.getProperty("os.name").toLowerCase();
        if (rawOS.contains("win")) {
            return "windows";
        } else if (rawOS.contains("mac")) {
            return "mac";
        } else if (rawOS.contains("nux")) {
            return "linux";
        } else {
            return "other";
        }
    }
}
