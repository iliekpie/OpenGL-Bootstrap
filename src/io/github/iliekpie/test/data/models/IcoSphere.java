package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Mesh;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.test.MeshTessellator;
import io.github.iliekpie.test.data.TestMaterial;
import org.lwjgl.util.vector.Vector3f;

public class IcoSphere extends Renderable {
    private final float radius;

    public IcoSphere(float radius, ShaderProgram program) {
        super(program);
        this.radius = radius;
        model.setMaterial(new TestMaterial());
        buildMesh();
    }

    private void buildMesh() {
        addIsocahedron();
        MeshTessellator tessellator = new MeshTessellator();
        model.setMesh(tessellator.subdivide(model.getMesh(), 3));
        toSphere(radius);
        model.getMesh().calculateNormals();
    }

    private void addIsocahedron() {
        Mesh mesh = model.getMesh();
        final float s = 0.525731f;
        final float t = 0.850651f;

        short v0 = mesh.addVertex(new Vertex().setXYZ(-t, -s, 0));
        short v1 = mesh.addVertex(new Vertex().setXYZ(t, -s, 0));
        short v2 = mesh.addVertex(new Vertex().setXYZ(-t, s, 0));
        short v3 = mesh.addVertex(new Vertex().setXYZ(t, s, 0));
        short v4 = mesh.addVertex(new Vertex().setXYZ(0, -t, -s));
        short v5 = mesh.addVertex(new Vertex().setXYZ(0, -t, s));
        short v6 = mesh.addVertex(new Vertex().setXYZ(0, t, -s));
        short v7 = mesh.addVertex(new Vertex().setXYZ(0, t, s));
        short v8 = mesh.addVertex(new Vertex().setXYZ(s, 0, -t));
        short v9 = mesh.addVertex(new Vertex().setXYZ(-s, 0, -t));
        short v10 = mesh.addVertex(new Vertex().setXYZ(s, 0, t));
        short v11 = mesh.addVertex(new Vertex().setXYZ(-s, 0, t));

        mesh.addTriangle(v0, v9, v4);
        mesh.addTriangle(v6, v9, v2);
        mesh.addTriangle(v9, v0, v2);
        mesh.addTriangle(v0, v11, v2);
        mesh.addTriangle(v1, v10, v5);
        mesh.addTriangle(v5, v10, v11);
        mesh.addTriangle(v5, v11, v0);
        mesh.addTriangle(v5, v0, v4);
        mesh.addTriangle(v1, v5, v4);
        mesh.addTriangle(v8, v1, v4);
        mesh.addTriangle(v8, v4, v9);
        mesh.addTriangle(v8, v9, v6);
        mesh.addTriangle(v3, v8, v6);
        mesh.addTriangle(v1, v8, v3);
        mesh.addTriangle(v10, v1, v3);
        mesh.addTriangle(v10, v3, v7);
        mesh.addTriangle(v3, v6, v7);
        mesh.addTriangle(v7, v6, v2);
        mesh.addTriangle(v7, v2, v11);
        mesh.addTriangle(v10, v7, v11);
    }

    // Creates a scaled unit sphere
    private void toSphere(float radius) {
        for (Vertex vertex : model.getMesh().getVertices()) {
            Vector3f position = vertex.getPosition();
            position.normalise().scale(radius);
            vertex.setPosition(position);
        }
    }
}
