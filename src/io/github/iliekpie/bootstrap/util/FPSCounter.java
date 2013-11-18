package io.github.iliekpie.bootstrap.util;

import org.lwjgl.Sys;

public abstract class FPSCounter {
    private long lastFPSCheck = 0L;
    private int frames = 0;
    private int fps = 0;

    public FPSCounter() {
        lastFPSCheck = Sys.getTime();
    }

    public void tick() {
        if ((Sys.getTime() - lastFPSCheck) > 1000L) {
            fps = frames;
            onFPSUpdate(fps);

            frames = 0;
            lastFPSCheck += 1000L;
        }
        frames++;
    }

    public int getFPS() {
        return fps;
    }

    public abstract void onFPSUpdate(int fps);

}
