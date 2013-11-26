package io.github.iliekpie.bootstrap.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Projection {
    public static final float PI = (float)Math.PI;

    /**
     * Returns a perspective matrix.
     * @param fovy Horizontal field of view
     * @param aspectRatio Aspect Ration (width/height)
     * @param zNear Inverted z-coordinate of the near clipping plane (0.1f)
     * @param zFar Inverted z-coordinate of the far clipping plane (100)
     * @return (Matrix4f) Perspective Matrix
     */
    public static Matrix4f getPerspectiveMatrix(float fovy, float aspectRatio, float zNear, float zFar){
        //Adapted from the LWJGL github.
        float sine, cotangent, depth;
        float radians = fovy / 2 * PI / 180;

        depth = zFar - zNear;
        sine = (float) Math.sin(radians);

        if ((depth == 0) || (sine == 0) || (aspectRatio == 0)) {
            throw new IllegalArgumentException();
        }

        cotangent = (float) Math.cos(radians) / sine;

        Matrix4f tempMatrix = new Matrix4f();

        tempMatrix.m00 = cotangent / aspectRatio;
        tempMatrix.m11 = cotangent;
        tempMatrix.m22 = -(zFar + zNear) / depth;
        tempMatrix.m23 = -1.0f;
        tempMatrix.m32 = -2.0f * zFar * zNear / depth;
        tempMatrix.m33 = 0.0f;

        return tempMatrix;
    }

    /**
     * Returns an orientation matrix assuming that up is always (0, 1, 0) (no roll)
     * @param eyePosition Position of the camera
     * @param targetPosition Target location
     * @return (Matrix4f) Orientation matrix
     */
    public static Matrix4f getLookAtMatrix(Vector3f eyePosition, Vector3f targetPosition){
        //Makes a view matrix with the default up vector: right side up.
        return getLookAtMatrix(eyePosition, targetPosition, new Vector3f(0.0f, 1.0f, 0.0f));
    }

    /**
     * Returns an orientation matrix based on a current position, target location, and an up vector.
     * @param eyePosition Position of the camera
     * @param targetPosition Target location
     * @param up Describes which way is up
     * @return (Matrix4f) Orientation matrix
     */
    public static Matrix4f getLookAtMatrix(Vector3f eyePosition, Vector3f targetPosition, Vector3f up){
        up.normalise();
        Vector3f forward = Vector3f.sub(targetPosition, eyePosition, null);
        forward.normalise();
        Vector3f right = Vector3f.cross(forward, up, null);
        right.normalise();
        Vector3f.cross(right, forward, up);

        Matrix4f tempMatrix = new Matrix4f();

        tempMatrix.m00 = right.getX();
        tempMatrix.m01 = up.getX();
        tempMatrix.m02 = -forward.getX();

        tempMatrix.m10 = right.getY();
        tempMatrix.m11 = up.getY();
        tempMatrix.m12 = -forward.getY();

        tempMatrix.m20 = right.getZ();
        tempMatrix.m21 = up.getZ();
        tempMatrix.m22 = -forward.getZ();

        tempMatrix.translate(eyePosition.negate(null));

        return tempMatrix;
    }
}
