package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class EntityScalingPersistenceTest {
    public static void main(String[] args) {
        uninitializedAttachmentRoundTrips();
        initializedEntityScalingRoundTripsExactly();
        hugeLevelsAndRarityRemainStableAcrossSaveLoad();
        malformedPayloadsFailClosed();
        System.out.println("EntityScalingPersistenceTest: PASS");
    }

    private static void uninitializedAttachmentRoundTrips() {
        EntityScalingAttachmentData data = EntityScalingAttachmentData.uninitialized();
        check(!data.initialized(), "fresh entity scaling attachment should be uninitialized");
        eq(data, EntityScalingStateCodec.decode(EntityScalingStateCodec.encode(data)));
        expect(IllegalStateException.class, data::requireState);
    }

    private static void initializedEntityScalingRoundTripsExactly() {
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 3L, -2L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                23L,
                OptionalLong.of(50L),
                50L,
                56L,
                56L
            ),
            1L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:veteran"), 5L)),
            0x123456789ABCDEFL
        );
        EntityScalingAttachmentData data = EntityScalingAttachmentData.initialized(state);

        byte[] encoded = EntityScalingStateCodec.encode(data);
        EntityScalingAttachmentData decoded = EntityScalingStateCodec.decode(encoded);

        check(decoded.initialized(), "decoded scaling should remain initialized");
        eq(state, decoded.requireState());
        eq(EntityArchetype.HOSTILE, decoded.requireState().archetype());
        eq(MobRarityKey.of("rpgskilltree:veteran"), decoded.requireState().rarity().orElseThrow().rarity());
        eq(56L, decoded.requireState().entityLevel());
        eq(TerritoryKey.of("minecraft:overworld", 3L, -2L), decoded.requireState().territory());
    }

    private static void hugeLevelsAndRarityRemainStableAcrossSaveLoad() {
        long huge = 5_000_000_000L;
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:the_end", -99L, 77L),
            new EntityLevelResolution(
                EntityArchetype.BOSS,
                huge,
                OptionalLong.of(huge - 1L),
                huge,
                huge + 1_024L,
                huge + 1_024L
            ),
            24L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:champion"), 1_000L)),
            Long.MIN_VALUE + 7L
        );

        EntityScalingAttachmentData decoded = EntityScalingStateCodec.decode(
            EntityScalingStateCodec.encode(EntityScalingAttachmentData.initialized(state))
        );

        eq(state, decoded.requireState());
        eq(huge + 1_024L, decoded.requireState().entityLevel());
        eq(huge, decoded.requireState().levelResolution().nativeAreaLevel());
        eq(Long.MIN_VALUE + 7L, decoded.requireState().deterministicSeed());
    }

    private static void malformedPayloadsFailClosed() {
        byte[] valid = EntityScalingStateCodec.encode(EntityScalingAttachmentData.uninitialized());

        byte[] unsupported = valid.clone();
        unsupported[3] = 99;
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(unsupported));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(trailing));

        byte[] truncated = Arrays.copyOf(valid, Math.max(0, valid.length - 1));
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(truncated));
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

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
