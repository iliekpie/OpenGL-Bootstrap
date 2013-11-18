package io.github.iliekpie.bootstrap.graphics;

public class Vertex {
    //Vertex data
    protected float[] xyzw = new float[] {0f, 0f, 0f, 1f};
    //protected float[] rgba = new float[] {1f, 1f, 1f, 1f};
    protected Color color = new Color();


    //Element size in bytes
    public static final int elementBytes = 4;

    //Elements per parameter
    public static final int positionElementCount = 4;
    public static final int colorElementCount = 4;

    //Bytes per parameter
    public static final int positionBytesCount = positionElementCount * elementBytes;
    public static final int colorByteCount = colorElementCount * elementBytes;

    //Byte offsets per parameter
    public static final int positionByteOffset = 0;
    public static final int colorByteOffset = positionBytesCount; //positionByteOffset + positionBytesCount

    //The amount of elements that a vertex has
    public static int elementCount = positionElementCount + colorElementCount;
    //The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    public static int stride = positionBytesCount + colorByteCount;

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
        this.xyzw = new float[] {x, y, z, w};
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
        out[i++] = this.xyzw[0];
        out[i++] = this.xyzw[1];
        out[i++] = this.xyzw[2];
        out[i++] = this.xyzw[3];

        //Insert RGBA elements
        out[i++] = this.color.getRed();
        out[i++] = this.color.getGreen();
        out[i++] = this.color.getBlue();
        out[i] = this.color.getAlpha();

        return out;
    }

    public float[] getXYZW() {
        return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getRGBA() {
        return new float[] {this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()};
    }

    public Color getColor() {
        return color;
    }
}
