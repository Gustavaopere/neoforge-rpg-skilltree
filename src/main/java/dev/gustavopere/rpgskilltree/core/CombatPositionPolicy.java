package dev.gustavopere.rpgskilltree.core;

/** Pure server-side geometry used only when both provider reach and entity motion are known. */
public final class CombatPositionPolicy {
    private static final double EPSILON = 1.0E-9D;

    private CombatPositionPolicy() {}

    public static boolean isIdealSpearRange(
        double distance,
        double entityInteractionReach,
        double providerReachBonus
    ) {
        requireFiniteNonNegative(distance, "distance");
        requireFinitePositive(entityInteractionReach, "entityInteractionReach");
        requireFiniteNonNegative(providerReachBonus, "providerReachBonus");
        double effectiveReach = entityInteractionReach + providerReachBonus;
        return distance + EPSILON >= effectiveReach * 0.70D
            && distance <= effectiveReach + EPSILON;
    }

    public static boolean isAdvancingToward(
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        double targetVelocityX,
        double targetVelocityZ
    ) {
        requireFinite(attackerX, "attackerX");
        requireFinite(attackerZ, "attackerZ");
        requireFinite(targetX, "targetX");
        requireFinite(targetZ, "targetZ");
        requireFinite(targetVelocityX, "targetVelocityX");
        requireFinite(targetVelocityZ, "targetVelocityZ");
        double toAttackerX = attackerX - targetX;
        double toAttackerZ = attackerZ - targetZ;
        if (lengthSquared(toAttackerX, toAttackerZ) <= EPSILON) return false;
        if (lengthSquared(targetVelocityX, targetVelocityZ) <= EPSILON) return false;
        return targetVelocityX * toAttackerX + targetVelocityZ * toAttackerZ > EPSILON;
    }

    public static boolean isFlankOrBack(
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        double targetLookX,
        double targetLookZ
    ) {
        requireFinite(attackerX, "attackerX");
        requireFinite(attackerZ, "attackerZ");
        requireFinite(targetX, "targetX");
        requireFinite(targetZ, "targetZ");
        requireFinite(targetLookX, "targetLookX");
        requireFinite(targetLookZ, "targetLookZ");
        double toAttackerX = attackerX - targetX;
        double toAttackerZ = attackerZ - targetZ;
        if (lengthSquared(toAttackerX, toAttackerZ) <= EPSILON) return false;
        if (lengthSquared(targetLookX, targetLookZ) <= EPSILON) return false;
        return targetLookX * toAttackerX + targetLookZ * toAttackerZ <= EPSILON;
    }

    private static double lengthSquared(double x, double z) {
        return x * x + z * z;
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException(name + " must be finite");
    }

    private static void requireFiniteNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }

    private static void requireFinitePositive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }
}
