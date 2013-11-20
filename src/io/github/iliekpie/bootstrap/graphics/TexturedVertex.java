package io.github.iliekpie.bootstrap.graphics;

import org.lwjgl.util.vector.Vector2f;

public class TexturedVertex extends Vertex {
    protected Vector2f uv = new Vector2f();

    public static final int textureElementCount = 2;
    public static final int textureByteCount = textureElementCount * elementBytes;
    public static final int textureByteOffset = colorByteOffset + colorByteCount;
    public static final int elementCount = positionElementCount + colorElementCount + textureElementCount;

}
