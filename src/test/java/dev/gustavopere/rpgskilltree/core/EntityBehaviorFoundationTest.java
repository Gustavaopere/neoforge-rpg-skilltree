package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;

public final class EntityBehaviorFoundationTest {
    public static void main(String[] args) {
        behaviorKeysAreStableAndExtensible();
        selectionIsCanonicalUniqueAndImmutable();
        policySeesScaledEntityRarityAffixesAndSeedAtHugeLevels();
        emptySelectionIsValidButInvalidResultsFailClosed();
        System.out.println("EntityBehaviorFoundationTest: PASS");
    }

    private static void behaviorKeysAreStableAndExtensible() {
        EntityBehaviorKey flanker = EntityBehaviorKey.of("rpgskilltree:combat/flanker");
        eq("rpgskilltree:combat/flanker", flanker.serializedId());
        eq("rpgskilltree", flanker.namespace());
        eq("combat/flanker", flanker.path());
        eq(flanker, EntityBehaviorKey.of(flanker.serializedId()));

        expect(IllegalArgumentException.class, () -> EntityBehaviorKey.of("flanker"));
        expect(IllegalArgumentException.class, () -> EntityBehaviorKey.of("RpgSkillTree:flanker"));
        expect(IllegalArgumentException.class, () -> EntityBehaviorKey.of("rpgskilltree:Flanker"));
        expect(IllegalArgumentException.class, () -> EntityBehaviorKey.of("rpgskilltree:"));
    }

    private static void selectionIsCanonicalUniqueAndImmutable() {
        EntityBehaviorKey flanker = EntityBehaviorKey.of("rpgskilltree:combat/flanker");
        EntityBehaviorKey pressure = EntityBehaviorKey.of("rpgskilltree:combat/pressure");
        ArrayList<EntityBehaviorKey> source = new ArrayList<>(List.of(pressure, flanker));
        EntityBehaviorSelection selection = new EntityBehaviorSelection(source);

        eq(List.of(flanker, pressure), selection.behaviors());
        source.clear();
        eq(2, selection.behaviors().size());
        expect(UnsupportedOperationException.class, () -> selection.behaviors().add(flanker));
        expect(IllegalArgumentException.class, () -> new EntityBehaviorSelection(List.of(flanker, flanker)));
    }

    private static void policySeesScaledEntityRarityAffixesAndSeedAtHugeLevels() {
        long hugeLevel = 5_000_000_000L;
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        EntityLevelResolution level = new EntityLevelResolution(
            EntityArchetype.HOSTILE,
            hugeLevel,
            OptionalLong.empty(),
            hugeLevel,
            hugeLevel,
            hugeLevel
        );
        EntityStatScalingResult scaled = new EntityStatScalingResult(
            level,
            new RpgEffectiveStats(hugeLevel, Map.of(health, new BigDecimal("250")))
        );
        MobRaritySelection rarity = new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 8L);
        MobAffixSelection affixes = new MobAffixSelection(List.of(
            MobAffixKey.of("rpgskilltree:armored"),
            MobAffixKey.of("rpgskilltree:swift")
        ));
        long seed = 0x13579BDF2468ACE0L;
        EntityBehaviorContext context = new EntityBehaviorContext(scaled, rarity, affixes, seed);

        EntityBehaviorPolicy policy = input -> {
            eq(EntityArchetype.HOSTILE, input.archetype());
            eq(hugeLevel, input.entityLevel());
            eq(rarity, input.rarity());
            eq(affixes, input.affixes());
            eq(seed, input.deterministicSeed());
            decimalEq("250", input.scaledEntity().effectiveStats().value(health));
            return new EntityBehaviorSelection(List.of(
                EntityBehaviorKey.of("rpgskilltree:combat/pressure"),
                EntityBehaviorKey.of("rpgskilltree:combat/flanker")
            ));
        };

        EntityBehaviorSelection first = EntityBehaviorService.resolve(context, policy);
        EntityBehaviorSelection second = EntityBehaviorService.resolve(context, policy);
        eq(first, second);
        eq(List.of(
            EntityBehaviorKey.of("rpgskilltree:combat/flanker"),
            EntityBehaviorKey.of("rpgskilltree:combat/pressure")
        ), first.behaviors());
    }

    private static void emptySelectionIsValidButInvalidResultsFailClosed() {
        long levelValue = 10L;
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        EntityLevelResolution level = new EntityLevelResolution(
            EntityArchetype.PASSIVE,
            levelValue,
            OptionalLong.empty(),
            levelValue,
            levelValue,
            levelValue
        );
        EntityStatScalingResult scaled = new EntityStatScalingResult(
            level,
            new RpgEffectiveStats(levelValue, Map.of(health, new BigDecimal("20")))
        );
        EntityBehaviorContext context = new EntityBehaviorContext(
            scaled,
            new MobRaritySelection(MobRarityKey.of("rpgskilltree:normal"), 0L),
            MobAffixSelection.empty(),
            42L
        );

        eq(List.of(), EntityBehaviorService.resolve(context, input -> EntityBehaviorSelection.empty()).behaviors());
        expect(NullPointerException.class, () -> EntityBehaviorService.resolve(context, null));
        expect(IllegalStateException.class, () -> EntityBehaviorService.resolve(context, input -> null));
    }

    private static void decimalEq(String expected, BigDecimal actual) {
        BigDecimal expectedDecimal = new BigDecimal(expected);
        if (expectedDecimal.compareTo(actual) != 0) {
            throw new AssertionError(expectedDecimal + " != " + actual);
        }
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
