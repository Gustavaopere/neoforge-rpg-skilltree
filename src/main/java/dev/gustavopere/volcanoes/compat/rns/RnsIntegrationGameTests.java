package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.function.Supplier;

/** Exact-host acceptance for the optional Create: Rock & Stone integration. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class RnsIntegrationGameTests {
    private static final String KUBEJS_MOD_ID = "kubejs";
    private static final String KUBEJS_VERSION = "2101.7.2-build.368";
    private static final String RNS_MISC_CLASS = "com.bmaster.createrns.RNSMisc";
    private static final String CUSTOM_LOCATION_CLASS =
            "com.bmaster.createrns.content.deposit.info.CustomServerDepositLocation";
    private static final ResourceLocation RNS_COPPER = id("create_rns", "deposit_copper");
    private static final ResourceLocation COPPER_TAG = id("c", "ores/copper");

    private RnsIntegrationGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostProjectsAuthoritativeHydrothermalDepositWithoutDisablingNativeWorldgen(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.assertTrue(RnsCompat.decide(false, null, false) == RnsCompat.InstallDecision.ABSENT,
                    "RNS-absent runtime must remain a no-op without resolving host classes");
            helper.succeed();
            return;
        }

        assertExactHost(helper);

        ServerLevel level = helper.getLevel();
        DepositRegistry productionRegistry = DepositRegistry.get(level);
        helper.assertTrue(RnsIntegrationRuntime.attemptsFor(level) > 0,
                "production RNS runtime must attempt installation before exact-host GameTests execute");

        BlockPos center = helper.absolutePos(new BlockPos(3136, 24, 3136));
        UUID sourceId = UUID.fromString("0be7fc49-631d-4c9c-9979-05c57ccca54d");
        GeologicalDeposit syntheticCopper = new GeologicalDeposit(
                sourceId,
                COPPER_TAG,
                center,
                32.0,
                0.8,
                DepositOrigin.HYDROTHERMAL);

        productionRegistry.remove(sourceId);
        try {
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 0,
                    "acceptance fixture must start without a custom RNS identity in the target chunk");
            helper.assertTrue(productionRegistry.register(syntheticCopper),
                    "acceptance fixture must be accepted by the canonical geological registry");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "active lifecycle bridge must project one authoritative Volcanoes copper deposit into RNS");
        } finally {
            productionRegistry.remove(sourceId);
        }

        helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 0,
                "removing the authoritative Volcanoes deposit must remove its RNS custom projection");
        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostNeverAdoptsOrDeletesPreExistingForeignCustomIdentity(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        assertExactHost(helper);

        ServerLevel level = helper.getLevel();
        DepositRegistry productionRegistry = DepositRegistry.get(level);
        BlockPos center = helper.absolutePos(new BlockPos(3264, 24, 3264));
        UUID sourceId = UUID.fromString("a78379f2-2ca5-4de2-a4f1-e22bb1814666");
        GeologicalDeposit syntheticCopper = new GeologicalDeposit(
                sourceId,
                COPPER_TAG,
                center,
                32.0,
                0.8,
                DepositOrigin.HYDROTHERMAL);

        productionRegistry.remove(sourceId);
        removeCustomIdentity(level, RNS_COPPER, center);
        helper.assertTrue(addCustomIdentity(level, RNS_COPPER, center),
                "acceptance fixture must create one pre-existing custom identity owned outside Volcanoes");

        try {
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "foreign fixture must exist before Volcanoes sees the geological source");
            helper.assertTrue(productionRegistry.register(syntheticCopper),
                    "authoritative Volcanoes source must still register even when RNS identity is occupied");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "Volcanoes must not duplicate an occupied foreign RNS identity");

            helper.assertTrue(productionRegistry.remove(sourceId),
                    "authoritative Volcanoes source must be removable independently of RNS ownership");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "Volcanoes must never adopt and later delete a pre-existing foreign custom identity");
        } finally {
            productionRegistry.remove(sourceId);
            removeCustomIdentity(level, RNS_COPPER, center);
        }

        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostNeverDeletesForeignReplacementAtSamePreciseLocation(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        assertExactHost(helper);

        ServerLevel level = helper.getLevel();
        DepositRegistry productionRegistry = DepositRegistry.get(level);
        BlockPos center = helper.absolutePos(new BlockPos(3392, 24, 3392));
        UUID sourceId = UUID.fromString("fe363cdc-67ab-49ae-9652-010a28e5198b");
        GeologicalDeposit syntheticCopper = new GeologicalDeposit(
                sourceId,
                COPPER_TAG,
                center,
                32.0,
                0.8,
                DepositOrigin.HYDROTHERMAL);

        productionRegistry.remove(sourceId);
        removeCustomIdentity(level, RNS_COPPER, center);
        try {
            helper.assertTrue(productionRegistry.register(syntheticCopper),
                    "Volcanoes must first create the authoritative custom projection");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "Volcanoes projection must exist before continuity is deliberately broken");

            removeCustomIdentity(level, RNS_COPPER, center);
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 0,
                    "fixture must remove the Volcanoes-created host record without notifying the bridge");
            helper.assertTrue(addCustomIdentity(level, RNS_COPPER, center),
                    "fixture must recreate a foreign custom location at the exact same position");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "foreign exact-position replacement must exist before source removal");

            helper.assertTrue(productionRegistry.remove(sourceId),
                    "authoritative Volcanoes source must still be removable after host-record replacement");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "historical ownership must not authorize deleting a foreign exact-position replacement");
        } finally {
            productionRegistry.remove(sourceId);
            removeCustomIdentity(level, RNS_COPPER, center);
        }

        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostRebindsPersistedOwnedProjectionAfterWriterRestart(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        assertExactHost(helper);

        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(3520, 24, 3520));
        UUID sourceId = UUID.fromString("09610142-2f87-46c0-abeb-cd8b9e8c98b7");
        RnsDepositProjectionPlanner.Projection projection =
                new RnsDepositProjectionPlanner.Projection(sourceId, RNS_COPPER, center);

        removeCustomIdentity(level, RNS_COPPER, center);
        RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        try {
            RnsHostDepositProjectionWriter originalWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(originalWriter.ensurePresent(projection),
                    "first writer must create and attribute the Volcanoes projection");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "owned host projection must exist before writer restart");

            RnsHostDepositProjectionWriter restartedWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(restartedWriter.ensurePresent(projection),
                    "persisted ownership must allow a restarted writer to rebind the surviving host projection");
            helper.assertTrue(restartedWriter.ensureAbsent(projection),
                    "restarted writer must retain enough ownership continuity to remove the authoritative projection");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 0,
                    "projection removal after writer restart must not leave an orphan RNS location");
        } finally {
            removeCustomIdentity(level, RNS_COPPER, center);
            RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        }

        helper.succeed();
    }

    private static void assertExactHost(GameTestHelper helper) {
        helper.assertTrue(RnsCompat.SUPPORTED_VERSION.equals(versionOf(RnsCompat.MOD_ID)),
                "acceptance host must be exact Create: Rock & Stone 1.3.1-1.21.1-6");
        helper.assertTrue(KUBEJS_VERSION.equals(versionOf(KUBEJS_MOD_ID)),
                "acceptance host must load the pinned KubeJS startup runtime");
    }

    private static String versionOf(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(container -> container.getModInfo().getVersion().toString())
                .orElse("");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object depositData(ServerLevel level) throws Exception {
        Class<?> rnsMisc = Class.forName(RNS_MISC_CLASS);
        Object supplierObject = rnsMisc.getField("LEVEL_DEPOSIT_DATA").get(null);
        Object attachmentObject = ((Supplier<?>) supplierObject).get();
        return level.getData((AttachmentType) attachmentObject);
    }

    private static Object customLocation(ResourceLocation rnsDepositId, BlockPos center) throws Exception {
        Class<?> locationClass = Class.forName(CUSTOM_LOCATION_CLASS);
        ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE, rnsDepositId);
        return locationClass.getConstructor(ResourceKey.class, BlockPos.class)
                .newInstance(structureKey, center);
    }

    private static boolean addCustomIdentity(
            ServerLevel level,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) throws Exception {
        Object depositData = depositData(level);
        Object location = customLocation(rnsDepositId, center);
        Method add = depositData.getClass().getMethod("addCustomDeposit", location.getClass());
        return (boolean) add.invoke(depositData, location);
    }

    private static void removeCustomIdentity(
            ServerLevel level,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) throws Exception {
        Object depositData = depositData(level);
        Object location = customLocation(rnsDepositId, center);
        Method remove = depositData.getClass().getMethod("removeCustomDeposit", location.getClass());
        remove.invoke(depositData, location);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static int countCustomIdentity(
            ServerLevel level,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) throws Exception {
        Object depositData = depositData(level);

        var serialize = depositData.getClass().getMethod("serializeNBT", HolderLookup.Provider.class);
        CompoundTag root = (CompoundTag) serialize.invoke(depositData, level.registryAccess());
        CompoundTag custom = root.getCompound("custom");
        ListTag locations = custom.getList(rnsDepositId.toString(), CompoundTag.TAG_COMPOUND);
        ChunkPos expectedChunk = new ChunkPos(center);

        int count = 0;
        for (int index = 0; index < locations.size(); index++) {
            CompoundTag location = locations.getCompound(index);
            if (!rnsDepositId.toString().equals(location.getString("id"))) {
                continue;
            }
            BlockPos precise = BlockPos.of(location.getLong("location"));
            if (new ChunkPos(precise).equals(expectedChunk)) {
                count++;
            }
        }
        return count;
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
