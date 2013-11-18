package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.Vertex;

public class Cube extends Renderable {
    public Cube(ShaderProgram program) {
        super(program);
    }

    public void buildMesh() {
        addCube(0, 0, 0);
    }

    public void addCube(float centerX, float centerY, float centerZ) {
        short topRightFront     = mesh.addVertex(new Vertex().setXYZ(centerX - 0.5f, centerY - 0.5f, centerZ + 0.5f).setRGB(1.0f, 0.0f, 0.0f));
        short topLeftFront      = mesh.addVertex(new Vertex().setXYZ(centerX + 0.5f, centerY - 0.5f, centerZ + 0.5f).setRGB(0.0f, 1.0f, 0.0f));
        short bottomLeftFront   = mesh.addVertex(new Vertex().setXYZ(centerX + 0.5f, centerY + 0.5f, centerZ + 0.5f).setRGB(0.0f, 0.0f, 1.0f));
        short bottomRightFront  = mesh.addVertex(new Vertex().setXYZ(centerX - 0.5f, centerY + 0.5f, centerZ + 0.5f).setRGB(1.0f, 1.0f, 1.0f));

        short bottomRightBack   = mesh.addVertex(new Vertex().setXYZ(centerX + 0.5f, centerY - 0.5f, centerZ - 0.5f).setRGB(1.0f, 0.0f, 0.0f));
        short topRightBack      = mesh.addVertex(new Vertex().setXYZ(centerX - 0.5f, centerY - 0.5f, centerZ - 0.5f).setRGB(0.0f, 1.0f, 0.0f));
        short topLeftBack       = mesh.addVertex(new Vertex().setXYZ(centerX - 0.5f, centerY + 0.5f, centerZ - 0.5f).setRGB(0.0f, 0.0f, 1.0f));
        short bottomLeftBack    = mesh.addVertex(new Vertex().setXYZ(centerX + 0.5f, centerY + 0.5f, centerZ - 0.5f).setRGB(1.0f, 1.0f, 1.0f));

        //front
        mesh.addTriangle(topRightFront, topLeftFront, bottomLeftFront);
        mesh.addTriangle(bottomLeftFront, bottomRightFront, topRightFront);

        //back
        mesh.addTriangle(bottomRightBack, topRightBack, topLeftBack);
        mesh.addTriangle(topLeftBack, bottomLeftBack, bottomRightBack);

        /*//right
        mesh.addTriangle(vertices[1], vertices[4], vertices[7]);
        mesh.addTriangle(vertices[1], vertices[7], vertices[2]);

        //left
        mesh.addTriangle(vertices[5], vertices[0], vertices[3]);
        mesh.addTriangle(vertices[5], vertices[3], vertices[6]);

        //top
        mesh.addTriangle(vertices[3], vertices[2], vertices[7]);
        mesh.addTriangle(vertices[3], vertices[7], vertices[6]);

        //bottom
        mesh.addTriangle(vertices[5], vertices[4], vertices[1]);
        mesh.addTriangle(vertices[5], vertices[1], vertices[0]);*/
    }
}
