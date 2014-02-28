package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Texture;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.test.data.TestTexture;

public class Cube extends Renderable {
    public Cube(ShaderProgram program) {
        super(program);
        buildMesh();
    }

    private void buildMesh() {
        mesh.setTexture(new TestTexture(), Texture.TEXTURE);
        addCube();
        mesh.calculateNormals();
    }

    private void addCube() {
        //very clunky - try using cubemap

        // Front
        short v0 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, 2.0f).setUV(0, 1));
        short v1 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -1.0f, 2.0f).setUV(0, 0));
        short v2 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, 2.0f).setUV(1, 0));
        short v3 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, 2.0f).setUV(1, 0));
        short v4 = mesh.addVertex(new Vertex().setXYZ(1.0f, 1.0f, 2.0f).setUV(1, 1));
        short v5 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, 2.0f).setUV(0, 1));

        // Top
        short v6 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 2.0f, -1.0f).setUV(0, 1));
        short v7 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 2.0f, 1.0f).setUV(0, 0));
        short v8 = mesh.addVertex(new Vertex().setXYZ(1.0f, 2.0f, 1.0f).setUV(1, 0));
        short v9 = mesh.addVertex(new Vertex().setXYZ(1.0f, 2.0f, 1.0f).setUV(1, 0));
        short v10 = mesh.addVertex(new Vertex().setXYZ(1.0f, 2.0f, -1.0f).setUV(1, 1));
        short v11 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 2.0f, -1.0f).setUV(0, 1));

        // Left
        short v12 = mesh.addVertex(new Vertex().setXYZ(-2.0f, 1.0f, 1.0f).setUV(0, 1));
        short v13 = mesh.addVertex(new Vertex().setXYZ(-2.0f, 1.0f, -1.0f).setUV(0, 0));
        short v14 = mesh.addVertex(new Vertex().setXYZ(-2.0f, -1.0f, -1.0f).setUV(1, 0));
        short v15 = mesh.addVertex(new Vertex().setXYZ(-2.0f, -1.0f, -1.0f).setUV(1, 0));
        short v16 = mesh.addVertex(new Vertex().setXYZ(-2.0f, -1.0f, 1.0f).setUV(1, 1));
        short v17 = mesh.addVertex(new Vertex().setXYZ(-2.0f, 1.0f, 1.0f).setUV(0, 1));

        // Back
        short v18 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, -2.0f).setUV(0, 1));
        short v19 = mesh.addVertex(new Vertex().setXYZ(1.0f, 1.0f, -2.0f).setUV(0, 0));
        short v20 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, -2.0f).setUV(1, 0));
        short v21 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, -2.0f).setUV(1, 0));
        short v22 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -1.0f, -2.0f).setUV(1, 1));
        short v23 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, -2.0f).setUV(0, 1));

        // Bottom
        short v24 = mesh.addVertex(new Vertex().setXYZ(1.0f, -2.0f, -1.0f).setUV(0, 1));
        short v25 = mesh.addVertex(new Vertex().setXYZ(1.0f, -2.0f, 1.0f).setUV(0, 0));
        short v26 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -2.0f, -1.0f).setUV(1, 0));
        short v27 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -2.0f, -1.0f).setUV(1, 0));
        short v28 = mesh.addVertex(new Vertex().setXYZ(1.0f, -2.0f, 1.0f).setUV(1, 1));
        short v29 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -2.0f, 1.0f).setUV(0, 1));

        // Right
        short v30 = mesh.addVertex(new Vertex().setXYZ(2.0f, 1.0f, 1.0f).setUV(0, 1));
        short v31 = mesh.addVertex(new Vertex().setXYZ(2.0f, -1.0f, 1.0f).setUV(0, 0));
        short v32 = mesh.addVertex(new Vertex().setXYZ(2.0f, -1.0f, -1.0f).setUV(1, 0));
        short v33 = mesh.addVertex(new Vertex().setXYZ(2.0f, -1.0f, -1.0f).setUV(1, 0));
        short v34 = mesh.addVertex(new Vertex().setXYZ(2.0f, 1.0f, -1.0f).setUV(1, 1));
        short v35 = mesh.addVertex(new Vertex().setXYZ(2.0f, 1.0f, 1.0f).setUV(0, 1));
        
        mesh.addTriangle(v0, v1, v2);
        mesh.addTriangle(v3, v4, v5);

        mesh.addTriangle(v6, v7, v8);
        mesh.addTriangle(v9, v10, v11);

        mesh.addTriangle(v12, v13, v14);
        mesh.addTriangle(v15, v16, v17);

        mesh.addTriangle(v18, v19, v20);
        mesh.addTriangle(v21, v22, v23);

        mesh.addTriangle(v24, v25, v26);
        mesh.addTriangle(v27, v28, v29);

        mesh.addTriangle(v30, v31, v32);
        mesh.addTriangle(v33, v34, v35);
    }
}
