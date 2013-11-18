package io.github.iliekpie.bootstrap.graphics;

public class Color {
    private float red = 1.0f;
    private float green = 1.0f;
    private float blue = 1.0f;
    private float alpha = 1.0f;

    public Color() {

    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }

    public Color(float r, float g, float b, float a) {
        setRGBA(r, g, b, a);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 1);
    }

    public Color(int r, int g, int b, int a) {
        setRGBA(convertRange(r), convertRange(g), convertRange(b), convertRange(a));
    }

    private float convertRange(int value) {
        return (float)value / 255.0f;
    }

    private float clampValue(float value) {
        if (value < 0.0f) return 0.0f;
        if (value > 1.0f) return 1.0f;
        return value;
    }

    public void setRGB(float r, float g, float b) {
        setRGBA(r, g, b, 1.0f);
    }

    public void setRGBA(float r, float g, float b, float a) {
        setRed(r);
        setGreen(g);
        setBlue(b);
        setAlpha(a);
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setRed(float value) {
        red = clampValue(value);
    }

    public void setGreen(float value) {
        green = clampValue(value);
    }

    public void setBlue(float value) {
        blue = clampValue(value);
    }

    public void setAlpha(float value) {
        alpha = clampValue(value);
    }
}
