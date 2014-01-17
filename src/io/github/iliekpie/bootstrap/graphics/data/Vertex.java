package io.github.iliekpie.bootstrap.graphics.data;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

//TODO: Refactor to allow textures/normals?
public class Vertex {
    //Vertex data
    protected Vector4f position = new Vector4f();
    protected Vector3f normal = new Vector3f();
    protected Vector2f uv = new Vector2f();

    //Element size in bytes
    public static final int elementBytes = 4;

    //Elements per parameter
    public static final int positionElementCount = 4;
    public static final int normalElementCount = 3;
    public static final int texCoordElementCount = 2;

    //Bytes per parameter
    public static final int positionByteCount = positionElementCount * elementBytes;
    public static final int normalByteCount = normalElementCount * elementBytes;
    public static final int texCoordByteCount = texCoordElementCount * elementBytes;

    //Byte offsets per parameter
    public static final int positionByteOffset = 0;
    public static final int normalByteOffset = positionByteCount; //positionByteOffset + positionBytesCount
    public static final int texCoordByteOffset = normalByteOffset + normalByteCount;

    //The amount of elements that a vertex has
    public static int elementCount = positionElementCount + normalElementCount + texCoordElementCount;
    //The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    public static int stride = positionByteCount + normalByteCount + texCoordByteCount;

    //Setters
    public Vertex setXYZ(float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);
        return this;
    }

    public Vertex setXYZW(float x, float y, float z, float w) {
        this.position = new Vector4f(x, y, z, w);
        return this;
    }

    public Vertex setPosition(Vector3f position) {
        this.position = new Vector4f(position.getX(), position.getY(), position.getZ(), 1);
        return this;
    }

    public Vertex setNormal(float x, float y, float z) {
        this.normal = new Vector3f(x, y, z);
        return this;
    }

    public Vertex setUV(float u, float v) {
        this.uv = new Vector2f(u, v);
        return this;
    }

    /*public Vertex setRGB(float r, float g, float b) {
        this.color.setRGB(r, g, b);
        return this;
    }

    /*public Vertex setRGBA(float r, float g, float b, float a) {
        this.color.setRGBA(r, g, b, a);
        return this;
    }

    public Vertex setColor(Color color) {
        this.color = color;
        return this;
    }*/

    //Getters
    public float[] getElements() {
        float[] out = new float[Vertex.elementCount];
        int i = 0;

        //Insert XYZW elements
        out[i++] = this.position.getX();
        out[i++] = this.position.getY();
        out[i++] = this.position.getZ();
        out[i++] = this.position.getW();

        out[i++] = this.normal.getX();
        out[i++] = this.normal.getY();
        out[i++] = this.normal.getZ();

        out[i++] = this.uv.getX();
        out[i] = this.uv.getY();

        /*//Insert RGBA elements
        out[i++] = this.color.getRed();
        out[i++] = this.color.getGreen();
        out[i++] = this.color.getBlue();
        out[i] = this.color.getAlpha();*/

        return out;
    }

    public Vector3f getPosition() {
        return new Vector3f(this.position.getX(), this.position.getY(), this.position.getZ());
    }

    public float[] getXYZW() {
        return new float[]{this.position.getX(), this.position.getY(), this.position.getZ(), this.position.getW()};
    }

    public float[] getNormal() {
        return new float[]{this.normal.getX(), this.normal.getY(), this.normal.getZ()};
    }

    public float[] getUV() {
        return new float[]{this.uv.getX(), this.uv.getY()};
    }

    /*public float[] getRGBA() {
        return new float[] {this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()};
    }

    public Color getColor() {
        return color;
    }*/
}