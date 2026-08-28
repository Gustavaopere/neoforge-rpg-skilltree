package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class DiscoverySaveRoundTripTest {
    public static void main(String[] args) {
        roundTripPreservesAllDiscoveryFields();
        removedModEntrySurvivesWithoutCatalogLookup();
        encodingIsDeterministicByCanonicalEntryId();
        invalidPayloadsFailClosed();
        System.out.println("DiscoverySaveRoundTripTest: PASS");
    }

    private static void roundTripPreservesAllDiscoveryFields() {
        CompendiumEntryId frog = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:frog");
        DiscoveryProgress original = DiscoveryProgress.empty().withRecord(new DiscoveryRecord(
            frog,
            DiscoveryState.MASTERED,
            987654L,
            Optional.of(new DiscoveryOrigin("minecraft:overworld", -12, 34)),
            Set.of("minecraft:warm", "minecraft:cold"),
            Set.of("observe", "breed", "variants"),
            Set.of("frog_seen_xp", "frog_mastery")
        ));

        DiscoveryProgress decoded = DiscoveryProgressCodec.decode(DiscoveryProgressCodec.encode(original));
        eq(original, decoded);
    }

    private static void removedModEntrySurvivesWithoutCatalogLookup() {
        CompendiumEntryId removed = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "removedmod:lost_creature");
        DiscoveryProgress original = DiscoveryProgress.empty().withRecord(new DiscoveryRecord(
            removed,
            DiscoveryState.STUDIED,
            42L,
            Optional.of(new DiscoveryOrigin("removedmod:lost_dimension", 1, 2)),
            Set.of("removedmod:ashen"),
            Set.of("observe", "study"),
            Set.of("removed_reward")
        ));

        DiscoveryProgress decoded = DiscoveryProgressCodec.decode(DiscoveryProgressCodec.encode(original));
        eq(removed, decoded.record(removed).orElseThrow().entryId());
        eq(DiscoveryState.STUDIED, decoded.record(removed).orElseThrow().state());
    }

    private static void encodingIsDeterministicByCanonicalEntryId() {
        DiscoveryRecord pig = record("minecraft:pig", 1L);
        DiscoveryRecord zombie = record("minecraft:zombie", 2L);

        LinkedHashMap<CompendiumEntryId, DiscoveryRecord> first = new LinkedHashMap<>();
        first.put(zombie.entryId(), zombie);
        first.put(pig.entryId(), pig);
        LinkedHashMap<CompendiumEntryId, DiscoveryRecord> second = new LinkedHashMap<>();
        second.put(pig.entryId(), pig);
        second.put(zombie.entryId(), zombie);

        bytes(DiscoveryProgressCodec.encode(new DiscoveryProgress(first)),
            DiscoveryProgressCodec.encode(new DiscoveryProgress(second)));
    }

    private static void invalidPayloadsFailClosed() {
        byte[] valid = DiscoveryProgressCodec.encode(DiscoveryProgress.empty());
        byte[] unsupportedVersion = valid.clone();
        unsupportedVersion[3] = 99;
        throwsIllegal(() -> DiscoveryProgressCodec.decode(unsupportedVersion));

        throwsIllegal(() -> DiscoveryProgressCodec.decode(Arrays.copyOf(valid, valid.length - 1)));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        trailing[trailing.length - 1] = 7;
        throwsIllegal(() -> DiscoveryProgressCodec.decode(trailing));

        throwsIllegal(() -> DiscoveryProgressCodec.decode(new byte[DiscoveryProgressCodec.MAX_PAYLOAD_BYTES + 1]));
        throwsIllegal(() -> DiscoveryProgressCodec.decode(malformedIdPayload()));
    }

    private static byte[] malformedIdPayload() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(buffer)) {
            out.writeInt(DiscoveryProgressCodec.CURRENT_VERSION);
            out.writeInt(1);
            writeString(out, "not-a-canonical-entry-id");
            out.writeInt(DiscoveryState.SEEN.ordinal());
            out.writeLong(1L);
            out.writeBoolean(false);
            out.writeInt(0);
            out.writeInt(0);
            out.writeInt(0);
            return buffer.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError("unexpected in-memory payload write failure", exception);
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static DiscoveryRecord record(String id, long gameTime) {
        CompendiumEntryId entry = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id);
        return new DiscoveryRecord(entry, DiscoveryState.SEEN, gameTime, Optional.empty(), Set.of(), Set.of(), Set.of());
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void bytes(byte[] expected, byte[] actual) {
        if (!Arrays.equals(expected, actual)) throw new AssertionError("encoded payloads differ");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
