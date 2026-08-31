package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationBootstrap;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.BattleMageIntegrationState;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-neutral GameTest that becomes a real MineColonies x Iron's probe when both mods are loaded. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageProviderGameTests {
    private static final ResourceLocation BATTLE_MAGE =
        ResourceLocation.fromNamespaceAndPath("rpgskilltree", "battle_mage");

    private BattleMageProviderGameTests() {
    }

    @GameTest(template = "foundation_empty")
    public static void providerRegistriesAndCitizenMagicDataAreLive(GameTestHelper helper) {
        if (!ModList.get().isLoaded("minecolonies") || !ModList.get().isLoaded("irons_spellbooks")) {
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

            Class<?> modEntitiesType = Class.forName("com.minecolonies.api.entity.ModEntities");
            Field citizenField = modEntitiesType.getField("CITIZEN");
            EntityType<?> citizenType = (EntityType<?>) citizenField.get(null);
            helper.assertTrue(citizenType != null, "MineColonies CITIZEN EntityType was not initialized");

            Class<?> citizenClass = Class.forName("com.minecolonies.core.entity.citizen.EntityCitizen");
            Constructor<?> constructor = citizenClass.getConstructor(EntityType.class, Level.class);
            Object citizen = constructor.newInstance(citizenType, helper.getLevel());
            helper.assertTrue(citizen instanceof LivingEntity, "MineColonies citizen must be a LivingEntity");

            Class<?> magicDataType = Class.forName("io.redspace.ironsspellbooks.api.magic.MagicData");
            Method getMagicData = magicDataType.getMethod("getPlayerMagicData", LivingEntity.class);
            Object magicData = getMagicData.invoke(null, (LivingEntity) citizen);
            helper.assertTrue(magicData != null, "Iron's native MagicData attachment was not available on a MineColonies citizen");

            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage provider-present contract probe failed", failure);
        }
    }
}
