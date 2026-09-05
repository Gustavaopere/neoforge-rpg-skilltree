package dev.gustavopere.rpgskilltree.core;

import java.nio.ByteBuffer;

/** Fixed-width codec for persisted A0104-A0106 cooldown deadlines. */
public final class CombatPerkCooldownStateCodec {
    public static final int BYTE_LENGTH = Long.BYTES * 3;

    private CombatPerkCooldownStateCodec() {}

    public static byte[] encode(CombatPerkCooldownState state) {
        if (state == null) throw new IllegalArgumentException("cooldown state must not be null");
        return ByteBuffer.allocate(BYTE_LENGTH)
            .putLong(state.secondWindCooldownUntilTick())
            .putLong(state.reactiveShellCooldownUntilTick())
            .putLong(state.emergencyGuardCooldownUntilTick())
            .array();
    }

    public static CombatPerkCooldownState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded cooldown state must not be null");
        if (encoded.length != BYTE_LENGTH) {
            throw new IllegalArgumentException(
                "invalid combat perk cooldown state length: " + encoded.length
            );
        }
        ByteBuffer buffer = ByteBuffer.wrap(encoded);
        return new CombatPerkCooldownState(
            buffer.getLong(),
            buffer.getLong(),
            buffer.getLong()
        );
    }
}
