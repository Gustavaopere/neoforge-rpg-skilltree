package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.InvocationTargetException;

public final class CombatPositionPolicyTest {
    public static void main(String[] args) {
        spearIdealRangeUsesProviderEffectiveReach();
        advancingRequiresMovementTowardTheAttacker();
        daggerFlankRequiresARealLateralOrRearPosition();
        System.out.println("CombatPositionPolicyTest: PASS");
    }

    private static void spearIdealRangeUsesProviderEffectiveReach() {
        require(idealSpearRange(2.8D, 3.0D, 1.0D), "70% boundary is ideal");
        require(idealSpearRange(4.0D, 3.0D, 1.0D), "100% boundary is ideal");
        require(!idealSpearRange(2.79D, 3.0D, 1.0D), "too-close spear hit is not ideal");
        require(!idealSpearRange(4.01D, 3.0D, 1.0D), "out-of-reach hit is not ideal");
    }

    private static void advancingRequiresMovementTowardTheAttacker() {
        require(
            advancingToward(0.0D, 0.0D, 4.0D, 0.0D, -0.1D, 0.0D),
            "movement toward the attacker is advancing"
        );
        require(
            !advancingToward(0.0D, 0.0D, 4.0D, 0.0D, 0.1D, 0.0D),
            "movement away from the attacker is not advancing"
        );
        require(
            !advancingToward(0.0D, 0.0D, 4.0D, 0.0D, 0.0D, 0.0D),
            "a stationary target is not advancing"
        );
    }

    private static void daggerFlankRequiresARealLateralOrRearPosition() {
        require(
            flankOrBack(0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 1.0D),
            "attacker behind the target qualifies"
        );
        require(
            flankOrBack(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D),
            "attacker at the target's side qualifies"
        );
        require(
            !flankOrBack(0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D),
            "attacker in front of the target does not qualify"
        );
        require(
            !flankOrBack(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D),
            "overlapping positions are not a valid flank"
        );
    }

    private static boolean idealSpearRange(double distance, double entityReach, double providerReach) {
        return (boolean) invoke(
            "isIdealSpearRange",
            new Class<?>[] {double.class, double.class, double.class},
            distance,
            entityReach,
            providerReach
        );
    }

    private static boolean advancingToward(
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        double targetVelocityX,
        double targetVelocityZ
    ) {
        return (boolean) invoke(
            "isAdvancingToward",
            new Class<?>[] {
                double.class, double.class, double.class, double.class, double.class, double.class
            },
            attackerX,
            attackerZ,
            targetX,
            targetZ,
            targetVelocityX,
            targetVelocityZ
        );
    }

    private static boolean flankOrBack(
        double attackerX,
        double attackerZ,
        double targetX,
        double targetZ,
        double targetLookX,
        double targetLookZ
    ) {
        return (boolean) invoke(
            "isFlankOrBack",
            new Class<?>[] {
                double.class, double.class, double.class, double.class, double.class, double.class
            },
            attackerX,
            attackerZ,
            targetX,
            targetZ,
            targetLookX,
            targetLookZ
        );
    }

    private static Object invoke(String methodName, Class<?>[] parameterTypes, Object... arguments) {
        try {
            Class<?> policy = Class.forName("dev.gustavopere.rpgskilltree.core.CombatPositionPolicy");
            return policy.getMethod(methodName, parameterTypes).invoke(null, arguments);
        } catch (ClassNotFoundException | NoSuchMethodException missingFeature) {
            throw new AssertionError("CombatPositionPolicy." + methodName + " is missing", missingFeature);
        } catch (IllegalAccessException inaccessible) {
            throw new AssertionError(inaccessible);
        } catch (InvocationTargetException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new AssertionError(cause);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
