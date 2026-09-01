package dev.gustavopere.volcanoes.environment;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.ShortTag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AtmosphereSavedDataSchemaFailClosedTest {
    @Test
    void explicitSchemaZeroIsUnsupportedAndPreservedReadOnly() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        CompoundTag explicitZero = new CompoundTag();
        explicitZero.putInt("schema_version", 0);
        explicitZero.putString("legacy_marker", "preserve-explicit-zero");
        ListTag sources = new ListTag();
        sources.add(source("00000000-0000-0000-0000-000000000541").toTag());
        explicitZero.put("sources", sources);

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(explicitZero, policy);

        assertEquals(0, restored.size(), "explicit unsupported schema must expose no mutable authority");
        assertFalse(restored.isDirty(), "explicit schema zero must not authorize destructive v0 migration");
        assertEquals(explicitZero, restored.toTag(), "explicit schema zero payload must round-trip opaquely");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000542")));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000541")));
    }

    @Test
    void wrongSourcesNbtTypeIsPreservedReadOnlyInsteadOfCollapsingToEmptyState() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        CompoundTag malformed = new CompoundTag();
        malformed.putInt("schema_version", AtmosphereSavedData.CURRENT_SCHEMA_VERSION);
        malformed.putString("sources", "not-a-list");
        malformed.putString("opaque_marker", "preserve-wrong-sources-type");

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(malformed, policy);

        assertEquals(0, restored.size(), "malformed payload must expose no mutable current-schema authority");
        assertFalse(restored.isDirty(), "wrong sources NBT type must not be normalized destructively");
        assertEquals(malformed, restored.toTag(), "malformed top-level payload must round-trip opaquely");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000543")));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000543")));
    }

    @Test
    void missingSourcesKeyIsPreservedReadOnlyInsteadOfInventingEmptyCurrentState() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        CompoundTag malformed = new CompoundTag();
        malformed.putInt("schema_version", AtmosphereSavedData.CURRENT_SCHEMA_VERSION);
        malformed.putString("opaque_marker", "preserve-missing-sources");

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(malformed, policy);

        assertEquals(0, restored.size(), "missing required source list must expose no mutable authority");
        assertFalse(restored.isDirty(), "missing source list must not be rewritten as an empty v1 store");
        assertEquals(malformed, restored.toTag(), "missing-source payload must round-trip opaquely");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000544")));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000544")));
    }

    @Test
    void nonCompoundSourcesListIsPreservedReadOnlyInsteadOfBeingFilteredToEmpty() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        CompoundTag malformed = new CompoundTag();
        malformed.putInt("schema_version", AtmosphereSavedData.CURRENT_SCHEMA_VERSION);
        ListTag sources = new ListTag();
        sources.add(ShortTag.valueOf((short) 7));
        malformed.put("sources", sources);
        malformed.putString("opaque_marker", "preserve-non-compound-list");

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(malformed, policy);

        assertEquals(0, restored.size());
        assertFalse(restored.isDirty(), "wrong list element type must not be normalized destructively");
        assertEquals(malformed, restored.toTag(), "non-compound list payload must round-trip opaquely");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000545")));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000545")));
    }

    private static AtmosphericSource source(String id) {
        return new AtmosphericSource(
                UUID.fromString(id),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                0.75,
                true);
    }
}
