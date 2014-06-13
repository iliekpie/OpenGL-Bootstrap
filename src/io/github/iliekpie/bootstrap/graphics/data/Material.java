package io.github.iliekpie.bootstrap.graphics.data;

import io.github.iliekpie.bootstrap.util.Color;

public class Material {
    protected Texture texture = new NullTexture();
    protected float shininess = 0.8f; //percent light reflection;
    protected Color baseColor = new Color(255, 255, 255);  //Specular (highlight) color

    public Material() {

    }

    public Material(Material material) {
        this.texture = material.texture;
        this.shininess = material.shininess;
        this.baseColor = new Color(material.baseColor);
    }

    public Material(Texture texture) {
        this(texture, 0.8f, new Color(255, 255, 255));
    }

    public Material(Texture texture, float shininess, Color baseColor){
        this.texture = texture;
        this.shininess = shininess;
        this.baseColor = baseColor;
    }

    public float getSpecularity() {
        return shininess;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Texture getTexture() {
        return texture;
    }
}
