package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.gametest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
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

/** Provider-present contract tests for the audited MineColonies 1.1.1375 economy adapter. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class MineColoniesEconomyProviderGameTests {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String COLONY = "com.minecolonies.api.colony.IColony";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String JOB = "com.minecolonies.api.colony.jobs.IJob";
    private static final String BUILDING = "com.minecolonies.api.colony.buildings.IBuilding";
    private static final String SCHEMATIC_PROVIDER = "com.minecolonies.api.colony.buildings.ISchematicProvider";
    private static final String BUILDING_ENTRY = "com.minecolonies.api.colony.buildings.registry.BuildingEntry";
    private static final String MODULE_PRODUCER = "com.minecolonies.api.colony.buildings.registry.BuildingEntry$ModuleProducer";
    private static final String MOD_BUILDINGS = "com.minecolonies.api.colony.buildings.ModBuildings";
    private static final String ROTATION_MIRROR = "com.ldtteam.structurize.api.RotationMirror";
    private static final String ADAPTER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyAdapter";
    private static final String BATTLE_MAGE_JOB =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";
    private static final String BATTLE_MAGE_REGISTRATION =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration";

    private MineColoniesEconomyProviderGameTests() {}

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void readsRealColonyInputsAndNativePermission(GameTestHelper helper) {
        if (!ModList.get().isLoaded("minecolonies")) {
            helper.succeed();
            return;
        }

        Fixture fixture = null;
        try {
            fixture = createFixture(helper);
            Object colony = fixture.colony();
            Player owner = fixture.owner();
            Class<?> colonyType = Class.forName(COLONY);
            Class<?> adapterType = Class.forName(ADAPTER);

            @SuppressWarnings("unchecked")
            Optional<Object> binding = (Optional<Object>) adapterType.getMethod("binding", colonyType)
                .invoke(null, colony);
            helper.assertTrue(binding.isPresent(), "real MineColonies colony must expose a native binding");
            Object nativeBinding = binding.orElseThrow();
            helper.assertTrue((int) nativeBinding.getClass().getMethod("colonyId").invoke(nativeBinding)
                    == (int) colonyType.getMethod("getID").invoke(colony),
                "native binding must preserve provider colony id");
            helper.assertTrue(Level.OVERWORLD.location().equals(
                    nativeBinding.getClass().getMethod("dimensionId").invoke(nativeBinding)),
                "native binding must preserve provider dimension id");
            helper.assertTrue(owner.getUUID().equals(
                    nativeBinding.getClass().getMethod("ownerUuid").invoke(nativeBinding)),
                "native binding must fingerprint the provider owner UUID");
            helper.assertTrue(fixture.townHallPos().equals(
                    nativeBinding.getClass().getMethod("townHallPos").invoke(nativeBinding)),
                "native binding must fingerprint the real Town Hall position");

            @SuppressWarnings("unchecked")
            Optional<Object> inputsResult = (Optional<Object>) adapterType.getMethod("economicInputs", colonyType)
                .invoke(null, colony);
            helper.assertTrue(inputsResult.isPresent(), "real provider graph must produce economy inputs");
            Object inputs = inputsResult.orElseThrow();
            helper.assertTrue((int) inputs.getClass().getMethod("adultWorkers").invoke(inputs) == 1,
                "job without work building must not contribute worker capacity");
            helper.assertTrue((int) inputs.getClass().getMethod("builtLevelPoints").invoke(inputs) == 10,
                "built levels must include Town Hall/guard/warehouse and clamp level 8 to 5");
            helper.assertTrue((int) inputs.getClass().getMethod("warehouseCount").invoke(inputs) == 1,
                "level-0/unbuilt warehouse must not contribute logistics capacity");

            Method mayManage = adapterType.getMethod(
                "mayManageEconomy",
                Class.forName("net.minecraft.server.level.ServerPlayer"),
                colonyType
            );
            helper.assertTrue((boolean) mayManage.invoke(null, owner, colony),
                "colony owner must be authorized through native MANAGE_HUTS permission");
            helper.assertTrue(!(boolean) mayManage.invoke(null, null, colony),
                "null actor must fail closed even with a valid colony");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("MineColonies economy provider contract probe failed", failure);
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
        ).invoke(manager, level, center, owner, "Economy Provider GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create economy test colony");
        Fixture partial = new Fixture(manager, colony, owner, center);

        try {
            Object structureManager = colony.getClass().getMethod("getServerBuildingManager").invoke(colony);

            Object townHall = createBuilding(colony, center, "townHall", 1);
            addBuilding(structureManager, townHall);

            Object employedCitizen = spawnCitizen(colony, level, center.above());
            Object employedData = employedCitizen.getClass().getMethod("getCitizenData").invoke(employedCitizen);
            Object guardTower = createBuilding(colony, center.offset(2, 0, 0), "guardTower", 1);
            addBuilding(structureManager, guardTower);
            Object producer = Class.forName(BATTLE_MAGE_REGISTRATION).getMethod("guardTowerWorkModule").invoke(null);
            Object workModule = guardTower.getClass().getMethod("getModule", Class.forName(MODULE_PRODUCER))
                .invoke(guardTower, producer);
            if (workModule == null) throw new AssertionError("Guard Tower missing Battle Mage work module");
            boolean assigned = (boolean) workModule.getClass().getMethod("assignCitizen", Class.forName(CITIZEN_DATA))
                .invoke(workModule, employedData);
            if (!assigned) throw new AssertionError("Guard Tower rejected employed economy fixture citizen");

            Object citizenManager = colony.getClass().getMethod("getCitizenManager").invoke(colony);
            Object detachedData = citizenManager.getClass().getMethod("createAndRegisterCivilianData").invoke(citizenManager);
            Constructor<?> jobConstructor = Class.forName(BATTLE_MAGE_JOB).getConstructor(Class.forName(CITIZEN_DATA));
            Object detachedJob = jobConstructor.newInstance(detachedData);
            Class.forName(CITIZEN_DATA).getMethod("setJob", Class.forName(JOB)).invoke(detachedData, detachedJob);
            if (Class.forName(CITIZEN_DATA).getMethod("getWorkBuilding").invoke(detachedData) != null) {
                throw new AssertionError("Detached job fixture unexpectedly acquired a work building");
            }

            Object warehouse = createBuilding(colony, center.offset(5, 0, 0), "wareHouse", 3);
            addBuilding(structureManager, warehouse);
            Object clampedBuilding = createBuilding(colony, center.offset(8, 0, 0), "home", 8);
            addBuilding(structureManager, clampedBuilding);
            Object unbuiltWarehouse = createBuilding(colony, center.offset(11, 0, 0), "wareHouse", 0);
            addBuilding(structureManager, unbuiltWarehouse);

            return partial;
        } catch (ReflectiveOperationException failure) {
            deleteFixture(partial, level);
            throw failure;
        } catch (RuntimeException | LinkageError | AssertionError failure) {
            deleteFixture(partial, level);
            throw failure;
        }
    }

    private static Object spawnCitizen(Object colony, ServerLevel level, BlockPos pos) throws ReflectiveOperationException {
        Object citizenManager = colony.getClass().getMethod("getCitizenManager").invoke(colony);
        Class<?> citizenDataType = Class.forName(CITIZEN_DATA);
        Object citizenData = citizenManager.getClass().getMethod("createAndRegisterCivilianData").invoke(citizenManager);
        Object spawnedData = citizenManager.getClass().getMethod(
            "spawnOrCreateCitizen", citizenDataType, Level.class, BlockPos.class
        ).invoke(citizenManager, citizenData, level, pos);
        @SuppressWarnings("unchecked")
        Optional<Object> entity = (Optional<Object>) citizenDataType.getMethod("getEntity").invoke(spawnedData);
        return entity.orElseThrow(() -> new AssertionError("MineColonies did not spawn economy fixture citizen"));
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

    private static void addBuilding(Object structureManager, Object building) throws ReflectiveOperationException {
        Method addBuilding = structureManager.getClass().getDeclaredMethod("addBuilding", Class.forName(BUILDING));
        addBuilding.setAccessible(true);
        addBuilding.invoke(structureManager, building);
    }

    private static void deleteFixture(Fixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int colonyId = (int) Class.forName(COLONY).getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER)
                .getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), colonyId, false, level);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            // Best-effort cleanup only; test assertions already carry the provider evidence.
        }
    }

    private record Fixture(Object manager, Object colony, Player owner, BlockPos townHallPos) {}
}
