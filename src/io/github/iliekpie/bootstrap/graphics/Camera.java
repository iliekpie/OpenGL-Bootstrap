package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.util.Projection;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f direction = new Vector3f(0, 0, -1);
    private Vector3f up = new Vector3f(0, 1, 0);
    private Matrix4f viewMatrix = new Matrix4f();

    private Matrix4f projectionMatrix = new Matrix4f();
    private int fov = 79;
    private int width, height;

    private Matrix4f combinedMatrix = new Matrix4f();

    public Camera(int width, int height) {
        this(width, height, 79);
    }

    public Camera(int width, int height, int fov) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        update(width, height);
    }

    public void moveTo(Vector3f position) {
        this.position = position;
        update();
    }

    public void lookAt(Vector3f target) {
        Vector3f.sub(target, position, direction);
        direction.normalise();
        normalizeUp();
        update();
    }

    private void normalizeUp() {
        Vector3f.cross(direction, up, up).normalise();
        Vector3f.cross(up, direction, up).normalise();
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getCombinedMatrix() {
        return combinedMatrix;
    }

    public void update(int width, int height) {
        this.width = width;
        this.height = height;

        projectionMatrix = Projection.getPerspectiveMatrix(fov, ((float) width / height), 0.1f, 100f);
        update();
    }

    public void update() {
        viewMatrix = Projection.getLookAtMatrix(position, Vector3f.add(position, direction, null), up);
        Matrix4f.mul(projectionMatrix, viewMatrix, combinedMatrix);
    }
}
