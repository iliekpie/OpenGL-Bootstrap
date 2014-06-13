package io.github.iliekpie.bootstrap.graphics.data;

import java.util.*;

public class Model {
    private Map<Texture.MapType, Texture> maps = new HashMap<Texture.MapType, Texture>(4); //Normal maps, height maps, etc.
    private Material material = new BlankMaterial();
    private Map<String, Number> attributes = new HashMap<String, Number>(2);
    private Mesh mesh = new Mesh();

    public Model() {

    }

    public Model(Model instance) {
        this.maps = new HashMap<Texture.MapType, Texture>(instance.maps);
        this.material = new Material(instance.material);
        this.attributes = new HashMap<String, Number>(instance.attributes);
        this.mesh = new Mesh(instance.mesh);
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void addMap(Texture.MapType type, Texture map) {
        maps.put(type, map);
    }

    public Texture getMap(Texture.MapType type) {
        return maps.get(type);
    }

    public Map<Texture.MapType, Texture> getMaps() {
        return maps;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}