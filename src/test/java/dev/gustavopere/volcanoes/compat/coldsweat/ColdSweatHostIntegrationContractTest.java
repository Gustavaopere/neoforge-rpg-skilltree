package dev.gustavopere.volcanoes.compat.coldsweat;

import com.momosoftworks.coldsweat.api.event.core.registry.TempModifierRegisterEvent;
import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ColdSweatHostIntegrationContractTest {
    @Test
    void exactHostAdapterHasAnExplicitInstallEntryPointAndBoundedCadence() throws Exception {
        assertEquals(boolean.class, ColdSweatCompat.class.getMethod("installIfAvailable").getReturnType());
        assertEquals(void.class,
                ColdSweatIntegration.class
                        .getDeclaredMethod("onRegisterModifiers", TempModifierRegisterEvent.class)
                        .getReturnType());
        assertEquals(void.class,
                ColdSweatIntegration.class
                        .getDeclaredMethod("onPlayerTick", PlayerTickEvent.Post.class)
                        .getReturnType());

        assertFalse(ColdSweatIntegration.shouldRefresh(19));
        assertTrue(ColdSweatIntegration.shouldRefresh(20));
        assertFalse(ColdSweatIntegration.shouldRefresh(21));
        assertTrue(ColdSweatIntegration.MODIFIER_TTL_TICKS > ColdSweatIntegration.REFRESH_INTERVAL_TICKS);
    }

    @Test
    void customModifierChangesWorldTemperatureOnly() {
        assertTrue(TempModifier.class.isAssignableFrom(VolcanicHeatTempModifier.class));
        VolcanicHeatTempModifier modifier = new VolcanicHeatTempModifier(0.4);

        assertEquals(0.4, modifier.worldDeltaMc(), 1.0e-12);
        assertEquals(
                0.6,
                modifier.calculate(null, Temperature.Trait.WORLD).apply(0.2),
                1.0e-12);
        assertEquals(
                0.2,
                modifier.calculate(null, Temperature.Trait.CORE).apply(0.2),
                1.0e-12);
        assertEquals(
                0.2,
                modifier.calculate(null, Temperature.Trait.BODY).apply(0.2),
                1.0e-12);

        assertThrows(IllegalArgumentException.class, () -> new VolcanicHeatTempModifier(-0.01));
        assertThrows(IllegalArgumentException.class, () -> new VolcanicHeatTempModifier(Double.NaN));
    }

    @Test
    void runtimeConsumesOnlyTheBoundedStage03HeatServiceAndRefreshesOneModifier() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/coldsweat/ColdSweatIntegration.java"));

        assertTrue(source.contains("VolcanicHeatService.nearby("));
        assertTrue(source.contains("POLICY.maxSourcesPerSample()"));
        assertTrue(source.contains("Temperature.Trait.WORLD"));
        assertTrue(source.contains("Temperature.replaceOrAddModifier("));
        assertTrue(source.contains("Matcher.SAME_CLASS"));
        assertTrue(source.contains("Temperature.removeModifiers("));
        assertFalse(source.contains("Temperature.Trait.CORE"));
        assertFalse(source.contains("Temperature.Trait.BODY"));
    }

    @Test
    void optionalBootstrapAttemptsColdSweatOnlyThroughTheNeutralGate() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/OptionalIntegrationBootstrap.java"));
        assertTrue(source.contains("ColdSweatCompat.installIfAvailable()"));
    }
}
