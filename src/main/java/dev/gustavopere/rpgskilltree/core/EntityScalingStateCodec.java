package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.OptionalLong;

/** Strict versioned binary codec for persisted entity-level/rarity decisions. */
public final class EntityScalingStateCodec {
    public static final int CURRENT_VERSION = 1;
    private static final int MAX_STRING_BYTES = 256;

    private EntityScalingStateCodec() {}

    public static byte[] encode(EntityScalingAttachmentData data) {
        if (data == null) throw new IllegalArgumentException("data must not be null");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(buffer)) {
                out.writeInt(CURRENT_VERSION);
                out.writeBoolean(data.initialized());
                if (data.initialized()) writeSnapshot(out, data.requireSnapshot());
            }
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("failed to encode entity scaling state", exception);
        }
    }

    public static EntityScalingAttachmentData decode(byte[] payload) {
        if (payload == null) throw new IllegalArgumentException("payload must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload))) {
            int version = in.readInt();
            if (version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported entity scaling state version: " + version);
            }
            boolean initialized = in.readBoolean();
            EntityScalingAttachmentData result = initialized
                ? EntityScalingAttachmentData.initialized(readSnapshot(in))
                : EntityScalingAttachmentData.uninitialized();
            if (in.available() != 0) {
                throw new IllegalArgumentException("entity scaling state contains trailing bytes");
            }
            return result;
        } catch (EOFException exception) {
            throw new IllegalArgumentException("truncated entity scaling state", exception);
        } catch (IOException exception) {
            throw new IllegalArgumentException("invalid entity scaling state", exception);
        }
    }

    private static void writeSnapshot(DataOutputStream out, EntityScalingSnapshot snapshot) throws IOException {
        EntityLevelResolution level = snapshot.levelResolution();
        writeString(out, level.archetype().name());
        out.writeLong(level.nativeAreaLevel());
        out.writeBoolean(level.relevantPlayerLevel().isPresent());
        if (level.relevantPlayerLevel().isPresent()) out.writeLong(level.relevantPlayerLevel().getAsLong());
        out.writeLong(level.baseFloor());
        out.writeLong(level.rolledLevel());
        out.writeLong(level.finalLevel());
        writeString(out, snapshot.raritySelection().rarity().serializedId());
        out.writeLong(snapshot.raritySelection().levelBonus());
    }

    private static EntityScalingSnapshot readSnapshot(DataInputStream in) throws IOException {
        EntityArchetype archetype;
        try {
            archetype = EntityArchetype.valueOf(readString(in));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown entity archetype in persisted scaling state", exception);
        }
        long nativeAreaLevel = in.readLong();
        OptionalLong relevantPlayerLevel = in.readBoolean() ? OptionalLong.of(in.readLong()) : OptionalLong.empty();
        long baseFloor = in.readLong();
        long rolledLevel = in.readLong();
        long finalLevel = in.readLong();
        MobRarityKey rarity = MobRarityKey.of(readString(in));
        long rarityBonus = in.readLong();
        return new EntityScalingSnapshot(
            new EntityLevelResolution(
                archetype,
                nativeAreaLevel,
                relevantPlayerLevel,
                baseFloor,
                rolledLevel,
                finalLevel
            ),
            new MobRaritySelection(rarity, rarityBonus)
        );
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("serialized string must not be blank");
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_STRING_BYTES) throw new IllegalArgumentException("serialized string too long");
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length <= 0 || length > MAX_STRING_BYTES) {
            throw new IllegalArgumentException("invalid serialized string length: " + length);
        }
        byte[] bytes = in.readNBytes(length);
        if (bytes.length != length) throw new EOFException("truncated serialized string");
        String value = new String(bytes, StandardCharsets.UTF_8);
        if (value.isBlank()) throw new IllegalArgumentException("serialized string must not be blank");
        return value;
    }
}
