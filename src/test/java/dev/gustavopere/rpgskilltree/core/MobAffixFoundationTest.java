package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MobAffixFoundationTest {
    public static void main(String[] args) {
        affixKeysAreStableAndExtensible();
        selectionIsCanonicalUniqueAndImmutable();
        policySeesFinalEntityStateRarityAndSeed();
        emptySelectionIsValidButInvalidResultsFailClosed();
        System.out.println("MobAffixFoundationTest: PASS");
    }

    private static void affixKeysAreStableAndExtensible() {
        MobAffixKey armored = MobAffixKey.of("rpgskilltree:armored");
        eq("rpgskilltree:armored", armored.serializedId());
        eq("rpgskilltree", armored.namespace());
        eq("armored", armored.path());
        eq(armored, MobAffixKey.of(armored.serializedId()));

        expect(IllegalArgumentException.class, () -> MobAffixKey.of("armored"));
        expect(IllegalArgumentException.class, () -> MobAffixKey.of("RpgSkillTree:armored"));
        expect(IllegalArgumentException.class, () -> MobAffixKey.of("rpgskilltree:Armored"));
    }

    private static void selectionIsCanonicalUniqueAndImmutable() {
        MobAffixKey swift = MobAffixKey.of("rpgskilltree:swift");
        MobAffixKey armored = MobAffixKey.of("rpgskilltree:armored");
        ArrayList<MobAffixKey> source = new ArrayList<>(List.of(swift, armored));
        MobAffixSelection selection = new MobAffixSelection(source);

        eq(List.of(armored, swift), selection.affixes());
        source.clear();
        eq(2, selection.affixes().size());
        expect(UnsupportedOperationException.class, () -> selection.affixes().add(swift));
        expect(IllegalArgumentException.class, () -> new MobAffixSelection(List.of(swift, swift)));
    }

    private static void policySeesFinalEntityStateRarityAndSeed() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20")));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(15L, 30L, EntityArchetype.HOSTILE),
            new EntityLevelAdjustment(1L, 4L)
        );
        EntityStatScalingResult scaled = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.HOSTILE, context -> Map.of(
                health, stat -> stat.providerValue().add(BigDecimal.valueOf(context.entityLevel()))
            ))
        );
        MobRaritySelection rarity = new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 4L);
        long seed = 0x123456789ABCDEFL;
        MobAffixContext context = new MobAffixContext(scaled, rarity, seed);

        MobAffixSelection selection = MobAffixService.resolve(context, input -> {
            eq(EntityArchetype.HOSTILE, input.archetype());
            eq(35L, input.entityLevel());
            eq(rarity, input.rarity());
            eq(seed, input.deterministicSeed());
            decimalEq("55", input.scaledEntity().effectiveStats().value(health));
            return new MobAffixSelection(List.of(
                MobAffixKey.of("rpgskilltree:swift"),
                MobAffixKey.of("rpgskilltree:armored")
            ));
        });

        eq(List.of(
            MobAffixKey.of("rpgskilltree:armored"),
            MobAffixKey.of("rpgskilltree:swift")
        ), selection.affixes());
    }

    private static void emptySelectionIsValidButInvalidResultsFailClosed() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(health, BigDecimal.TEN));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(1L, EntityArchetype.PASSIVE),
            EntityLevelAdjustment.NONE
        );
        EntityStatScalingResult scaled = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.PASSIVE, context -> Map.of(
                health, stat -> stat.providerValue()
            ))
        );
        MobAffixContext context = new MobAffixContext(
            scaled,
            new MobRaritySelection(MobRarityKey.of("rpgskilltree:normal"), 0L),
            1L
        );

        eq(List.of(), MobAffixService.resolve(context, input -> MobAffixSelection.empty()).affixes());
        expect(IllegalStateException.class, () -> MobAffixService.resolve(context, input -> null));
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
