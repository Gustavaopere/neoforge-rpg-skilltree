package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicTerrainMutationAuthorityTest {
    @Test
    void absentProtectionIntegrationFailsClosedForDestructiveTerrainMutation() {
        assertFalse(VolcanicProtectionService.none().allowsTerrainMutation());
    }

    @Test
    void anExplicitProtectionAuthorityCanEnableBoundedTerrainMutation() {
        VolcanicProtectionService service = new VolcanicProtectionService() {
            @Override
            public boolean isProtected(ServerLevel level, BlockPos pos) {
                return false;
            }

            @Override
            public boolean allowsTerrainMutation() {
                return true;
            }
        };

        assertTrue(service.allowsTerrainMutation());
    }
}
