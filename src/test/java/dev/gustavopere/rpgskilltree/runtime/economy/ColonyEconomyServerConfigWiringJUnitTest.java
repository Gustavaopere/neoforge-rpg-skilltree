package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/** Static bootstrap contract for the world-specific server economy policy. */
final class ColonyEconomyServerConfigWiringJUnitTest {
    @Test
    void serverConfigIsRegisteredAndSettlementConsumesWorldPolicy() throws IOException {
        String modMain = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
        ));
        String events = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyEvents.java"
        ));
        String config = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyServerConfig.java"
        ));

        assertTrue(
            modMain.contains("container.registerConfig(ModConfig.Type.SERVER, ColonyEconomyServerConfig.SPEC)"),
            "MineColonies Economy server config must be registered with the mod container"
        );
        assertTrue(
            events.contains("ColonyEconomyServerConfig.snapshot()"),
            "settlement runtime must consume the loaded server config"
        );
        assertTrue(
            events.contains("config.settlementIntervalTicks()"),
            "settlement cadence must come from the server config"
        );
        assertTrue(
            events.contains("config.enabled()"),
            "disabled economy policy must suppress settlement"
        );
        assertTrue(
            config.contains(".worldRestart()"),
            "settlement interval is session-bound and must require a world restart to change"
        );
    }
}
