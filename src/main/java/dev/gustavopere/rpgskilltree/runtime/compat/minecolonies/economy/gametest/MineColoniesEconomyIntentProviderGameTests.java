package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.gametest;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present acceptance for the canonical MineColonies economy administrative intent boundary. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class MineColoniesEconomyIntentProviderGameTests {
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
    private static final String INTENT_SERVICE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyIntentService";
    private static final String NATIVE_BINDING =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.NativeColonyBinding";
    private static final String SAVED_DATA =
        "dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData";
    private static final String SERVER_PLAYER = "net.minecraft.server.level.ServerPlayer";

    private MineColoniesEconomyIntentProviderGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 250)
    public static void canonicalIntentFlowIsAuthorizedIdempotentAndFailClosed(GameTestHelper helper) {
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
            Class<?> bindingType = Class.forName(NATIVE_BINDING);
            Class<?> savedDataType = Class.forName(SAVED_DATA);
            Class<?> serverPlayerType = Class.forName(SERVER_PLAYER);
            Class<?> adapterType = Class.forName(ADAPTER);
            Class<?> intentType = Class.forName(INTENT_SERVICE);

            @SuppressWarnings("unchecked")
            Optional<Object> bindingResult = (Optional<Object>) adapterType.getMethod("binding", colonyType)
                .invoke(null, colony);
            helper.assertTrue(bindingResult.isPresent(), "intent fixture must expose a native binding");
            Object binding = bindingResult.orElseThrow();
            Object data = savedDataType.getConstructor().newInstance();

            Method preflight = intentType.getMethod(
                "preflightMint",
                serverPlayerType,
                colonyType,
                bindingType,
                long.class,
                savedDataType
            );
            Object acceptedPreflight = preflight.invoke(null, owner, colony, binding, 20L, data);
            assertStatus(helper, acceptedPreflight, "ACCEPTED", "valid owner preflight must be accepted");
            @SuppressWarnings("unchecked")
            Optional<Object> preflightProjection = (Optional<Object>) acceptedPreflight.getClass()
                .getMethod("preflight")
                .invoke(acceptedPreflight);
            helper.assertTrue(preflightProjection.isPresent(), "accepted preflight must expose a projection");
            Object projection = preflightProjection.orElseThrow();
            helper.assertTrue((long) projection.getClass().getMethod("projectedEffectiveSupply").invoke(projection) == 20L,
                "preflight must project the requested mint without mutating state");

            Method mint = intentType.getMethod(
                "mint",
                serverPlayerType,
                colonyType,
                bindingType,
                UUID.class,
                long.class,
                long.class,
                savedDataType
            );
            Method retire = intentType.getMethod(
                "retire",
                serverPlayerType,
                colonyType,
                bindingType,
                UUID.class,
                long.class,
                long.class,
                savedDataType
            );

            UUID mintIntent = UUID.fromString("00000000-0000-0000-0000-000000002101");
            Object mintApplied = mint.invoke(null, owner, colony, binding, mintIntent, 30L, 1_000L, data);
            assertStatus(helper, mintApplied, "APPLIED", "valid mint must apply once");
            Object mintedState = resolvedState(mintApplied);
            helper.assertTrue((long) mintedState.getClass().getMethod("issuedSupply").invoke(mintedState) == 30L,
                "applied mint must issue exactly the requested amount");
            helper.assertTrue((long) mintedState.getClass().getMethod("treasuryBalance").invoke(mintedState) == 30L,
                "newly issued V1 supply must enter the colony treasury");

            Object mintReplay = mint.invoke(null, owner, colony, binding, mintIntent, 30L, 1_001L, data);
            assertStatus(helper, mintReplay, "DUPLICATE", "replayed mint intent must be idempotent");
            Object replayState = resolvedState(mintReplay);
            helper.assertTrue((long) replayState.getClass().getMethod("issuedSupply").invoke(replayState) == 30L,
                "replayed mint must not duplicate supply");

            UUID retireIntent = UUID.fromString("00000000-0000-0000-0000-000000002102");
            Object retireApplied = retire.invoke(null, owner, colony, binding, retireIntent, 10L, 1_002L, data);
            assertStatus(helper, retireApplied, "APPLIED", "valid retirement must apply once");
            Object retiredState = resolvedState(retireApplied);
            helper.assertTrue((long) retiredState.getClass().getMethod("retiredSupply").invoke(retiredState) == 10L,
                "retirement must permanently reduce effective supply");
            helper.assertTrue((long) retiredState.getClass().getMethod("treasuryBalance").invoke(retiredState) == 20L,
                "retirement must consume treasury funds in V1");

            Object invalidAmount = mint.invoke(
                null,
                owner,
                colony,
                binding,
                UUID.fromString("00000000-0000-0000-0000-000000002103"),
                0L,
                1_003L,
                data
            );
            assertStatus(helper, invalidAmount, "INVALID_AMOUNT", "zero mint must fail before ledger mutation");

            Object emptyData = savedDataType.getConstructor().newInstance();
            Object insufficientRetire = retire.invoke(
                null,
                owner,
                colony,
                binding,
                UUID.fromString("00000000-0000-0000-0000-000000002104"),
                1L,
                1_004L,
                emptyData
            );
            assertStatus(helper, insufficientRetire, "INSUFFICIENT_TREASURY",
                "retirement without treasury balance must fail closed");

            Object wrongBinding = bindingType.getConstructor(
                ResourceLocation.class,
                int.class,
                UUID.class,
                BlockPos.class
            ).newInstance(
                bindingType.getMethod("dimensionId").invoke(binding),
                ((int) bindingType.getMethod("colonyId").invoke(binding)) + 1,
                bindingType.getMethod("ownerUuid").invoke(binding),
                bindingType.getMethod("townHallPos").invoke(binding)
            );
            Object wrongColony = preflight.invoke(null, owner, colony, wrongBinding, 1L, data);
            assertStatus(helper, wrongColony, "WRONG_COLONY",
                "client-supplied native context must not authorize a different colony");

            Object missingPlayer = preflight.invoke(null, null, colony, binding, 1L, data);
            assertStatus(helper, missingPlayer, "PROVIDER_READ_FAILED",
                "missing server actor must fail closed before authorization");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("MineColonies economy intent provider acceptance failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static Object resolvedState(Object intentResult) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        Optional<Object> state = (Optional<Object>) intentResult.getClass().getMethod("state").invoke(intentResult);
        return state.orElseThrow(() -> new AssertionError("applied intent must expose resolved state"));
    }

    private static void assertStatus(GameTestHelper helper, Object result, String expected, String message)
        throws ReflectiveOperationException {
        Object status = result.getClass().getMethod("status").invoke(result);
        helper.assertTrue(expected.equals(((Enum<?>) status).name()), message + "; actual=" + status);
    }

    private static Fixture createFixture(GameTestHelper helper) throws ReflectiveOperationException {
        ServerLevel level = helper.getLevel();
        Player owner = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(BlockPos.ZERO);
        Class<?> managerType = Class.forName(COLONY_MANAGER);
        Object manager = managerType.getMethod("getInstance").invoke(null);
        Object colony = managerType.getMethod(
            "createColony", ServerLevel.class, BlockPos.class, Player.class, String.class, String.class
        ).invoke(manager, level, center, owner, "Economy Intent GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create intent test colony");
        Fixture partial = new Fixture(manager, colony, owner);

        try {
            Object structureManager = colony.getClass().getMethod("getServerBuildingManager").invoke(colony);
            Object townHall = createBuilding(colony, center, "townHall", 1);
            Method addBuilding = structureManager.getClass().getDeclaredMethod("addBuilding", Class.forName(BUILDING));
            addBuilding.setAccessible(true);
            addBuilding.invoke(structureManager, townHall);
            Class.forName(BUILDING).getMethod("onUpgradeComplete", Class.forName(BLUEPRINT), int.class)
                .invoke(townHall, null, 1);
            if (!(boolean) Class.forName(BUILDING).getMethod("isBuilt").invoke(townHall)) {
                throw new AssertionError("intent fixture Town Hall did not enter built state");
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
            // Best-effort cleanup; assertion evidence is preserved by the test failure itself.
        }
    }

    private record Fixture(Object manager, Object colony, Player owner) {}
}
