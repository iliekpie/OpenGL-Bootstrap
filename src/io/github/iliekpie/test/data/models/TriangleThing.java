package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Texture;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.test.MeshTessellator;
import io.github.iliekpie.test.data.TestTexture;

public class TriangleThing extends Renderable {
    public TriangleThing(ShaderProgram program) {
        super(program);
        mesh.setTexture(new TestTexture(), Texture.TEXTURE);
        buildMesh();
    }

    private void buildMesh() {
        addTriangle();
        MeshTessellator tessellator = new MeshTessellator();
        //mesh = tessellator.subdivide(mesh, 5);
        mesh.calculateNormals();
    }

    private void addTriangle() {
        short v0 = mesh.addVertex(new Vertex().setXYZ(0, 0, 0).setUV(0, 0));
        short v1 = mesh.addVertex(new Vertex().setXYZ(1, 0, 0).setUV(1, 0));
        short v2 = mesh.addVertex(new Vertex().setXYZ(0.5f, 0, (float) Math.sqrt(0.75)).setUV(0, 1));
        short v3 = mesh.addVertex(new Vertex().setXYZ(0.5f, (float) Math.sqrt(0.75), (float) Math.sqrt(0.75)).setUV(1, 1));

        mesh.addTriangle(v0, v1, v2);
        mesh.addTriangle(v0, v2, v3);
        mesh.addTriangle(v2, v1, v3);
        mesh.addTriangle(v0, v3, v1);
    }
}