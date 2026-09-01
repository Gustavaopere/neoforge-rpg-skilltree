package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.gametest;

import java.lang.reflect.Field;
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

/** Provider-present hostile safety probes. All foreign types stay behind reflection for provider-free boots. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageSafetyGameTests {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String ENTITY_CITIZEN = "com.minecolonies.core.entity.citizen.EntityCitizen";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE = "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String LOADOUT_RESOLVER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLoadoutResolver";
    private static final String CONTROLLER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageCombatController";
    private static final String LIFECYCLE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLifecycleEvents";
    private static final String JOB_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";
    private static final String REGISTRATION =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration";

    private BattleMageSafetyGameTests() {
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void insufficientManaAndUnsupportedSpellFailClosed(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            Method setStack = inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class);
            Class<?> controller = Class.forName(CONTROLLER);
            Class<?> entityCitizen = Class.forName(ENTITY_CITIZEN);
            Object magicData = nativeMagicData(citizen);

            ItemStack healBook = spellbook("irons_spellbooks:heal", 1);
            setStack.invoke(inventory, 0, healBook);
            citizen.setHealth(Math.max(1.0f, citizen.getMaxHealth() * 0.25f));
            float healthBefore = citizen.getHealth();
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 0.0f);

            boolean noManaCast = (boolean) controller
                .getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, citizen, null);
            helper.assertTrue(!noManaCast, "Battle Mage must fail closed when Iron's mana is insufficient");
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "insufficient mana must not enter provider casting state");
            helper.assertTrue(Math.abs(citizen.getHealth() - healthBefore) < 0.001f,
                "insufficient mana must not execute the heal effect");

            ItemStack unsupportedBook = spellbook("irons_spellbooks:burning_dash", 1);
            setStack.invoke(inventory, 0, unsupportedBook);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            boolean hasSupported = (boolean) controller.getMethod("hasSupportedSpell", entityCitizen)
                .invoke(null, citizen);
            boolean unsupportedCast = (boolean) controller
                .getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, citizen, null);
            helper.assertTrue(!hasSupported, "spell without an explicit Battle Mage profile must remain unsupported");
            helper.assertTrue(!unsupportedCast, "unsupported spell must never start a provider cast");
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "unsupported spell must leave MagicData idle");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - 100.0f) < 0.001f,
                "unsupported spell rejection must not consume mana");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage no-mana/unsupported safety probe failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void hostileAoeRefusesProtectedCitizenWithoutChargingResources(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        Entity target = null;
        Entity allyEntity = null;
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            BlockPos casterPos = citizen.blockPosition();

            // Spawn through MineColonies from the exact arrival coordinate that already produced
            // the Battle Mage. The provider resolves a safe point within its own five-block scan;
            // reusing the entity's resolved position can move that scan to the edge of this tiny template.
            allyEntity = (Entity) spawnCitizen(
                fixture.colony(), helper.getLevel(), helper.absolutePos(BlockPos.ZERO).above());
            LivingEntity ally = (LivingEntity) allyEntity;
            BlockPos allyPos = casterPos.offset(11, 0, 0);
            ally.moveTo(allyPos.getX() + 0.5, allyPos.getY(), allyPos.getZ() + 0.5, 0.0f, 0.0f);

            target = spawnZombie(helper.getLevel(), casterPos.offset(10, 0, 0));
            LivingEntity hostile = (LivingEntity) target;

            Object inventory = citizenInventory(citizen);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(inventory, 0, spellbook("irons_spellbooks:fireball", 1));
            Object loadout = resolveLoadout(citizen).orElseThrow();
            Object spellData = firstSpellData(loadout);
            Object spell = spellData.getClass().getMethod("getSpell").invoke(spellData);

            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            float hostileHealth = hostile.getHealth();
            float allyHealth = ally.getHealth();

            boolean began = (boolean) Class.forName(CONTROLLER)
                .getMethod("tryBeginCast", Class.forName(ENTITY_CITIZEN), LivingEntity.class)
                .invoke(null, citizen, hostile);
            helper.assertTrue(!began,
                "HOSTILE_AREA spell must be rejected while a protected MineColonies citizen is in the danger radius");
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "friendly-fire rejection must not enter provider casting state");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "friendly-fire rejection must consume zero mana");
            Object cooldowns = magicData.getClass().getMethod("getPlayerCooldowns").invoke(magicData);
            boolean cooldown = (boolean) cooldowns.getClass().getMethod("isOnCooldown", Class.forName(ABSTRACT_SPELL))
                .invoke(cooldowns, spell);
            helper.assertTrue(!cooldown, "friendly-fire rejection must consume zero cooldown");
            helper.assertTrue(Math.abs(hostile.getHealth() - hostileHealth) < 0.001f,
                "rejected AoE must not damage the hostile target");
            helper.assertTrue(Math.abs(ally.getHealth() - allyHealth) < 0.001f,
                "rejected AoE must not damage the protected citizen");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage friendly-fire provider probe failed", failure);
        } finally {
            if (target != null) target.discard();
            if (allyEntity != null) allyEntity.discard();
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void targetLossCancelsTrackedCastWithoutManaOrEffect(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        Entity target = null;
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            target = spawnZombie(helper.getLevel(), citizen.blockPosition().offset(8, 0, 0));
            LivingEntity hostile = (LivingEntity) target;
            Object inventory = citizenInventory(citizen);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(inventory, 0, spellbook("irons_spellbooks:magic_arrow", 1));

            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            boolean began = (boolean) Class.forName(CONTROLLER)
                .getMethod("tryBeginCast", Class.forName(ENTITY_CITIZEN), LivingEntity.class)
                .invoke(null, citizen, hostile);
            helper.assertTrue(began, "valid hostile spell must start before target-loss cancellation is exercised");
            helper.assertTrue((boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "target-loss probe requires a tracked provider cast");

            hostile.discard();
            Class.forName(LIFECYCLE).getMethod("onEntityTick", EntityTickEvent.Post.class)
                .invoke(null, new EntityTickEvent.Post((Entity) citizen));
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "lost/dead hostile target must cancel the tracked provider cast");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "target-loss cancellation must not charge mana");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage target-loss provider probe failed", failure);
        } finally {
            if (target != null) target.discard();
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void jobRemovalAndDeathCancelTrackedCastsAndPreserveBookIdentity(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        HiredFixture deathFixture = null;
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            ItemStack book = spellbook("irons_spellbooks:heal", 1);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class).invoke(inventory, 0, book);
            citizen.setHealth(Math.max(1.0f, citizen.getMaxHealth() * 0.25f));
            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            Class<?> controller = Class.forName(CONTROLLER);
            Class<?> entityCitizen = Class.forName(ENTITY_CITIZEN);
            boolean began = (boolean) controller.getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, citizen, null);
            helper.assertTrue(began, "job-removal probe requires an active Battle Mage cast");

            boolean removed = (boolean) fixture.workModule().getClass()
                .getMethod("removeCitizen", Class.forName(CITIZEN_DATA))
                .invoke(fixture.workModule(), fixture.citizenData());
            helper.assertTrue(removed, "native GuardBuildingModule must remove the assigned Battle Mage citizen");
            Object currentJob = citizen.getClass().getMethod("getCitizenJobHandler").invoke(citizen);
            currentJob = currentJob.getClass().getMethod("getColonyJob").invoke(currentJob);
            helper.assertTrue(!Class.forName(JOB_BATTLE_MAGE).isInstance(currentJob),
                "native worker removal must clear the Battle Mage job before lifecycle revalidation");
            Class.forName(LIFECYCLE).getMethod("onEntityTick", EntityTickEvent.Post.class)
                .invoke(null, new EntityTickEvent.Post((Entity) citizen));
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "job removal must cancel the tracked provider cast");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "job-removal cancellation must not charge mana");
            helper.assertTrue(inventory.getClass().getMethod("getStackInSlot", int.class).invoke(inventory, 0) == book,
                "job removal must not duplicate or replace the real spellbook ItemStack");
            boolean castAfterRemoval = (boolean) controller.getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, citizen, null);
            helper.assertTrue(!castAfterRemoval, "citizen without the Battle Mage job must not start a new autonomous cast");

            deathFixture = createHiredBattleMage(helper);
            LivingEntity dyingCitizen = (LivingEntity) deathFixture.citizen();
            Object dyingInventory = citizenInventory(dyingCitizen);
            ItemStack deathBook = spellbook("irons_spellbooks:heal", 1);
            dyingInventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(dyingInventory, 0, deathBook);
            dyingCitizen.setHealth(Math.max(1.0f, dyingCitizen.getMaxHealth() * 0.25f));
            Object dyingMagic = nativeMagicData(dyingCitizen);
            dyingMagic.getClass().getMethod("setMana", float.class).invoke(dyingMagic, 100.0f);
            float deathManaBefore = (float) dyingMagic.getClass().getMethod("getMana").invoke(dyingMagic);
            boolean deathCast = (boolean) controller.getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, dyingCitizen, null);
            helper.assertTrue(deathCast, "death probe requires an active Battle Mage cast");
            dyingCitizen.setHealth(0.0f);
            Class.forName(LIFECYCLE).getMethod("onEntityTick", EntityTickEvent.Post.class)
                .invoke(null, new EntityTickEvent.Post((Entity) dyingCitizen));
            helper.assertTrue(!(boolean) dyingMagic.getClass().getMethod("isCasting").invoke(dyingMagic),
                "dead Battle Mage must not retain provider casting state");
            helper.assertTrue(Math.abs((float) dyingMagic.getClass().getMethod("getMana").invoke(dyingMagic) - deathManaBefore) < 0.001f,
                "death cancellation must not charge mana");
            helper.assertTrue(dyingInventory.getClass().getMethod("getStackInSlot", int.class).invoke(dyingInventory, 0) == deathBook,
                "lifecycle cancellation must not duplicate or replace the authoritative spellbook");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage job/death lifecycle provider probe failed", failure);
        } finally {
            deleteFixture(deathFixture, helper.getLevel());
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
        ).invoke(manager, level, center, owner, "Battle Mage Safety GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create safety-test colony");

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
        return new HiredFixture(manager, colony, citizenData, citizen, workModule);
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
        return entity.orElseThrow(() -> new AssertionError("MineColonies did not spawn safety-test citizen"));
    }

    private static LivingEntity spawnZombie(ServerLevel level, BlockPos pos) {
        LivingEntity zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("vanilla zombie EntityType failed to create");
        zombie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
        if (!level.addFreshEntity(zombie)) throw new AssertionError("failed to add hostile target to GameTest level");
        return zombie;
    }

    private static Object citizenInventory(Object citizen) throws ReflectiveOperationException {
        return citizen.getClass().getMethod("getInventoryCitizen").invoke(citizen);
    }

    private static Object nativeMagicData(LivingEntity citizen) throws ReflectiveOperationException {
        Class<?> magicDataType = Class.forName(MAGIC_DATA);
        return magicDataType.getMethod("getPlayerMagicData", LivingEntity.class).invoke(null, citizen);
    }

    private static Optional<?> resolveLoadout(Object citizen) throws ReflectiveOperationException {
        return (Optional<?>) Class.forName(LOADOUT_RESOLVER)
            .getMethod("resolve", Class.forName("com.minecolonies.api.entity.citizen.AbstractEntityCitizen"))
            .invoke(null, citizen);
    }

    private static Object firstSpellData(Object loadout) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        java.util.List<Object> spells = (java.util.List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
        if (spells.isEmpty()) throw new AssertionError("expected at least one spell in safety-test loadout");
        return spells.getFirst();
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
        if (!added) throw new AssertionError("Iron's rejected safety-test spell " + spellId + "@" + level);
        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
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

    private record HiredFixture(
        Object manager,
        Object colony,
        Object citizenData,
        Object citizen,
        Object workModule
    ) {
    }
}
