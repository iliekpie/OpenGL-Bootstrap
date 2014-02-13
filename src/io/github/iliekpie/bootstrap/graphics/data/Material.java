package io.github.iliekpie.bootstrap.graphics.data;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Material {
    protected Texture texture = new Texture();

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
