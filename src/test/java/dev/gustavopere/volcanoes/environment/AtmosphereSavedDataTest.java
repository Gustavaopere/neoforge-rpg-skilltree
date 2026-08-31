package dev.gustavopere.volcanoes.environment;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphereSavedDataTest {
    @Test
    void savedDataPersistsOnlyPolicyApprovedSourcesAndRoundTripsCurrentState() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphereSavedData data = new AtmosphereSavedData(policy);
        AtmosphericSource persistent = source("00000000-0000-0000-0000-000000000501", true, 0.75);
        AtmosphericSource transientSource = source("00000000-0000-0000-0000-000000000502", false, 0.50);
        data.upsert(persistent);
        data.upsert(transientSource);

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(data.toTag(), policy);
        assertEquals(1, restored.size());
        assertEquals(persistent, restored.source(persistent.id()).orElseThrow());
        assertTrue(restored.source(transientSource.id()).isEmpty());
    }

    @Test
    void currentV1PayloadWritesSchemaAndRoundTripsDeterministicallyInUuidOrder() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphereSavedData data = new AtmosphereSavedData(policy);
        AtmosphericSource second = source("00000000-0000-0000-0000-000000000522", true, 0.60);
        AtmosphericSource first = source("00000000-0000-0000-0000-000000000521", true, 0.70);
        data.upsert(second);
        data.upsert(first);

        CompoundTag serialized = data.toTag();
        assertEquals(1, serialized.getInt("schema_version"));
        ListTag sources = serialized.getList("sources", CompoundTag.TAG_COMPOUND);
        assertEquals(first.id(), sources.getCompound(0).getUUID("id"));
        assertEquals(second.id(), sources.getCompound(1).getUUID("id"));

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(serialized, policy);
        assertFalse(restored.isDirty(), "unchanged schema-v1 payload must not force a rewrite");
        assertEquals(serialized, restored.toTag());
    }

    @Test
    void unversionedV0PayloadMigratesToSchemaV1AndMarksStorageDirty() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphericSource persistent = source("00000000-0000-0000-0000-000000000523", true, 0.75);
        CompoundTag v0 = rootWithSources(persistent.toTag());

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(v0, policy);

        assertEquals(persistent, restored.source(persistent.id()).orElseThrow());
        assertTrue(restored.isDirty(), "real unversioned payload must be normalized into schema v1");
        assertEquals(1, restored.toTag().getInt("schema_version"));
    }

    @Test
    void identicalDuplicateUuidCollapsesToOneAuthorityAndNormalizesStorage() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphericSource source = source("00000000-0000-0000-0000-000000000524", true, 0.75);
        CompoundTag v1 = schemaV1Root(source.toTag(), source.toTag());

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(v1, policy);

        assertEquals(1, restored.size());
        assertEquals(source, restored.source(source.id()).orElseThrow());
        assertTrue(restored.isDirty(), "duplicate identity must be collapsed on next save");
    }

    @Test
    void conflictingDuplicateUuidPreservesFirstAcceptedAuthority() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        String id = "00000000-0000-0000-0000-000000000525";
        AtmosphericSource first = source(id, true, 0.80);
        AtmosphericSource conflictingLater = source(id, true, 0.25);
        CompoundTag v1 = schemaV1Root(first.toTag(), conflictingLater.toTag());

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(v1, policy);

        assertEquals(1, restored.size());
        assertEquals(first, restored.source(first.id()).orElseThrow(),
                "a corrupt/conflicting tail entry must not replace the first valid authority");
        assertTrue(restored.isDirty(), "conflicting duplicate must be normalized out of the save");
    }

    @Test
    void futureSchemaPayloadIsPreservedReadOnlyWithoutCurrentSchemaInterpretation() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        CompoundTag future = new CompoundTag();
        future.putInt("schema_version", 7);
        future.putString("future_marker", "preserve-me");
        CompoundTag futureNested = new CompoundTag();
        futureNested.putString("opaque", "payload");
        future.put("future_payload", futureNested);
        future.put("sources", new ListTag());

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(future, policy);

        assertEquals(0, restored.size(), "future schema must expose no mutable current-schema authority");
        assertFalse(restored.isDirty(), "future schema must not be destructively normalized");
        assertEquals(future, restored.toTag(), "future payload must survive a semantic load/save round trip");
        assertThrows(IllegalStateException.class,
                () -> restored.upsert(source("00000000-0000-0000-0000-000000000526", true, 0.50)));
        assertThrows(IllegalStateException.class,
                () -> restored.remove(UUID.fromString("00000000-0000-0000-0000-000000000526")));
        assertEquals(future, restored.toTag(), "rejected mutation must not rewrite the preserved future payload");
    }

    @Test
    void upsertRemovesPreviouslyPersistedSourceWhenItBecomesTransient() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphereSavedData data = new AtmosphereSavedData(policy);
        String id = "00000000-0000-0000-0000-000000000510";
        AtmosphericSource persistent = source(id, true, 0.75);
        AtmosphericSource transientReplacement = source(id, false, 0.75);

        data.upsert(persistent);
        assertEquals(persistent, data.source(persistent.id()).orElseThrow());
        data.setDirty(false);

        data.upsert(transientReplacement);

        assertTrue(data.source(persistent.id()).isEmpty(),
                "the lifecycle sink must not retain a stale persisted copy after the source becomes transient");
        assertTrue(data.isDirty(), "purging the stale persisted copy must schedule a save");
        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(data.toTag(), policy);
        assertTrue(restored.source(persistent.id()).isEmpty(),
                "a source that became transient must not reappear after restart");
    }

    @Test
    void loadMarksDataDirtyWhenPolicyDropsOrTruncatesPreviouslyPersistedSources() {
        AtmospherePersistencePolicy originalPolicy = new AtmospherePersistencePolicy(true, 128);
        AtmosphereSavedData original = new AtmosphereSavedData(originalPolicy);
        original.upsert(source("00000000-0000-0000-0000-000000000505", true, 0.90));
        original.upsert(source("00000000-0000-0000-0000-000000000506", true, 0.80));

        AtmosphereSavedData unchanged = AtmosphereSavedData.fromTag(original.toTag(), originalPolicy);
        assertEquals(2, unchanged.size());
        assertFalse(unchanged.isDirty(), "unchanged load must not force a rewrite");

        AtmosphereSavedData disabled = AtmosphereSavedData.fromTag(
                original.toTag(), new AtmospherePersistencePolicy(false, 128));
        assertEquals(0, disabled.size());
        assertTrue(disabled.isDirty(), "disabled persistence must purge stale saved sources on next save");

        AtmosphereSavedData truncated = AtmosphereSavedData.fromTag(
                original.toTag(), new AtmospherePersistencePolicy(true, 1));
        assertEquals(1, truncated.size());
        assertTrue(truncated.isDirty(), "reduced maxSources must persist the truncated source set");
    }

    @Test
    void malformedPersistedEntryIsDroppedWhileTwoValidEntriesSurviveAndStorageIsMarkedDirty() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphericSource first = source("00000000-0000-0000-0000-000000000507", true, 0.70);
        AtmosphericSource second = source("00000000-0000-0000-0000-000000000527", true, 0.65);
        CompoundTag root = new CompoundTag();
        root.putInt("schema_version", 1);
        ListTag sources = new ListTag();
        sources.add(first.toTag());

        CompoundTag malformed = new CompoundTag();
        malformed.putString("dimension", "minecraft:overworld");
        malformed.putDouble("radius", -1.0);
        malformed.putDouble("strength", 1.0);
        malformed.putBoolean("persistent", true);
        sources.add(malformed);
        sources.add(second.toTag());
        root.put("sources", sources);

        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(root, policy);

        assertEquals(2, restored.size());
        assertEquals(first, restored.source(first.id()).orElseThrow());
        assertEquals(second, restored.source(second.id()).orElseThrow());
        assertTrue(restored.isDirty(), "malformed entries must be purged on the next save");
    }

    @Test
    void externallyManagedLegacyEntryWithAtmospherePersistenceIsPurgedFailClosedForKnownSchemas() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphericSource valid = source("00000000-0000-0000-0000-000000000511", true, 0.70);
        AtmosphericSource legacyBase = source("00000000-0000-0000-0000-000000000512", true, 0.60);
        CompoundTag staleExternal = legacyBase.toTag();
        staleExternal.putString("evolution_mode", AtmosphericSourceEvolution.EXTERNAL.name());
        staleExternal.putBoolean("persistent", true);

        for (int schemaVersion : new int[]{0, 1}) {
            CompoundTag root = rootWithSources(valid.toTag(), staleExternal.copy());
            if (schemaVersion == 1) {
                root.putInt("schema_version", 1);
            }

            AtmosphereSavedData restored = AtmosphereSavedData.fromTag(root, policy);

            assertEquals(1, restored.size());
            assertEquals(valid, restored.source(valid.id()).orElseThrow());
            assertTrue(restored.source(legacyBase.id()).isEmpty(),
                    "upstream-owned sources must not survive as duplicate Atmosphere persistence authority");
            assertTrue(restored.isDirty(), "purging an incompatible external entry must rewrite the save cleanly");
        }
    }

    @Test
    void v1RestartProjectionRestoresEachPersistedIdentityExactlyOnce() {
        AtmospherePersistencePolicy policy = new AtmospherePersistencePolicy(true, 128);
        AtmosphericSource source = source("00000000-0000-0000-0000-000000000528", true, 0.75);
        AtmosphereSavedData restored = AtmosphereSavedData.fromTag(schemaV1Root(source.toTag(), source.toTag()), policy);
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.5, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                restored);

        for (AtmosphericSource persisted : restored.all()) {
            field.restore(persisted);
        }

        assertEquals(1, restored.size());
        assertEquals(1, field.sourceCount());
        assertEquals(source, field.source(source.id()).orElseThrow());
    }

    @Test
    void newPersistentSourceFailsClosedWhenPersistenceCapacityIsExhausted() {
        AtmosphereSavedData data = new AtmosphereSavedData(new AtmospherePersistencePolicy(true, 1));
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.5, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                data);
        AtmosphericSource first = source("00000000-0000-0000-0000-000000000508", true, 1.0);
        AtmosphericSource overflow = source("00000000-0000-0000-0000-000000000509", true, 1.0);

        field.register(first);
        assertThrows(IllegalStateException.class, () -> field.register(overflow),
                "a persistent source must not be accepted into runtime when it cannot be persisted");

        assertEquals(1, data.size());
        assertEquals(first, data.source(first.id()).orElseThrow());
        assertTrue(data.source(overflow.id()).isEmpty());
        assertEquals(1, field.sourceCount());
        assertEquals(first, field.source(first.id()).orElseThrow());
        assertTrue(field.source(overflow.id()).isEmpty(),
                "failed persistence capacity must roll runtime registration back too");
    }

    @Test
    void atmosphereFieldNotifiesPersistenceSinkOnlyForBoundedSourceLifecycleWork() {
        List<AtmosphericSource> upserts = new ArrayList<>();
        List<UUID> removals = new ArrayList<>();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
                upserts.add(source);
            }

            @Override
            public void remove(UUID id) {
                removals.add(id);
            }
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.5, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                sink);
        AtmosphericSource first = source("00000000-0000-0000-0000-000000000503", true, 1.0);
        AtmosphericSource second = source("00000000-0000-0000-0000-000000000504", true, 1.0);
        field.register(first);
        field.register(second);
        assertEquals(2, upserts.size());

        field.tick(1);
        assertEquals(3, upserts.size());
        assertEquals(first.id(), upserts.get(2).id());
        assertEquals(0.5, upserts.get(2).strength(), 1.0e-9);
        assertEquals(1.0, field.source(second.id()).orElseThrow().strength(), 1.0e-9);

        field.remove(second.id());
        assertEquals(List.of(second.id()), removals);
    }

    private static CompoundTag rootWithSources(CompoundTag... entries) {
        CompoundTag root = new CompoundTag();
        ListTag sources = new ListTag();
        for (CompoundTag entry : entries) {
            sources.add(entry);
        }
        root.put("sources", sources);
        return root;
    }

    private static CompoundTag schemaV1Root(CompoundTag... entries) {
        CompoundTag root = rootWithSources(entries);
        root.putInt("schema_version", 1);
        return root;
    }

    private static AtmosphericSource source(String id, boolean persistent, double strength) {
        return new AtmosphericSource(
                UUID.fromString(id),
                "minecraft:overworld", 0.0, 64.0, 0.0, 16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(), strength, persistent);
    }
}
