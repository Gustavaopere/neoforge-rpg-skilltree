package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationState;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-neutral GameTests that become real MineColonies x Iron's probes when both mods are loaded. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageProviderGameTests {
    private static final ResourceLocation BATTLE_MAGE =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage");
    private static final String ABSTRACT_CITIZEN = "com.minecolonies.api.entity.citizen.AbstractEntityCitizen";
    private static final String ENTITY_CITIZEN = "com.minecolonies.core.entity.citizen.EntityCitizen";
    private static final String COLONY_MANAGER = "com.minecolonies.api.colony.IColonyManager";
    private static final String CITIZEN_DATA = "com.minecolonies.api.colony.ICitizenData";
    private static final String I_JOB = "com.minecolonies.api.colony.jobs.IJob";
    private static final String SPELL_CONTAINER = "io.redspace.ironsspellbooks.api.spells.ISpellContainer";
    private static final String SPELL_CONTAINER_MUTABLE = "io.redspace.ironsspellbooks.api.spells.ISpellContainerMutable";
    private static final String ABSTRACT_SPELL = "io.redspace.ironsspellbooks.api.spells.AbstractSpell";
    private static final String SPELL_DATA = "io.redspace.ironsspellbooks.api.spells.SpellData";
    private static final String SPELL_REGISTRY = "io.redspace.ironsspellbooks.api.registry.SpellRegistry";
    private static final String MAGIC_DATA = "io.redspace.ironsspellbooks.api.magic.MagicData";
    private static final String LOADOUT_RESOLVER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLoadoutResolver";
    private static final String MAGIC_BRIDGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.IronsCitizenMagicBridge";
    private static final String COMBAT_CONTROLLER =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageCombatController";
    private static final String LIFECYCLE_EVENTS =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageLifecycleEvents";
    private static final String JOB_BATTLE_MAGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.JobBattleMage";

    private BattleMageProviderGameTests() {
    }

    @GameTest(template = "foundation_empty")
    public static void providerRegistriesAndCitizenMagicDataAreLive(GameTestHelper helper) {
        if (!providersPresent()) {
            // Provider-free GameTests intentionally exercise the optional-classloading lane.
            helper.succeed();
            return;
        }

        try {
            String mineColoniesVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.MINECOLONIES);
            String ironsVersion = OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS);
            helper.assertTrue(
                BattleMageIntegrationBootstrap.evaluate(true, true, mineColoniesVersion, ironsVersion)
                    == BattleMageIntegrationState.ACTIVE,
                "Battle Mage exact-version gate must be ACTIVE in the provider-present GameTest"
            );

            Class<?> apiType = Class.forName("com.minecolonies.api.IMinecoloniesAPI");
            Object api = apiType.getMethod("getInstance").invoke(null);
            Registry<?> jobs = (Registry<?>) apiType.getMethod("getJobRegistry").invoke(api);
            Registry<?> guardTypes = (Registry<?>) apiType.getMethod("getGuardTypeRegistry").invoke(api);
            helper.assertTrue(jobs.containsKey(BATTLE_MAGE), "MineColonies job registry is missing rpgskilltree:battle_mage");
            helper.assertTrue(
                guardTypes.containsKey(BATTLE_MAGE),
                "MineColonies guard-type registry is missing rpgskilltree:battle_mage"
            );

            Object citizen = newStandaloneCitizen(helper.getLevel());
            helper.assertTrue(citizen instanceof LivingEntity, "MineColonies citizen must be a LivingEntity");
            helper.assertTrue(nativeMagicData((LivingEntity) citizen) != null,
                "Iron's native MagicData attachment was not available on a MineColonies citizen");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage provider-present contract probe failed", failure);
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void realColonyCitizenTracksLiveSpellbookSwapAndRemoval(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        ColonyFixture fixture = null;
        try {
            fixture = createColonyCitizen(helper);
            Object inventory = citizenInventory(fixture.citizen());
            Method setStack = inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class);

            ItemStack bookA = spellbook(
                "iron_spell_book",
                new String[]{"irons_spellbooks:magic_arrow", "irons_spellbooks:fireball"},
                new int[]{1, 2}
            );
            setStack.invoke(inventory, 0, bookA);

            Object loadout = resolveLoadout(fixture.citizen()).orElseThrow(
                () -> new AssertionError("real colony citizen did not resolve the equipped Iron's spellbook")
            );
            ItemStack resolvedBook = (ItemStack) loadout.getClass().getMethod("bookStack").invoke(loadout);
            helper.assertTrue(resolvedBook == bookA, "loadout must retain the MineColonies slot ItemStack identity, not a clone");
            helper.assertTrue(
                spellSignatures(loadout).equals(List.of("irons_spellbooks:magic_arrow@1", "irons_spellbooks:fireball@2")),
                "book A loadout must expose exactly the provider SpellData and levels stored in the real book"
            );

            ItemStack bookB = spellbook(
                "iron_spell_book",
                new String[]{"irons_spellbooks:heal"},
                new int[]{3}
            );
            setStack.invoke(inventory, 0, bookB);
            helper.assertTrue(
                spellSignatures(loadout).equals(List.of("irons_spellbooks:heal@3")),
                "replacing the MineColonies inventory stack must update the existing live loadout without restart"
            );
            helper.assertTrue(
                loadout.getClass().getMethod("bookStack").invoke(loadout) == bookB,
                "live loadout must read the replacement ItemStack directly from MineColonies inventory"
            );

            setStack.invoke(inventory, 0, ItemStack.EMPTY);
            @SuppressWarnings("unchecked")
            List<Object> activeAfterRemoval = (List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
            helper.assertTrue(activeAfterRemoval.isEmpty(), "removing the spellbook must immediately empty the live repertoire");
            helper.assertTrue(
                !(boolean) loadout.getClass().getMethod("isStillUsable").invoke(loadout),
                "removed spellbook must invalidate the previously resolved live loadout"
            );
            helper.assertTrue(
                resolveLoadout(fixture.citizen()).isEmpty(),
                "fresh resolution after book removal must not retain a copied repertoire"
            );

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage real-colony loadout GameTest failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void providerNativeHealConsumesManaAndCompletesExactlyOnce(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        ColonyFixture fixture = null;
        try {
            fixture = createColonyCitizen(helper);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class).invoke(
                inventory,
                0,
                spellbook("iron_spell_book", new String[]{"irons_spellbooks:heal"}, new int[]{1})
            );

            Object loadout = resolveLoadout(citizen).orElseThrow();
            Object spellData = firstSpellData(loadout);
            Object spell = spellData.getClass().getMethod("getSpell").invoke(spellData);
            int level = (int) spellData.getClass().getMethod("getLevel").invoke(spellData);
            int manaCost = (int) spell.getClass().getMethod("getManaCost", int.class).invoke(spell, level);

            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            citizen.setHealth(Math.max(1.0f, citizen.getMaxHealth() - 8.0f));
            float healthBefore = citizen.getHealth();

            Class<?> bridge = Class.forName(MAGIC_BRIDGE);
            boolean began = (boolean) bridge.getMethod("beginCast", LivingEntity.class, Class.forName(SPELL_DATA))
                .invoke(null, citizen, spellData);
            helper.assertTrue(began, "provider-native heal cast must begin on the real colony citizen");
            helper.assertTrue(
                Float.compare(manaBefore, (float) magicData.getClass().getMethod("getMana").invoke(magicData)) == 0,
                "mana must not be charged before the provider cast completes"
            );

            Object result = bridge.getMethod("tickCast", LivingEntity.class).invoke(null, citizen);
            helper.assertTrue("COMPLETED".equals(result.toString()), "instant provider heal cast must complete once");
            float manaAfter = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            helper.assertTrue(
                Math.abs(manaAfter - (manaBefore - manaCost)) < 0.001f,
                "provider MagicData mana must be charged by the real Iron's mana cost exactly once"
            );
            helper.assertTrue(citizen.getHealth() > healthBefore, "provider heal effect must execute on the real citizen");
            float healthAfter = citizen.getHealth();

            Object secondResult = bridge.getMethod("tickCast", LivingEntity.class).invoke(null, citizen);
            helper.assertTrue("IDLE".equals(secondResult.toString()), "completed cast must not re-enter on a second tick");
            helper.assertTrue(
                Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaAfter) < 0.001f,
                "completed cast must not charge mana twice"
            );
            helper.assertTrue(
                Math.abs(citizen.getHealth() - healthAfter) < 0.001f,
                "completed cast must not execute the heal effect twice"
            );

            Object cooldowns = magicData.getClass().getMethod("getPlayerCooldowns").invoke(magicData);
            boolean onCooldown = (boolean) cooldowns.getClass().getMethod("isOnCooldown", Class.forName(ABSTRACT_SPELL))
                .invoke(cooldowns, spell);
            helper.assertTrue(onCooldown, "completed autonomous cast must use Iron's provider cooldown state");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage provider-native cast GameTest failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 200)
    public static void bookRemovalAndUnloadCancelTrackedCastWithoutChargeOrEffect(GameTestHelper helper) {
        if (!providersPresent()) {
            helper.succeed();
            return;
        }

        ColonyFixture fixture = null;
        try {
            fixture = createColonyCitizen(helper);
            assignBattleMageJob(fixture);
            LivingEntity citizen = (LivingEntity) fixture.citizen();
            Object inventory = citizenInventory(citizen);
            Method setStack = inventory.getClass().getMethod("setStackInSlot", int.class, ItemStack.class);
            setStack.invoke(
                inventory,
                0,
                spellbook("iron_spell_book", new String[]{"irons_spellbooks:heal"}, new int[]{1})
            );

            Object magicData = nativeMagicData(citizen);
            magicData.getClass().getMethod("setMana", float.class).invoke(magicData, 100.0f);
            float manaBefore = (float) magicData.getClass().getMethod("getMana").invoke(magicData);
            citizen.setHealth(Math.max(1.0f, citizen.getMaxHealth() * 0.25f));
            float healthBefore = citizen.getHealth();

            Class<?> controller = Class.forName(COMBAT_CONTROLLER);
            boolean began = (boolean) controller.getMethod(
                "tryBeginCast",
                Class.forName(ENTITY_CITIZEN),
                LivingEntity.class
            ).invoke(null, citizen, null);
            helper.assertTrue(began, "critical real Battle Mage citizen must begin the supported self-heal cast");
            helper.assertTrue((boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "tracked provider cast must be active before lifecycle invalidation");

            setStack.invoke(inventory, 0, ItemStack.EMPTY);
            Class.forName(LIFECYCLE_EVENTS)
                .getMethod("onEntityTick", EntityTickEvent.Post.class)
                .invoke(null, new EntityTickEvent.Post((Entity) citizen));
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "removing the authoritative spellbook must cancel the tracked cast immediately");
            helper.assertTrue(
                Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "book-removal cancellation must not charge mana"
            );
            helper.assertTrue(Math.abs(citizen.getHealth() - healthBefore) < 0.001f,
                "book-removal cancellation must not execute the heal effect");

            // Start a second tracked cast, then exercise the explicit unload seam.
            setStack.invoke(
                inventory,
                0,
                spellbook("iron_spell_book", new String[]{"irons_spellbooks:heal"}, new int[]{1})
            );
            boolean beganAgain = (boolean) controller.getMethod(
                "tryBeginCast",
                Class.forName(ENTITY_CITIZEN),
                LivingEntity.class
            ).invoke(null, citizen, null);
            helper.assertTrue(beganAgain, "Battle Mage must be able to start a fresh cast after clean cancellation");
            Class.forName(LIFECYCLE_EVENTS)
                .getMethod("onEntityLeaveLevel", EntityLeaveLevelEvent.class)
                .invoke(null, new EntityLeaveLevelEvent((Entity) citizen, helper.getLevel()));
            helper.assertTrue(!(boolean) magicData.getClass().getMethod("isCasting").invoke(magicData),
                "entity unload must cancel the tracked provider cast");
            helper.assertTrue(
                Math.abs((float) magicData.getClass().getMethod("getMana").invoke(magicData) - manaBefore) < 0.001f,
                "unload cancellation must not charge mana or duplicate the previous cancellation"
            );
            helper.assertTrue(Math.abs(citizen.getHealth() - healthBefore) < 0.001f,
                "unload cancellation must not execute an orphaned heal effect");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage lifecycle cancellation GameTest failed", failure);
        } finally {
            deleteFixture(fixture, helper.getLevel());
        }
    }

    private static boolean providersPresent() {
        return ModList.get().isLoaded("minecolonies") && ModList.get().isLoaded("irons_spellbooks");
    }

    private static Object newStandaloneCitizen(Level level) throws ReflectiveOperationException {
        Class<?> modEntitiesType = Class.forName("com.minecolonies.api.entity.ModEntities");
        Field citizenField = modEntitiesType.getField("CITIZEN");
        EntityType<?> citizenType = (EntityType<?>) citizenField.get(null);
        if (citizenType == null) {
            throw new AssertionError("MineColonies CITIZEN EntityType was not initialized");
        }

        Class<?> citizenClass = Class.forName(ENTITY_CITIZEN);
        Constructor<?> constructor = citizenClass.getConstructor(EntityType.class, Level.class);
        return constructor.newInstance(citizenType, level);
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
        ).invoke(manager, level, center, owner, "Battle Mage GameTest", "default");
        if (colony == null) {
            throw new AssertionError("MineColonies failed to create a real GameTest colony");
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
        Object citizen = entity.orElseThrow(() -> new AssertionError("MineColonies citizen manager did not spawn the real colony citizen"));
        if (!(citizen instanceof LivingEntity)) {
            throw new AssertionError("spawned MineColonies colony citizen is not a LivingEntity");
        }
        return new ColonyFixture(manager, colony, spawnedData, citizen);
    }

    private static void assignBattleMageJob(ColonyFixture fixture) throws ReflectiveOperationException {
        Class<?> citizenDataType = Class.forName(CITIZEN_DATA);
        Class<?> jobInterface = Class.forName(I_JOB);
        Class<?> battleMageJob = Class.forName(JOB_BATTLE_MAGE);
        Object job = battleMageJob.getConstructor(citizenDataType).newInstance(fixture.citizenData());
        citizenDataType.getMethod("setJob", jobInterface).invoke(fixture.citizenData(), job);

        Object jobHandler = fixture.citizen().getClass().getMethod("getCitizenJobHandler").invoke(fixture.citizen());
        Object currentJob = jobHandler.getClass().getMethod("getColonyJob").invoke(jobHandler);
        if (!battleMageJob.isInstance(currentJob)) {
            throw new AssertionError("MineColonies citizen did not persist the Battle Mage job assignment");
        }
    }

    private static void deleteFixture(ColonyFixture fixture, ServerLevel level) {
        if (fixture == null) return;
        try {
            int colonyId = (int) fixture.colony().getClass().getMethod("getID").invoke(fixture.colony());
            Class.forName(COLONY_MANAGER)
                .getMethod("deleteColonyByWorld", int.class, boolean.class, ServerLevel.class)
                .invoke(fixture.manager(), colonyId, false, level);
        } catch (ReflectiveOperationException ignored) {
            // Test server teardown still isolates the fixture; do not hide the primary assertion failure.
        }
    }

    private static Object citizenInventory(Object citizen) throws ReflectiveOperationException {
        return citizen.getClass().getMethod("getInventoryCitizen").invoke(citizen);
    }

    private static Object nativeMagicData(LivingEntity citizen) throws ReflectiveOperationException {
        Class<?> magicDataType = Class.forName(MAGIC_DATA);
        return magicDataType.getMethod("getPlayerMagicData", LivingEntity.class).invoke(null, citizen);
    }

    private static ItemStack spellbook(String itemPath, String[] spellIds, int[] levels) throws ReflectiveOperationException {
        if (spellIds.length != levels.length || spellIds.length == 0) {
            throw new IllegalArgumentException("spell ids/levels must have the same non-zero length");
        }

        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", itemPath));
        ItemStack stack = new ItemStack(item);

        Class<?> containerType = Class.forName(SPELL_CONTAINER);
        Class<?> mutableType = Class.forName(SPELL_CONTAINER_MUTABLE);
        Class<?> abstractSpellType = Class.forName(ABSTRACT_SPELL);
        Class<?> spellRegistryType = Class.forName(SPELL_REGISTRY);

        Object immutableSeed = containerType.getMethod("create", int.class, boolean.class, boolean.class)
            .invoke(null, Math.max(1, spellIds.length), true, false);
        Object mutable = containerType.getMethod("mutableCopy").invoke(immutableSeed);
        Method getSpell = spellRegistryType.getMethod("getSpell", String.class);
        Method addSpell = mutableType.getMethod(
            "addSpellAtIndex",
            abstractSpellType,
            int.class,
            int.class,
            boolean.class
        );

        for (int index = 0; index < spellIds.length; index++) {
            Object spell = getSpell.invoke(null, spellIds[index]);
            boolean added = (boolean) addSpell.invoke(mutable, spell, levels[index], index, false);
            if (!added) {
                throw new AssertionError("Iron's rejected GameTest spellbook entry " + spellIds[index] + "@" + levels[index]);
            }
        }

        Object immutable = mutableType.getMethod("toImmutable").invoke(mutable);
        containerType.getMethod("set", ItemStack.class, containerType).invoke(null, stack, immutable);
        return stack;
    }

    private static Optional<?> resolveLoadout(Object citizen) throws ReflectiveOperationException {
        Class<?> resolverType = Class.forName(LOADOUT_RESOLVER);
        Class<?> abstractCitizenType = Class.forName(ABSTRACT_CITIZEN);
        return (Optional<?>) resolverType.getMethod("resolve", abstractCitizenType).invoke(null, citizen);
    }

    private static Object firstSpellData(Object loadout) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        List<Object> spells = (List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
        if (spells.isEmpty()) throw new AssertionError("expected at least one active spell in provider loadout");
        return spells.getFirst();
    }

    private static List<String> spellSignatures(Object loadout) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        List<Object> spells = (List<Object>) loadout.getClass().getMethod("activeSpells").invoke(loadout);
        List<String> signatures = new ArrayList<>(spells.size());
        for (Object spellData : spells) {
            Object spell = spellData.getClass().getMethod("getSpell").invoke(spellData);
            String spellId = (String) spell.getClass().getMethod("getSpellId").invoke(spell);
            int level = (int) spellData.getClass().getMethod("getLevel").invoke(spellData);
            signatures.add(spellId + "@" + level);
        }
        return List.copyOf(signatures);
    }

    private record ColonyFixture(Object manager, Object colony, Object citizenData, Object citizen) {
    }
}
