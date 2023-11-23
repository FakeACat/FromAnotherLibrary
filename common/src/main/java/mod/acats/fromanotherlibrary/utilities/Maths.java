package mod.acats.fromanotherlibrary.utilities;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class Maths {
    public static Vec3 lerpVec3(float progress, Vec3 start, Vec3 finish) {
        float f = 1.0F - progress;
        return finish.multiply(progress, progress, progress)
                .add(start.multiply(f, f, f));
    }

    public static Optional<Float> cosRuleFindAngleInRadians(float adjSide1, float adjSide2, float oppositeSide) {

        float value = (float) Math.acos((adjSide1 * adjSide1 +
                adjSide2 * adjSide2 -
                oppositeSide * oppositeSide) / (2.0F * adjSide1 * adjSide2));

        return Float.isNaN(value)
                ? Optional.empty()
                : Optional.of(value);
    }

    public static float radiansToDegrees(float angleInRadians) {
        return angleInRadians * Mth.RAD_TO_DEG;
    }

    public static Optional<Float> xRotFromVec3Degrees(Vec3 vec3) {
        return xRotFromVec3Radians(vec3).map(Maths::radiansToDegrees);
    }

    public static Optional<Float> yRotFromVec3Degrees(Vec3 vec3) {
        return yRotFromVec3Radians(vec3).map(Maths::radiansToDegrees);
    }

    public static Optional<Float> xRotFromVec3Radians(Vec3 vec3) {
        float f = (float) Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z);
        return !(Math.abs(vec3.y) > 1.0E-5F) && !(Math.abs(f) > 1.0E-5F)
                ? Optional.empty()
                : Optional.of((float) -Mth.atan2(vec3.y, f));
    }

    public static Optional<Float> yRotFromVec3Radians(Vec3 vec3) {
        return !(Math.abs(vec3.x) > 1.0E-5F) && !(Math.abs(vec3.z) > 1.0E-5F)
                ? Optional.empty()
                : Optional.of((float) (Mth.atan2(vec3.z, vec3.x) - (float) Math.PI / 2.0F));
    }
}
