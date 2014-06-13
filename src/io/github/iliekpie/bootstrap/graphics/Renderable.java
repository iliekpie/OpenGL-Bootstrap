package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.data.Model;
import io.github.iliekpie.bootstrap.util.Transformation;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

/**
 * This class is extended to all renderable objects.
 * It contains the mesh and the renderer.
 */
public abstract class Renderable {
    protected Model model;
    protected ModelRenderer renderer;
    private boolean bound = false;
    private final FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(3 * 3);

    protected Matrix4f modelMatrix = new Matrix4f();

    public Renderable(ShaderProgram shaderProgram) {
        model = new Model();
        renderer = new ModelRenderer();
        renderer.bind(shaderProgram);
    }

    /**
     * Renders the mesh - if it isn't bound yet, load it and then draw it.
     * This allows us to generate the mesh in the constructor of the subclass without a shader program.
     */
    public void draw(int type) {
        if(!bound) {
            renderer.loadModel(model);
            bound = true;
        }
        renderer.draw(type);
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    /**
     * Apply a local transformation to the model.
     * @param translation Translation vector
     * @param rotation Rotation vector
     */
    public void transform(Vector3f translation, Vector3f rotation) {
        Transformation.applyLocalTransformation(translation, rotation, modelMatrix);
    }

    public void destroy() {
        renderer.destroy();
    }
}
