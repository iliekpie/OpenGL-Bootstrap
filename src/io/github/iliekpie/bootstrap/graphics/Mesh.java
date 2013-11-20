package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.graphics.Vertex;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private List<Vertex> vertices = new ArrayList<Vertex>(256);
    private List<Short> indices = new ArrayList<Short>(4096);
    private List<Vector3f> normals = new ArrayList<Vector3f>();

    public Mesh() {

    }

    public short[] addVertices(Vertex[] vertices) {
        short[] vertexIDs = new short[vertices.length];
        for(int i = 0; i < vertices.length; i++) {
            vertexIDs[i] = addVertex(vertices[i]);
        }
        return vertexIDs;
    }

    public short addVertex(Vertex vertex) {
        if(vertices.contains(vertex)) {
            return (short)vertices.indexOf(vertex);
        }
        vertices.add(vertex);
        return (short)(vertices.size()-1); //Last element of the list _should_ be the new vertex's index.
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    /**
     * Adds the triangle's 3 vertices to the index list.
     * @param v1 Vertex 1
     * @param v2 Vertex 2
     * @param v3 Vertex 3
     */
    public void addTriangle(short v1, short v2, short v3) {
        indices.add(v1);
        indices.add(v2);
        indices.add(v3);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(short index) {
        return vertices.get(index);
    }

    public short[] getIndices() {
        short[] tempIndices = new short[indices.size()];

        for(int i=0; i < indices.size(); i++) {
            tempIndices[i] = indices.get(i);
        }

        return tempIndices;
    }
}
