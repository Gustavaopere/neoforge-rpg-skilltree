package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class EntityScalingPersistenceTest {
    public static void main(String[] args) {
        firstInitializationSelectsRarityOnceAndResumeNeverRerolls();
        codecRoundTripPreservesRarityAndSeed();
        malformedPayloadsFailClosed();
        System.out.println("EntityScalingPersistenceTest: PASS");
    }

    private static void firstInitializationSelectsRarityOnceAndResumeNeverRerolls() {
        MobRarityContext context = new MobRarityContext(
            EntityLevelContext.withRelevantPlayer(20L, 12L, EntityArchetype.HOSTILE),
            0x1122334455667788L
        );
        AtomicInteger calls = new AtomicInteger();

        EntityScalingState initial = EntityScalingBootstrap.initializeOrResume(
            Optional.empty(),
            context,
            input -> {
                calls.incrementAndGet();
                return new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 4L);
            }
        );

        eq(1, calls.get());
        eq(MobRarityKey.of("rpgskilltree:elite"), initial.raritySelection().rarity());
        eq(4L, initial.raritySelection().levelBonus());
        eq(0x1122334455667788L, initial.deterministicSeed());

        EntityScalingState resumed = EntityScalingBootstrap.initializeOrResume(
            Optional.of(initial),
            context,
            input -> {
                throw new AssertionError("persisted entity rarity must not be rerolled");
            }
        );
        eq(initial, resumed);
        eq(1, calls.get());
    }

    private static void codecRoundTripPreservesRarityAndSeed() {
        EntityScalingState original = new EntityScalingState(
            new MobRaritySelection(MobRarityKey.of("rpgskilltree:champion"), 12L),
            Long.MIN_VALUE + 998877L
        );

        byte[] encoded = EntityScalingStateCodec.encode(original);
        eq(EntityScalingStateCodec.CURRENT_VERSION, readInt(encoded, 0));
        eq(original, EntityScalingStateCodec.decode(encoded));
    }

    private static void malformedPayloadsFailClosed() {
        EntityScalingState state = new EntityScalingState(
            new MobRaritySelection(MobRarityKey.of("rpgskilltree:veteran"), 2L),
            55L
        );
        byte[] valid = EntityScalingStateCodec.encode(state);

        byte[] unsupported = valid.clone();
        writeInt(unsupported, 0, 99);
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(unsupported));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        trailing[trailing.length - 1] = 1;
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(trailing));

        byte[] truncated = Arrays.copyOf(valid, valid.length - 1);
        expect(IllegalArgumentException.class, () -> EntityScalingStateCodec.decode(truncated));
    }

    private static int readInt(byte[] data, int offset) {
        return ((data[offset] & 0xFF) << 24)
            | ((data[offset + 1] & 0xFF) << 16)
            | ((data[offset + 2] & 0xFF) << 8)
            | (data[offset + 3] & 0xFF);
    }

    private static void writeInt(byte[] data, int offset, int value) {
        data[offset] = (byte) (value >>> 24);
        data[offset + 1] = (byte) (value >>> 16);
        data[offset + 2] = (byte) (value >>> 8);
        data[offset + 3] = (byte) value;
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
