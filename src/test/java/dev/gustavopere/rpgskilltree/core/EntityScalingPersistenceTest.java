package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;
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
        expect(IllegalStateException.class, data::requireSnapshot);
    }

    private static void initializedEntityScalingRoundTripsExactly() {
        EntityLevelResolution level = new EntityLevelResolution(
            EntityArchetype.HOSTILE,
            23L,
            OptionalLong.of(50L),
            50L,
            56L,
            56L
        );
        MobRaritySelection rarity = new MobRaritySelection(MobRarityKey.of("rpgskilltree:veteran"), 5L);
        EntityScalingSnapshot snapshot = new EntityScalingSnapshot(level, rarity);
        EntityScalingAttachmentData data = EntityScalingAttachmentData.initialized(snapshot);

        byte[] encoded = EntityScalingStateCodec.encode(data);
        EntityScalingAttachmentData decoded = EntityScalingStateCodec.decode(encoded);

        check(decoded.initialized(), "decoded scaling should remain initialized");
        eq(snapshot, decoded.requireSnapshot());
        eq(EntityArchetype.HOSTILE, decoded.requireSnapshot().archetype());
        eq(MobRarityKey.of("rpgskilltree:veteran"), decoded.requireSnapshot().rarity());
        eq(56L, decoded.requireSnapshot().entityLevel());
    }

    private static void hugeLevelsAndRarityRemainStableAcrossSaveLoad() {
        long huge = 5_000_000_000L;
        EntityLevelResolution level = new EntityLevelResolution(
            EntityArchetype.BOSS,
            huge,
            OptionalLong.of(huge - 1L),
            huge,
            huge + 1_024L,
            huge + 1_024L
        );
        EntityScalingSnapshot snapshot = new EntityScalingSnapshot(
            level,
            new MobRaritySelection(MobRarityKey.of("rpgskilltree:champion"), 1_000L)
        );

        EntityScalingAttachmentData decoded = EntityScalingStateCodec.decode(
            EntityScalingStateCodec.encode(EntityScalingAttachmentData.initialized(snapshot))
        );

        eq(snapshot, decoded.requireSnapshot());
        eq(huge + 1_024L, decoded.requireSnapshot().entityLevel());
        eq(huge, decoded.requireSnapshot().levelResolution().nativeAreaLevel());
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
