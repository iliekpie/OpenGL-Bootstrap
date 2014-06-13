package io.github.iliekpie.bootstrap.graphics.data;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

/**
 * Allows the shader type to be stored in an array in ShaderProgram
 */
public enum Shader {
    VERTEX(0, GL20.GL_VERTEX_SHADER),
    FRAGMENT(1, GL20.GL_FRAGMENT_SHADER),
    GEOMETRY(2, GL32.GL_GEOMETRY_SHADER),
    TESS_CONTROL(3, GL40.GL_TESS_CONTROL_SHADER),
    TESS_EVALUATION(4, GL40.GL_TESS_EVALUATION_SHADER);

    private int index;
    private int type;
    Shader(int index, int type) {
        this.index = index;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }
}
