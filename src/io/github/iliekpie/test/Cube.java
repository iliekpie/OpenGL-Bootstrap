package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Mesh;
import io.github.iliekpie.bootstrap.graphics.MeshRenderer;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.Vertex;

public class Cube {
    private Mesh mesh;
    private MeshRenderer renderer;
    private float radius = 5f;
    private float step = 0.2f;

    public Cube(ShaderProgram shaderProgram) {
        mesh = new Mesh();
        buildMesh();
        renderer = new MeshRenderer(mesh);
        renderer.bind(shaderProgram);
    }

    public void buildMesh() {

    }

    private void addQuad(float centerX, float centerY) {
        short bottomLeft = mesh.addVertex(new Vertex().setXYZ(-1, -1, 0));
        short bottomRight = mesh.addVertex(new Vertex().setXYZ(1, -1, 0));
        short topRight = mesh.addVertex(new Vertex().setXYZ(1, +1, 0));
        short topLeft = mesh.addVertex(new Vertex().setXYZ(-1, +1, 0));

        mesh.addTriangle(bottomLeft, bottomRight, topRight);
        mesh.addTriangle(topRight, topLeft, bottomLeft);
    }

    public void draw() {
        renderer.draw();
    }
}
