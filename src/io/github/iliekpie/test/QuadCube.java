package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Color;
import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.Vertex;
import org.lwjgl.util.vector.Vector3f;

public class QuadCube extends Renderable {
    private final float radius;

    public QuadCube(float radius, ShaderProgram program) {
        super(program);
        this.radius = radius;
        buildMesh();
    }

    private void buildMesh() {
        addCube();
        MeshTessellator tessellator = new MeshTessellator();
        mesh = tessellator.subdivide(mesh, 5);
        toSphere();
    }

    private void addCube() {
        final Color black = new Color(0, 0, 0);
        final Color white = new Color(1f, 1f, 1f);

        short topLeftFront      = mesh.addVertex(new Vertex().setXYZ(1, 1, -1).setColor(white));
        short topRightFront     = mesh.addVertex(new Vertex().setXYZ(-1, 1, -1).setColor(black));
        short bottomLeftFront   = mesh.addVertex(new Vertex().setXYZ(1, -1, -1).setColor(black));
        short bottomRightFront  = mesh.addVertex(new Vertex().setXYZ(-1, -1, -1).setColor(black));

        short topLeftBack       = mesh.addVertex(new Vertex().setXYZ(1, 1, 1).setColor(black));
        short topRightBack      = mesh.addVertex(new Vertex().setXYZ(-1, 1, 1).setColor(black));
        short bottomLeftBack    = mesh.addVertex(new Vertex().setXYZ(-1, -1, 1).setColor(black));
        short bottomRightBack   = mesh.addVertex(new Vertex().setXYZ(1, -1, 1).setColor(white));

        //back
        mesh.addTriangle(bottomLeftBack, bottomRightBack, topLeftBack);
        mesh.addTriangle(bottomLeftBack, topLeftBack, topRightBack);

        //front
        mesh.addTriangle(bottomLeftFront, bottomRightFront, topRightFront);
        mesh.addTriangle(bottomLeftFront, topRightFront, topLeftFront);

        //left
        mesh.addTriangle(bottomRightBack, bottomLeftFront, topLeftFront);
        mesh.addTriangle(bottomRightBack, topLeftFront, topLeftBack);
        
        //right
        mesh.addTriangle(bottomRightFront, bottomLeftBack, topRightBack);
        mesh.addTriangle(bottomRightFront, topRightBack, topRightFront);

        //top
        mesh.addTriangle(topRightBack, topLeftBack, topLeftFront);
        mesh.addTriangle(topRightBack, topLeftFront, topRightFront);

        //bottom
        mesh.addTriangle(bottomRightFront, bottomLeftFront, bottomRightBack);
        mesh.addTriangle(bottomRightFront, bottomRightBack, bottomLeftBack);
    }

    // Creates a scaled unit sphere
    private void toSphere() {
        for (Vertex vertex : mesh.getVertices()) {
            Vector3f position = vertex.getPosition();
            position.normalise().scale(radius);
            vertex.setPosition(position);
        }
    }

    /*private float mapAxis(float a, float b, float c) {
        return a * (float) Math.sqrt(1.0f - b * b * 0.5f - c * c * 0.5f + b * b * c * c / 3.0f);
    }*/
}
