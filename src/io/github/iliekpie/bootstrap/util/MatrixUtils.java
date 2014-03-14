package io.github.iliekpie.bootstrap.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class MatrixUtils {
    private static FloatBuffer m3fBuffer = BufferUtils.createFloatBuffer(3 * 3);
    public static FloatBuffer getNormalBuffer(Matrix4f modelMatrix, Matrix4f viewMatrix) {
        Matrix4f mv = Matrix4f.mul(viewMatrix, modelMatrix, null);
        mv.invert().transpose();
        mv.store3f(m3fBuffer);
        m3fBuffer.flip();
        return m3fBuffer.asReadOnlyBuffer();
    }

    public static Matrix3f getNormalMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) {
        return (Matrix3f)(new Matrix3f().load(getNormalBuffer(modelMatrix, viewMatrix)));
    }
}
