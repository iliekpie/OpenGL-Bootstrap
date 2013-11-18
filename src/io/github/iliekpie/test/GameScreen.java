package io.github.iliekpie.test;

import io.github.iliekpie.bootstrap.graphics.Screen;
import org.lwjgl.util.vector.Vector3f;

public class GameScreen extends Screen {

    Cube cube;

    public GameScreen(int width, int height) {
        super(width, height, "test");
        camera.moveTo(new Vector3f(0.5f, 0.5f, -1.0f));
        camera.lookAt(new Vector3f(0, 0, 0));
        cube = new Cube(shaderProgram);
    }

    protected void render() {
        shaderProgram.setUniformMatrix(
                shaderProgram.getUniformLocation("MVP"),
                false,
                camera.getCombinedMatrix()
        );

        cube.draw();
    }

    protected void tick() {

    }
}
