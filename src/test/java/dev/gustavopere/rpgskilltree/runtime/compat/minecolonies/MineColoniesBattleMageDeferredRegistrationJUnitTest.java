package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistration;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.MineColoniesBattleMageRegistries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class MineColoniesBattleMageDeferredRegistrationJUnitTest {
    @Test
    void deferredHoldersUseCanonicalBattleMageId() {
        assertEquals(
            MineColoniesBattleMageRegistries.BATTLE_MAGE_ID,
            MineColoniesBattleMageRegistration.BATTLE_MAGE_JOB.getId()
        );
        assertEquals(
            MineColoniesBattleMageRegistries.BATTLE_MAGE_ID,
            MineColoniesBattleMageRegistration.BATTLE_MAGE_GUARD_TYPE.getId()
        );
    }
}
