package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.*;
import io.github.iliekpie.bootstrap.util.Color;
import io.github.iliekpie.test.MeshTessellator;
import org.lwjgl.util.vector.Vector3f;

public class Cube extends Renderable {
    public Cube(ShaderProgram program) {
        super(program);
        buildModel();
    }

    private void buildModel() {
        model.setMaterial(new Material(new Texture().loadTexture("textures/goat.png")));
        addCube();
        MeshTessellator tessellator = new MeshTessellator();
        model.setMesh(tessellator.subdivide(model.getMesh(), 4));
        toSphere(2);
        model.getMesh().calculateNormals();
    }

    private void addCube() {
        //very clunky - try using cubemap
        Mesh mesh = model.getMesh();
        float expansion = 1.0f;

        //uv coordinates - OpenGL assumes images start from bottom left; PNGDecoder loads them from upper left

        // Front
        short v0 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, expansion * 1.0f).setUV(0, 0));
        short v1 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -1.0f, expansion * 1.0f).setUV(0, 1));
        short v2 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, expansion * 1.0f).setUV(1, 1));
        short v3 = mesh.addVertex(new Vertex().setXYZ(1.0f, 1.0f, expansion * 1.0f).setUV(1, 0));

        mesh.addTriangle(v0, v1, v2);
        mesh.addTriangle(v2, v3, v0);

        // Back
        short v4 = mesh.addVertex(new Vertex().setXYZ(1.0f, 1.0f, expansion * -1.0f).setUV(0, 0));
        short v5 = mesh.addVertex(new Vertex().setXYZ(1.0f, -1.0f, expansion * -1.0f).setUV(0, 1));
        short v6 = mesh.addVertex(new Vertex().setXYZ(-1.0f, -1.0f, expansion * -1.0f).setUV(1, 1));
        short v7 = mesh.addVertex(new Vertex().setXYZ(-1.0f, 1.0f, expansion * -1.0f).setUV(1, 0));

        mesh.addTriangle(v4, v5, v6);
        mesh.addTriangle(v6, v7, v4);

        // Left
        short v8 = mesh.addVertex(new Vertex().setXYZ(expansion * -1.0f, 1.0f, -1.0f).setUV(0, 0));
        short v9 = mesh.addVertex(new Vertex().setXYZ(expansion * -1.0f, -1.0f, -1.0f).setUV(0, 1));
        short v10 = mesh.addVertex(new Vertex().setXYZ(expansion * -1.0f, -1.0f, 1.0f).setUV(1, 1));
        short v11 = mesh.addVertex(new Vertex().setXYZ(expansion * -1.0f, 1.0f, 1.0f).setUV(1, 0));

        mesh.addTriangle(v8, v9, v10);
        mesh.addTriangle(v10, v11, v8);

        // Right
        short v12 = mesh.addVertex(new Vertex().setXYZ(expansion * 1.0f, 1.0f, 1.0f).setUV(0, 0));
        short v13 = mesh.addVertex(new Vertex().setXYZ(expansion * 1.0f, -1.0f, 1.0f).setUV(0, 1));
        short v14 = mesh.addVertex(new Vertex().setXYZ(expansion * 1.0f, -1.0f, -1.0f).setUV(1, 1));
        short v15 = mesh.addVertex(new Vertex().setXYZ(expansion * 1.0f, 1.0f, -1.0f).setUV(1, 0));

        mesh.addTriangle(v12, v13, v14);
        mesh.addTriangle(v14, v15, v12);

        // Top
        short v16 = mesh.addVertex(new Vertex().setXYZ(-1.0f, expansion * 1.0f, -1.0f).setUV(0, 0));
        short v17 = mesh.addVertex(new Vertex().setXYZ(-1.0f, expansion * 1.0f, 1.0f).setUV(0, 1));
        short v18 = mesh.addVertex(new Vertex().setXYZ(1.0f, expansion * 1.0f, 1.0f).setUV(1, 1));
        short v19 = mesh.addVertex(new Vertex().setXYZ(1.0f, expansion * 1.0f, -1.0f).setUV(1, 0));

        mesh.addTriangle(v16, v17, v18);
        mesh.addTriangle(v18, v19, v16);

        // Bottom
        short v20 = mesh.addVertex(new Vertex().setXYZ(-1.0f, expansion * -1.0f, 1.0f).setUV(1, 0));
        short v21 = mesh.addVertex(new Vertex().setXYZ(-1.0f, expansion * -1.0f, -1.0f).setUV(1, 1));
        short v22 = mesh.addVertex(new Vertex().setXYZ(1.0f, expansion * -1.0f, -1.0f).setUV(0, 1));
        short v23 = mesh.addVertex(new Vertex().setXYZ(1.0f, expansion * -1.0f, 1.0f).setUV(0, 0));

        mesh.addTriangle(v20, v21, v22);
        mesh.addTriangle(v22, v23, v20);
    }

    // Creates a scaled unit sphere
    private void toSphere(int radius) {
        for (Vertex vertex : model.getMesh().getVertices()) {
            Vector3f position = vertex.getPosition();
            position.normalise().scale(radius);
            vertex.setPosition(position);
            vertex.setColor(new Color(0.5f, 0.3f, 0.7f));
        }
    }
}
