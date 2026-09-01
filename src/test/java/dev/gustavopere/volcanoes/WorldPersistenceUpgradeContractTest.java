package dev.gustavopere.volcanoes;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicRegionState;
import dev.gustavopere.volcanoes.volcano.MagmaChamber;
import dev.gustavopere.volcanoes.volcano.MagmaComposition;
import dev.gustavopere.volcanoes.volcano.VolcanoSavedData;
import dev.gustavopere.volcanoes.volcano.VolcanoSite;
import dev.gustavopere.volcanoes.volcano.VolcanoState;
import dev.gustavopere.volcanoes.volcano.VolcanoType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class WorldPersistenceUpgradeContractTest {
    private static final int CURRENT_SCHEMA = 2;

    @Test
    void currentSavedDataPayloadsAreVersionedAndRoundTrip() {
        TectonicRegionState tectonics = new TectonicRegionState();
        tectonics.putStress(4, -7, 0.72);
        CompoundTag tectonicTag = tectonics.toTag();
        assertEquals(CURRENT_SCHEMA, tectonicTag.getInt("schema_version"));
        assertEquals(tectonics.entries(), TectonicRegionState.fromTag(tectonicTag).entries());

        VolcanoSavedData volcanoes = new VolcanoSavedData();
        VolcanoSite site = site();
        MagmaChamber chamber = chamber();
        volcanoes.register(site);
        volcanoes.updateLifecycle(site.persistenceId(), VolcanoState.ACTIVE, chamber);
        CompoundTag volcanoTag = volcanoes.toTag();
        assertEquals(CURRENT_SCHEMA, volcanoTag.getInt("sites_schema_version"));
        assertEquals(CURRENT_SCHEMA, volcanoTag.getInt("chambers_schema_version"));
        VolcanoSavedData restoredVolcanoes = VolcanoSavedData.fromTag(volcanoTag);
        assertEquals(site.persistenceId(), restoredVolcanoes.get(site.persistenceId()).orElseThrow().persistenceId());
        assertEquals(chamber, restoredVolcanoes.chamber(site.persistenceId()).orElseThrow());

        DepositRegistry deposits = new DepositRegistry();
        GeologicalDeposit deposit = deposit();
        deposits.register(deposit);
        CompoundTag depositTag = deposits.toTag();
        assertEquals(CURRENT_SCHEMA, depositTag.getInt("schema_version"));
        assertEquals(deposit, DepositRegistry.fromTag(depositTag).get(deposit.persistenceId()).orElseThrow());
    }

    @Test
    void unversionedReleasedSchemaMigratesToV2WithoutLosingState() {
        TectonicRegionState tectonics = new TectonicRegionState();
        tectonics.putStress(1, 2, 0.33);
        CompoundTag legacyTectonics = tectonics.toTag();
        legacyTectonics.remove("schema_version");
        TectonicRegionState migratedTectonics = TectonicRegionState.fromTag(legacyTectonics);
        assertEquals(tectonics.entries(), migratedTectonics.entries());
        assertEquals(CURRENT_SCHEMA, migratedTectonics.toTag().getInt("schema_version"));

        VolcanoSavedData volcanoes = new VolcanoSavedData();
        VolcanoSite site = site();
        volcanoes.register(site);
        volcanoes.updateLifecycle(site.persistenceId(), VolcanoState.ACTIVE, chamber());
        CompoundTag legacyVolcanoes = volcanoes.toTag();
        legacyVolcanoes.remove("sites_schema_version");
        legacyVolcanoes.remove("chambers_schema_version");
        VolcanoSavedData migratedVolcanoes = VolcanoSavedData.fromTag(legacyVolcanoes);
        assertTrue(migratedVolcanoes.get(site.persistenceId()).isPresent());
        assertTrue(migratedVolcanoes.chamber(site.persistenceId()).isPresent());
        assertEquals(CURRENT_SCHEMA, migratedVolcanoes.toTag().getInt("sites_schema_version"));
        assertEquals(CURRENT_SCHEMA, migratedVolcanoes.toTag().getInt("chambers_schema_version"));

        DepositRegistry deposits = new DepositRegistry();
        GeologicalDeposit deposit = deposit();
        deposits.register(deposit);
        CompoundTag legacyDeposits = deposits.toTag();
        legacyDeposits.remove("schema_version");
        DepositRegistry migratedDeposits = DepositRegistry.fromTag(legacyDeposits);
        assertEquals(deposit, migratedDeposits.get(deposit.persistenceId()).orElseThrow());
        assertEquals(CURRENT_SCHEMA, migratedDeposits.toTag().getInt("schema_version"));
    }

    @Test
    void corruptEntriesAreSkippedWithoutDiscardingValidNeighbors() {
        TectonicRegionState tectonics = new TectonicRegionState();
        tectonics.putStress(3, 4, 0.5);
        CompoundTag tectonicTag = tectonics.toTag();
        CompoundTag corruptRegion = new CompoundTag();
        corruptRegion.putLong("region_x", 9);
        corruptRegion.putLong("region_z", 10);
        corruptRegion.putDouble("stress", 4.0);
        tectonicTag.getList("regions", CompoundTag.TAG_COMPOUND).add(corruptRegion);
        assertEquals(1, TectonicRegionState.fromTag(tectonicTag).size());

        DepositRegistry deposits = new DepositRegistry();
        GeologicalDeposit deposit = deposit();
        deposits.register(deposit);
        CompoundTag depositTag = deposits.toTag();
        CompoundTag corruptDeposit = deposit().toTag();
        corruptDeposit.putDouble("radius", 0.0);
        depositTag.getList("deposits", CompoundTag.TAG_COMPOUND).add(corruptDeposit);
        assertEquals(1, DepositRegistry.fromTag(depositTag).size());

        VolcanoSavedData volcanoes = new VolcanoSavedData();
        VolcanoSite site = site();
        volcanoes.register(site);
        volcanoes.updateLifecycle(site.persistenceId(), VolcanoState.ACTIVE, chamber());
        CompoundTag volcanoTag = volcanoes.toTag();
        ListTag sites = volcanoTag.getList("sites", CompoundTag.TAG_COMPOUND);
        sites.add(new CompoundTag());
        CompoundTag valid = sites.getCompound(0);
        CompoundTag corruptChamber = valid.getCompound("magma_chamber");
        corruptChamber.putDouble("temperature_kelvin", 0.0);
        VolcanoSavedData restored = VolcanoSavedData.fromTag(volcanoTag);
        assertEquals(1, restored.size());
        assertTrue(restored.get(site.persistenceId()).isPresent());
        assertTrue(restored.chamber(site.persistenceId()).isEmpty());
    }

    @Test
    void futureSchemasFailClosedAndCannotBeOverwrittenByRuntimeMutation() {
        CompoundTag tectonicFuture = new CompoundTag();
        tectonicFuture.putInt("schema_version", 99);
        TectonicRegionState tectonics = TectonicRegionState.fromTag(tectonicFuture);
        assertFalse(tectonics.putStress(1, 1, 0.4));
        assertEquals(0, tectonics.size());

        CompoundTag volcanoFuture = new CompoundTag();
        volcanoFuture.putInt("sites_schema_version", 99);
        volcanoFuture.putInt("chambers_schema_version", 99);
        VolcanoSavedData volcanoes = VolcanoSavedData.fromTag(volcanoFuture);
        assertFalse(volcanoes.register(site()));
        assertEquals(0, volcanoes.size());

        CompoundTag depositFuture = new CompoundTag();
        depositFuture.putInt("schema_version", 99);
        DepositRegistry deposits = DepositRegistry.fromTag(depositFuture);
        assertFalse(deposits.register(deposit()));
        assertEquals(0, deposits.size());
    }

    private static VolcanoSite site() {
        return new VolcanoSite(
                UUID.fromString("3aa2e94a-0c17-4db2-af0d-b024f8eb9807"),
                new BlockPos(640, 96, -384),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.DORMANT,
                TectonicContext.CONVERGENT,
                42L,
                43L,
                0.82);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                7.5,
                245.0,
                0.18,
                1_220.0,
                0.25);
    }

    private static GeologicalDeposit deposit() {
        return new GeologicalDeposit(
                UUID.fromString("999946ba-3bde-467a-aeed-cbf353f8e61e"),
                ResourceLocation.parse("minecraft:iron_ores"),
                new BlockPos(100, 32, -100),
                12.0,
                0.7,
                DepositOrigin.MAGMATIC);
    }
}
