package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class MobAffixPersistenceTest {
    public static void main(String[] args) {
        currentSchemaRoundTripsCanonicalAffixSelection();
        schemaV1LoadsWithEmptyAffixes();
        oldStateConstructorDefaultsToEmptyAffixes();
        System.out.println("MobAffixPersistenceTest: PASS");
    }

    private static void currentSchemaRoundTripsCanonicalAffixSelection() {
        eq(4, EntityScalingStateCodec.CURRENT_VERSION);
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 7L, -4L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                23L,
                OptionalLong.of(30L),
                30L,
                37L,
                37L
            ),
            2L,
            Optional.of(new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 5L)),
            0x123456789ABCDEFL,
            new MobAffixSelection(List.of(
                MobAffixKey.of("rpgskilltree:swift"),
                MobAffixKey.of("rpgskilltree:armored")
            ))
        );

        EntityScalingState decoded = EntityScalingStateCodec.decodeState(EntityScalingStateCodec.encode(state));
        eq(state, decoded);
        eq(Optional.empty(), decoded.effectiveStats());
        eq(List.of(
            MobAffixKey.of("rpgskilltree:armored"),
            MobAffixKey.of("rpgskilltree:swift")
        ), decoded.affixes().affixes());
    }

    private static void schemaV1LoadsWithEmptyAffixes() {
        EntityScalingState decoded = EntityScalingStateCodec.decodeState(legacyV1Payload());
        eq(40L, decoded.entityLevel());
        eq(MobRarityKey.of("rpgskilltree:veteran"), decoded.rarity().orElseThrow().rarity());
        eq(Optional.empty(), decoded.effectiveStats());
        eq(MobAffixSelection.empty(), decoded.affixes());
    }

    private static void oldStateConstructorDefaultsToEmptyAffixes() {
        EntityScalingState state = new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.PASSIVE,
                1L,
                OptionalLong.empty(),
                1L,
                1L,
                1L
            ),
            0L,
            Optional.empty(),
            42L
        );
        eq(Optional.empty(), state.effectiveStats());
        eq(MobAffixSelection.empty(), state.affixes());
    }

    private static byte[] legacyV1Payload() {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(buffer)) {
                out.writeInt(1);
                out.writeBoolean(true);
                writeString(out, "minecraft:overworld");
                out.writeLong(2L);
                out.writeLong(-1L);
                writeString(out, "HOSTILE");
                out.writeLong(20L);
                out.writeBoolean(true);
                out.writeLong(35L);
                out.writeLong(35L);
                out.writeLong(40L);
                out.writeLong(40L);
                out.writeLong(1L);
                out.writeBoolean(true);
                writeString(out, "rpgskilltree:veteran");
                out.writeLong(4L);
                out.writeLong(99L);
            }
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
