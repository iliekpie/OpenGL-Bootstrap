package io.github.iliekpie.bootstrap.graphics.data;

import io.github.iliekpie.bootstrap.util.Color;

public class BlankMaterial extends Material {
    public BlankMaterial() {
        this.texture =  new NullTexture();
        this.shininess = 80.0f;
        this.baseColor = new Color(255, 255, 255);
    }
}
