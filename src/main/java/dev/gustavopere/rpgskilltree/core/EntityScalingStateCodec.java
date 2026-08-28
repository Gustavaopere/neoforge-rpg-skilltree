package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

/** Strict versioned binary codec for persisted entity-scaling lifecycle decisions. */
public final class EntityScalingStateCodec {
    public static final int CURRENT_VERSION = 2;
    private static final int MAX_STRING_BYTES = 256;
    private static final int MAX_AFFIXES = 256;

    private EntityScalingStateCodec() {}

    public static byte[] encode(EntityScalingAttachmentData data) {
        if (data == null) throw new IllegalArgumentException("data must not be null");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(buffer)) {
                out.writeInt(CURRENT_VERSION);
                out.writeBoolean(data.initialized());
                if (data.initialized()) writeState(out, data.requireState());
            }
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("failed to encode entity scaling state", exception);
        }
    }

    /** Uses the same canonical envelope format as attachment persistence. */
    public static byte[] encode(EntityScalingState state) {
        return encode(EntityScalingAttachmentData.initialized(state));
    }

    public static EntityScalingAttachmentData decode(byte[] payload) {
        if (payload == null) throw new IllegalArgumentException("payload must not be null");
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(payload))) {
            int version = in.readInt();
            if (version != 1 && version != CURRENT_VERSION) {
                throw new IllegalArgumentException("unsupported entity scaling state version: " + version);
            }
            boolean initialized = in.readBoolean();
            EntityScalingAttachmentData result = initialized
                ? EntityScalingAttachmentData.initialized(readState(in, version))
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

    public static EntityScalingState decodeState(byte[] payload) {
        return decode(payload).requireState();
    }

    private static void writeState(DataOutputStream out, EntityScalingState state) throws IOException {
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

        List<MobAffixKey> affixes = state.affixes().affixes();
        if (affixes.size() > MAX_AFFIXES) {
            throw new IllegalArgumentException("too many persisted mob affixes");
        }
        out.writeInt(affixes.size());
        for (MobAffixKey affix : affixes) {
            writeString(out, affix.serializedId());
        }
    }

    private static EntityScalingState readState(DataInputStream in, int version) throws IOException {
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

        MobAffixSelection affixes = MobAffixSelection.empty();
        if (version >= 2) {
            int count = in.readInt();
            if (count < 0 || count > MAX_AFFIXES) {
                throw new IllegalArgumentException("invalid persisted mob affix count: " + count);
            }
            ArrayList<MobAffixKey> keys = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                keys.add(MobAffixKey.of(readString(in)));
            }
            affixes = new MobAffixSelection(keys);
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
            deterministicSeed,
            affixes
        );
    }

    private static EntityArchetype parseArchetype(String name) {
        try {
            return EntityArchetype.valueOf(name);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown entity archetype: " + name, exception);
        }
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
