package io.github.iliekpie.bootstrap.graphics.data;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Mesh {

    private List<Vertex> vertices = new ArrayList<Vertex>(512);
    private List<Short> indices = new ArrayList<Short>(4096);

    private EnumSet<Vertex.Component> components = EnumSet.allOf(Vertex.Component.class);

    public Mesh() {

    }

    public Mesh(EnumSet<Vertex.Component> components) {
        this.components = EnumSet.copyOf(components);
    }

    public Mesh(Mesh mesh) {
        this.vertices = new ArrayList<Vertex>(mesh.vertices);
        this.components = EnumSet.copyOf(mesh.components);
    }

    public short[] addVertices(Vertex[] vertices) {
        short[] vertexIDs = new short[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            vertexIDs[i] = addVertex(vertices[i]);
        }
        return vertexIDs;
    }

    /**
     * Adds the vertex to the mesh.
     * Attempts to reduce vertex duplicates by only adding new vertices if they do not already exist.
     *
     * @param vertex Vertex to be added
     * @return Index of the vertex
     */
    public short addVertex(Vertex vertex) {
        if (vertices.contains(vertex)) {
            return (short) vertices.indexOf(vertex);
        }
        vertices.add(vertex);
        return (short) (vertices.size() - 1); //Last element of the list should be the new vertex's index.
    }

    // Overwrite all vertices
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    /**
     * Adds the triangle's 3 vertices to the index list.
     *
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

    public short[] getIndexArray() {
        short[] tempIndices = new short[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            tempIndices[i] = indices.get(i);
        }

        return tempIndices;
    }

    public List<Short> getIndices() {
        return indices;
    }

    public void calculateNormals() {
        //for each triangle: add calculate face normal and add it to each relevant vertex
        for (int i = 0; i < indices.size(); i += 3) {
            Vertex[] faceVertices = {
                    vertices.get(indices.get(i)),
                    vertices.get(indices.get(i + 1)),
                    vertices.get(indices.get(i + 2))};
            Vector3f faceNormal = calculateFaceNormal(faceVertices[0], faceVertices[1], faceVertices[2]);
            for (Vertex vertex : faceVertices) {
                Vector3f.add(vertex.getNormal(), faceNormal, vertex.getNormal());
            }
        }
        for (Vertex vertex : vertices) {
            if (vertex.getNormal().length() > 1) {
                vertex.getNormal().normalise();
            }
        }
    }

    private Vector3f calculateFaceNormal(Vertex A, Vertex B, Vertex C) {
        return (Vector3f) Vector3f.cross(
                Vector3f.sub(A.getPosition(), B.getPosition(), null),
                Vector3f.sub(B.getPosition(), C.getPosition(), null),
                null).normalise();
    }

    /**
     * Inverts the mesh's indices - useful for flipping normals/culling
     */
    public void invert() {
        Collections.reverse(indices);
    }

    public EnumSet<Vertex.Component> getComponents() {
        return components;
    }

    public boolean addComponent(Vertex.Component component) {
        return components.add(component);
    }

    public void setComponents(EnumSet<Vertex.Component> components) {
        this.components = components;
    }
}
