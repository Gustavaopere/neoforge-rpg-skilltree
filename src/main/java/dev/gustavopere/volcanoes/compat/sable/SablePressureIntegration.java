package dev.gustavopere.volcanoes.compat.sable;

import dev.gustavopere.volcanoes.pressure.ContextualAtmosphericPressureRuntime;
import dev.gustavopere.volcanoes.pressure.PressureEntityContext;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.joml.Vector3d;

import java.util.OptionalDouble;

/** Direct Sable 2.0.5 bridge for pressure at the physical position of moving sub-levels. */
public final class SablePressureIntegration {
    private static boolean installed;

    private SablePressureIntegration() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        ContextualAtmosphericPressureRuntime.register(SablePressureIntegration::pressureAtm);
        installed = true;
    }

    private static OptionalDouble pressureAtm(
            PressureEntityContext context,
            double atmosphericSampleY
    ) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return OptionalDouble.empty();
        }
        ResourceLocation dimensionId = ResourceLocation.tryParse(context.dimensionId());
        if (dimensionId == null) {
            return OptionalDouble.empty();
        }
        ResourceKey<Level> dimensionKey = ResourceKey.create(Registries.DIMENSION, dimensionId);
        ServerLevel level = server.getLevel(dimensionKey);
        if (level == null) {
            return OptionalDouble.empty();
        }

        Vec3 localSample = new Vec3(context.x(), atmosphericSampleY, context.z());
        if (Sable.HELPER.getContaining(level, localSample) == null) {
            return OptionalDouble.empty();
        }

        Vec3 physicalSample = Sable.HELPER.projectOutOfSubLevel(level, localSample);
        double pressureAtm = DimensionPhysicsData.getAirPressure(
                level,
                new Vector3d(physicalSample.x, physicalSample.y, physicalSample.z));
        if (!Double.isFinite(pressureAtm) || pressureAtm < 0.0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(pressureAtm);
    }
}
