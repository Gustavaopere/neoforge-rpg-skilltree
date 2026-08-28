package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityInspectionPolicy;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityInstanceInspector;
import java.lang.reflect.RecordComponent;
import java.util.List;
import java.util.Map;

public final class EntityInstanceInspectorTest {
    public static void main(String[] args) {
        livingSnapshotIsBoundedAndImmutable();
        babyAndTameStateRemainExplicit();
        policyRejectsOutOfRangeAndMissingLineOfSight();
        snapshotHasNoArbitraryNbtSurface();
        System.out.println("EntityInstanceInspectorTest: PASS");
    }

    private static void livingSnapshotIsBoundedAndImmutable() {
        EntityInstanceSnapshot snapshot = EntityInstanceInspector.inspect(new EntityInstanceInspector.Input(
            "minecraft:wolf",
            0.6F,
            0.85F,
            14.0D,
            20.0D,
            Map.of(EntityFactKeys.ARMOR, 2.0D),
            null,
            List.of(new EntityEffectSnapshot("minecraft:speed", 1, 200L, false, true)),
            true,
            "00000000-0000-0000-0000-000000000001",
            false,
            true,
            false,
            false,
            false,
            true
        ));

        check(snapshot.entityId().equals("minecraft:wolf"), "entity id");
        check(snapshot.currentHealth() == 14.0D, "current health");
        check(snapshot.maxHealth() == 20.0D, "max health");
        check(snapshot.currentAttributes().size() == 1, "only present attributes are exposed");
        check(snapshot.effects().size() == 1, "active effects");
        try {
            snapshot.currentAttributes().put("bad", 1.0D);
            throw new AssertionError("attribute map must be immutable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void babyAndTameStateRemainExplicit() {
        EntityInstanceSnapshot snapshot = EntityInstanceInspector.inspect(new EntityInstanceInspector.Input(
            "minecraft:wolf",
            0.6F,
            0.85F,
            8.0D,
            20.0D,
            Map.of(),
            -1200,
            List.of(),
            true,
            "owner",
            true,
            false,
            false,
            false,
            false,
            false
        ));

        check(Boolean.TRUE.equals(snapshot.baby()), "negative age is baby");
        check(Integer.valueOf(-1200).equals(snapshot.ageTicks()), "age preserved");
        check(Boolean.TRUE.equals(snapshot.tame()), "tame");
        check(snapshot.ownerId().equals("owner"), "owner");
        check(Boolean.TRUE.equals(snapshot.sitting()), "sitting");
        check(Boolean.FALSE.equals(snapshot.breedReady()), "breeding state");
    }

    private static void policyRejectsOutOfRangeAndMissingLineOfSight() {
        EntityInspectionPolicy policy = new EntityInspectionPolicy(64.0D, true);
        check(policy.allows(64.0D, true), "boundary distance accepted");
        check(!policy.allows(64.01D, true), "too far rejected");
        check(!policy.allows(10.0D, false), "line of sight required");
        check(!policy.allows(Double.NaN, true), "NaN rejected");
    }

    private static void snapshotHasNoArbitraryNbtSurface() {
        for (RecordComponent component : EntityInstanceSnapshot.class.getRecordComponents()) {
            String surface = (component.getName() + " " + component.getType().getName()).toLowerCase();
            check(!surface.contains("nbt") && !surface.contains("compoundtag"), "NBT surface forbidden: " + surface);
        }
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
