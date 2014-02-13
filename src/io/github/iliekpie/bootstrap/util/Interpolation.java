package io.github.iliekpie.bootstrap.util;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Interpolation {
    public static Vector2f linear(Vector2f v0, Vector2f v1, float alpha) {
        return Vector2f.add(v0, (Vector2f) Vector2f.sub(v1, v0, null).scale(alpha), null);
    }

    public static Vector3f linear(Vector3f v0, Vector3f v1, float alpha) {
        return Vector3f.add(v0, (Vector3f) Vector3f.sub(v1, v0, null).scale(alpha), null);
    }
}
