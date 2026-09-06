package dev.gustavopere.volcanoes.environment;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphereSavedDataDeterminismTest {
    @Test
    void overflowSelectionIsDeterministicRegardlessOfPersistedListOrder() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 1);
        AtmosphericSource lowerId = source("00000000-0000-0000-0000-000000000531", 0.70);
        AtmosphericSource higherId = source("00000000-0000-0000-0000-000000000532", 0.80);

        AtmosphereSavedData forward = AtmosphereSavedData.fromTag(
                schemaV1Root(lowerId.toTag(), higherId.toTag()), policy);
        AtmosphereSavedData reverse = AtmosphereSavedData.fromTag(
                schemaV1Root(higherId.toTag(), lowerId.toTag()), policy);

        assertEquals(1, forward.size());
        assertEquals(1, reverse.size());
        assertEquals(lowerId, forward.source(lowerId.id()).orElseThrow());
        assertEquals(lowerId, reverse.source(lowerId.id()).orElseThrow(),
                "capacity normalization must select the same canonical UUID regardless of list order");
        assertTrue(forward.isDirty());
        assertTrue(reverse.isDirty());
        assertEquals(forward.toTag(), reverse.toTag());
    }

    @Test
    void overflowSelectionUsesTheSameCanonicalUuidOrderAsSerialization() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 1);
        AtmosphericSource lexicallyFirst = source("7fffffff-ffff-ffff-0000-000000000001", 0.70);
        AtmosphericSource naturallySignedFirst = source("80000000-0000-0000-0000-000000000001", 0.80);

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(
                schemaV1Root(naturallySignedFirst.toTag(), lexicallyFirst.toTag()), policy);

        assertEquals(1, restored.size());
        assertEquals(lexicallyFirst, restored.source(lexicallyFirst.id()).orElseThrow(),
                "capacity normalization must use the same UUID-string order as canonical serialization");
        ListTag serialized = restored.toTag().getList("sources", CompoundTag.TAG_COMPOUND);
        assertEquals(lexicallyFirst.id(), serialized.getCompound(0).getUUID("id"));
    }

    @Test
    void wrongTypeSchemaDiscriminatorIsPreservedReadOnlyInsteadOfCoercedToLegacyV0() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 8);
        CompoundTag malformedFuture = new CompoundTag();
        malformedFuture.putString("schema_version", "not-an-integer");
        malformedFuture.putString("opaque_marker", "preserve-me");
        malformedFuture.put("sources", new ListTag());

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(malformedFuture, policy);

        assertEquals(0, restored.size(), "invalid schema discriminator must not expose v0/v1 authority");
        assertFalse(restored.isDirty(), "unknown discriminator type must not trigger destructive normalization");
        assertEquals(malformedFuture, restored.toTag(), "opaque payload must survive unchanged");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000533", 0.50)));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000533")));
        assertEquals(malformedFuture, restored.toTag(), "rejected mutation must preserve opaque payload");
    }

    private static CompoundTag schemaV1Root(CompoundTag... entries) {
        CompoundTag root = new CompoundTag();
        root.putInt("schema_version", 1);
        ListTag sources = new ListTag();
        for (CompoundTag entry : entries) {
            sources.add(entry);
        }
        root.put("sources", sources);
        return root;
    }

    private static AtmosphericSource source(String id, double strength) {
        return new AtmosphericSource(
                UUID.fromString(id),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                strength,
                true);
    }
}
