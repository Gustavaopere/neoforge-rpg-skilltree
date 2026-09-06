package dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithComposition;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithPartState;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithProvenance;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.ProcessState;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.WorkmanshipBand;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class BlacksmithPersistenceContractTest {
    @Test
    void partStateAndCompositionCodecsRoundTripWithoutLosingPhysicalIdentity() {
        BlacksmithPartState part = partState(1);
        JsonElement encodedPart = BlacksmithCodecs.PART_STATE.encodeStart(JsonOps.INSTANCE, part).getOrThrow();
        BlacksmithPartState decodedPart = BlacksmithCodecs.PART_STATE.parse(JsonOps.INSTANCE, encodedPart).getOrThrow();
        assertEquals(part, decodedPart);

        BlacksmithComposition composition = BlacksmithComposition.of(
            id("blacksmith", "modular_sword"),
            Map.of("blade", part),
            88.5D,
            WorkmanshipBand.MASTERFUL,
            Set.of(ProcessState.CAST, ProcessState.SHAPED, ProcessState.HEAT_TREATED, ProcessState.FINISHED),
            1
        );
        JsonElement encodedComposition = BlacksmithCodecs.COMPOSITION.encodeStart(JsonOps.INSTANCE, composition).getOrThrow();
        BlacksmithComposition decodedComposition = BlacksmithCodecs.COMPOSITION.parse(JsonOps.INSTANCE, encodedComposition).getOrThrow();

        assertEquals(composition.assemblyDefinitionId(), decodedComposition.assemblyDefinitionId());
        assertEquals(composition.parts(), decodedComposition.parts());
        assertEquals(composition.resolvedMaterialIds(), decodedComposition.resolvedMaterialIds());
        assertEquals(composition.workmanshipScore(), decodedComposition.workmanshipScore());
        assertEquals(composition.workmanshipBand(), decodedComposition.workmanshipBand());
        assertEquals(composition.processSummary(), decodedComposition.processSummary());
        assertEquals(composition.schemaVersion(), decodedComposition.schemaVersion());
    }

    @Test
    void schemaMigratorAcceptsCurrentAndRejectsUnknownFutureSchemas() {
        BlacksmithPartState currentPart = partState(BlacksmithSchemaMigrator.CURRENT_SCHEMA_VERSION);
        assertSame(currentPart, BlacksmithSchemaMigrator.requireCurrent(currentPart));

        BlacksmithComposition currentComposition = BlacksmithComposition.of(
            id("blacksmith", "modular_sword"),
            Map.of("blade", currentPart),
            80.0D,
            WorkmanshipBand.PRECISE,
            Set.of(ProcessState.CAST, ProcessState.FINISHED),
            BlacksmithSchemaMigrator.CURRENT_SCHEMA_VERSION
        );
        assertSame(currentComposition, BlacksmithSchemaMigrator.requireCurrent(currentComposition));

        assertThrows(IllegalArgumentException.class, () -> BlacksmithSchemaMigrator.requireCurrent(partState(2)));
        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithSchemaMigrator.requireCurrent(
                BlacksmithComposition.of(
                    id("blacksmith", "future_sword"),
                    Map.of("blade", partState(1)),
                    80.0D,
                    WorkmanshipBand.PRECISE,
                    Set.of(ProcessState.FINISHED),
                    2
                )
            )
        );
    }

    @Test
    void dataComponentRegistryUsesStableBlacksmithIdsWithoutNeoForgeBootstrap() {
        assertEquals(id("rpgskilltree", "blacksmith_part_state"), BlacksmithComponentIds.PART_STATE);
        assertEquals(id("rpgskilltree", "blacksmith_composition"), BlacksmithComponentIds.COMPOSITION);
    }

    private static BlacksmithPartState partState(int schemaVersion) {
        return new BlacksmithPartState(
            id("blacksmith", "blade"),
            id("blacksmith", "steel"),
            schemaVersion,
            ProcessState.FINISHED,
            1.0D,
            new BlacksmithProvenance(
                id("productivemetalworks", "provider"),
                id("blacksmith", "casting/blade_steel")
            )
        );
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
