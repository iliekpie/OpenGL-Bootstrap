package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Mesh;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.test.data.TestMaterial;

public class Plane extends Renderable {
    public Plane(ShaderProgram program) {
        super(program);
        model.setMaterial(new TestMaterial());
        buildMesh();
    }

    private void buildMesh() {
        addPlane();
        model.getMesh().calculateNormals();
    }

    private void addPlane() {
        Mesh mesh = model.getMesh();
        short v0 = mesh.addVertex(new Vertex().setXYZ(-1, 0, -1).setUV(0, 0));
        short v1 = mesh.addVertex(new Vertex().setXYZ(1, 0, -1).setUV(1, 0));
        short v2 = mesh.addVertex(new Vertex().setXYZ(1, 0, 1).setUV(1, 1));
        short v3 = mesh.addVertex(new Vertex().setXYZ(-1, 0, 1).setUV(0, 1));

        //front
        mesh.addTriangle(v0, v1, v2);
        mesh.addTriangle(v2, v3, v0);
    }
}
