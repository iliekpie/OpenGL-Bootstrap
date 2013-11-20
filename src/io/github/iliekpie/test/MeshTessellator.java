package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Color;
import io.github.iliekpie.bootstrap.graphics.Mesh;
import io.github.iliekpie.bootstrap.graphics.Vertex;
import org.lwjgl.util.vector.Vector3f;

public class MeshTessellator {
    private Mesh tempMesh = new Mesh();

    public short getSubVertex(Vertex v1, Vertex v2) {
        Vector3f subVector = Vector3f.add(v1.getPosition(), v2.getPosition(), null);
        subVector.scale(0.5f);
        return tempMesh.addVertex(
                new Vertex().setPosition(subVector)
                        .setColor(Color.interpolate(v1.getColor(), v2.getColor(), 0.5f))
        );
    }

    public Mesh subdivide(Mesh mesh, int iterations) {
        if (iterations == 0) {
            return mesh;
        }
        iterations--;
        return subdivide(subdivide(mesh), iterations);
    }

    public Mesh subdivide(Mesh mesh) {
        tempMesh = new Mesh();
        tempMesh.setVertices(mesh.getVertices());
        short[] indices = mesh.getIndices();
        for (int i = 0; i < indices.length; i += 3) {
            short i1 = indices[i];
            short i2 = indices[i + 1];
            short i3 = indices[i + 2];

            short a = getSubVertex(mesh.getVertex(i1), mesh.getVertex(i2));
            short b = getSubVertex(mesh.getVertex(i2), mesh.getVertex(i3));
            short c = getSubVertex(mesh.getVertex(i3), mesh.getVertex(i1));

            tempMesh.addTriangle(i1, a, c);
            tempMesh.addTriangle(i2, b, a);
            tempMesh.addTriangle(i3, c, b);
            tempMesh.addTriangle(a, b, c);
        }

        return tempMesh;
    }
}
