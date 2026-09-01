package dev.gustavopere.rpgskilltree.gametest;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Isolated proof that an offensive Battle Mage cast stays on Iron's single canonical pipeline. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageOffensiveProviderGameTests {
    private static final String ABSTRACT_CITIZEN = "com.minecolonies.api.entity.citizen.AbstractEntityCitizen";
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE = "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String LOADOUT_RESOLVER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLoadoutResolver";
    private static final String MAGIC_BRIDGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.IronsCitizenMagicBridge";
    private static final String MAGIC_ARROW_PROJECTILE =
        "io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile";

    private BattleMageOffensiveProviderGameTests() {
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void magicArrowCompletesWithExactlyOneProjectileChargeAndCooldown(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        ColonyFixture fixture = null;
        Entity spawnedProjectile = null;
        try {
            fixture = createColonyCitizen(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();

            Object inventory = citizen.getClass().getMethod("getInventoryCitizen").invoke(citizen);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class)
                .invoke(inventory, 0, spellbook("irons_spellbooks:magic_arrow", 1));

            Object loadout = resolveLoadout(citizen).orElseThrow(
                () -> new AssertionError("registered MineColonies citizen did not resolve the real magic_arrow spellbook")
            );
            Object spellData = firstSpellData(loadout);
            Object spell = spellData.getClass().getMethod("getSpell").invoke(spellData);
            int spellLevel = (int) spellData.getClass().getMethod("getLevel").invoke(spellData);
            int manaCost = (int) spell.getClass().getMethod("getManaCost", int.class).invoke(spell, spellLevel);

            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            int projectilesBefore = countMagicArrowProjectiles(helper.getLevel());

            Class<?> bridge = Class.forName(MAGIC_BRIDGE);
            boolean began = (boolean) bridge
                .getMethod("beginCast", LivingEntity.class, Class.forName("io.redspace.ironsspellbooks.api.spells.SpellData"))
                .invoke(null, citizen, spellData);
            helper.assertTrue(began, "real Iron's magic_arrow cast must begin on the registered MineColonies citizen");
            helper.assertTrue((boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "offensive provider cast must enter MagicData casting state");

            Object result = null;
            for (int tick = 0; tick < 100; tick++) {
                result = bridge.getMethod("tickCast", LivingEntity.class).invoke(null, citizen);
                if ("COMPLETED".equals(result.toString()) || "CANCELLED".equals(result.toString())) {
                    break;
                }
            }
            helper.assertTrue(result != null && "COMPLETED".equals(result.toString()),
                "magic_arrow must complete through Iron's provider lifecycle instead of timing out/cancelling");
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "completed offensive cast must leave provider casting state");

            int projectilesAfter = countMagicArrowProjectiles(helper.getLevel());
            helper.assertTrue(projectilesAfter == projectilesBefore + 1,
                "one confirmed magic_arrow cast must create exactly one Iron's MagicArrowProjectile");
            float manaAfter = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            helper.assertTrue(Math.abs(manaAfter - (manaBefore - manaCost)) < 0.001f,
                "offensive cast must charge the exact provider mana cost once");

            Object cooldowns = magicData.getClass().getMethod("getPlayerCooldowns").invoke(magicData);
            boolean onCooldown = (boolean) cooldowns.getClass()
                .getMethod("isOnCooldown", Class.forName(ABSTRACT_SPELL))
                .invoke(cooldowns, spell);
            helper.assertTrue(onCooldown, "completed offensive cast must enter Iron's provider cooldown state");

            Object second = bridge.getMethod("tickCast", LivingEntity.class).invoke(null, citizen);
            helper.assertTrue("IDLE".equals(second.toString()),
                "completed offensive cast must not re-enter on the next bridge tick");
            helper.assertTrue(countMagicArrowProjectiles(helper.getLevel()) == projectilesAfter,
                "post-completion bridge tick must not duplicate the projectile");
            helper.assertTrue(Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaAfter) < 0.001f,
                "post-completion bridge tick must not charge mana twice");

            for (Entity entity : helper.getLevel().getAllEntities()) {
                if (MAGIC_ARROW_PROJECTILE.equals(entity.getClass().getName())) {
                    spawnedProjectile = entity;
                    break;
                }
            }
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage offensive provider pipeline probe failed", failure);
        } finally {
            if (spawnedProjectile != null) spawnedProjectile.discard();
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static boolean providersPresent() {
        return ModList.get().isLoaded("minecolonies") && ModList.get().isLoaded("irons_spellbooks");
    }

    private static ColonyFixture createColonyCitizen(GameTestHelper helper) throws ReflectiveOperationException {
        ServerLevel level = helper.getLevel();
        Player owner = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(BlockPos.ZERO);

        Class<?> managerType = Class.forName(COLONY_MANAGER);
        Object manager = managerType.getMethod("getInstance").invoke(null);
        Object colony = managerType.getMethod(
            "createColony",
            ServerLevel.class,
            BlockPos.class,
            Player.class,
            String.class,
            String.class
        ).invoke(manager, level, center, owner, "Battle Mage Offensive GameTest", "default");
        if (colony == null) {
            throw new AssertionError("MineColonies failed to create the offensive GameTest colony");
        }

        Object citizenManager = colony.getClass().getMethod("getCitizenManager").invoke(colony);
        Class<?> citizenDataType = Class.forName(CITIZEN_DATA);
        Object citizenData = citizenManager.getClass().getMethod("createAndRegisterCivilianData").invoke(citizenManager);
        Object spawnedData = citizenManager.getClass().getMethod(
            "spawnOrCreateCitizen",
            citizenDataType,
            Level.class,
            BlockPos.class
        ).invoke(citizenManager, citizenData, level, center.above());

        @SuppressWarnings("unchecked")
        Optional<Object> entity = (Optional<Object>) citizenDataType.getMethod("getEntity").invoke(spawnedData);
        Object citizen = entity.orElseThrow(
            () -> new AssertionError("MineColonies citizen manager did not spawn the offensive test citizen")
        );
        if (!(citizen instanceof LivingEntity)) {
            throw new AssertionError("spawned offensive MineColonies citizen is not a LivingEntity");
        }
        return new ColonyFixture(manager, colony, citizen);
    }

    private static void deleteFixture(ColonyFixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int colonyId = (int) fixture.colony().getClass().getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER)
                .getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), colonyId, false, level);
        } catch (ReflectiveOperationException ignored) {
            // GameTest server teardown still isolates the fixture; preserve the primary assertion failure.
        }
    }

    private static Optional<?> resolveLoadout(Object citizen) throws ReflectiveOperationException {
        return (Optional<?>) Class.forName(LOADOUT_RESOLVER)
            .getMethod("resolve", Class.forName(ABSTRACT_CITIZEN))
            .invoke(null, citizen);
    }

    private static Object firstSpellData(Object loadout) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        List<Object> spells = (List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
        if (spells.isEmpty()) throw new AssertionError("expected magic_arrow in provider spellbook");
        return spells.getFirst();
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
        if (!added) throw new AssertionError("Iron's rejected offensive provider spell " + spellId + "@" + level);
        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
    }

    private static int countMagicArrowProjectiles(Level level) {
        int count = 0;
        if (!(level instanceof ServerLevel serverLevel)) return count;
        for (Entity entity : serverLevel.getAllEntities()) {
            if (MAGIC_ARROW_PROJECTILE.equals(entity.getClass().getName())) count++;
        }
        return count;
    }

    private record ColonyFixture(Object manager, Object colony, Object citizen) {
    }
}
