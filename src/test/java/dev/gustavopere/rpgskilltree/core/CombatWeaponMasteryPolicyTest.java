package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public final class CombatWeaponMasteryPolicyTest {
    public static void main(String[] args) {
        var expectedLanes = Map.ofEntries(
            Map.entry(WeaponFamily.SWORD, "epicfight:sword"),
            Map.entry(WeaponFamily.AXE, "epicfight:axe"),
            Map.entry(WeaponFamily.SPEAR, "epicfight:spear"),
            Map.entry(WeaponFamily.DAGGER, "epicfight:dagger"),
            Map.entry(WeaponFamily.HAMMER, "epicfight:heavy"),
            Map.entry(WeaponFamily.MACE, "combat:mace"),
            Map.entry(WeaponFamily.SCYTHE, "combat:scythe"),
            Map.entry(WeaponFamily.BOW, "combat:bow"),
            Map.entry(WeaponFamily.CROSSBOW, "combat:crossbow")
        );

        for (var expected : expectedLanes.entrySet()) {
            eq(expected.getValue(), masteryLane(expected.getKey()));
            List<MasteryAward> awards = confirmedHitAwards(
                new ActionOrigin("epicfight:damage_post", 0),
                expected.getKey(),
                "weapon_hit"
            );
            eq(2, awards.size());
            eq(1L, countLane(awards, "epicfight:weapon"));
            eq(1L, countLane(awards, expected.getValue()));
        }

        eq(
            List.of(),
            confirmedHitAwards(
                new ActionOrigin("epicfight:damage_post", 1),
                WeaponFamily.SWORD,
                "derived_hit"
            )
        );

        System.out.println("CombatWeaponMasteryPolicyTest: PASS");
    }

    private static String masteryLane(WeaponFamily family) {
        return (String) invoke("masteryLane", new Class<?>[] {WeaponFamily.class}, family);
    }

    @SuppressWarnings("unchecked")
    private static List<MasteryAward> confirmedHitAwards(
        ActionOrigin origin,
        WeaponFamily family,
        String sourceId
    ) {
        return (List<MasteryAward>) invoke(
            "forConfirmedHit",
            new Class<?>[] {ActionOrigin.class, WeaponFamily.class, String.class},
            origin,
            family,
            sourceId
        );
    }

    private static Object invoke(String methodName, Class<?>[] parameterTypes, Object... arguments) {
        try {
            Class<?> policy = Class.forName("dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicy");
            return policy.getMethod(methodName, parameterTypes).invoke(null, arguments);
        } catch (ClassNotFoundException | NoSuchMethodException missingFeature) {
            throw new AssertionError("CombatWeaponMasteryPolicy." + methodName + " is missing", missingFeature);
        } catch (IllegalAccessException inaccessible) {
            throw new AssertionError(inaccessible);
        } catch (InvocationTargetException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new AssertionError(cause);
        }
    }

    private static long countLane(List<MasteryAward> awards, String lane) {
        return awards.stream().filter(award -> award.laneId().equals(lane)).count();
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
