package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.*;
import org.lwjgl.util.vector.Matrix4f;

public abstract class Renderable {
    protected Mesh mesh;
    private MeshRenderer renderer;

    private Matrix4f transformation = new Matrix4f();

    public Renderable(ShaderProgram shaderProgram) {
        mesh = new Mesh();
        buildMesh();
        renderer = new MeshRenderer(mesh);
        renderer.bind(shaderProgram);
    }

    public abstract void buildMesh();

    public void draw() {
        renderer.draw();
    }

    public Matrix4f getModelMatrix() {
        return transformation;
    }
}
