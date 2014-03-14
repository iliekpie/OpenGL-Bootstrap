package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.Screen;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Shader;
import io.github.iliekpie.bootstrap.util.FileUtils;
import io.github.iliekpie.bootstrap.util.MatrixUtils;
import io.github.iliekpie.test.data.models.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {
    protected List<Renderable> renderables = new ArrayList<Renderable>();

    public GameScreen(int width, int height) {
        super(width, height, "test");
        //TODO: switch to scene/level class, clunky shader program handling
        shaderPrograms.setActiveProgram("LightingTest");
        camera.moveTo(new Vector3f(0, 0f, 7.0f));
        Renderable quadcube = new QuadCube(4, shaderPrograms.getActiveProgram());
        quadcube.transform(new Vector3f(6, 0, 0), new Vector3f());
        renderables.add(quadcube);
        Renderable sphere = new IcoSphere(1, shaderPrograms.getActiveProgram());
        renderables.add(sphere);
        Renderable pyramid = new TriangleThing(shaderPrograms.getActiveProgram());
        pyramid.transform(new Vector3f(-2, 0, 0), new Vector3f());
        renderables.add(pyramid);
        Renderable cube = new Cube(shaderPrograms.getActiveProgram());
        cube.transform(new Vector3f(0, -5, 0), new Vector3f());
        renderables.add(cube);
    }

    protected void render() {
        shaderPrograms.setActiveProgram("LightingTest");
        drawRenderables(GL11.GL_TRIANGLES);
        shaderPrograms.setActiveProgram("NormalVisualization");
        drawRenderables(GL11.GL_POINTS);
    }

    private void drawRenderables(int type) {
        ShaderProgram activeShader = shaderPrograms.getActiveProgram();
        activeShader.use();
        activeShader.setUniform("lightPos", new Vector3f(0, -3, 2));
        for (Renderable renderable : renderables) {
            activeShader.setUniform("MVP",
                    Matrix4f.mul(
                            camera.getCombinedMatrix(),
                            renderable.getModelMatrix(),
                            null
                    )
            );
            activeShader.setUniform("normalMatrix",
                    MatrixUtils.getNormalMatrix(
                            renderable.getModelMatrix(),
                            camera.getViewMatrix()
                    )
            );
            activeShader.setUniform("modelMatrix", renderable.getModelMatrix());
            renderable.draw(type);
        }
        activeShader.disable();
    }

    protected void tick(float delta) {
       // camera.translate(new Vector3f(0, 0f, delta));
    }

    @Override
    protected void setupShaders() {
        ShaderProgram drawProgram = new ShaderProgram();
        drawProgram.addShader(FileUtils.loadFile("shaders/render/BasicProjection.vert"), Shader.VERTEX);
        drawProgram.addShader(FileUtils.loadFile("shaders/render/BasicProjection.frag"), Shader.FRAGMENT);
        try {
            drawProgram.link();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
        shaderPrograms.addProgram("BasicRender", drawProgram);

        ShaderProgram debugProgram = new ShaderProgram();
        debugProgram.addShader(FileUtils.loadFile("shaders/debug/NormalVisualization.vert"), Shader.VERTEX);
        debugProgram.addShader(FileUtils.loadFile("shaders/debug/NormalVisualization.frag"), Shader.FRAGMENT);
        debugProgram.addShader(FileUtils.loadFile("shaders/debug/NormalVisualization.geo"), Shader.GEOMETRY);
        try {
            debugProgram.link();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
        shaderPrograms.addProgram("NormalVisualization", debugProgram);

        ShaderProgram texturedProgram = new ShaderProgram();
        texturedProgram.addShader(FileUtils.loadFile("shaders/render/Textured.vert"), Shader.VERTEX);
        texturedProgram.addShader(FileUtils.loadFile("shaders/render/Textured.frag"), Shader.FRAGMENT);
        try {
            texturedProgram.link();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
        shaderPrograms.addProgram("Textured", texturedProgram);

        ShaderProgram lightingProgram = new ShaderProgram();
        lightingProgram.addShader(FileUtils.loadFile("shaders/render/LightingTest.vert"), Shader.VERTEX);
        lightingProgram.addShader(FileUtils.loadFile("shaders/render/LightingTest.frag"), Shader.FRAGMENT);
        try {
            lightingProgram.link();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
        shaderPrograms.addProgram("LightingTest", lightingProgram);
    }

    @Override
    protected void onScreenClose() {
        super.onScreenClose();
        for (Renderable renderable : renderables) {
            renderable.destroy();
        }
        System.exit(0);
    }
}
