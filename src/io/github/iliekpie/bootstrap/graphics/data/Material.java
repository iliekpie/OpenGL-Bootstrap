package io.github.iliekpie.bootstrap.graphics.data;

import io.github.iliekpie.bootstrap.util.Color;

public class Material {
    private Texture texture = new NullTexture();
    private float shininess = 80.0f; //80% of the light is reflected
    private Color baseColor = new Color(255, 255, 255); //Specular (highlight) color

    public Material(){

    }
}
