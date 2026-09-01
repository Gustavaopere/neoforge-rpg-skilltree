package dev.gustavopere.volcanoes.compat.coldsweat;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

/**
 * Cold Sweat-owned WORLD-temperature modifier carrying one bounded Volcanoes environmental delta.
 * It never changes CORE/BODY and never applies damage independently of Cold Sweat.
 */
public final class VolcanicHeatTempModifier extends TempModifier {
    private static final String WORLD_DELTA_MC = "VolcanoesWorldDeltaMc";

    /** Required by Cold Sweat's modifier registry/codec. */
    public VolcanicHeatTempModifier() {
        this(0.0);
    }

    public VolcanicHeatTempModifier(double worldDeltaMc) {
        setWorldDeltaMc(worldDeltaMc);
    }

    public double worldDeltaMc() {
        double value = getNBT().getDouble(WORLD_DELTA_MC);
        if (!Double.isFinite(value) || value < 0.0) {
            return 0.0;
        }
        return Math.min(ColdSweatHeatProjectionPolicy.defaults().maxWorldDeltaMc(), value);
    }

    private void setWorldDeltaMc(double value) {
        double maximum = ColdSweatHeatProjectionPolicy.defaults().maxWorldDeltaMc();
        if (!Double.isFinite(value) || value < 0.0 || value > maximum) {
            throw new IllegalArgumentException(
                    "worldDeltaMc must be finite and within [0, " + maximum + "]");
        }
        getNBT().putDouble(WORLD_DELTA_MC, value);
        markDirty();
    }

    @Override
    protected Function<Double, Double> calculate(LivingEntity entity, Temperature.Trait trait) {
        if (trait != Temperature.Trait.WORLD) {
            return temperature -> temperature;
        }
        double delta = worldDeltaMc();
        return temperature -> temperature + delta;
    }
}
