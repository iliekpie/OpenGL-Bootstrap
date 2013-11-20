package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.Vertex;
import org.lwjgl.util.vector.Vector3f;

public class IcoSphere extends Renderable {
    private final float radius;

    public IcoSphere(float radius, ShaderProgram program) {
        super(program);
        this.radius = radius;
        buildMesh();
    }

    private void buildMesh() {
        addIsocahedron();
        MeshTessellator tessellator = new MeshTessellator();
        mesh = tessellator.subdivide(mesh, 3);
        toSphere(radius);
    }

    private void addIsocahedron() {
        final float x = 0.525731f;
        final float z = 0.850651f;
        short v0 = mesh.addVertex(new Vertex().setXYZ(-x, 0, z).setRGB(1, 0, 0));
        short v1 = mesh.addVertex(new Vertex().setXYZ(x, 0, z).setRGB(1, 0.5f, 0));
        short v2 = mesh.addVertex(new Vertex().setXYZ(-x, 0, -z).setRGB(1, 1, 0));
        short v3 = mesh.addVertex(new Vertex().setXYZ(x, 0, -z).setRGB(0.5f, 1, 0));
        short v4 = mesh.addVertex(new Vertex().setXYZ(0, z, x).setRGB(0, 1, 0));
        short v5 = mesh.addVertex(new Vertex().setXYZ(0, z, -x).setRGB(0, 1, 0.5f));
        short v6 = mesh.addVertex(new Vertex().setXYZ(0, -z, x).setRGB(0, 1, 1));
        short v7 = mesh.addVertex(new Vertex().setXYZ(0, -z, -x).setRGB(0, 0.5f, 1));
        short v8 = mesh.addVertex(new Vertex().setXYZ(z, x, 0).setRGB(0, 0, 1));
        short v9 = mesh.addVertex(new Vertex().setXYZ(-z, x, 0).setRGB(0.5f, 0, 1));
        short v10 = mesh.addVertex(new Vertex().setXYZ(z, -x, 0).setRGB(1, 0, 1));
        short v11 = mesh.addVertex(new Vertex().setXYZ(-z, -x, 0).setRGB(1, 0, 0.5f));

        mesh.addTriangle(v0, v4, v1);
        mesh.addTriangle(v0, v9, v4);
        mesh.addTriangle(v9, v5, v4);
        mesh.addTriangle(v4, v5, v8);
        mesh.addTriangle(v4, v8, v1);
        mesh.addTriangle(v8, v10, v1);
        mesh.addTriangle(v8, v3, v10);
        mesh.addTriangle(v5, v3, v8);
        mesh.addTriangle(v5, v2, v3);
        mesh.addTriangle(v2, v7, v3);
        mesh.addTriangle(v7, v10, v3);
        mesh.addTriangle(v7, v6, v10);
        mesh.addTriangle(v7, v11, v6);
        mesh.addTriangle(v11, v0, v6);
        mesh.addTriangle(v0, v1, v6);
        mesh.addTriangle(v6, v1, v10);
        mesh.addTriangle(v9, v0, v11);
        mesh.addTriangle(v9, v11, v2);
        mesh.addTriangle(v9, v2, v5);
        mesh.addTriangle(v7, v2, v11);
    }

    private void toSphere(float radius) {
        for (Vertex vertex : mesh.getVertices()) {
            Vector3f position = vertex.getPosition();
            position.normalise().scale(radius);
            vertex.setPosition(position);
        }
    }
}
