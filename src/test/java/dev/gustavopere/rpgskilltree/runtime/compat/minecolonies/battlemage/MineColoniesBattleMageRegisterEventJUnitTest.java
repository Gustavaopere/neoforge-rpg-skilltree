package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import net.minecraft.core.registries.Registries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MineColoniesBattleMageRegisterEventJUnitTest {
    @Test
    void listenerTargetsOnlyMineColoniesJobAndGuardRegistries() {
        assertTrue(MineColoniesBattleMageRegistration.targetsRegistry(
            MineColoniesBattleMageRegistries.JOB_REGISTRY_KEY
        ));
        assertTrue(MineColoniesBattleMageRegistration.targetsRegistry(
            MineColoniesBattleMageRegistries.GUARD_TYPE_REGISTRY_KEY
        ));
        assertFalse(MineColoniesBattleMageRegistration.targetsRegistry(Registries.ITEM));
    }
}
