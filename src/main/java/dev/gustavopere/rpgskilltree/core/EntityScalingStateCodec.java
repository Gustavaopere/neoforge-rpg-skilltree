package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.OptionalLong;

/** Versioned compact codec for entity scaling lifecycle metadata. */
public final class EntityScalingStateCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int MAX_STRING_BYTES = 256;

    private EntityScalingStateCodec() {}

    public static byte[] encode(EntityScalingState state) {
        if (state == null) throw new IllegalArgumentException("state must not be null");
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(CURRENT_VERSION);
                writeString(out, state.territory().dimensionId());
                out.writeLong(state.territory().cellX());
                out.writeLong(state.territory().cellZ());

                EntityLevelResolution level = state.levelResolution();
                writeString(out, level.archetype().name());
                out.writeLong(level.nativeAreaLevel());
                out.writeBoolean(level.relevantPlayerLevel().isPresent());
                if (level.relevantPlayerLevel().isPresent()) {
                    out.writeLong(level.relevantPlayerLevel().getAsLong());
                }
                out.writeLong(level.baseFloor());
                out.writeLong(level.rolledLevel());
                out.writeLong(level.finalLevel());
                out.writeLong(state.variance());

                out.writeBoolean(state.rarity().isPresent());
                if (state.rarity().isPresent()) {
                    MobRaritySelection rarity = state.rarity().orElseThrow();
                    writeString(out, rarity.rarity().serializedId());
                    out.writeLong(rarity.levelBonus());
                }
                out.writeLong(state.deterministicSeed());
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static EntityScalingState decode(byte[] encoded) {
        if (encoded == null) throw new IllegalArgumentException("encoded state must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(encoded))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported entity scaling state version: " + version);
            }

            TerritoryKey territory = TerritoryKey.of(readString(in), in.readLong(), in.readLong());
            EntityArchetype archetype = parseArchetype(readString(in));
            long nativeAreaLevel = in.readLong();
            OptionalLong relevantPlayerLevel = in.readBoolean()
                ? OptionalLong.of(in.readLong())
                : OptionalLong.empty();
            long baseFloor = in.readLong();
            long rolledLevel = in.readLong();
            long finalLevel = in.readLong();
            long variance = in.readLong();

            Optional<MobRaritySelection> rarity = Optional.empty();
            if (in.readBoolean()) {
                rarity = Optional.of(new MobRaritySelection(
                    MobRarityKey.of(readString(in)),
                    in.readLong()
                ));
            }
            long deterministicSeed = in.readLong();
            if (in.available() != 0) {
                throw new IllegalArgumentException("entity scaling state contains trailing bytes");
            }

            return new EntityScalingState(
                territory,
                new EntityLevelResolution(
                    archetype,
                    nativeAreaLevel,
                    relevantPlayerLevel,
                    baseFloor,
                    rolledLevel,
                    finalLevel
                ),
                variance,
                rarity,
                deterministicSeed
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid entity scaling state payload", exception);
        }
    }

    private static EntityArchetype parseArchetype(String name) {
        try {
            return EntityArchetype.valueOf(name);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown entity archetype: " + name, exception);
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("serialized string must not be blank");
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("serialized string too long");
        }
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("invalid serialized string length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) {
            throw new IllegalArgumentException("truncated serialized string");
        }
        String value = new String(bytes, StandardCharsets.UTF_8);
        if (value.isBlank()) {
            throw new IllegalArgumentException("serialized string must not be blank");
        }
        return value;
    }
}
