package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Renderable;
import io.github.iliekpie.bootstrap.graphics.Screen;
import io.github.iliekpie.bootstrap.util.Transformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GameScreen extends Screen {

    Renderable cube;

    public GameScreen(int width, int height) {
        super(width, height, "test");
        camera.moveTo(new Vector3f(0.5f, 0.5f, -5.0f));
        camera.lookAt(new Vector3f(0, 0, 0));
        cube = new Cube(shaderProgram);
    }

    protected void render() {
        shaderProgram.setUniformMatrix(
                shaderProgram.getUniformLocation("MVP"),
                false,
                Matrix4f.mul(
                        cube.getModelMatrix(),
                        camera.getCombinedMatrix(),
                        null
                )
        );

        cube.draw();
    }

    protected void tick() {
        Transformation.applyLocalTransformation(new Vector3f(), new Vector3f(0, (float) Math.toRadians(0.5), 0), cube.getModelMatrix());
    }
}
