package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

final class BattleMageCombatControllerJUnitTest {
    @Test
    void criticalSelfSupportPrecedesHostileSpellsThenUsesPriorityAndBookOrder() {
        BattleMageSpellProfile heal = profile("irons_spellbooks:heal", BattleMageTargetMode.SELF, 20, 0.0, 0.0);
        BattleMageSpellProfile fireball = profile("irons_spellbooks:fireball", BattleMageTargetMode.HOSTILE_AREA, 80, 4.0, 28.0);
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 80, 2.0, 30.0);

        List<BattleMageSpellPolicy.Candidate> ordered = BattleMageSpellPolicy.orderTacticalCandidates(
            List.of(
                new BattleMageSpellPolicy.Candidate(fireball, 1),
                new BattleMageSpellPolicy.Candidate(heal, 2),
                new BattleMageSpellPolicy.Candidate(arrow, 0)
            ),
            true
        );

        assertEquals("irons_spellbooks:heal", ordered.get(0).profile().spellId());
        assertEquals("irons_spellbooks:magic_arrow", ordered.get(1).profile().spellId());
        assertEquals("irons_spellbooks:fireball", ordered.get(2).profile().spellId());
    }

    @Test
    void outsideCriticalStateHostileModesPrecedeSelfUtility() {
        BattleMageSpellProfile heal = profile("irons_spellbooks:heal", BattleMageTargetMode.SELF, 100, 0.0, 0.0);
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 20, 2.0, 30.0);

        List<BattleMageSpellPolicy.Candidate> ordered = BattleMageSpellPolicy.orderTacticalCandidates(
            List.of(
                new BattleMageSpellPolicy.Candidate(heal, 0),
                new BattleMageSpellPolicy.Candidate(arrow, 1)
            ),
            false
        );

        assertEquals("irons_spellbooks:magic_arrow", ordered.get(0).profile().spellId());
        assertEquals("irons_spellbooks:heal", ordered.get(1).profile().spellId());
    }

    @Test
    void rangeAndWorldEffectChecksAreFailClosed() {
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 20, 3.0, 25.0);
        assertFalse(BattleMageSpellPolicy.inRange(arrow, 2.99));
        assertTrue(BattleMageSpellPolicy.inRange(arrow, 3.0));
        assertTrue(BattleMageSpellPolicy.inRange(arrow, 25.0));
        assertFalse(BattleMageSpellPolicy.inRange(arrow, 25.01));

        BattleMageSpellProfile worldEffect = new BattleMageSpellProfile(
            "irons_spellbooks:test_world",
            BattleMageTargetMode.HOSTILE_AREA,
            100,
            0.0,
            20.0,
            5.0,
            true,
            false
        );
        assertFalse(BattleMageSpellPolicy.isRuntimeSupported(worldEffect));
        assertTrue(BattleMageSpellPolicy.isRuntimeSupported(arrow));
    }

    @Test
    void allyTargetModeIsExplicitlyFailClosedUntilItHasAProviderHandler() {
        BattleMageSpellProfile ally = profile(
            "irons_spellbooks:test_ally",
            BattleMageTargetMode.ALLY_ENTITY,
            90,
            0.0,
            18.0
        );

        assertFalse(BattleMageSpellPolicy.isRuntimeSupported(ally));
    }

    @Test
    void protectedAllyBlocksUnsafeHostileAreaBeforeAnyProviderCast() {
        BattleMageSpellProfile unsafeArea = new BattleMageSpellProfile(
            "irons_spellbooks:fireball",
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            6.0,
            false,
            false
        );
        BattleMageSpellProfile allySafeArea = new BattleMageSpellProfile(
            "irons_spellbooks:test_safe_area",
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            6.0,
            false,
            true
        );

        assertFalse(BattleMageSpellPolicy.isAreaSafe(unsafeArea, true));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(unsafeArea, false));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(allySafeArea, true));
    }

    private static BattleMageSpellProfile profile(
        String id,
        BattleMageTargetMode mode,
        int priority,
        double minRange,
        double maxRange
    ) {
        return new BattleMageSpellProfile(id, mode, priority, minRange, maxRange, 0.0, false, false);
    }
}
