package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.data.Shader;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {
    private final int programID;
    private int[] shaders = new int[5];
    private Map<String, Integer> attributes = new HashMap<String, Integer>(4);
    private Map<String, Integer> uniforms = new HashMap<String, Integer>();

    private FloatBuffer matrix44Buffer = null;

    /**
     * Creates a shader program.
     */
    public ShaderProgram() {
        //Create a program and get its ID.
        programID = GL20.glCreateProgram();
    }

    /**
     * Attaches a shader to the program.
     * Remember to call link() after.
     * @param code The shader in a single String
     * @param shader Shader type
     */
    public void addShader(String code, Shader shader) {
        try {
            final int shaderID = compileShader(code, shader.getType());
            // May need to change to a map to use more than one vertex shader (common functions)
            shaders[shader.getIndex()] = shaderID;
            GL20.glAttachShader(programID, shaderID);
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Links the program to all its components (shaders).
     * @throws LWJGLException
     */
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
        if (infoLog!=null && infoLog.trim().length()!=0) {
            System.err.println(getTypeName(type) + ": " + infoLog + "\n");
        }

        //if the compiling was unsuccessful, throw an exception
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new LWJGLException("Failure in compiling " + getTypeName(type)
                    + ". Error log:\n" + infoLog);
        }

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
     * Checks if the uniform has already been looked up, and returns that value if found - potential performance increase. TODO: find out if it's worth it
     * @param uniformName The name of the uniform
     * @return The location of the uniform
     */
    public int getUniformLocation(String uniformName) {
        if (uniforms.get(uniformName) == null) {
            uniforms.put(uniformName, GL20.glGetUniformLocation(programID, uniformName));
        }
        return uniforms.get(uniformName);
    }

    /**
     * Sets the uniform associated with the identifier to a 4x4 matrix.
     * @param uniformName The name of the uniform (mat4)
     * @param matrix The matrix to be passed
     */
    public void setUniform(String uniformName, Matrix4f matrix){
        if (matrix44Buffer == null){
            matrix44Buffer = BufferUtils.createFloatBuffer(16);
        }
        matrix44Buffer.clear();
        matrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(getUniformLocation(uniformName), false, matrix44Buffer);
    }

    /**
     * Sets the uniform associated with the identifier to a 3x3 matrix.
     * @param uniformName The name of the uniform (mat3)
     * @param matrix The matrix to be passed
     */
    public void setUniform(String uniformName, Matrix3f matrix){
        if (matrix44Buffer == null){
            matrix44Buffer = BufferUtils.createFloatBuffer(16);
        }
        matrix44Buffer.clear();
        matrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix3(getUniformLocation(uniformName), false, matrix44Buffer);
    }

    /**
     * Sets the uniform associated with the identifier to a vector3f.
     * @param uniformName The name of the uniform (vec3)
     * @param vector The vector to be passed
     */
    public void setUniform(String uniformName, Vector3f vector){
        GL20.glUniform3f(getUniformLocation(uniformName), vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Sets the uniform associated with the identifier to a float
     * @param uniformName The name of the uniform (float)
     * @param number The float to be passed
     */
    public void setUniform(String uniformName, float number) {
        GL20.glUniform1f(getUniformLocation(uniformName), number);
    }

    /**
     * Gets the location of the specified attribute name.
     * Checks if the attribute has already been looked up, and returns that value if found - potential performance increase. TODO: find out if it's worth it
     * @param attributeName The name of the attribute
     * @return (int) The location of the attribute
     */
    public int getAttributeLocation(String attributeName) {
        if (attributes.get(attributeName) == null) {
            attributes.put(attributeName, GL20.glGetAttribLocation(programID, attributeName));
        }
        return attributes.get(attributeName);
    }
}
