package io.github.iliekpie.bootstrap.graphics;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Vertex {
    //Vertex data
    protected Vector4f xyzw = new Vector4f();
    protected Color color = new Color();

    //Element size in bytes
    public static final int elementBytes = 4;

    //Elements per parameter
    public static final int positionElementCount = 4;
    public static final int colorElementCount = 4;

    //Bytes per parameter
    public static final int positionByteCount = positionElementCount * elementBytes;
    public static final int colorByteCount = colorElementCount * elementBytes;

    //Byte offsets per parameter
    public static final int positionByteOffset = 0;
    public static final int colorByteOffset = positionByteCount; //positionByteOffset + positionBytesCount

    //The amount of elements that a vertex has
    public static int elementCount = positionElementCount + colorElementCount;
    //The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    public static int stride = positionByteCount + colorByteCount;

    //Setters
    public Vertex setXYZ(float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);
        return this;
    }

    public Vertex setRGB(float r, float g, float b) {
        this.color.setRGB(r, g, b);
        return this;
    }

    public Vertex setXYZW(float x, float y, float z, float w) {
        this.xyzw = new Vector4f(x, y, z, w);
        return this;
    }

    public Vertex setPosition(Vector3f position) {
        this.xyzw = new Vector4f(position.getX(), position.getY(), position.getZ(), 1);
        return this;
    }

    public Vertex setRGBA(float r, float g, float b, float a) {
        this.color.setRGBA(r, g, b, a);
        return this;
    }

    public Vertex setColor(Color color) {
        this.color = color;
        return this;
    }

    //Getters
    public float[] getElements() {
        float[] out = new float[Vertex.elementCount];
        int i = 0;

        //Insert XYZW elements
        out[i++] = this.xyzw.getX();
        out[i++] = this.xyzw.getY();
        out[i++] = this.xyzw.getZ();
        out[i++] = this.xyzw.getW();

        //Insert RGBA elements
        out[i++] = this.color.getRed();
        out[i++] = this.color.getGreen();
        out[i++] = this.color.getBlue();
        out[i] = this.color.getAlpha();

        return out;
    }

    public Vector3f getPosition() {
        return new Vector3f(this.xyzw.getX(), this.xyzw.getY(), this.xyzw.getZ());
    }

    public float[] getXYZW() {
        return new float[]{this.xyzw.getX(), this.xyzw.getY(), this.xyzw.getZ(), this.xyzw.getW()};
    }

    public float[] getRGBA() {
        return new float[] {this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()};
    }

    public Color getColor() {
        return color;
    }
}
