package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.lang.reflect.Method;
import java.util.Map;
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
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Final provider-present authority/reload acceptance probes for Battle Mages. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageReloadAndAuthorityGameTests {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String ENTITY_CITIZEN = "com.minecolonies.core.entity.citizen.EntityCitizen";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE = "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String CONTROLLER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageCombatController";
    private static final String JOB_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";
    private static final String REGISTRATION =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration";

    private BattleMageReloadAndAuthorityGameTests() {
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void spellAbsentFromRealBookIsNeverFabricatedFromGlobalProfiles(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        Entity hostileEntity = null;
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            ItemStack healOnlyBook = spellbook("irons_spellbooks:heal", 1);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(inventory, 0, healOnlyBook);

            citizen.setHealth(citizen.getMaxHealth());
            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);

            hostileEntity = spawnZombie(helper.getLevel(), citizen.blockPosition().offset(8, 0, 0));
            LivingEntity hostile = (LivingEntity) hostileEntity;
            float hostileHealthBefore = hostile.getHealth();

            Class<?> controller = Class.forName(CONTROLLER);
            Class<?> entityCitizen = Class.forName(ENTITY_CITIZEN);
            boolean hasSupported = (boolean) controller.getMethod("hasSupportedSpell", entityCitizen)
                .invoke(null, citizen);
            helper.assertTrue(hasSupported,
                "heal-only spellbook must still be recognized as containing an explicitly supported spell");
            boolean began = (boolean) controller.getMethod("tryBeginCast", entityCitizen, LivingEntity.class)
                .invoke(null, citizen, hostile);
            helper.assertTrue(!began,
                "controller must not fabricate magic_arrow/fireball merely because hostile profiles exist globally");
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "spell absent from the real book must leave provider MagicData idle");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "phantom-spell rejection must consume zero mana");
            helper.assertTrue(Math.abs(hostile.getHealth() - hostileHealthBefore) < 0.001f,
                "phantom-spell rejection must execute zero hostile effects");
            helper.assertTrue(inventory.getClass().getMethod("getStackInSlot", int.class).invoke(inventory, 0) == healOnlyBook,
                "authority test must retain the exact real spellbook ItemStack identity");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage absent-spell authority probe failed", failure);
        } finally {
            if (hostileEntity != null) hostileEntity.discard();
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void onlineBattleMageObservesAtomicProfileReloadWithoutRestart(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        HiredFixture fixture = null;
        Map<String, BattleMageSpellProfile> original = BattleMageSpellProfileCatalog.snapshot();
        try {
            fixture = createHiredBattleMage(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            ItemStack book = spellbook("irons_spellbooks:magic_arrow", 1);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class).invoke(inventory, 0, book);

            Class<?> controller = Class.forName(CONTROLLER);
            Class<?> entityCitizen = Class.forName(ENTITY_CITIZEN);
            helper.assertTrue((boolean) controller.getMethod("hasSupportedSpell", entityCitizen).invoke(null, citizen),
                "baseline datapack profiles must support magic_arrow for the online Battle Mage");

            BattleMageSpellProfileReloader reloader = new BattleMageSpellProfileReloader();
            ResourceLocation healResource = ResourceLocation.fromNamespaceAndPath(
                "rpgskilltree", "battle_mage_spell_profiles/reload_heal"
            );
            Map<ResourceLocation, JsonElement> healOnly = Map.of(
                healResource,
                JsonParser.parseString("""
                    {
                      "spell": "irons_spellbooks:heal",
                      "target_mode": "SELF",
                      "priority": 100,
                      "min_range": 0.0,
                      "max_range": 0.0,
                      "friendly_fire_radius": 0.0,
                      "world_effect": false,
                      "ally_safe": true
                    }
                    """)
            );
            reloader.apply(healOnly, null, null);
            helper.assertTrue(!(boolean) controller.getMethod("hasSupportedSpell", entityCitizen).invoke(null, citizen),
                "online citizen must immediately stop treating removed magic_arrow profile as supported");
            helper.assertTrue(inventory.getClass().getMethod("getStackInSlot", int.class).invoke(inventory, 0) == book,
                "profile reload must not replace or mutate the authoritative spellbook ItemStack");

            Map<String, BattleMageSpellProfile> lastValid = BattleMageSpellProfileCatalog.snapshot();
            ResourceLocation invalidResource = ResourceLocation.fromNamespaceAndPath(
                "rpgskilltree", "battle_mage_spell_profiles/reload_invalid"
            );
            boolean invalidRejected = false;
            try {
                reloader.apply(Map.of(
                    invalidResource,
                    JsonParser.parseString("""
                        {
                          "spell": "irons_spellbooks:magic_arrow",
                          "target_mode": "GUESS_FROM_NAME",
                          "priority": 999,
                          "min_range": 0.0,
                          "max_range": 99.0,
                          "friendly_fire_radius": 0.0
                        }
                        """)
                ), null, null);
            } catch (IllegalArgumentException expected) {
                invalidRejected = true;
            }
            helper.assertTrue(invalidRejected, "invalid datapack profile reload must fail closed");
            helper.assertTrue(BattleMageSpellProfileCatalog.snapshot().equals(lastValid),
                "invalid reload must preserve the last fully valid profile revision atomically");

            ResourceLocation arrowResource = ResourceLocation.fromNamespaceAndPath(
                "rpgskilltree", "battle_mage_spell_profiles/reload_arrow"
            );
            reloader.apply(Map.of(
                arrowResource,
                JsonParser.parseString("""
                    {
                      "spell": "irons_spellbooks:magic_arrow",
                      "target_mode": "HOSTILE_ENTITY",
                      "priority": 70,
                      "min_range": 2.0,
                      "max_range": 30.0,
                      "friendly_fire_radius": 0.0,
                      "world_effect": false,
                      "ally_safe": true
                    }
                    """)
            ), null, null);
            helper.assertTrue((boolean) controller.getMethod("hasSupportedSpell", entityCitizen).invoke(null, citizen),
                "same online citizen must see a later valid magic_arrow profile reload without restart");
            helper.assertTrue(citizen.isAlive(), "profile reload must not replace the live MineColonies citizen entity");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage online profile-reload probe failed", failure);
        } finally {
            BattleMageSpellProfileCatalog.replace(original);
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
        ).invoke(manager, level, center, owner, "Battle Mage Reload GameTest", "default");
        if (colony == null) throw new AssertionError("MineColonies failed to create reload-test colony");

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
        return new HiredFixture(manager, colony, citizenData, citizen);
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
        return entity.orElseThrow(() -> new AssertionError("MineColonies did not spawn reload-test citizen"));
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
        if (!added) throw new AssertionError("Iron's rejected reload-test spell " + spellId + "@" + level);
        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
    }

    private static LivingEntity spawnZombie(ServerLevel level, BlockPos pos) {
        LivingEntity zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("vanilla zombie EntityType failed to create");
        zombie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
        if (!level.addFreshEntity(zombie)) throw new AssertionError("failed to add hostile target to GameTest level");
        return zombie;
    }

    private static void deleteFixture(HiredFixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int id = (int) fixture.colony().getClass().getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER).getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), id, false, level);
        } catch (ReflectiveOperationException ignored) {
            // Preserve the primary assertion failure; GameTest teardown isolates remaining state.
        }
    }

    private record HiredFixture(Object manager, Object colony, Object citizenData, Object citizen) {
    }
}
