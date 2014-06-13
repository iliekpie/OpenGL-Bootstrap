package io.github.iliekpie.bootstrap.util;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Interpolation {
    public static float linear(float naught, float prime, float alpha) {
        return naught + (prime - naught) * alpha;
    }

    public static Vector2f linear(Vector2f naught, Vector2f prime, float alpha) {
        return Vector2f.add(naught, (Vector2f) Vector2f.sub(prime, naught, null).scale(alpha), null);
    }

    public static Vector3f linear(Vector3f naught, Vector3f prime, float alpha) {
        return Vector3f.add(naught, (Vector3f) Vector3f.sub(prime, naught, null).scale(alpha), null);
    }
}
