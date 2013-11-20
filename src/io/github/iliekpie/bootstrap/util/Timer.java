package io.github.iliekpie.bootstrap.util;

import org.lwjgl.Sys;

public class Timer {
    private long lastTime = 0L;

    public Timer() {
        lastTime = getTime();
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    //returns ms?
    public float getDeltaTime() {
        long currentTime = getTime();
        float delta = (float) (currentTime - lastTime);
        lastTime = currentTime;

        return (delta/1000);
    }
}
