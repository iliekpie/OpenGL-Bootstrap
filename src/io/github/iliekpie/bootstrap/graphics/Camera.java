package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.util.Projection;
import io.github.iliekpie.bootstrap.util.Transformation;
import io.github.iliekpie.bootstrap.util.VectorUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

//TODO: Redo with Vectors and Quats, look at libgdx
public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private float yaw = 0.0f;       //Yaw is the rotation around the Y axis AKA left/right
    private float pitch = 0.0f;     //Pitch is the rotation around the X axis AKA up/down

    private Matrix4f viewMatrix = new Matrix4f();

    private Matrix4f projectionMatrix = new Matrix4f();
    private int fov = 79;
    private int width, height;

    private Matrix4f combinedMatrix = new Matrix4f();

    public Camera(int width, int height) {
        this(width, height, 60);
    }

    public Camera(int width, int height, int fov) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        update(width, height);
    }

    /**
     * Alter the camera's yaw
     * @param deltaYaw Amount to add to yaw
     */
    public void yaw(float deltaYaw) {
        yaw += deltaYaw;
        //Keep yaw within -180 and 180 - reduce precision errors
        if (yaw < -180.0f) {
            yaw += 360.0f;
        }
        if (yaw > 180.0f) {
            yaw -= 360.0f;
        }
        update();
    }

    /**
     * Alter the camera'a pitch
     * @param deltaPitch Amount to add to pitch
     */
    public void pitch(float deltaPitch) {
        pitch += deltaPitch;
        //Lock pitch to up and down.
        if (pitch < -90.0f) {
            pitch = -90.0f;
        }
        if (pitch > 90.0f) {
            pitch = 90.0f;
        }
        update();
    }

    /**
     * Move forward (based on yaw and pitch)
     * @param distance The distance to move
     */
    public void moveForward(float distance) {
        Vector3f components = VectorUtils.breakComponents(distance, yaw, pitch);
        position.x += components.x;
        position.y += -components.y;
        position.z += -components.z;
        update();
    }

    /**
     * Move backward (based on yaw and pitch)
     * @param distance The distance to move
     */
    public void moveBackward(float distance) {
        Vector3f components = VectorUtils.breakComponents(distance, yaw, pitch);
        position.x += -components.x;
        position.y += components.y;
        position.z += components.z;
        update();
    }

    /**
     * Move left (based on yaw)
     * @param distance The distance to move
     */
    public void moveLeft(float distance) {
        Vector2f components = VectorUtils.breakComponents(distance, yaw);
        position.x += -components.x;
        position.z += -components.y;
        update();
    }

    /**
     * Move right (based on yaw).
     * @param distance The distance to move
     */
    public void moveRight(float distance) {
        Vector2f components = VectorUtils.breakComponents(distance, yaw);
        position.x += components.x;
        position.z += components.y;
        update();
    }

    public void moveTo(Vector3f position) {
        this.position = position;
        update();
    }

    public void translate(Vector3f vector) {
        Vector3f.add(position, vector, position);
        update();
    }

    public Vector3f getPosition() {
        return position;
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

        projectionMatrix = Projection.getPerspectiveMatrix(fov, (float) width / (float) height, 0.1f, 100.0f);
        update();
    }

    public void update() {
        viewMatrix = Transformation.getWorldTransformationMatrix(position, new Vector3f((float)Math.toRadians(pitch), (float)Math.toRadians(yaw), 0.0f));
        Matrix4f.mul(projectionMatrix, viewMatrix, combinedMatrix);
    }
}
