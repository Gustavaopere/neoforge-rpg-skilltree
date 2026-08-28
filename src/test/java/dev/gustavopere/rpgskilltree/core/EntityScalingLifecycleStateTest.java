package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;

public final class EntityScalingLifecycleStateTest {
    public static void main(String[] args) {
        persistedStateRoundTripsWithoutRerolling();
        existingStateWinsWithoutInvokingInitializer();
        missingStateInitializesExactlyOnce();
        rarityIsOptionalButResolutionRemainsAuditable();
        inconsistentStateFailsClosed();
        malformedPayloadsFailClosed();
        System.out.println("EntityScalingLifecycleStateTest: PASS");
    }

    private static void persistedStateRoundTripsWithoutRerolling() {
        long huge = 5_000_000_000L;
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", -17L, 23L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                huge,
                OptionalLong.of(4_000_000_000L),
                huge,
                huge + 7L,
                huge + 7L
            ),
            2L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 5L)),
            Long.MIN_VALUE + 97L
        );

        byte[] encoded = EntityScalingStateCodec.encode(state);
        EntityScalingState decoded = EntityScalingStateCodec.decode(encoded);

        eq(state, decoded);
        eq(huge + 7L, decoded.levelResolution().finalLevel());
        eq("minecraft:overworld", decoded.territory().dimensionId());
        eq(MobRarityKey.of("rpgskilltree:elite"), decoded.rarity().orElseThrow().rarity());
    }

    private static void existingStateWinsWithoutInvokingInitializer() {
        EntityScalingState existing = sampleState();
        AtomicInteger calls = new AtomicInteger();

        EntityScalingState resolved = EntityScalingBootstrap.resumeOrInitialize(
            Optional.of(existing),
            () -> {
                calls.incrementAndGet();
                return sampleState();
            }
        );

        same(existing, resolved);
        eq(0, calls.get());
    }

    private static void missingStateInitializesExactlyOnce() {
        AtomicInteger calls = new AtomicInteger();
        EntityScalingState created = sampleState();

        EntityScalingState resolved = EntityScalingBootstrap.resumeOrInitialize(
            Optional.empty(),
            () -> {
                calls.incrementAndGet();
                return created;
            }
        );

        same(created, resolved);
        eq(1, calls.get());
        expect(IllegalStateException.class, () -> EntityScalingBootstrap.resumeOrInitialize(
            Optional.empty(),
            () -> null
        ));
    }

    private static void rarityIsOptionalButResolutionRemainsAuditable() {
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:the_nether", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.SPECIAL,
                30L,
                OptionalLong.empty(),
                30L,
                27L,
                27L
            ),
            -3L,
            Optional.empty(),
            123456789L
        );

        EntityScalingState decoded = EntityScalingStateCodec.decode(EntityScalingStateCodec.encode(state));
        eq(state, decoded);
        eq(Optional.empty(), decoded.rarity());
    }

    private static void inconsistentStateFailsClosed() {
        TerritoryKey territory = TerritoryKey.of("minecraft:overworld", 1L, 2L);
        MobRaritySelection elite = new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 4L);

        expect(IllegalArgumentException.class, () -> new EntityScalingState(
            territory,
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                20L,
                OptionalLong.of(25L),
                20L,
                26L,
                26L
            ),
            1L,
            Optional.of(elite),
            1L
        ));

        expect(IllegalArgumentException.class, () -> new EntityScalingState(
            territory,
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                20L,
                OptionalLong.of(25L),
                25L,
                30L,
                29L
            ),
            1L,
            Optional.of(elite),
            1L
        ));
    }

    private static void malformedPayloadsFailClosed() {
        byte[] encoded = EntityScalingStateCodec.encode(sampleState());

        byte[] unsupported = encoded.clone();
        unsupported[3] = (byte) 99;
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(unsupported));

        byte[] trailing = Arrays.copyOf(encoded, encoded.length + 1);
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(trailing));

        byte[] truncated = Arrays.copyOf(encoded, encoded.length - 1);
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(truncated));
    }

    private static EntityScalingState sampleState() {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 4L, -9L),
            new EntityLevelResolution(
                EntityArchetype.PASSIVE,
                12L,
                OptionalLong.of(9L),
                12L,
                14L,
                14L
            ),
            2L,
            Optional.empty(),
            0x1122334455667788L
        );
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

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
