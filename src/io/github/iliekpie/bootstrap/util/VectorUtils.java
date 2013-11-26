package io.github.iliekpie.bootstrap.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class VectorUtils {
    /**
     * Dumps the given matrix in a friendlier manner.
     * @param variableName Human-friendly name
     * @param matrix Matrix to be dumped
     */
    public static void dumpMatrixData(String variableName, Matrix4f matrix){
        //Dumps the matrix for debugging purposes.
        System.out.println(variableName + ":\r\n" + matrix.toString());
    }

    /**
     * Breaks a 3D vector into its components. Soon to be deprecated.
     * @param distance Magnitude
     * @param yaw Yaw (X)
     * @param pitch Pitch (Y)
     * @return (Vector3f) The components of the vector
     */
    public static Vector3f breakComponents(float distance, float yaw, float pitch) {
        return new Vector3f(
                distance * (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                distance * (float) Math.sin(Math.toRadians(pitch)),
                distance * (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
        );
    }

    /**
     * Breaks a 2D vector into its components. Soon to be deprecated.
     * @param distance Magnitude
     * @param yaw Yaw (X)
     * @return (Vector2f) The components of the vector
     */
    public static Vector2f breakComponents(float distance, float yaw) {
        return new Vector2f(
                distance * (float) Math.cos(Math.toRadians(yaw)),
                distance * (float) Math.sin(Math.toRadians(yaw))
        );
    }
}
