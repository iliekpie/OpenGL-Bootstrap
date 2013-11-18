package io.github.iliekpie.bootstrap.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;
import java.util.Map;

public class ShaderProgram {
    private final int programID;
    private int[] shaders = new int[5];

    private FloatBuffer matrix44Buffer = null;


    /**
     * Creates a shader program.
     */
    public ShaderProgram() {
        //Create a program and get its ID.
        programID = GL20.glCreateProgram();
    }

    public void addShader(String code, Shader shader) {
        try {
            final int shaderID = compileShader(code, shader.getType());
            shaders[shader.getIndex()] = shaderID;
            GL20.glAttachShader(programID, shaderID);
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void link() throws LWJGLException {
        //Link the program
        GL20.glLinkProgram(programID);

        //Get info log
        String infoLog = GL20.glGetProgramInfoLog(programID, GL20.glGetProgrami(programID, GL20.GL_INFO_LOG_LENGTH));

        //if some log exists, append it
        if (infoLog!=null && infoLog.trim().length()!=0)
            System.err.println(infoLog);

        //If the link failed, throw some sort of exception
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
            throw new LWJGLException(
                    "Failure in linking program. Error log:\n" + infoLog);

        //Shader cleanup
        for (int shaderID : shaders) {
            GL20.glDetachShader(programID, shaderID);
            GL20.glDeleteShader(shaderID);
        }
    }

    //Compiles a shader and returns its ID.
    private int compileShader(String shaderCode, int type) throws LWJGLException {
        //Creates a shader object based on type and gets its ID
        int shaderID = GL20.glCreateShader(type);

        //Loads and compiles the shader
        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);

        //if info/warnings are found, append it to our shader log
        String infoLog = GL20.glGetShaderInfoLog(shaderID,
                GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH));
        if (infoLog!=null && infoLog.trim().length()!=0)
            System.err.println(getTypeName(type) + ": " + infoLog + "\n");

        //if the compiling was unsuccessful, throw an exception
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
            throw new LWJGLException("Failure in compiling " + getTypeName(type)
                    + ". Error log:\n" + infoLog);

        return shaderID;
    }

    //Returns the human-readable name of shaderType.
    private String getTypeName(int shaderType){
        final String name;
        switch (shaderType){
            case GL20.GL_VERTEX_SHADER:
                name = "Vertex Shader";
                break;
            case GL20.GL_FRAGMENT_SHADER:
                name = "Fragment Shader";
                break;
            case GL32.GL_GEOMETRY_SHADER:
                name = "Geometry Shader";
                break;
            case GL40.GL_TESS_CONTROL_SHADER:
                name = "Tessellation Control Shader";
                break;
            case GL40.GL_TESS_EVALUATION_SHADER:
                name = "Tessellation Evaluation Shader";
                break;
            default:
                name = "Generic Shader";
                break;
        }

        return name;
    }

    /**
     * Sets this shader as the active program.
     */
    public void use() {
        GL20.glUseProgram(programID);
    }

    /**
     * Disables (set active program to 0) the shader program.
     */
    public void disable() {
        GL20.glUseProgram(0);
    }

    /**
     * Destroys the shader program.
     */
    public void destroy() {
        GL20.glDeleteProgram(programID);
    }

    //Uniform getter and setters

    /**
     * Gets the location of the specified uniform name.
     * @param uniformName The name of the uniform
     * @return The location of the uniform
     */
    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * Sets the uniform at the specified location to a 4x4 matrix.
     * @param location The uniform (mat4)'s location
     * @param transpose If the matrix should be transposed
     * @param matrix The matrix to be passed
     */
    public void setUniformMatrix(int location, boolean transpose, Matrix4f matrix){
        if (location == -1) return;
        if (matrix44Buffer == null){
            matrix44Buffer = BufferUtils.createFloatBuffer(16);
        }
        matrix44Buffer.clear();
        matrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(location, transpose, matrix44Buffer);
    }

    /**
     * Gets the location of the specified attribute name.
     * @param attributeName The name of the attribute
     * @return (int) The location of the attribute
     */
    public int getAttributeLocation(String attributeName) {
        return GL20.glGetAttribLocation(programID, attributeName);
    }
}
