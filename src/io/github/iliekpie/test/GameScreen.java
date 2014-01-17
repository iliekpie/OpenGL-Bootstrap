package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.Screen;
import io.github.iliekpie.bootstrap.graphics.ShaderProgram;
import io.github.iliekpie.bootstrap.graphics.data.Shader;
import io.github.iliekpie.bootstrap.graphics.data.Vertex;
import io.github.iliekpie.bootstrap.util.FileUtils;
import io.github.iliekpie.bootstrap.util.Transformation;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {
    //switch to scene and put in main screen?
    protected List<Renderable> renderables = new ArrayList<Renderable>();

    public GameScreen(int width, int height) {
        super(width, height, "test");
        shaderPrograms.setActiveProgram("BasicRender");
        camera.moveTo(new Vector3f(0, 0f, 7.0f));
        Renderable quadcube = new QuadCube(4, shaderPrograms.getActiveProgram());
        quadcube.transform(new Vector3f(6, 0, 0), new Vector3f());
        renderables.add(quadcube);
        Renderable sphere = new IcoSphere(1, shaderPrograms.getActiveProgram());
        renderables.add(sphere);
    }

    protected void render() {
        shaderPrograms.setActiveProgram("BasicRender");
        drawRenderables(GL11.GL_TRIANGLES);
        shaderPrograms.setActiveProgram("NormalVisualization");
        drawRenderables(GL11.GL_POINTS);
    }

    private void drawRenderables(int type) {
        shaderPrograms.getActiveProgram().use();
        for (Renderable renderable : renderables) {
            shaderPrograms.getActiveProgram().setUniformMatrix(
                    shaderPrograms.getActiveProgram().getUniformLocation("MVP"),
                    false,
                    Matrix4f.mul(
                            camera.getCombinedMatrix(),
                            renderable.getModelMatrix(),
                            null
                    )
            );
            renderable.draw(type);
        }
        shaderPrograms.getActiveProgram().disable();
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
