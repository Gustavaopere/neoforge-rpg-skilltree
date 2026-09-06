package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
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

        assertEquals("irons_spellbooks:heal", ordered.get(0).profile().spellId().toString());
        assertEquals("irons_spellbooks:magic_arrow", ordered.get(1).profile().spellId().toString());
        assertEquals("irons_spellbooks:fireball", ordered.get(2).profile().spellId().toString());
    }

    @Test
    void outsideCriticalStateHostileModesPrecedeUnsupportedAllyAndSelfUtility() {
        BattleMageSpellProfile heal = profile("irons_spellbooks:heal", BattleMageTargetMode.SELF, 100, 0.0, 0.0);
        BattleMageSpellProfile ally = profile("irons_spellbooks:fortify", BattleMageTargetMode.ALLY_ENTITY, 100, 0.0, 18.0);
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 20, 2.0, 30.0);

        List<BattleMageSpellPolicy.Candidate> ordered = BattleMageSpellPolicy.orderTacticalCandidates(
            List.of(
                new BattleMageSpellPolicy.Candidate(heal, 0),
                new BattleMageSpellPolicy.Candidate(ally, 2),
                new BattleMageSpellPolicy.Candidate(arrow, 1)
            ),
            false
        );

        assertEquals("irons_spellbooks:magic_arrow", ordered.get(0).profile().spellId().toString());
        assertEquals("irons_spellbooks:fortify", ordered.get(1).profile().spellId().toString());
        assertEquals("irons_spellbooks:heal", ordered.get(2).profile().spellId().toString());
    }

    @Test
    void exactTiesUseBookOrderThenSpellIdAndNullCandidatesAreIgnored() {
        BattleMageSpellProfile alpha = profile("irons_spellbooks:alpha", BattleMageTargetMode.HOSTILE_ENTITY, 50, 0.0, 20.0);
        BattleMageSpellProfile beta = profile("irons_spellbooks:beta", BattleMageTargetMode.HOSTILE_ENTITY, 50, 0.0, 20.0);

        List<BattleMageSpellPolicy.Candidate> ordered = BattleMageSpellPolicy.orderTacticalCandidates(
            java.util.Arrays.asList(
                new BattleMageSpellPolicy.Candidate(beta, 4),
                null,
                new BattleMageSpellPolicy.Candidate(alpha, 4),
                new BattleMageSpellPolicy.Candidate(beta, 1)
            ),
            false
        );

        assertEquals(3, ordered.size());
        assertEquals(1, ordered.get(0).bookIndex());
        assertEquals("irons_spellbooks:alpha", ordered.get(1).profile().spellId().toString());
        assertEquals("irons_spellbooks:beta", ordered.get(2).profile().spellId().toString());
    }

    @Test
    void candidateAndOrderingContractsRejectInvalidInputs() {
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 20, 2.0, 30.0);
        assertThrows(NullPointerException.class, () -> new BattleMageSpellPolicy.Candidate(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleMageSpellPolicy.Candidate(arrow, -1));
        assertThrows(NullPointerException.class, () -> BattleMageSpellPolicy.orderTacticalCandidates(null, false));
    }

    @Test
    void rangeAndRuntimeSupportChecksAreFailClosed() {
        BattleMageSpellProfile arrow = profile("irons_spellbooks:magic_arrow", BattleMageTargetMode.HOSTILE_ENTITY, 20, 3.0, 25.0);
        assertFalse(BattleMageSpellPolicy.inRange(null, 3.0));
        assertFalse(BattleMageSpellPolicy.inRange(arrow, Double.NaN));
        assertFalse(BattleMageSpellPolicy.inRange(arrow, Double.POSITIVE_INFINITY));
        assertFalse(BattleMageSpellPolicy.inRange(arrow, 2.99));
        assertTrue(BattleMageSpellPolicy.inRange(arrow, 3.0));
        assertTrue(BattleMageSpellPolicy.inRange(arrow, 25.0));
        assertFalse(BattleMageSpellPolicy.inRange(arrow, 25.01));

        BattleMageSpellProfile worldEffect = new BattleMageSpellProfile(
            id("irons_spellbooks:test_world"),
            BattleMageTargetMode.HOSTILE_AREA,
            100,
            0.0,
            20.0,
            5.0,
            true,
            false
        );
        BattleMageSpellProfile self = profile("irons_spellbooks:heal", BattleMageTargetMode.SELF, 20, 0.0, 0.0);
        BattleMageSpellProfile area = profile("irons_spellbooks:fireball", BattleMageTargetMode.HOSTILE_AREA, 20, 0.0, 20.0);
        BattleMageSpellProfile ally = profile("irons_spellbooks:fortify", BattleMageTargetMode.ALLY_ENTITY, 20, 0.0, 20.0);

        assertFalse(BattleMageSpellPolicy.isSupported(null));
        assertTrue(BattleMageSpellPolicy.isSupported(arrow));
        assertFalse(BattleMageSpellPolicy.isRuntimeSupported(null));
        assertFalse(BattleMageSpellPolicy.isRuntimeSupported(worldEffect));
        assertTrue(BattleMageSpellPolicy.isRuntimeSupported(self));
        assertTrue(BattleMageSpellPolicy.isRuntimeSupported(arrow));
        assertTrue(BattleMageSpellPolicy.isRuntimeSupported(area));
        assertFalse(BattleMageSpellPolicy.isRuntimeSupported(ally));
    }

    @Test
    void areaSafetyCoversNonAreaAllySafeUnknownFootprintAndProtectedAllyBranches() {
        BattleMageSpellProfile self = profile("irons_spellbooks:heal", BattleMageTargetMode.SELF, 20, 0.0, 0.0);
        BattleMageSpellProfile unsafeArea = new BattleMageSpellProfile(
            id("irons_spellbooks:fireball"),
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            6.0,
            false,
            false
        );
        BattleMageSpellProfile unknownFootprint = new BattleMageSpellProfile(
            id("irons_spellbooks:test_unknown_area"),
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            0.0,
            false,
            false
        );
        BattleMageSpellProfile allySafeArea = new BattleMageSpellProfile(
            id("irons_spellbooks:test_safe_area"),
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            6.0,
            false,
            true
        );

        assertFalse(BattleMageSpellPolicy.isAreaSafe(null, false));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(self, true));
        assertFalse(BattleMageSpellPolicy.isAreaSafe(unsafeArea, true));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(unsafeArea, false));
        assertFalse(BattleMageSpellPolicy.isAreaSafe(unknownFootprint, true));
        assertFalse(BattleMageSpellPolicy.isAreaSafe(unknownFootprint, false));
        assertTrue(BattleMageSpellPolicy.isAreaSafe(allySafeArea, true));
    }

    @Test
    void configuredFriendlyFireRadiusFailsClosedWithoutProviderRuntime() {
        BattleMageSpellProfile unsafeArea = new BattleMageSpellProfile(
            id("irons_spellbooks:fireball"),
            BattleMageTargetMode.HOSTILE_AREA,
            80,
            4.0,
            28.0,
            6.0,
            false,
            false
        );

        assertEquals(Double.POSITIVE_INFINITY, BattleMageSpellPolicy.configuredFriendlyFireRadius(null));
        assertEquals(6.0, BattleMageSpellPolicy.configuredFriendlyFireRadius(unsafeArea));
    }

    private static BattleMageSpellProfile profile(
        String id,
        BattleMageTargetMode mode,
        int priority,
        double minRange,
        double maxRange
    ) {
        return new BattleMageSpellProfile(id(id), mode, priority, minRange, maxRange, 0.0, false, false);
    }

    private static ResourceLocation id(String raw) {
        return BattleMageSpellProfile.parseNamespacedId(raw);
    }
}
