package io.github.iliekpie.bootstrap.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Transformation {
    /**
     * Applies an origin-based transformation on the given matrix.
     * @param position A vector that describes the translation
     * @param rotation A vector that describes the rotation
     * @param targetMatrix The target matrix
     * @return (Matrix4f) The target matrix. Allows for method chaining
     */
    public static Matrix4f applyWorldTransformation(Vector3f position, Vector3f rotation, Matrix4f targetMatrix) {
        //Rotation vector: x = pitch, y = yaw, z = roll
        targetMatrix.rotate(rotation.getX(), new Vector3f(1.0f, 0.0f, 0.0f));
        targetMatrix.rotate(rotation.getY(), new Vector3f(0.0f, 1.0f, 0.0f));
        targetMatrix.rotate(rotation.getZ(), new Vector3f(0.0f, 0.0f, 1.0f));
        targetMatrix.translate(new Vector3f(-position.getX(), -position.getY(), -position.getZ()));

        return targetMatrix;
    }

    /**
     * Returns a matrix containing an origin-based transformation
     * @param position A vector that describes the translation
     * @param rotation A vector that describes the rotation
     * @return (Matrix4f) A matrix that contains the origin-based transformation
     */
    public static Matrix4f getWorldTransformationMatrix(Vector3f position, Vector3f rotation) {
        Matrix4f tempMatrix = new Matrix4f();
        return applyWorldTransformation(position, rotation, tempMatrix);
    }

    /**
     * Applies a local (model-based) transformation on the given matrix.
     * @param position A vector that describes the translation
     * @param rotation A vector that describes the rotation
     * @param targetMatrix The target matrix
     * @return (Matrix4f) The target matrix. Allows for method chaining
     */
    public static Matrix4f applyLocalTransformation(Vector3f position, Vector3f rotation, Matrix4f targetMatrix) {
        //rotates roll, pitch, yaw; translates x, y, z
        targetMatrix.translate(new Vector3f(-position.getX(), -position.getY(), -position.getZ()));

        targetMatrix.rotate(rotation.getY(), new Vector3f(0.0f, 1.0f, 0.0f));
        targetMatrix.rotate(rotation.getX(), new Vector3f(1.0f, 0.0f, 0.0f));
        targetMatrix.rotate(rotation.getZ(), new Vector3f(0.0f, 0.0f, 1.0f));

        return targetMatrix;
    }

    /**
     * Returns a matrix containing a local (model-based) transformation
     * @param position A vector that describes the translation
     * @param rotation A vector that describes the rotation
     * @return (Matrix4f) A matrix that contains the local transformation
     */
    public static Matrix4f getLocalTransformationMatrix(Vector3f position, Vector3f rotation) {
        Matrix4f tempMatrix = new Matrix4f();
        return applyLocalTransformation(position, rotation, tempMatrix);
    }
}
