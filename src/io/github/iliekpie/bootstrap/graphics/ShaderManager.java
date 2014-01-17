package io.github.iliekpie.bootstrap.graphics;

import java.util.HashMap;
import java.util.Map;

public class ShaderManager {
    private Map<String, ShaderProgram> shaderPrograms = new HashMap<String, ShaderProgram>(4);
    private String activeShader;

    public void setActiveProgram(String programName) {
        activeShader = programName;
    }

    public ShaderProgram getActiveProgram() {
        return shaderPrograms.get(activeShader);
    }

    public void addProgram(String name, ShaderProgram program) {
        shaderPrograms.put(name, program);
        setActiveProgram(name);
    }

    public void destroyProgram(String name) {
        shaderPrograms.get(name).destroy();
        shaderPrograms.remove(name);
    }

    public void destroyAll() {
        for (ShaderProgram program : shaderPrograms.values()) {
            program.destroy();
        }
        shaderPrograms.clear();
    }
}
