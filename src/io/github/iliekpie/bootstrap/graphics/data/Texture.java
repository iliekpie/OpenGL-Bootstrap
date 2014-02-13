package io.github.iliekpie.bootstrap.graphics.data;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Texture {
    private ByteBuffer data;
    int height = 0;
    int width = 0;

    public void loadTexture(String path) {
        try {
            InputStream in = new FileInputStream(path);
            PNGDecoder decoder = new PNGDecoder(in);
            height = decoder.getHeight();
            width = decoder.getWidth();

            data = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(data, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
            data.flip();

            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ByteBuffer getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
