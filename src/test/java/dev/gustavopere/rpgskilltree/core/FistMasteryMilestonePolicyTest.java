package dev.gustavopere.rpgskilltree.core;

import java.util.List;

public final class FistMasteryMilestonePolicyTest {
    private FistMasteryMilestonePolicyTest() {}

    public static void main(String[] args) {
        fistHitUsesCanonicalCombatLedger();
        knuckleAndFistShareDiscoveryIdentity();
        unsupportedCategoryFailsClosed();
        invalidDamageFailsClosed();
        System.out.println("FistMasteryMilestonePolicyTest: PASS");
    }

    private static void fistHitUsesCanonicalCombatLedger() {
        var milestone = FistMasteryMilestonePolicy.confirmedHit(
            "epicfight:damage_post",
            "epicfight",
            "fist",
            "minecraft:zombie",
            5.0D
        );

        require(
            milestone.discoveryKey().equals("mastery:combat:fist/hostile_type/minecraft:zombie"),
            "FIST discovery identity must use the canonical combat:fist ledger"
        );
        require(milestone.action().weaponCategory().equals("fist"), "canonical weapon category must be fist");
        require(hasAward(milestone.awards(), "combat:fist", 10), "canonical combat:fist +10 award missing");
        require(hasAward(milestone.awards(), "epicfight:weapon", 5), "shared epicfight:weapon +5 award missing");
        require(!hasLane(milestone.awards(), "epicfight:fist"), "epicfight:fist must not become a parallel gate ledger");
    }

    private static void knuckleAndFistShareDiscoveryIdentity() {
        var fist = FistMasteryMilestonePolicy.confirmedHit(
            "epicfight:damage_post",
            "epicfight",
            "fist",
            "minecraft:skeleton",
            4.0D
        );
        var knuckle = FistMasteryMilestonePolicy.confirmedHit(
            "epicfight:damage_post",
            "epicfight",
            "knuckle",
            "minecraft:skeleton",
            4.0D
        );

        require(
            fist.discoveryKey().equals(knuckle.discoveryKey()),
            "fist and knuckle must deduplicate as the same semantic weapon family"
        );
        require(knuckle.action().weaponCategory().equals("fist"), "knuckle must normalize to canonical fist category");
        require(!hasLane(knuckle.awards(), "epicfight:knuckle"), "epicfight:knuckle must not become a parallel gate ledger");
    }

    private static void unsupportedCategoryFailsClosed() {
        boolean rejected = false;
        try {
            FistMasteryMilestonePolicy.confirmedHit(
                "epicfight:damage_post",
                "epicfight",
                "dagger",
                "minecraft:zombie",
                4.0D
            );
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "non-fist categories must fail closed");
    }

    private static void invalidDamageFailsClosed() {
        boolean rejected = false;
        try {
            FistMasteryMilestonePolicy.confirmedHit(
                "epicfight:damage_post",
                "epicfight",
                "fist",
                "minecraft:zombie",
                0.0D
            );
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "zero-damage fist attempts must not become mastery milestones");
    }

    private static boolean hasAward(List<MasteryAward> awards, String lane, int experience) {
        return awards.stream().anyMatch(award -> award.laneId().equals(lane) && award.experience() == experience);
    }

    private static boolean hasLane(List<MasteryAward> awards, String lane) {
        return awards.stream().anyMatch(award -> award.laneId().equals(lane));
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
