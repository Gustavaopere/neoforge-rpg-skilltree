package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.gametest;

import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomyState;
import dev.gustavopere.rpgskilltree.core.economy.EconomyColonyKey;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyRepository;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present acceptance for bounded round-robin economy settlement. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class MineColoniesEconomySettlementProviderGameTests {
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
    private static final String SETTLEMENT_BRIDGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomySettlementBridge";
    private static final String PASS_RESULT = SETTLEMENT_BRIDGE + "$SettlementPassResult";

    private MineColoniesEconomySettlementProviderGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 250)
    public static void liveColonySettlementIsBoundedPersistedAndFailClosed(GameTestHelper helper) {
        if (!ModList.get().isLoaded("minecolonies")) {
            helper.succeed();
            return;
        }

        Fixture fixture = null;
        try {
            fixture = createFixture(helper);
            ServerLevel level = helper.getLevel();
            MinecraftServer server = level.getServer();
            Class<?> colonyType = Class.forName(COLONY);
            Class<?> adapterType = Class.forName(ADAPTER);
            Class<?> bridgeType = Class.forName(SETTLEMENT_BRIDGE);

            @SuppressWarnings("unchecked")
            Optional<NativeColonyBinding> bindingResult = (Optional<NativeColonyBinding>) adapterType
                .getMethod("binding", colonyType)
                .invoke(null, fixture.colony());
            NativeColonyBinding binding = bindingResult.orElseThrow();

            Object bridge = bridgeType.getConstructor(int.class).newInstance(1);
            Object pass = bridgeType.getMethod("settleNextBatch", MinecraftServer.class, long.class)
                .invoke(bridge, server, 2_000L);

            int observed = (int) pass.getClass().getMethod("observedColonies").invoke(pass);
            int settled = (int) pass.getClass().getMethod("settledColonies").invoke(pass);
            int skipped = (int) pass.getClass().getMethod("skippedColonies").invoke(pass);
            helper.assertTrue(observed >= 1, "settlement must observe the live provider colony");
            helper.assertTrue(settled >= 1, "settlement must persist at least the live fixture colony");
            helper.assertTrue(settled + skipped <= observed, "processed colonies must remain bounded by observed colonies");
            helper.assertTrue((int) bridgeType.getMethod("cursor").invoke(bridge) >= 0,
                "round-robin cursor must remain non-negative");

            ColonyEconomySavedData data = ColonyEconomySavedData.get(server);
            EconomyColonyKey key = data.binding(binding).orElseThrow();
            ColonyEconomyState state = new ColonyEconomyRepository(data).find(key).orElseThrow();
            helper.assertTrue(state.currentEconomicCapacity() > 0L,
                "settlement must derive positive capacity from the completed Town Hall");
            helper.assertTrue(state.lastSettlementTick() == 2_000L,
                "settlement must persist the authoritative game-time checkpoint");

            assertInvocationCause(
                helper,
                IllegalArgumentException.class,
                () -> bridgeType.getConstructor(int.class).newInstance(0),
                "zero settlement batch size must be rejected"
            );
            assertInvocationCause(
                helper,
                IllegalArgumentException.class,
                () -> bridgeType.getMethod("settleNextBatch", MinecraftServer.class, long.class)
                    .invoke(bridge, server, -1L),
                "negative game time must be rejected"
            );

            Class<?> resultType = Class.forName(PASS_RESULT);
            assertInvocationCause(
                helper,
                IllegalArgumentException.class,
                () -> resultType.getConstructor(int.class, int.class, int.class).newInstance(-1, 0, 0),
                "negative settlement counters must be rejected"
            );
            assertInvocationCause(
                helper,
                IllegalArgumentException.class,
                () -> resultType.getConstructor(int.class, int.class, int.class).newInstance(1, 1, 1),
                "processed settlement counters must not exceed observed colonies"
            );
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("MineColonies economy settlement provider acceptance failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static void assertInvocationCause(
        GameTestHelper helper,
        Class<? extends Throwable> expected,
        ThrowingInvocation invocation,
        String message
    ) throws ReflectiveOperationException {
        try {
            invocation.run();
            throw new AssertionError(message + "; no exception was thrown");
        } catch (InvocationTargetException failure) {
            helper.assertTrue(expected.isInstance(failure.getCause()),
                message + "; actual=" + failure.getCause());
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
        ).invoke(manager, level, center, owner, "Economy Settlement GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create settlement test colony");
        Fixture partial = new Fixture(manager, colony);

        try {
            Object structureManager = colony.getClass().getMethod("getServerBuildingManager").invoke(colony);
            Object townHall = createBuilding(colony, center, "townHall", 2);
            Method addBuilding = structureManager.getClass().getDeclaredMethod("addBuilding", Class.forName(BUILDING));
            addBuilding.setAccessible(true);
            addBuilding.invoke(structureManager, townHall);
            Class.forName(BUILDING).getMethod("onUpgradeComplete", Class.forName(BLUEPRINT), int.class)
                .invoke(townHall, null, 2);
            if (!(boolean) Class.forName(BUILDING).getMethod("isBuilt").invoke(townHall)) {
                throw new AssertionError("settlement fixture Town Hall did not enter built state");
            }
            return partial;
        } catch (ReflectiveOperationException | RuntimeException | LinkageError | AssertionError failure) {
            deleteFixture(partial, level);
            throw failure;
        }
    }

    private static Object createBuilding(Object colony, BlockPos pos, String holderField, int level)
        throws ReflectiveOperationException {
        Object holder = Class.forName(MOD_BUILDINGS).getField(holderField).get(null);
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
            // Best-effort cleanup; provider assertions preserve the actual failure evidence.
        }
    }

    @FunctionalInterface
    private interface ThrowingInvocation {
        void run() throws ReflectiveOperationException;
    }

    private record Fixture(Object manager, Object colony) {}
}
