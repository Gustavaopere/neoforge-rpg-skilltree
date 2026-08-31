package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class AtmosphereGameTests {
    private AtmosphereGameTests() {
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void livingBreatheHookConsumesAndRefillsAirFromAtmosphere(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);

        AtmosphereField field = AtmosphereRuntime.fieldFor(level);
        UUID sourceId = UUID.nameUUIDFromBytes(
                ("atmosphere-gametest:" + level.dimension().location() + ":" + center.asLong())
                        .getBytes(StandardCharsets.UTF_8));
        field.remove(sourceId);
        try {
            field.register(new AtmosphericSource(
                    sourceId,
                    level.dimension().location().toString(),
                    player.getX(),
                    player.getEyeY(),
                    player.getZ(),
                    8.0,
                    VolcanicSourceProfiles.carbonDioxide(0.60).contribution(),
                    1.0,
                    false));

            player.setAirSupply(player.getMaxAirSupply());
            int fullAir = player.getAirSupply();
            CommonHooks.onLivingBreathe(player, 1, 4);
            helper.assertTrue(
                    player.getAirSupply() < fullAir,
                    "CO2 displacement/hypoxia must consume air through LivingBreatheEvent");

            field.remove(sourceId);
            player.setAirSupply(Math.max(0, fullAir - 20));
            int depletedAir = player.getAirSupply();
            CommonHooks.onLivingBreathe(player, 1, 4);
            helper.assertTrue(
                    player.getAirSupply() > depletedAir,
                    "normal atmosphere must refill air through LivingBreatheEvent");
            helper.succeed();
        } finally {
            field.remove(sourceId);
        }
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void safeAtmosphereDoesNotOverrideAnUpstreamBreathingDenial(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);

        LivingBreatheEvent event = new LivingBreatheEvent(player, false, 3, 4);
        AtmosphereRuntime.onLivingBreathe(event);

        helper.assertTrue(!event.canBreathe(),
                "safe Atmosphere must not overwrite another breathing authority's denial");
        helper.assertTrue(event.getConsumeAirAmount() == 3,
                "safe Atmosphere must preserve the upstream consume amount for a denied breath");
        helper.assertTrue(event.getRefillAirAmount() == 4,
                "safe Atmosphere must preserve the inactive refill amount for later listeners");
        helper.succeed();
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void atmosphericDenialPreservesInactiveRefillForLaterListeners(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(1, 1, 1));
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);

        AtmosphereField field = AtmosphereRuntime.fieldFor(level);
        UUID sourceId = UUID.nameUUIDFromBytes(
                ("atmosphere-interop-gametest:" + level.dimension().location() + ":" + center.asLong())
                        .getBytes(StandardCharsets.UTF_8));
        field.remove(sourceId);
        try {
            field.register(new AtmosphericSource(
                    sourceId,
                    level.dimension().location().toString(),
                    player.getX(),
                    player.getEyeY(),
                    player.getZ(),
                    8.0,
                    VolcanicSourceProfiles.carbonDioxide(0.60).contribution(),
                    1.0,
                    false));

            LivingBreatheEvent event = new LivingBreatheEvent(player, true, 1, 4);
            AtmosphereRuntime.onLivingBreathe(event);

            helper.assertTrue(!event.canBreathe(),
                    "hazardous Atmosphere must be able to deny breathing");
            helper.assertTrue(event.getConsumeAirAmount() > 1,
                    "hazardous Atmosphere must strengthen the active air-consumption path");
            helper.assertTrue(event.getRefillAirAmount() == 4,
                    "Atmosphere denial must preserve inactive refill for a later authority that re-allows breathing");
            helper.succeed();
        } finally {
            field.remove(sourceId);
        }
    }

    @GameTest(template = "atmosphere_empty", timeoutTicks = 40)
    public static void persistedSourceRehydratesThroughRuntimeFieldRecreation(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(1, 1, 1));
        UUID sourceId = UUID.nameUUIDFromBytes(
                ("atmosphere-persistence-gametest:" + level.dimension().location() + ":" + center.asLong())
                        .getBytes(StandardCharsets.UTF_8));
        AtmosphereField initialField = AtmosphereRuntime.fieldFor(level);
        initialField.remove(sourceId);

        AtmosphericSource persistent = new AtmosphericSource(
                sourceId,
                level.dimension().location().toString(),
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                8.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                1.0,
                true);

        try {
            helper.assertTrue(initialField.tryUpsert(persistent) == AtmosphericSourceAdmission.ACCEPTED,
                    "persistent Atmosphere source must be admitted before runtime recreation");
            AtmosphereSavedData savedData = AtmosphereSavedData.get(level, AtmosphereConfig.persistencePolicy());
            helper.assertTrue(persistent.equals(savedData.source(sourceId).orElse(null)),
                    "admitted persistent source must reach the level SavedData authority");

            AtmosphereRuntime.onLevelUnload(new LevelEvent.Unload(level));
            AtmosphereField recreatedField = AtmosphereRuntime.fieldFor(level);

            helper.assertTrue(recreatedField != initialField,
                    "level-unload handling must discard the old AtmosphereField runtime");
            helper.assertTrue(persistent.equals(recreatedField.source(sourceId).orElse(null)),
                    "recreated runtime must rehydrate the persisted source through SavedData -> all -> restore");
            helper.succeed();
        } finally {
            AtmosphereRuntime.fieldFor(level).remove(sourceId);
        }
    }
}
