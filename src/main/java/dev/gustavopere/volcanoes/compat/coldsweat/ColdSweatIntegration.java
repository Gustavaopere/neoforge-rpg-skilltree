package dev.gustavopere.volcanoes.compat.coldsweat;

import com.momosoftworks.coldsweat.api.event.core.registry.TempModifierRegisterEvent;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.api.util.placement.Matcher;
import dev.gustavopere.volcanoes.volcano.VolcanicHeatService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Host-linked implementation loaded only after ColdSweatCompat validates the exact host version. */
public final class ColdSweatIntegration {
    static final int REFRESH_INTERVAL_TICKS = 20;
    static final int MODIFIER_TTL_TICKS = 40;
    private static final ResourceLocation MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "volcanic_heat");
    private static final ColdSweatHeatProjectionPolicy POLICY = ColdSweatHeatProjectionPolicy.defaults();
    private static boolean installed;

    private ColdSweatIntegration() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        NeoForge.EVENT_BUS.addListener(ColdSweatIntegration::onRegisterModifiers);
        NeoForge.EVENT_BUS.addListener(ColdSweatIntegration::onPlayerTick);
        installed = true;
    }

    static void onRegisterModifiers(TempModifierRegisterEvent event) {
        event.register(MODIFIER_ID, VolcanicHeatTempModifier::new);
    }

    static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || !(player.level() instanceof ServerLevel level)
                || !shouldRefresh(player.tickCount)) {
            return;
        }

        var sources = VolcanicHeatService.nearby(
                level,
                player.blockPosition(),
                0.0,
                POLICY.maxSourcesPerSample());
        double deltaMc = ColdSweatHeatProjection.projectMcDelta(
                player.blockPosition(),
                sources,
                POLICY);

        if (deltaMc <= 0.0) {
            Temperature.removeModifiers(
                    player,
                    Temperature.Trait.WORLD,
                    VolcanicHeatTempModifier.class);
            return;
        }

        VolcanicHeatTempModifier modifier = new VolcanicHeatTempModifier(deltaMc);
        modifier.tickRate(REFRESH_INTERVAL_TICKS);
        modifier.expires(MODIFIER_TTL_TICKS);
        Temperature.replaceOrAddModifier(
                player,
                modifier,
                Temperature.Trait.WORLD,
                Matcher.SAME_CLASS);
    }

    static boolean shouldRefresh(int tickCount) {
        return tickCount >= 0 && Math.floorMod(tickCount, REFRESH_INTERVAL_TICKS) == 0;
    }
}
