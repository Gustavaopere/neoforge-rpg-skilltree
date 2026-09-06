package dev.gustavopere.volcanoes.compat.curios;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class CuriosEquipmentContractTest {
    @Test
    void pressureRuntimeExposesBoundedFailClosedHostEquipmentExtension() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/pressure/PressureNeoForgeRuntime.java"));

        assertTrue(source.contains("MAX_HOST_EQUIPPED_ITEMS = 64"));
        assertTrue(source.contains("interface HostEquipmentProvider"));
        assertTrue(source.contains("registerHostEquipmentProvider"));
        assertTrue(source.contains("hostEquippedItems(player)"));
        assertTrue(source.contains("catch (RuntimeException | LinkageError optionalHostFailure)"));
    }

    @Test
    void curiosGatePinsExactInstalledArtifactAndKeepsHostClassesIsolated() throws Exception {
        Path compatPath = Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/curios/CuriosEquipmentCompat.java");
        assertTrue(Files.exists(compatPath), "Curios compatibility gate must exist");
        String compat = Files.readString(compatPath);

        assertTrue(compat.contains("MOD_ID = \"curios\""));
        assertTrue(compat.contains("VERIFIED_ARTIFACT_VERSION = \"9.5.1+1.21.1\""));
        assertTrue(compat.contains("ExactModVersionGate.isExactlyLoaded"));
        assertTrue(compat.contains("CuriosEquipmentIntegration.install()"));

        Path integrationPath = Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/curios/CuriosEquipmentIntegration.java");
        assertTrue(Files.exists(integrationPath), "Exact Curios API bridge must exist");
        String integration = Files.readString(integrationPath);

        assertTrue(integration.contains("CuriosApi.getCuriosInventory(player)"));
        assertTrue(integration.contains("getEquippedCurios()"));
        assertTrue(integration.contains("PressureNeoForgeRuntime.registerHostEquipmentProvider"));
    }
}
