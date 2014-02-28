package io.github.iliekpie.test.data.models;

import io.github.iliekpie.bootstrap.graphics.data.Texture;
import io.github.iliekpie.bootstrap.util.Color;
import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.test.MeshTessellator;
import io.github.iliekpie.test.data.TestTexture;
import org.lwjgl.util.vector.Vector3f;

public class QuadCube extends Renderable {
    private final float radius;

    public QuadCube(float radius, ShaderProgram program) {
        super(program);
        this.radius = radius;
        mesh.setTexture(new TestTexture(), Texture.TEXTURE);
        buildMesh();
    }

    private void buildMesh() {
        addCube();
        MeshTessellator tessellator = new MeshTessellator();
        //mesh = tessellator.subdivide(mesh, 5);
        //toSphere();
        mesh.calculateNormals();
    }

    private void addCube() {
        //i think my mapping/vertex descriptions are off.
        short topLeftFront      = mesh.addVertex(new Vertex().setXYZ(1, 1, -1).setUV(0, 1));
        short topRightFront     = mesh.addVertex(new Vertex().setXYZ(-1, 1, -1).setUV(1, 0));
        short bottomLeftFront   = mesh.addVertex(new Vertex().setXYZ(1, -1, -1).setUV(0, 1));
        short bottomRightFront  = mesh.addVertex(new Vertex().setXYZ(-1, -1, -1).setUV(1, 1));

        short topLeftBack       = mesh.addVertex(new Vertex().setXYZ(1, 1, 1).setUV(0, 0));
        short topRightBack      = mesh.addVertex(new Vertex().setXYZ(-1, 1, 1).setUV(1, 0));
        short bottomLeftBack    = mesh.addVertex(new Vertex().setXYZ(-1, -1, 1).setUV(0, 1));
        short bottomRightBack   = mesh.addVertex(new Vertex().setXYZ(1, -1, 1).setUV(1, 1));

        /*short topLeftFront      = mesh.addVertex(new Vertex().setXYZ(1, 1, -1).setUV(0, 1));
        short topRightFront     = mesh.addVertex(new Vertex().setXYZ(-1, 1, -1).setUV(1, 1));
        short bottomLeftFront   = mesh.addVertex(new Vertex().setXYZ(1, -1, -1).setUV(0, 0));
        short bottomRightFront  = mesh.addVertex(new Vertex().setXYZ(-1, -1, -1).setUV(1, 0));

        short topLeftBack       = mesh.addVertex(new Vertex().setXYZ(1, 1, 1));
        short topRightBack      = mesh.addVertex(new Vertex().setXYZ(-1, 1, 1));
        short bottomLeftBack    = mesh.addVertex(new Vertex().setXYZ(-1, -1, 1));
        short bottomRightBack   = mesh.addVertex(new Vertex().setXYZ(1, -1, 1));*/

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
