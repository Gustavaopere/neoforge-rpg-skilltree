package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/** Frozen P-0017 contract: A0023 may activate only from real target-facing server geometry. */
public final class FrozenA0023PositionContractTest {
    public static void main(String[] args) throws Exception {
        realTargetFacingClassifiesOnlyFlankOrBack();
        missingOrientationFailsClosed();
        untrustedHeuristicsCannotActivateBlindSpot();
        providerAdapterUsesTargetLookAtHitInstant();
        System.out.println("FrozenA0023PositionContractTest: PASS");
    }

    private static void realTargetFacingClassifiesOnlyFlankOrBack() {
        require(!CombatPositionPolicy.isFlankOrBack(0, 2, 0, 0, 0, 1),
            "attacker in front of a target looking +Z is not flank/back");
        require(CombatPositionPolicy.isFlankOrBack(2, 0, 0, 0, 0, 1),
            "attacker at the target side is a proved flank");
        require(CombatPositionPolicy.isFlankOrBack(0, -2, 0, 0, 0, 1),
            "attacker behind the target is a proved back attack");
    }

    private static void missingOrientationFailsClosed() {
        require(!CombatPositionPolicy.isFlankOrBack(2, 0, 0, 0, 0, 0),
            "zero/absent horizontal target orientation cannot prove flank/back");
        require(!CombatPositionPolicy.isFlankOrBack(0, 0, 0, 0, 0, 1),
            "overlapping positions cannot prove a relative attack side");
    }

    private static void untrustedHeuristicsCannotActivateBlindSpot() {
        var ranks = CombatPerkRanks.of(Map.of("A0023", 2));

        for (int i = 0; i < 5; i++) {
            var state = new NotionCombatPerkState();
            state.addFlow("p", 2, 1_000L, 7_000L);
            CombatPerkAttackPolicy.AttackContext unproved = new CombatPerkAttackPolicy.AttackContext(
                CanonicalActionIdentity.root("p", "unproved-" + i, "test"),
                "p", "mob", WeaponFamily.DAGGER,
                true, true,
                i == 0,
                i == 1,
                i == 2,
                i == 3,
                false,
                i == 4,
                i == 4 ? 0.01D : 1.0D,
                true,
                999.0D,
                2_000L + i
            );
            CombatPerkAttackPolicy.HitModifiers result = CombatPerkAttackPolicy.beforeHit(unproved, ranks, state);
            require(state.flow("p", 2_000L + i) == 2,
                "defense/heavy/range/advance/damage facts cannot substitute missing flank/back proof");
            require(close(result.armorNegationPoints(), 0.0D),
                "unproved positional hit receives no A0023 penetration");
            require(close(result.damageMultiplier(), 1.0D),
                "critical/damage facts cannot fabricate A0023 critical multiplier");
        }

        var proved = new NotionCombatPerkState();
        proved.addFlow("p", 2, 1_000L, 7_000L);
        CombatPerkAttackPolicy.AttackContext flank = new CombatPerkAttackPolicy.AttackContext(
            CanonicalActionIdentity.root("p", "proved-flank", "test"),
            "p", "mob", WeaponFamily.DAGGER,
            true, true, false, false, false, false, true, false,
            1.0D, true, 0.0D, 2_100L
        );
        CombatPerkAttackPolicy.HitModifiers result = CombatPerkAttackPolicy.beforeHit(flank, ranks, proved);
        require(proved.flow("p", 2_100L) == 0, "proved flank consumes exactly two Flow");
        require(close(result.armorNegationPoints(), 10.0D), "proved rank-two flank applies up to ten penetration points");
        require(close(result.damageMultiplier(), 1.25D), "proved critical flank applies rank-two critical multiplier");
    }

    private static void providerAdapterUsesTargetLookAtHitInstant() throws IOException {
        String source = Files.readString(findRepoFile(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightCombatPerkHooks.java"));
        int start = source.indexOf("var targetLook = target.getLookAngle();");
        require(start >= 0, "Epic Fight adapter must read the struck target's server-side look vector");
        int end = source.indexOf("return new CombatPerkAttackPolicy.AttackContext", start);
        require(end > start, "provider context must pass positional result into AttackContext");
        String positionalBlock = source.substring(start, end);
        require(positionalBlock.contains("boolean flankOrBack = family == WeaponFamily.DAGGER"),
            "A0023 position classification is dagger-specific");
        require(positionalBlock.contains("CombatPositionPolicy.isFlankOrBack("),
            "provider delegates classification to canonical server geometry");
        require(positionalBlock.contains("player.getX()") && positionalBlock.contains("player.getZ()"),
            "provider uses attacker server position at the hit context");
        require(positionalBlock.contains("target.getX()") && positionalBlock.contains("target.getZ()"),
            "provider uses target server position at the hit context");
        require(positionalBlock.contains("targetLook.x") && positionalBlock.contains("targetLook.z"),
            "provider passes target orientation, not attacker camera orientation");
        require(!positionalBlock.contains("player.getLookAngle()"),
            "attacker camera/look is forbidden as an A0023 substitute");
        String classification = positionalBlock.substring(positionalBlock.indexOf("boolean flankOrBack"));
        require(!classification.contains("modifiedDamage") && !classification.contains("baseDamage")
                && !classification.contains("lastAngle") && !classification.contains("previousAngle"),
            "damage or inter-hit angular heuristics cannot enter A0023 classification");
    }

    private static Path findRepoFile(String relative) {
        Path cursor = Path.of("").toAbsolutePath().normalize();
        while (cursor != null) {
            Path candidate = cursor.resolve(relative);
            if (Files.isRegularFile(candidate)) return candidate;
            cursor = cursor.getParent();
        }
        throw new AssertionError("repository file not found: " + relative);
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
