package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DepositRegistryTest {
    private static final UUID COPPER_ID = UUID.fromString("10000000-0000-0000-0000-000000000001");
    private static final UUID GOLD_ID = UUID.fromString("20000000-0000-0000-0000-000000000002");

    @Test
    void registeringSamePersistentDepositAcrossReloadIsIdempotent() {
        GeologicalDeposit copper = deposit(
                COPPER_ID,
                "c:ores/copper",
                new BlockPos(16, 32, 16),
                DepositOrigin.HYDROTHERMAL);
        DepositRegistry registry = new DepositRegistry();

        assertTrue(registry.register(copper));
        assertFalse(registry.register(copper));
        assertEquals(1, registry.size());

        DepositRegistry restored = DepositRegistry.fromTag(registry.toTag());
        assertEquals(copper, restored.get(COPPER_ID).orElseThrow());
        assertFalse(restored.register(copper), "chunk reload must not duplicate an existing persistence ID");
        assertEquals(1, restored.size());
    }

    @Test
    void legacyGenericHydrothermalDepositMigratesToExactIdentityWithoutChangingId() {
        BlockPos center = new BlockPos(16, 32, 16);
        GeologicalDeposit legacy = deposit(
                COPPER_ID,
                "volcanoes:resources/mineral",
                center,
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit exact = deposit(
                COPPER_ID,
                "c:ores/copper",
                center,
                DepositOrigin.HYDROTHERMAL);
        DepositRegistry registry = new DepositRegistry();

        assertTrue(registry.register(legacy));
        assertTrue(registry.register(exact),
                "upgrade replay must migrate the legacy generic hydrothermal identity in place");
        assertEquals(1, registry.size(), "migration must not create a duplicate deposit");
        assertEquals(exact, registry.get(COPPER_ID).orElseThrow());

        DepositRegistry restored = DepositRegistry.fromTag(registry.toTag());
        assertEquals(exact, restored.get(COPPER_ID).orElseThrow(),
                "the migrated exact identity must survive SavedData reload");
        assertFalse(restored.register(exact), "replay after migration must be idempotent");
    }

    @Test
    void legacyMigrationRejectsDifferentHydrothermalGeometryOrRichness() {
        GeologicalDeposit legacy = deposit(
                COPPER_ID,
                "volcanoes:resources/mineral",
                new BlockPos(16, 32, 16),
                12.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
        DepositRegistry registry = new DepositRegistry();
        assertTrue(registry.register(legacy));

        GeologicalDeposit moved = deposit(
                COPPER_ID,
                "c:ores/copper",
                new BlockPos(17, 32, 16),
                12.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit resized = deposit(
                COPPER_ID,
                "c:ores/copper",
                legacy.center(),
                13.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit reriched = deposit(
                COPPER_ID,
                "c:ores/copper",
                legacy.center(),
                12.0,
                0.70,
                DepositOrigin.HYDROTHERMAL);

        assertThrows(IllegalStateException.class, () -> registry.register(moved));
        assertThrows(IllegalStateException.class, () -> registry.register(resized));
        assertThrows(IllegalStateException.class, () -> registry.register(reriched));
        assertEquals(legacy, registry.get(COPPER_ID).orElseThrow());
    }

    @Test
    void legacyMigrationDoesNotRewriteOneExactMetalToAnotherOrReverseToGeneric() {
        GeologicalDeposit copper = deposit(
                COPPER_ID,
                "c:ores/copper",
                new BlockPos(16, 32, 16),
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit gold = deposit(
                COPPER_ID,
                "c:ores/gold",
                copper.center(),
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit generic = deposit(
                COPPER_ID,
                "volcanoes:resources/mineral",
                copper.center(),
                DepositOrigin.HYDROTHERMAL);
        DepositRegistry registry = new DepositRegistry();

        assertTrue(registry.register(copper));
        assertThrows(IllegalStateException.class, () -> registry.register(gold));
        assertThrows(IllegalStateException.class, () -> registry.register(generic));
        assertEquals(copper, registry.get(COPPER_ID).orElseThrow());
    }

    @Test
    void conflictingContentForSamePersistentIdFailsClosed() {
        DepositRegistry registry = new DepositRegistry();
        GeologicalDeposit original = deposit(
                COPPER_ID,
                "c:ores/copper",
                new BlockPos(0, 20, 0),
                DepositOrigin.MAGMATIC);
        GeologicalDeposit conflicting = deposit(
                COPPER_ID,
                "c:ores/gold",
                new BlockPos(64, 20, 64),
                DepositOrigin.MAGMATIC);

        assertTrue(registry.register(original));
        assertThrows(IllegalStateException.class, () -> registry.register(conflicting));
        assertEquals(original, registry.get(COPPER_ID).orElseThrow());
    }

    @Test
    void persistenceOrderAndSpatialQueryAreDeterministic() {
        GeologicalDeposit copper = deposit(
                COPPER_ID,
                "c:ores/copper",
                new BlockPos(10, 30, 10),
                DepositOrigin.HYDROTHERMAL);
        GeologicalDeposit gold = deposit(
                GOLD_ID,
                "c:ores/gold",
                new BlockPos(200, 30, 200),
                DepositOrigin.MAGMATIC);

        DepositRegistry first = new DepositRegistry();
        first.register(gold);
        first.register(copper);
        DepositRegistry second = new DepositRegistry();
        second.register(copper);
        second.register(gold);

        assertEquals(first.toTag(), second.toTag(), "serialization must not depend on insertion order");
        assertEquals(List.of(copper), first.nearby(new BlockPos(0, 30, 0), 32.0));
    }

    @Test
    void registryIsRealLevelSavedData() throws Exception {
        assertEquals(SavedData.class, DepositRegistry.class.getSuperclass());
        assertNotNull(DepositRegistry.class.getMethod("get", ServerLevel.class));
    }

    private static GeologicalDeposit deposit(
            UUID id,
            String resourceTag,
            BlockPos center,
            DepositOrigin origin
    ) {
        return deposit(id, resourceTag, center, 12.0, 0.65, origin);
    }

    private static GeologicalDeposit deposit(
            UUID id,
            String resourceTag,
            BlockPos center,
            double radius,
            double richness,
            DepositOrigin origin
    ) {
        return new GeologicalDeposit(
                id,
                ResourceLocation.parse(resourceTag),
                center,
                radius,
                richness,
                origin);
    }
}
