package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.util.Transformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Renderable {
    protected Mesh mesh;
    protected MeshRenderer renderer;
    private boolean bound = false;

    protected Matrix4f modelMatrix = new Matrix4f();

    public Renderable(ShaderProgram shaderProgram) {
        mesh = new Mesh();
        renderer = new MeshRenderer();
        renderer.bind(shaderProgram);
    }

    public void draw() {
        if(!bound) {
            renderer.loadMesh(mesh);
            bound = true;
        }
        renderer.draw();
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void transform(Vector3f translation, Vector3f rotation) {
        Transformation.applyLocalTransformation(translation, rotation, modelMatrix);
    }

    public void destroy() {
        renderer.destroy();
    }
}
