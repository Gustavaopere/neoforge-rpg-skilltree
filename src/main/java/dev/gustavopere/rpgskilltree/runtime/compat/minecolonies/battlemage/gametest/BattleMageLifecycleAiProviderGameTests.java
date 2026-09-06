package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.gametest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present proof for the native MineColonies AI seam and Battle Mage cast lifecycle. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageLifecycleAiProviderGameTests {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String ENTITY_CITIZEN = "com.minecolonies.core.entity.citizen.EntityCitizen";
    private static final String ABSTRACT_GUARD_AI =
        "com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard";
    private static final String STATE_MACHINE =
        "com.minecolonies.api.entity.ai.statemachine.tickratestatemachine.ITickRateStateMachine";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE =
        "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String JOB_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";
    private static final String ENTITY_AI_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.EntityAIBattleMage";
    private static final String COMBAT_AI =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageCombatAI";
    private static final String LIFECYCLE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLifecycleEvents";
    private static final String CAST_TRACKER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageCastTracker";
    private static final String REGISTRATION =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration";
    private static final String MAGIC_ARROW_PROJECTILE =
        "io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile";

    private BattleMageLifecycleAiProviderGameTests() {
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void nativeAiCastCompletesThroughLifecycleExactlyOnce(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        LivingEntity hostile = null;
        try {
            fixture = createHiredBattleMage(helper);
            Object citizen = fixture.citizen();
            LivingEntity livingCitizen = (LivingEntity) citizen;
            Object job = fixture.job();

            Method generateGuardAi = job.getClass().getDeclaredMethod("generateGuardAI");
            generateGuardAi.setAccessible(true);
            Object nativeAi = generateGuardAi.invoke(job);
            helper.assertTrue(Class.forName(ENTITY_AI_BATTLE_MAGE).isInstance(nativeAi),
                "JobBattleMage must generate the native EntityAIBattleMage worker AI");
            helper.assertTrue(job.getClass().getMethod("getModel").invoke(job) != null,
                "Battle Mage job must expose a MineColonies render model");

            Method getStateAi = findMethod(nativeAi.getClass(), "getStateAI");
            Object stateMachine = getStateAi.invoke(nativeAi);
            Constructor<?> combatConstructor = Class.forName(COMBAT_AI).getConstructor(
                Class.forName(ENTITY_CITIZEN),
                Class.forName(STATE_MACHINE),
                Class.forName(ABSTRACT_GUARD_AI)
            );
            Object combatAi = combatConstructor.newInstance(citizen, stateMachine, nativeAi);

            boolean attackWithoutBook = (boolean) combatAi.getClass().getMethod("canAttack").invoke(combatAi);
            helper.assertTrue(!attackWithoutBook,
                "Battle Mage combat AI must not attack without a supported real spellbook");

            Object inventory = citizenInventory(citizen);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(inventory, 0, spellbook("irons_spellbooks:magic_arrow", 1));
            Object magicData = nativeMagicData(livingCitizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);

            boolean attackWithBook = (boolean) combatAi.getClass().getMethod("canAttack").invoke(combatAi);
            helper.assertTrue(attackWithBook,
                "Battle Mage combat AI must become eligible when a supported real spellbook is equipped");

            Method attackDistance = combatAi.getClass().getDeclaredMethod("getAttackDistance");
            attackDistance.setAccessible(true);
            double distance = (double) attackDistance.invoke(combatAi);
            helper.assertTrue(distance > 0.0 && distance <= 64.0,
                "Battle Mage combat AI must expose a finite provider/profile-derived attack distance");

            Method attackDelay = combatAi.getClass().getDeclaredMethod("getAttackDelay");
            attackDelay.setAccessible(true);
            helper.assertTrue((int) attackDelay.invoke(combatAi) == 10,
                "Battle Mage AI think interval must remain the explicit 10-tick decision cadence");

            hostile = spawnZombie(helper.getLevel(), livingCitizen.blockPosition().offset(8, 0, 0));
            citizen.getClass().getMethod("setTarget", LivingEntity.class).invoke(citizen, hostile);
            int projectilesBefore = countMagicArrowProjectiles(helper.getLevel());

            Method doAttack = combatAi.getClass().getDeclaredMethod("doAttack", LivingEntity.class);
            doAttack.setAccessible(true);
            doAttack.invoke(combatAi, hostile);
            helper.assertTrue((boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "native Battle Mage combat seam must enter Iron's provider casting state");
            helper.assertTrue(ownsTrackedCast(citizen),
                "controller-started AI cast must be tracked before lifecycle completion");

            Method lifecycleTick = Class.forName(LIFECYCLE)
                .getMethod("onEntityTick", EntityTickEvent.Post.class);
            for (int tick = 0; tick < 100; tick++) {
                lifecycleTick.invoke(null, new EntityTickEvent.Post((Entity) citizen));
                if (!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData)) {
                    break;
                }
            }

            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "valid AI cast must complete through BattleMageLifecycleEvents");
            helper.assertTrue(!ownsTrackedCast(citizen),
                "normal lifecycle completion must clear the ephemeral Battle Mage cast tracker");
            int projectilesAfter = countMagicArrowProjectiles(helper.getLevel());
            helper.assertTrue(projectilesAfter == projectilesBefore + 1,
                "lifecycle completion must execute exactly one Iron's magic-arrow effect");

            lifecycleTick.invoke(null, new EntityTickEvent.Post((Entity) citizen));
            helper.assertTrue(countMagicArrowProjectiles(helper.getLevel()) == projectilesAfter,
                "idle lifecycle tick after completion must not duplicate the provider effect");
            helper.assertTrue(!ownsTrackedCast(citizen),
                "idle lifecycle tick after completion must leave the tracker clear");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage native AI/lifecycle provider probe failed", failure);
        } finally {
            if (hostile != null) hostile.discard();
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static boolean providersPresent() {
        return ModList.get().isLoaded("minecolonies") && ModList.get().isLoaded("irons_spellbooks");
    }

    private static HiredFixture createHiredBattleMage(GameTestHelper helper) throws ReflectiveOperationException {
        ServerLevel level = helper.getLevel();
        Player owner = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(BlockPos.ZERO);
        Class<?> managerType = Class.forName(COLONY_MANAGER);
        Object manager = managerType.getMethod("getInstance").invoke(null);
        Object colony = managerType.getMethod(
            "createColony", ServerLevel.class, BlockPos.class, Player.class, String.class, String.class
        ).invoke(manager, level, center, owner, "Battle Mage Lifecycle AI GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create lifecycle-AI test colony");

        Object citizen = spawnCitizen(colony, level, center.above());
        Object citizenData = citizen.getClass().getMethod("getCitizenData").invoke(citizen);

        Class<?> colonyType = Class.forName("com.minecolonies.api.colony.IColony");
        Class<?> buildingType = Class.forName("com.minecolonies.api.colony.buildings.IBuilding");
        Class<?> buildingEntryType = Class.forName("com.minecolonies.api.colony.buildings.registry.BuildingEntry");
        Class<?> moduleProducerType = Class.forName(
            "com.minecolonies.api.colony.buildings.registry.BuildingEntry$ModuleProducer"
        );
        Class<?> modBuildings = Class.forName("com.minecolonies.api.colony.buildings.ModBuildings");
        Object guardTowerHolder = modBuildings.getField("guardTower").get(null);
        Object guardTowerEntry = guardTowerHolder.getClass().getMethod("get").invoke(guardTowerHolder);
        Object tower = buildingEntryType.getMethod("produceBuilding", BlockPos.class, colonyType)
            .invoke(guardTowerEntry, center.offset(2, 0, 0), colony);
        tower.getClass().getMethod("setBuildingLevel", int.class).invoke(tower, 1);
        Object structureManager = colony.getClass().getMethod("getServerBuildingManager").invoke(colony);
        Method addBuilding = structureManager.getClass().getDeclaredMethod("addBuilding", buildingType);
        addBuilding.setAccessible(true);
        addBuilding.invoke(structureManager, tower);

        Object producer = Class.forName(REGISTRATION).getMethod("guardTowerWorkModule").invoke(null);
        Object workModule = tower.getClass().getMethod("getModule", moduleProducerType).invoke(tower, producer);
        if (workModule == null) throw new AssertionError("Guard Tower missing Battle Mage work module");
        boolean assigned = (boolean) workModule.getClass().getMethod("assignCitizen", Class.forName(CITIZEN_DATA))
            .invoke(workModule, citizenData);
        if (!assigned) throw new AssertionError("Guard Tower rejected Battle Mage assignment");

        Object jobHandler = citizen.getClass().getMethod("getCitizenJobHandler").invoke(citizen);
        Object job = jobHandler.getClass().getMethod("getColonyJob").invoke(jobHandler);
        if (!Class.forName(JOB_BATTLE_MAGE).isInstance(job)) {
            throw new AssertionError("native Guard Tower assignment did not create JobBattleMage");
        }
        return new HiredFixture(manager, colony, citizen, job);
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
        return entity.orElseThrow(() -> new AssertionError("MineColonies did not spawn lifecycle-AI test citizen"));
    }

    private static LivingEntity spawnZombie(ServerLevel level, BlockPos pos) {
        LivingEntity zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("vanilla zombie EntityType failed to create");
        zombie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
        if (!level.addFreshEntity(zombie)) throw new AssertionError("failed to add lifecycle-AI hostile target");
        return zombie;
    }

    private static Object citizenInventory(Object citizen) throws ReflectiveOperationException {
        return citizen.getClass().getMethod("getInventoryCitizen").invoke(citizen);
    }

    private static Object nativeMagicData(LivingEntity citizen) throws ReflectiveOperationException {
        Class<?> magicDataType = Class.forName(MAGIC_DATA);
        return magicDataType.getMethod("getPlayerMagicData", LivingEntity.class).invoke(null, citizen);
    }

    private static ItemStack spellbook(String spellId, int level) throws ReflectiveOperationException {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "iron_spell_book"));
        ItemStack stack = new ItemStack(item);
        Class<?> containerType = Class.forName(SPELL_CONTAINER);
        Class<?> mutableType = Class.forName(SPELL_CONTAINER_MUTABLE);
        Class<?> abstractSpellType = Class.forName(ABSTRACT_SPELL);
        Object immutableSeed = containerType.getMethod("create", int.class, boolean.class, boolean.class)
            .invoke(null, 1, true, false);
        Object mutable = containerType.getMethod("mutableCopy").invoke(immutableSeed);
        Object spell = Class.forName(SPELL_REGISTRY).getMethod("getSpell", String.class).invoke(null, spellId);
        boolean added = (boolean) mutableType.getMethod(
            "addSpellAtIndex", abstractSpellType, int.class, int.class, boolean.class
        ).invoke(mutable, spell, level, 0, false);
        if (!added) throw new AssertionError("Iron's rejected lifecycle-AI spell " + spellId + "@" + level);
        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
    }

    private static boolean ownsTrackedCast(Object citizen) throws ReflectiveOperationException {
        Class<?> tracker = Class.forName(CAST_TRACKER);
        Method owns = tracker.getDeclaredMethod("ownsCast", Class.forName(ENTITY_CITIZEN));
        owns.setAccessible(true);
        return (boolean) owns.invoke(null, citizen);
    }

    private static Method findMethod(Class<?> type, String name) throws NoSuchMethodException {
        Class<?> cursor = type;
        while (cursor != null) {
            try {
                Method method = cursor.getDeclaredMethod(name);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException ignored) {
                cursor = cursor.getSuperclass();
            }
        }
        throw new NoSuchMethodException(type.getName() + "#" + name);
    }

    private static int countMagicArrowProjectiles(ServerLevel level) {
        int count = 0;
        for (Entity entity : level.getAllEntities()) {
            if (MAGIC_ARROW_PROJECTILE.equals(entity.getClass().getName())) count++;
        }
        return count;
    }

    private static void deleteFixture(HiredFixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int id = (int) fixture.colony().getClass().getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER).getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), id, false, level);
        } catch (ReflectiveOperationException ignored) {
            // Preserve the primary assertion failure; GameTest server teardown isolates remaining state.
        }
    }

    private record HiredFixture(Object manager, Object colony, Object citizen, Object job) {
    }
}
