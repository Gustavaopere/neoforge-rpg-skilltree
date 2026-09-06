package dev.gustavopere.rpgskilltree.runtime.volcanoes;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.geology.GeologicalDepositSource;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import dev.gustavopere.volcanoes.volcano.VolcanicRegionService;
import net.minecraft.server.level.ServerLevel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NativeVolcanoesServicesContractTest {
    @Test
    void exposesReadOnlyNativeProviderSurfacesWithoutOptionalModDiscovery() throws Exception {
        assertMethod("geologicalDeposits", GeologicalDepositSource.class, ServerLevel.class);
        assertMethod("volcanicRegions", VolcanicRegionService.class, ServerLevel.class);
        assertMethod("tectonics", TectonicService.class, ServerLevel.class);
        assertMethod("atmosphereAt", AtmosphereState.class,
                ServerLevel.class, double.class, double.class, double.class);
        assertMethod("atmosphericPressureAtm", double.class, ServerLevel.class, double.class);

        String source = java.nio.file.Files.readString(java.nio.file.Path.of(
                "src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/NativeVolcanoesServices.java"));
        assertTrue(!source.contains("ModList"), "native provider must not discover Volcanoes as an optional mod");
        assertTrue(!source.contains("Class.forName"), "native provider must not use reflective linkage");
    }

    private static void assertMethod(String name, Class<?> returnType, Class<?>... parameters)
            throws Exception {
        Method method = NativeVolcanoesServices.class.getMethod(name, parameters);
        assertEquals(returnType, method.getReturnType(), name + " return type");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()), name + " must be static");
    }
}
