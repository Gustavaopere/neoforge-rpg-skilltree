package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.gametest;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import dev.gustavopere.rpgskilltree.core.economy.EconomyPreflight;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyColonyContext;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomyMintPreflightResultPayload;
import dev.gustavopere.rpgskilltree.runtime.network.economy.EconomySnapshotPayload;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present coverage for the server-only network authority without relying on packet transport. */
@SuppressWarnings("java:S2187") // NeoForge @GameTest methods are not JUnit tests, but are executed by the GameTest server.
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class MineColoniesEconomyNetworkAuthorityProviderGameTests {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String COLONY = "com.minecolonies.api.colony.IColony";
    private static final String BUILDING = "com.minecolonies.api.colony.buildings.IBuilding";
    private static final String SCHEMATIC_PROVIDER = "com.minecolonies.api.colony.buildings.ISchematicProvider";
    private static final String BUILDING_ENTRY = "com.minecolonies.api.colony.buildings.registry.BuildingEntry";
    private static final String MOD_BUILDINGS = "com.minecolonies.api.colony.buildings.ModBuildings";
    private static final String ROTATION_MIRROR = "com.ldtteam.structurize.api.RotationMirror";
    private static final String BLUEPRINT = "com.ldtteam.structurize.blueprints.v1.Blueprint";
    private static final String ADAPTER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyAdapter";
    private static final String AUTHORITY =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyNetworkAuthority";
    private static final String INTENT_SERVICE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntentService";
    private static final String SAVED_DATA = "dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData";
    private static final String SERVER_PLAYER = "net.minecraft.server.level.ServerPlayer";

    private MineColoniesEconomyNetworkAuthorityProviderGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 250)
    public static void resolvesAndProjectsCanonicalServerState(GameTestHelper helper) {
        if (!ModList.get().isLoaded("minecolonies")) {
            helper.succeed();
            return;
        }

        Fixture fixture = null;
        try {
            fixture = createFixture(helper);
            Object colony = fixture.colony();
            Object owner = fixture.owner();
            Class<?> colonyType = Class.forName(COLONY);
            Class<?> authority = Class.forName(AUTHORITY);
            Class<?> adapter = Class.forName(ADAPTER);
            Class<?> savedDataType = Class.forName(SAVED_DATA);
            Class<?> serverPlayerType = Class.forName(SERVER_PLAYER);
            Class<?> resolvedType = Class.forName(AUTHORITY + "$Resolved");

            @SuppressWarnings("unchecked")
            Optional<NativeColonyBinding> bindingResult = (Optional<NativeColonyBinding>) adapter
                .getMethod("binding", colonyType)
                .invoke(null, colony);
            NativeColonyBinding binding = bindingResult.orElseThrow();
            EconomyColonyContext context = new EconomyColonyContext(binding.dimensionId(), binding.colonyId());

            Method resolve = authority.getDeclaredMethod("resolve", serverPlayerType, EconomyColonyContext.class);
            resolve.setAccessible(true);
            helper.assertTrue(resolve.invoke(null, null, context) == null,
                "network authority must reject a null actor");
            helper.assertTrue(resolve.invoke(null, owner, null) == null,
                "network authority must reject a null colony context");
            EconomyColonyContext missing = new EconomyColonyContext(binding.dimensionId(), binding.colonyId() + 50_000);
            helper.assertTrue(resolve.invoke(null, owner, missing) == null,
                "network authority must reject an unknown native colony id");

            Object resolved = resolve.invoke(null, owner, context);
            helper.assertTrue(resolved != null, "valid owner/native context must resolve server authority");

            Method currentCapacity = authority.getDeclaredMethod("currentCapacity", colonyType, EconomyParameters.class);
            currentCapacity.setAccessible(true);
            long capacity = (long) currentCapacity.invoke(null, colony, EconomyParameters.defaults());
            helper.assertTrue(capacity > 0L, "built Town Hall must produce positive bounded economic capacity");
            helper.assertTrue((long) currentCapacity.invoke(null, null, EconomyParameters.defaults()) == 0L,
                "missing provider graph must project zero capacity");

            Method snapshot = authority.getDeclaredMethod("snapshot", resolvedType, EconomyColonyContext.class);
            snapshot.setAccessible(true);
            EconomySnapshotPayload initial = (EconomySnapshotPayload) snapshot.invoke(null, resolved, context);
            helper.assertTrue(initial.colony().equals(context), "snapshot must preserve native lookup context");
            helper.assertTrue(!initial.metrics().initialized(), "read-only snapshot must not create monetary state");
            helper.assertTrue(initial.balances().effectiveSupply() == 0L, "uninitialized colony must expose zero supply");
            helper.assertTrue(initial.metrics().economicCapacity() == capacity,
                "uninitialized snapshot must still expose provider-derived capacity");

            Method dataAccessor = resolvedType.getDeclaredMethod("data");
            dataAccessor.setAccessible(true);
            Object data = dataAccessor.invoke(resolved);
            Method mint = Class.forName(INTENT_SERVICE).getMethod(
                "mint",
                serverPlayerType,
                colonyType,
                NativeColonyBinding.class,
                UUID.class,
                long.class,
                long.class,
                savedDataType
            );
            Object applied = mint.invoke(
                null,
                owner,
                colony,
                binding,
                UUID.fromString("00000000-0000-0000-0000-000000002201"),
                40L,
                2_000L,
                data
            );
            Object appliedStatus = applied.getClass().getMethod("status").invoke(applied);
            helper.assertTrue("APPLIED".equals(((Enum<?>) appliedStatus).name()),
                "fixture mint must initialize the exact SavedData used by network authority");

            EconomySnapshotPayload initialized = (EconomySnapshotPayload) snapshot.invoke(null, resolved, context);
            helper.assertTrue(initialized.metrics().initialized(), "snapshot must detect initialized monetary state");
            helper.assertTrue(initialized.balances().issued() == 40L, "snapshot must expose canonical issued supply");
            helper.assertTrue(initialized.balances().treasury() == 40L, "snapshot must expose canonical treasury balance");
            helper.assertTrue(initialized.balances().effectiveSupply() == 40L,
                "snapshot must derive effective supply from canonical buckets");

            Method unavailableSnapshot = authority.getDeclaredMethod("unavailableSnapshot", EconomyColonyContext.class);
            unavailableSnapshot.setAccessible(true);
            EconomySnapshotPayload unavailable = (EconomySnapshotPayload) unavailableSnapshot.invoke(null, context);
            helper.assertTrue(!unavailable.metrics().initialized(), "unavailable snapshot must fail closed");
            helper.assertTrue(unavailable.metrics().economicCapacity() == 0L,
                "unavailable snapshot must not invent provider capacity");

            EconomyColonyKey preflightKey = new EconomyColonyKey(
                UUID.fromString("00000000-0000-0000-0000-000000002202")
            );
            ColonyEconomyState preflightState = ColonyEconomyState.empty(preflightKey);
            EconomyPreflight preflight = new EconomyPreflight(
                preflightState,
                0L,
                25L,
                Math.max(1L, capacity),
                100.0D,
                125.0D
            );
            Method projection = authority.getDeclaredMethod("projection", EconomyPreflight.class);
            projection.setAccessible(true);
            EconomyMintPreflightResultPayload.Projection projected =
                (EconomyMintPreflightResultPayload.Projection) projection.invoke(null, preflight);
            helper.assertTrue(projected.currentEffectiveSupply() == 0L, "projection must preserve current supply");
            helper.assertTrue(projected.projectedEffectiveSupply() == 25L, "projection must preserve projected supply");
            helper.assertTrue(projected.economicCapacity() == Math.max(1L, capacity),
                "projection must preserve audited economic capacity");
            helper.assertTrue(Double.compare(projected.projectedTargetPriceIndex(), 125.0D) == 0,
                "projection must preserve the deterministic target price index");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("MineColonies economy network authority acceptance failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static Fixture createFixture(GameTestHelper helper) throws ReflectiveOperationException {
        ServerLevel level = helper.getLevel();
        Player owner = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(BlockPos.ZERO);
        Class<?> managerType = Class.forName(COLONY_MANAGER);
        Object manager = managerType.getMethod("getInstance").invoke(null);
        Object colony = managerType.getMethod(
            "createColony", ServerLevel.class, BlockPos.class, Player.class, String.class, String.class
        ).invoke(manager, level, center, owner, "Economy Authority GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create authority test colony");
        Fixture partial = new Fixture(manager, colony, owner);

        try {
            Object structureManager = colony.getClass().getMethod("getServerBuildingManager").invoke(colony);
            Object townHall = createBuilding(colony, center, 1);
            Method addBuilding = structureManager.getClass().getDeclaredMethod("addBuilding", Class.forName(BUILDING));
            addBuilding.setAccessible(true);
            addBuilding.invoke(structureManager, townHall);
            Class.forName(BUILDING).getMethod("onUpgradeComplete", Class.forName(BLUEPRINT), int.class)
                .invoke(townHall, null, 1);
            if (!(boolean) Class.forName(BUILDING).getMethod("isBuilt").invoke(townHall)) {
                throw new AssertionError("authority fixture Town Hall did not enter built state");
            }
            return partial;
        } catch (ReflectiveOperationException | RuntimeException | LinkageError | AssertionError failure) {
            deleteFixture(partial, level);
            throw failure;
        }
    }

    private static Object createBuilding(Object colony, BlockPos pos, int level) throws ReflectiveOperationException {
        Object holder = Class.forName(MOD_BUILDINGS).getField("townHall").get(null);
        Object entry = holder.getClass().getMethod("get").invoke(holder);
        Object building = Class.forName(BUILDING_ENTRY)
            .getMethod("produceBuilding", BlockPos.class, Class.forName(COLONY))
            .invoke(entry, pos, colony);
        Class<?> rotationMirrorType = Class.forName(ROTATION_MIRROR);
        Object identityRotation = rotationMirrorType.getField("NONE").get(null);
        Class.forName(SCHEMATIC_PROVIDER).getMethod("setRotationMirror", rotationMirrorType)
            .invoke(building, identityRotation);
        building.getClass().getMethod("setBuildingLevel", int.class).invoke(building, level);
        return building;
    }

    private static void deleteFixture(Fixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int colonyId = (int) Class.forName(COLONY).getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER)
                .getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), colonyId, false, level);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            // Best-effort cleanup; assertion evidence is preserved by the test failure itself.
        }
    }

    private record Fixture(Object manager, Object colony, Player owner) {}
}
