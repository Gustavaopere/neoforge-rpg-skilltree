package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.VolcanoesMod;
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

/** Regression coverage for ownership continuity across writer restart plus foreign replacement. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class RnsOwnershipRestartReplacementGameTests {
    private static final String RNS_MISC_CLASS = "com.bmaster.createrns.RNSMisc";
    private static final String CUSTOM_LOCATION_CLASS =
            "com.bmaster.createrns.content.deposit.info.CustomServerDepositLocation";
    private static final String OWNER_SOURCE_ID = "volcanoes_owner_source_id";
    private static final ResourceLocation RNS_COPPER =
            ResourceLocation.fromNamespaceAndPath("create_rns", "deposit_copper");

    private RnsOwnershipRestartReplacementGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostRestartMustNotAdoptForeignExactReplacement(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        assertExactRnsHost(helper);

        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(3648, 24, 3648));
        UUID sourceId = UUID.fromString("6f5d62ef-c094-4685-b402-b3f30beac7ad");
        RnsDepositProjectionPlanner.Projection projection =
                new RnsDepositProjectionPlanner.Projection(sourceId, RNS_COPPER, center);

        removeCustomIdentity(level, RNS_COPPER, center);
        RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        try {
            RnsHostDepositProjectionWriter originalWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(originalWriter.ensurePresent(projection),
                    "first writer must create and persist a Volcanoes-owned projection");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "owned projection must exist before simulated downtime");

            // Simulate another integration replacing the host record while Volcanoes has no live
            // writer continuity. The replacement deliberately uses the exact same RNS identity and
            // precise center, so value equality and the old side ledger are insufficient ownership proof.
            removeCustomIdentity(level, RNS_COPPER, center);
            helper.assertTrue(addCustomIdentity(level, RNS_COPPER, center),
                    "fixture must install a foreign exact-value replacement while the writer is down");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "foreign replacement must be the sole host record before replay");

            RnsHostDepositProjectionWriter restartedWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(!restartedWriter.ensurePresent(projection),
                    "persisted side-ledger values must never make a restarted writer adopt a foreign replacement");
            helper.assertTrue(restartedWriter.ensureAbsent(projection),
                    "foreign host record must be treated as absent from Volcanoes ownership");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 1,
                    "restarted Volcanoes writer must not delete the foreign exact-value replacement");
        } finally {
            removeCustomIdentity(level, RNS_COPPER, center);
            RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        }

        helper.succeed();
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 160)
    public static void exactHostOwnershipMarkerSurvivesRnsNbtRoundTrip(GameTestHelper helper)
            throws Exception {
        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            helper.succeed();
            return;
        }

        assertExactRnsHost(helper);

        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(3776, 24, 3776));
        UUID sourceId = UUID.fromString("136e360a-b409-42a0-b8e2-1f29bd13a8bd");
        RnsDepositProjectionPlanner.Projection projection =
                new RnsDepositProjectionPlanner.Projection(sourceId, RNS_COPPER, center);

        removeCustomIdentity(level, RNS_COPPER, center);
        RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        try {
            RnsHostDepositProjectionWriter originalWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(originalWriter.ensurePresent(projection),
                    "writer must create a marked Volcanoes projection before host serialization");

            CompoundTag snapshot = serializeDepositData(level);
            CompoundTag serializedLocation = findSerializedLocation(snapshot, RNS_COPPER, center);
            helper.assertTrue(serializedLocation != null,
                    "RNS serialization must contain the Volcanoes custom projection");
            helper.assertTrue(serializedLocation.hasUUID(OWNER_SOURCE_ID),
                    "the RNS host record itself must persist the Volcanoes owner marker");
            helper.assertTrue(sourceId.equals(serializedLocation.getUUID(OWNER_SOURCE_ID)),
                    "persisted host marker must identify the exact authoritative Volcanoes source");

            deserializeDepositData(level, snapshot);

            RnsHostDepositProjectionWriter restartedWriter = new RnsHostDepositProjectionWriter(level);
            helper.assertTrue(restartedWriter.ensurePresent(projection),
                    "writer must rebind only because the reconstructed RNS host record carries the owner marker");
            helper.assertTrue(restartedWriter.ensureAbsent(projection),
                    "reconstructed marked host record must remain safely removable by its owner");
            helper.assertTrue(countCustomIdentity(level, RNS_COPPER, center) == 0,
                    "removing a marked projection after RNS NBT round-trip must leave no orphan");
        } finally {
            removeCustomIdentity(level, RNS_COPPER, center);
            RnsProjectionOwnershipData.get(level).clearIdentity(projection);
        }

        helper.succeed();
    }

    private static void assertExactRnsHost(GameTestHelper helper) {
        helper.assertTrue(RnsCompat.SUPPORTED_VERSION.equals(versionOf(RnsCompat.MOD_ID)),
                "acceptance host must be exact Create: Rock & Stone 1.3.1-1.21.1-6");
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

    private static CompoundTag serializeDepositData(ServerLevel level) throws Exception {
        Object data = depositData(level);
        Method serialize = data.getClass().getMethod("serializeNBT", HolderLookup.Provider.class);
        return (CompoundTag) serialize.invoke(data, level.registryAccess());
    }

    private static void deserializeDepositData(ServerLevel level, CompoundTag snapshot) throws Exception {
        Object data = depositData(level);
        Method deserialize = data.getClass().getMethod(
                "deserializeNBT",
                HolderLookup.Provider.class,
                CompoundTag.class);
        deserialize.invoke(data, level.registryAccess(), snapshot.copy());
    }

    private static CompoundTag findSerializedLocation(
            CompoundTag root,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) {
        CompoundTag custom = root.getCompound("custom");
        ListTag locations = custom.getList(rnsDepositId.toString(), CompoundTag.TAG_COMPOUND);
        for (int index = 0; index < locations.size(); index++) {
            CompoundTag location = locations.getCompound(index);
            if (rnsDepositId.toString().equals(location.getString("id"))
                    && center.equals(BlockPos.of(location.getLong("location")))) {
                return location;
            }
        }
        return null;
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
        Object data = depositData(level);
        Object location = customLocation(rnsDepositId, center);
        Method add = data.getClass().getMethod("addCustomDeposit", location.getClass());
        return (boolean) add.invoke(data, location);
    }

    private static void removeCustomIdentity(
            ServerLevel level,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) throws Exception {
        Object data = depositData(level);
        Object location = customLocation(rnsDepositId, center);
        Method remove = data.getClass().getMethod("removeCustomDeposit", location.getClass());
        remove.invoke(data, location);
    }

    private static int countCustomIdentity(
            ServerLevel level,
            ResourceLocation rnsDepositId,
            BlockPos center
    ) throws Exception {
        CompoundTag root = serializeDepositData(level);
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
}
