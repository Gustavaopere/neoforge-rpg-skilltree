package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class MobRarityFoundationTest {
    public static void main(String[] args) {
        rarityKeysAreStableAndExtensible();
        raritySelectionFeedsEntityLevelAdjustment();
        hugeLevelsAndDeterministicSeedRemainIntact();
        invalidSelectionsFailClosed();
        System.out.println("MobRarityFoundationTest: PASS");
    }

    private static void rarityKeysAreStableAndExtensible() {
        MobRarityKey elite = MobRarityKey.of("rpgskilltree:elite");
        eq("rpgskilltree:elite", elite.serializedId());
        eq("rpgskilltree", elite.namespace());
        eq("elite", elite.path());
        eq(elite, MobRarityKey.of(elite.serializedId()));

        expect(IllegalArgumentException.class, () -> MobRarityKey.of("elite"));
        expect(IllegalArgumentException.class, () -> MobRarityKey.of("RpgSkillTree:elite"));
        expect(IllegalArgumentException.class, () -> MobRarityKey.of("rpgskilltree:Elite"));
        expect(IllegalArgumentException.class, () -> MobRarityKey.of("rpgskilltree:"));
    }

    private static void raritySelectionFeedsEntityLevelAdjustment() {
        EntityLevelContext levelContext = EntityLevelContext.withRelevantPlayer(
            20L,
            12L,
            EntityArchetype.HOSTILE
        );
        MobRarityContext context = new MobRarityContext(levelContext, 0x1234ABCDL);

        MobRaritySelection selection = MobRarityService.resolve(context, input -> {
            eq(EntityArchetype.HOSTILE, input.archetype());
            eq(20L, input.baseFloor());
            eq(0x1234ABCDL, input.deterministicSeed());
            return new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 4L);
        });

        eq(MobRarityKey.of("rpgskilltree:elite"), selection.rarity());
        eq(4L, selection.levelBonus());

        EntityLevelResolution resolved = EntityLevelService.resolve(
            levelContext,
            selection.toLevelAdjustment(-2L)
        );
        eq(20L, resolved.baseFloor());
        eq(22L, resolved.rolledLevel());
        eq(22L, resolved.finalLevel());
    }

    private static void hugeLevelsAndDeterministicSeedRemainIntact() {
        long huge = 5_000_000_000L;
        long seed = Long.MIN_VALUE + 12345L;
        MobRarityContext context = new MobRarityContext(
            EntityLevelContext.nativeOnly(huge, EntityArchetype.BOSS),
            seed
        );

        MobRaritySelection selection = MobRarityService.resolve(context, input -> {
            eq(huge, input.baseFloor());
            eq(seed, input.deterministicSeed());
            return new MobRaritySelection(MobRarityKey.of("rpgskilltree:boss"), 0L);
        });

        EntityLevelResolution resolved = EntityLevelService.resolve(
            context.levelContext(),
            selection.toLevelAdjustment(0L)
        );
        eq(huge, resolved.finalLevel());
    }

    private static void invalidSelectionsFailClosed() {
        MobRarityContext context = new MobRarityContext(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.PASSIVE),
            7L
        );

        expect(IllegalArgumentException.class, () -> new MobRaritySelection(
            MobRarityKey.of("rpgskilltree:invalid"),
            -1L
        ));
        expect(IllegalStateException.class, () -> MobRarityService.resolve(context, input -> null));
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
