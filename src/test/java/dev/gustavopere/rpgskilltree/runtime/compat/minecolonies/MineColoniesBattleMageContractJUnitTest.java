package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageSpellPolicy;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageSpellProfile;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.BattleMageTargetMode;
import java.util.List;
import org.junit.jupiter.api.Test;

final class MineColoniesBattleMageContractJUnitTest {
    @Test
    void exactMineColoniesSnapshotIsTheOnlySupportedBaseline() {
        assertTrue(MineColoniesVersionContract.supports("1.1.1375-1.21.1-snapshot"));
        assertTrue(MineColoniesVersionContract.supports("1.1.1375"));
        assertFalse(MineColoniesVersionContract.supports("1.1.1374-1.21.1-snapshot"));
        assertFalse(MineColoniesVersionContract.supports("1.1.1376-1.21.1-snapshot"));
        assertFalse(MineColoniesVersionContract.supports("unknown"));
        assertFalse(MineColoniesVersionContract.supports(null));
    }

    @Test
    void spellProfilesAreFailClosedAndDeterministicallyOrdered() {
        BattleMageSpellProfile fireball = new BattleMageSpellProfile(
            "irons_spellbooks:fireball",
            BattleMageTargetMode.HOSTILE_ENTITY,
            70,
            4.0,
            28.0,
            4.0,
            false,
            true
        );
        BattleMageSpellProfile ray = new BattleMageSpellProfile(
            "irons_spellbooks:ray_of_siphoning",
            BattleMageTargetMode.HOSTILE_ENTITY,
            70,
            2.0,
            24.0,
            0.0,
            false,
            true
        );

        assertEquals(
            List.of("irons_spellbooks:fireball", "irons_spellbooks:ray_of_siphoning"),
            BattleMageSpellPolicy.orderCandidates(List.of(ray, fireball)).stream()
                .map(BattleMageSpellProfile::spellId)
                .toList()
        );
        assertTrue(BattleMageSpellPolicy.isSupported(fireball));
        assertFalse(BattleMageSpellPolicy.isSupported(null));
    }

    @Test
    void unsafeProfilesAreRejectedAtConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new BattleMageSpellProfile(
            "bad id",
            BattleMageTargetMode.HOSTILE_AREA,
            10,
            12.0,
            4.0,
            -1.0,
            false,
            false
        ));
    }

    @Test
    void friendlyFireBlocksUnsafeAreaSpell() {
        BattleMageSpellProfile area = new BattleMageSpellProfile(
            "irons_spellbooks:fire_breath",
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            2.0,
            12.0,
            5.0,
            false,
            false
        );

        assertFalse(BattleMageSpellPolicy.isAreaSafe(area, true));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(area, false));
    }
}
