package io.github.iliekpie.bootstrap.util;

import org.lwjgl.Sys;

public abstract class FPSCounter {
    private long lastFPSCheck = 0L;
    private int frames = 0;
    private int fps = 0;

    public FPSCounter() {
        lastFPSCheck = Sys.getTime();
    }

    //returns ms
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Increments a counter every tick and calculates FPS every second.
     * Call this function every frame.
     */
    public void tick() {
        if ((getTime() - lastFPSCheck) > 1000L) {
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
