package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.data.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.*;

public class ModelRenderer {
    private ShaderProgram shaderProgram = null;

    //Vertices and Indices
    private int indexCount = -1;
    private Map<Texture.MapType, Integer> objectIDMap = new HashMap<Texture.MapType, Integer>(5);
    private int vertexDataObjectID = -1;
    private int indexObjectID = -1;

    private int vaoID = -1;

    private HashMap<Vertex.Component, Integer> attributeLocations = new HashMap<Vertex.Component, Integer>(5);

    public ModelRenderer() {

    }

    /**
     * Binds the object's data to the shader program.
     * @param program The active shader program
     */
    public void bind(ShaderProgram program) {
        shaderProgram = program;
    }

    /**
     * Loads a model's data into the GPU
     * @param model Model to be loaded
     */
    public void loadModel(Model model) {
        // Bind a VAO so we can just load it without having to worry about other states
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        bindVertexBuffer(getVertexBuffer(model.getMesh().getVertices()), model.getMesh().getComponents());
        bindIndexBuffer(getIndexBuffer(model.getMesh().getIndexArray()));
        bindMaterial(model.getMaterial());
        bindMaps(model.getMaps());
    }

    private void bindVertexBuffer(FloatBuffer vertexBuffer, EnumSet<Vertex.Component> attributeComponents) {
        //Create and select a VBO.
        vertexDataObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexDataObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        //Bind position, normal, color, and texcoords
        for (Vertex.Component component : attributeComponents) {
            attributeLocations.put(component, shaderProgram.getAttributeLocation(component.getAttribute()));
            GL20.glVertexAttribPointer(attributeLocations.get(component), component.getElementCount(), GL11.GL_FLOAT, false, Vertex.getStride(), component.getOffset());
            GL20.glEnableVertexAttribArray(attributeLocations.get(component));
        }
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
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.getElementCount());
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

    private void bindMaterial(Material material) {
        bindTexture(material.getTexture(), Texture.MapType.COLOR);
    }

    private void bindMaps(Map<Texture.MapType, Texture> maps) {

    }

    private void bindTexture(Texture texture, Texture.MapType type) {
        objectIDMap.put(type, GL11.glGenTextures());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, objectIDMap.get(type));

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                texture.getWidth(), texture.getHeight(), 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.getData());

    }

    /**
     * Draws the object using the specified type
     */
    public void draw(int type) {
        GL30.glBindVertexArray(vaoID);

        //Set the texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, objectIDMap.get(Texture.MapType.COLOR));

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
        //Disable the VBO indices
        for (int attributeLocation : attributeLocations.values()) {
            GL20.glDisableVertexAttribArray(attributeLocation);
        }

        for (int textureObjectID : objectIDMap.values()) {
            GL11.glDeleteTextures(textureObjectID);
        }

        //Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vertexDataObjectID);

        //Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(indexObjectID);

        GL30.glDeleteVertexArrays(vaoID);
    }
}
