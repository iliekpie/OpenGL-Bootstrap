package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.data.Mesh;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public class MeshRenderer {
    private ShaderProgram shaderProgram = null;

    //Vertices and Indices
    private int indexCount = -1;
    private int vertexDataObjectID = -1;
    private int indexObjectID = -1;

    private int vaoID = -1;

    private int vertexLocation = -1;
    private int normalLocation = -1;
    private int texCoordLocation = -1;

    public MeshRenderer() {

    }

    /**
     * Binds the object's data to the shader program.
     * @param program The active shader program
     */
    public void bind(ShaderProgram program) {
        shaderProgram = program;
    }

    /**
     * Loads a mesh's data into the GPU
     * @param mesh Mesh to be loaded
     */
    public void loadMesh(Mesh mesh) {
        // Bind a VAO so we can just load it without having to worry about other states
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        bindVertexBuffer(getVertexBuffer(mesh.getVertices()));
        bindIndexBuffer(getIndexBuffer(mesh.getIndices()));
    }

    // TODO: do not limit to vertex and color
    private void bindVertexBuffer(FloatBuffer vertexBuffer) {
        vertexLocation = shaderProgram.getAttributeLocation("in_VertexPosition");
        normalLocation = shaderProgram.getAttributeLocation("in_Normal");
        texCoordLocation = shaderProgram.getAttributeLocation("in_UV");

        //Create and select a VBO.
        vertexDataObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexDataObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        //Put the positions in the "in_VertexPosition" attribute location.
        GL20.glVertexAttribPointer(vertexLocation, 4, GL11.GL_FLOAT, false, Vertex.stride, 0);

        //Put the normals in the "in_Normal" attribute location.
        GL20.glVertexAttribPointer(normalLocation, 4, GL11.GL_FLOAT, false, Vertex.stride, Vertex.normalByteOffset);

        //Put the texture coordinates in the "in_UV" attribute location.
        GL20.glVertexAttribPointer(texCoordLocation, 4, GL11.GL_FLOAT, false, Vertex.stride, Vertex.texCoordByteOffset);

        GL20.glEnableVertexAttribArray(vertexLocation);
        GL20.glEnableVertexAttribArray(normalLocation);
        GL20.glEnableVertexAttribArray(texCoordLocation);
    }

    private void bindIndexBuffer(ShortBuffer indexBuffer) {
        //Create a VBO for indices and select it.
        indexObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexObjectID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        //Bind the VBO.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexObjectID);
    }

    private FloatBuffer getVertexBuffer(List<Vertex> vertices) {

        //Put the vertex data into the float buffer.
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.elementCount);
        for (Vertex vertex : vertices) {
            vertexBuffer.put(vertex.getElements());
        }
        vertexBuffer.flip();

        return vertexBuffer;
    }

    private ShortBuffer getIndexBuffer(short[] indices) {
        indexCount = indices.length;

        //Put the indices into a buffer for OpenGL.
        ShortBuffer indexBuffer = BufferUtils.createShortBuffer(indexCount);
        indexBuffer.put(indices);
        indexBuffer.flip();

        return indexBuffer;
    }

    /**
     * Draws the object using the specified type
     */
    public void draw(int type) {
        GL30.glBindVertexArray(vaoID);

        //Draw the vertices
        GL11.glDrawElements(type, indexCount, GL11.GL_UNSIGNED_SHORT, 0);
    }

    /**
     * Draws the object with a default type - Triangles
     */
    public void draw() {
        draw(GL11.GL_TRIANGLES);
    }

    /**
     * Destroys the object's attributes and buffers.
     */
    public void destroy() {
        //Disable the VBO index
        GL20.glDisableVertexAttribArray(vertexLocation);
        GL20.glDisableVertexAttribArray(normalLocation);
        GL20.glDisableVertexAttribArray(texCoordLocation);

        //Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vertexDataObjectID);

        //Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(indexObjectID);

        GL30.glDeleteVertexArrays(vaoID);
    }
}
