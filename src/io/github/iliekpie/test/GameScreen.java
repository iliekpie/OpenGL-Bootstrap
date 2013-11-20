package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.Screen;
import io.github.iliekpie.bootstrap.util.Transformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {
    //switch to scene and put in main screen?
    protected List<Renderable> renderables = new ArrayList<Renderable>();

    Renderable quadcube;
    Renderable sphere;

    public GameScreen(int width, int height) {
        super(width, height, "test");
        camera.moveTo(new Vector3f(0, 0f, 7.0f));
        quadcube = new QuadCube(4, shaderProgram);
        quadcube.transform(new Vector3f(6, 0, 0), new Vector3f());
        renderables.add(quadcube);
        sphere = new IcoSphere(1, shaderProgram);
        renderables.add(sphere);
    }

    protected void render() {
        for (Renderable renderable : renderables) {
            shaderProgram.setUniformMatrix(
                    shaderProgram.getUniformLocation("MVP"),
                    false,
                    Matrix4f.mul(
                            camera.getCombinedMatrix(),
                            renderable.getModelMatrix(),
                            null
                    )
            );
            renderable.draw();
        }
    }

    protected void tick(float delta) {
       // camera.translate(new Vector3f(0, 0f, delta));
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
