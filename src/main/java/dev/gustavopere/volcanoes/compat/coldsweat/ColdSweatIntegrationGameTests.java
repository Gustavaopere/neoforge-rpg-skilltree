package dev.gustavopere.volcanoes.compat.coldsweat;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.volcano.VolcanicHeatService;
import dev.gustavopere.volcanoes.volcano.VolcanicHeatSource;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Dedicated-server acceptance for the optional Cold Sweat host.
 *
 * <p>The class intentionally contains no Cold Sweat type references. Standard GameTests therefore
 * prove Volcanoes remains loadable without the host; the dedicated acceptance workflow supplies
 * the exact 2.4.2 jar and exercises the same adapter through reflection.</p>
 */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class ColdSweatIntegrationGameTests {
    private static final String INTEGRATION_CLASS =
            "dev.gustavopere.volcanoes.compat.coldsweat.ColdSweatIntegration";
    private static final String MODIFIER_CLASS =
            "dev.gustavopere.volcanoes.compat.coldsweat.VolcanicHeatTempModifier";
    private static final String TEMPERATURE_CLASS =
            "com.momosoftworks.coldsweat.api.util.Temperature";
    private static final String TRAIT_CLASS =
            "com.momosoftworks.coldsweat.api.util.Temperature$Trait";
    private static final String REGISTRY_CLASS =
            "com.momosoftworks.coldsweat.api.registry.TempModifierRegistry";

    private ColdSweatIntegrationGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 120)
    public static void exactHostAddsAndRemovesWorldModifierFromIndexedHeat(GameTestHelper helper) throws Exception {
        boolean hostLoaded = ModList.get().isLoaded(ColdSweatCompat.MOD_ID);
        if (!hostLoaded) {
            helper.assertTrue(!ColdSweatCompat.installIfAvailable(),
                    "Cold Sweat-absent runtime must remain a no-op without host class resolution");
            helper.succeed();
            return;
        }

        helper.assertTrue(ColdSweatCompat.installIfAvailable(),
                "exact Cold Sweat 2.4.2 host must pass the guarded adapter installation gate");
        helper.assertTrue(modifierRegistered(),
                "Cold Sweat registry must contain volcanoes:volcanic_heat after server startup");

        ServerLevel level = helper.getLevel();
        FakePlayer player = FakePlayerFactory.getMinecraft(level);
        BlockPos center = helper.absolutePos(new BlockPos(4096, 64, 4096));
        helper.assertTrue(VolcanicHeatService.nearby(level, center, 0.0, 32).isEmpty(),
                "isolated acceptance position must start without unrelated volcanic heat");
        player.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);

        UUID sourceId = UUID.fromString("76225ebc-6624-4a48-a03b-cdbd836d853f");
        VolcanicHeatSource heat = new VolcanicHeatSource(
                sourceId,
                VolcanicHeatSource.Kind.LAVA,
                center,
                16.0,
                0.8,
                Long.MAX_VALUE);
        helper.assertTrue(VolcanicHeatService.upsert(level, heat),
                "acceptance fixture must publish one indexed lava heat source");

        try {
            player.tickCount = 20;
            invokeAdapterTick(player);
            Optional<?> active = worldModifier(player);
            helper.assertTrue(active.isPresent(),
                    "indexed volcanic heat must create the Volcanoes WORLD modifier");
            double delta = (double) active.orElseThrow().getClass().getMethod("worldDeltaMc").invoke(active.orElseThrow());
            helper.assertTrue(delta > 0.0 && delta <= ColdSweatHeatProjectionPolicy.defaults().maxWorldDeltaMc(),
                    "WORLD modifier delta must be positive and bounded");

            helper.assertTrue(VolcanicHeatService.remove(level, sourceId),
                    "acceptance fixture heat source must be removable by stable identity");
            helper.assertTrue(VolcanicHeatService.nearby(level, center, 0.0, 32).isEmpty(),
                    "removing the fixture source must leave the isolated heat query empty");
            player.tickCount = 40;
            invokeAdapterTick(player);
            helper.assertTrue(worldModifier(player).isEmpty(),
                    "removing indexed heat must remove the Volcanoes WORLD modifier on refresh");
        } finally {
            VolcanicHeatService.remove(level, sourceId);
        }
        helper.succeed();
    }

    private static boolean modifierRegistered() throws ReflectiveOperationException {
        Class<?> registry = Class.forName(REGISTRY_CLASS);
        Method containsKey = registry.getMethod("containsKey", ResourceLocation.class);
        return (boolean) containsKey.invoke(
                null,
                ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, "volcanic_heat"));
    }

    private static void invokeAdapterTick(FakePlayer player) throws ReflectiveOperationException {
        Class<?> integration = Class.forName(INTEGRATION_CLASS);
        Method onPlayerTick = integration.getDeclaredMethod("onPlayerTick", PlayerTickEvent.Post.class);
        onPlayerTick.setAccessible(true);
        onPlayerTick.invoke(null, new PlayerTickEvent.Post(player));
    }

    private static Optional<?> worldModifier(FakePlayer player) throws ReflectiveOperationException {
        Class<?> temperature = Class.forName(TEMPERATURE_CLASS);
        Class<?> trait = Class.forName(TRAIT_CLASS);
        Object world = Arrays.stream(trait.getEnumConstants())
                .filter(value -> value instanceof Enum<?> enumeration && enumeration.name().equals("WORLD"))
                .findFirst()
                .orElseThrow();
        Class<?> modifier = Class.forName(MODIFIER_CLASS);
        Method getModifier = temperature.getMethod("getModifier", LivingEntity.class, trait, Class.class);
        return (Optional<?>) getModifier.invoke(null, player, world, modifier);
    }
}
