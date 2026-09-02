package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.gametest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Shared provider-native fixture plumbing for Battle Mage NeoForge GameTests. */
public final class BattleMageProviderGameTestSupport {
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String ABSTRACT_CITIZEN = "com.minecolonies.api.entity.citizen.AbstractEntityCitizen";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE = "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String LOADOUT_RESOLVER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLoadoutResolver";
    private static final String REGISTRATION =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration";
    private static final String JOB_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";

    private BattleMageProviderGameTestSupport() {
    }

    public static boolean providersPresent() {
        return ModList.get().isLoaded("minecolonies") && ModList.get().isLoaded("irons_spellbooks");
    }

    public static ColonyFixture createColonyCitizen(GameTestHelper helper, String colonyName)
        throws ReflectiveOperationException {
        ServerLevel level = helper.getLevel();
        Player owner = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(BlockPos.ZERO);
        Class<?> managerType = Class.forName(COLONY_MANAGER);
        Object manager = managerType.getMethod("getInstance").invoke(null);
        Object colony = managerType.getMethod(
            "createColony", ServerLevel.class, BlockPos.class, Player.class, String.class, String.class
        ).invoke(manager, level, center, owner, colonyName, "default");
        if (colony == null) {
            throw new AssertionError("MineColonies failed to create provider GameTest colony: " + colonyName);
        }

        Object citizenManager = colony.getClass().getMethod("getCitizenManager").invoke(colony);
        Class<?> citizenDataType = Class.forName(CITIZEN_DATA);
        Object citizenData = citizenManager.getClass().getMethod("createAndRegisterCivilianData").invoke(citizenManager);
        Object spawnedData = citizenManager.getClass().getMethod(
            "spawnOrCreateCitizen", citizenDataType, Level.class, BlockPos.class
        ).invoke(citizenManager, citizenData, level, center.above());
        @SuppressWarnings("unchecked")
        Optional<Object> entity = (Optional<Object>) citizenDataType.getMethod("getEntity").invoke(spawnedData);
        Object citizen = entity.orElseThrow(
            () -> new AssertionError("MineColonies did not spawn provider GameTest citizen: " + colonyName)
        );
        if (!(citizen instanceof LivingEntity)) {
            throw new AssertionError("spawned MineColonies provider citizen is not a LivingEntity");
        }
        return new ColonyFixture(manager, colony, spawnedData, citizen);
    }

    public static HiredFixture createHiredBattleMage(GameTestHelper helper, String colonyName)
        throws ReflectiveOperationException {
        ColonyFixture fixture = createColonyCitizen(helper, colonyName);
        Object citizen = fixture.citizen();
        Object citizenData = fixture.citizenData();
        Object colony = fixture.colony();
        BlockPos center = helper.absolutePos(BlockPos.ZERO);

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
        if (workModule == null) {
            throw new AssertionError("Guard Tower missing Battle Mage work module");
        }
        boolean assigned = (boolean) workModule.getClass().getMethod("assignCitizen", Class.forName(CITIZEN_DATA))
            .invoke(workModule, citizenData);
        if (!assigned) {
            throw new AssertionError("Guard Tower rejected Battle Mage assignment");
        }

        Object jobHandler = citizen.getClass().getMethod("getCitizenJobHandler").invoke(citizen);
        Object job = jobHandler.getClass().getMethod("getColonyJob").invoke(jobHandler);
        if (!Class.forName(JOB_BATTLE_MAGE).isInstance(job)) {
            throw new AssertionError("native Guard Tower assignment did not create JobBattleMage");
        }
        return new HiredFixture(fixture.manager(), colony, citizenData, citizen, workModule);
    }

    public static Object spawnCitizen(Object colony, ServerLevel level, BlockPos pos)
        throws ReflectiveOperationException {
        Object citizenManager = colony.getClass().getMethod("getCitizenManager").invoke(colony);
        Class<?> citizenDataType = Class.forName(CITIZEN_DATA);
        Object citizenData = citizenManager.getClass().getMethod("createAndRegisterCivilianData").invoke(citizenManager);
        Object spawnedData = citizenManager.getClass().getMethod(
            "spawnOrCreateCitizen", citizenDataType, Level.class, BlockPos.class
        ).invoke(citizenManager, citizenData, level, pos);
        @SuppressWarnings("unchecked")
        Optional<Object> entity = (Optional<Object>) citizenDataType.getMethod("getEntity").invoke(spawnedData);
        return entity.orElseThrow(() -> new AssertionError("MineColonies did not spawn provider GameTest citizen"));
    }

    public static LivingEntity spawnZombie(ServerLevel level, BlockPos pos) {
        LivingEntity zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) {
            throw new AssertionError("vanilla zombie EntityType failed to create");
        }
        zombie.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
        if (!level.addFreshEntity(zombie)) {
            throw new AssertionError("failed to add hostile target to GameTest level");
        }
        return zombie;
    }

    public static Object citizenInventory(Object citizen) throws ReflectiveOperationException {
        return citizen.getClass().getMethod("getInventoryCitizen").invoke(citizen);
    }

    public static Object nativeMagicData(LivingEntity citizen) throws ReflectiveOperationException {
        Class<?> magicDataType = Class.forName(MAGIC_DATA);
        return magicDataType.getMethod("getPlayerMagicData", LivingEntity.class).invoke(null, citizen);
    }

    public static Optional<?> resolveLoadout(Object citizen) throws ReflectiveOperationException {
        return (Optional<?>) Class.forName(LOADOUT_RESOLVER)
            .getMethod("resolve", Class.forName(ABSTRACT_CITIZEN))
            .invoke(null, citizen);
    }

    public static Object firstSpellData(Object loadout) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        List<Object> spells = (List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
        if (spells.isEmpty()) {
            throw new AssertionError("expected at least one spell in provider GameTest loadout");
        }
        return spells.getFirst();
    }

    public static ItemStack spellbook(String spellId, int level) throws ReflectiveOperationException {
        Item item = BuiltInRegistries.ITEM.get(
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "iron_spell_book")
        );
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
        if (!added) {
            throw new AssertionError("Iron's rejected provider GameTest spell " + spellId + "@" + level);
        }
        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
    }

    public static void deleteColony(Object manager, Object colony, ServerLevel level) {
        if (manager == null || colony == null) {
            return;
        }
        try {
            int id = (int) colony.getClass().getMethod("getID").invoke(colony);
            Class.forName(COLONY_MANAGER).getMethod(
                "deleteColonyByWorld", int.class, boolean.class, ServerLevel.class
            ).invoke(manager, id, false, level);
        } catch (ReflectiveOperationException ignored) {
            // Preserve the primary assertion failure; the isolated GameTest server still tears down the fixture.
        }
    }

    public record ColonyFixture(Object manager, Object colony, Object citizenData, Object citizen) {
    }

    public record HiredFixture(
        Object manager,
        Object colony,
        Object citizenData,
        Object citizen,
        Object workModule
    ) {
    }
}
