package io.github.iliekpie.bootstrap.graphics;

import io.github.iliekpie.bootstrap.input.CameraController;
import io.github.iliekpie.bootstrap.util.FPSCounter;
import io.github.iliekpie.bootstrap.util.FileUtils;
import io.github.iliekpie.bootstrap.util.Timer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public abstract class Screen {
    FPSCounter counter = new FPSCounter() {
        public void onFPSUpdate(int fps) {
            Display.setTitle("FPS: " + fps);
        }
    };
    private Timer timer = new Timer();

    protected CameraController controller;
    protected Camera camera;

    protected ShaderProgram shaderProgram;

    public Screen(int width, int height, String title) { //swap to context/options class?
        setupDisplay(width, height, title);
        setupShaders();
        camera = new Camera(width, height);
        controller = new CameraController();
        controller.bind(camera);
    }

    private void setupDisplay(int width, int height, String title) {
        //Setup an OpenGL context of version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAttribs = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            //Display.setVSyncEnabled(true);
            Display.setResizable(true);
            Display.create(pixelFormat, contextAttribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        //Set the background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0.0f);

        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void setupShaders() {
        shaderProgram = new ShaderProgram();
        shaderProgram.addShader(FileUtils.loadFile("shaders/BasicProjection.vert"), Shader.VERTEX);
        shaderProgram.addShader(FileUtils.loadFile("shaders/BasicProjection.frag"), Shader.FRAGMENT);
        try {
            shaderProgram.link();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            //Clear the color and depth buffers.
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            if (Display.wasResized()) {
                reshapeDisplay(Display.getWidth(), Display.getHeight());
            }

            controller.handleInput();
            tick(timer.getDeltaTime());
            draw();
            counter.tick();
            Display.update();
            Display.sync(60);
        }
        onScreenClose();
    }

    private void draw() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Set the shader program as the currently used one.
        shaderProgram.use();

        render();

        shaderProgram.disable();
    }

    private void reshapeDisplay(int width, int height) {
        camera.update(width, height);
        GL11.glViewport(0, 0, width, height);
    }

    protected abstract void tick(float delta);

    protected abstract void render();

    protected void onScreenClose() {
        shaderProgram.destroy();
    }
}
