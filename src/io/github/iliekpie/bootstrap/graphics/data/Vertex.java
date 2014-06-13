package io.github.iliekpie.bootstrap.graphics.data;

import io.github.iliekpie.bootstrap.util.Color;
import io.github.iliekpie.bootstrap.util.Interpolation;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Vertex {
    //Vertex data
    private Vector4f position = new Vector4f(0, 0, 0, 1);
    private Vector3f normal = new Vector3f(0, 0, 0);
    private Color color = new Color(Color.EMPTY);
    private Vector2f uv = new Vector2f(0, 0);

    //The amount of elements that a vertex has
    private static int elementCount = 0;
    //The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
    private static int stride = 0;

    public static Vertex interpolate(Vertex naught, Vertex prime, float alpha) {
        return new Vertex()
                .setPosition(Interpolation.linear(naught.getPosition(), prime.getPosition(), alpha))
                .setNormal(Interpolation.linear(naught.getNormal(), prime.getNormal(), alpha))
                .setColor(Color.interpolate(naught.getColor(), prime.getColor(), alpha))
                .setUV(Interpolation.linear(naught.getTexCoords(), prime.getTexCoords(), alpha));
    }

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

    public Vertex setNormal(Vector3f normal) {
        this.normal = new Vector3f(normal);
        return this;
    }

    public Vertex setUV(float u, float v) {
        this.uv = new Vector2f(u, v);
        return this;
    }

    public Vertex setUV(Vector2f uv) {
        this.uv = uv;
        return this;
    }

    public Vertex setRGB(float r, float g, float b) {
        this.color.setRGB(r, g, b);
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
        float[] out = new float[Vertex.getElementCount()];
        int i = 0;

        //Insert XYZW elements
        out[i++] = this.position.getX();
        out[i++] = this.position.getY();
        out[i++] = this.position.getZ();
        out[i++] = this.position.getW();

        out[i++] = this.normal.getX();
        out[i++] = this.normal.getY();
        out[i++] = this.normal.getZ();

        //Insert RGBA elements
        out[i++] = this.color.getRed();
        out[i++] = this.color.getGreen();
        out[i++] = this.color.getBlue();
        out[i++] = this.color.getAlpha();

        out[i++] = this.uv.getX();
        out[i] = this.uv.getY();

        return out;
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    public float[] getXYZW() {
        return new float[]{this.position.getX(), this.position.getY(), this.position.getZ(), this.position.getW()};
    }

    public Vector3f getNormal() {
        return new Vector3f(normal);
    }

    public float[] getNormalXYZ() {
        return new float[]{this.normal.getX(), this.normal.getY(), this.normal.getZ()};
    }

    public Vector2f getTexCoords() {
        return new Vector2f(uv);
    }

    public float[] getUV() {
        return new float[]{this.uv.getX(), this.uv.getY()};
    }

    public Color getColor() {
        return color;
    }

    public float[] getRGBA() {
        return new float[] {this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha()};
    }

    public static int getElementCount() {
        if(elementCount == 0) {
            for (Component component : Component.values()) {
                elementCount += component.getElementCount();
            }
        }
        return elementCount;
    }

    public static int getStride() {
        if(stride == 0) {
            for (Component component : Component.values()) {
                if (component.getOffset() > stride) {
                    stride = component.getNextOffset();
                }
            }
        }
        return stride;
    }

    public enum Component {
        POSITION("Position", 4, 0), NORMAL("Normal", 3, POSITION.getNextOffset()), COLOR("Color", 4, NORMAL.getNextOffset()), TEXCOORD("UV", 2, COLOR.getNextOffset());

        //Size of each element (float = 4 bytes)
        private int elementBytes = 4;

        private String attributeName;
        private int elementCount;
        private int offset;

        /**
         * Declare a default attribute (automatically adds in_)
         * @param attribute Name of the attribute without "in_"
         */
        Component(String attribute, int elementCount, int byteOffset) {
            attributeName = "in_" + attribute;
            this.elementCount = elementCount;
            offset = byteOffset;
        }

        public String getAttribute() {
            return attributeName;
        }

        public int getElementCount() {
            return elementCount;
        }

        public int getOffset() {
            return offset;
        }

        public int getNextOffset() {
            return offset + elementCount * elementBytes;
        }
    }
}