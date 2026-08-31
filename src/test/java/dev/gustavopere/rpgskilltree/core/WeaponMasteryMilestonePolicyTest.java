package dev.gustavopere.rpgskilltree.core;

import java.util.List;

public final class WeaponMasteryMilestonePolicyTest {
    private WeaponMasteryMilestonePolicyTest() {}

    public static void main(String[] args) {
        bowHitUsesCanonicalLaneAndPersistentDiscoveryIdentity();
        providerOriginsShareTheSameBowDiscoveryIdentity();
        crossbowPhysicalHitUsesCanonicalLaneAndPersistentDiscoveryIdentity();
        providerOriginsShareTheSameCrossbowDiscoveryIdentity();
        uncorrelatedPhysicalProjectileFailsClosed();
        unsupportedPhysicalProjectileCategoryFailsClosed();
        invalidDamageFailsClosed();
        System.out.println("WeaponMasteryMilestonePolicyTest: PASS");
    }

    private static void bowHitUsesCanonicalLaneAndPersistentDiscoveryIdentity() {
        var milestone = WeaponMasteryMilestonePolicy.confirmedHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            "bow",
            "minecraft:zombie",
            7.5D
        );

        require(
            milestone.discoveryKey().equals("mastery:epicfight:weapon/bow/hostile_type/minecraft:zombie"),
            "BOW discovery identity must match the canonical Epic Fight-compatible ledger key"
        );
        require(milestone.action().provider().equals("minecraft"), "provider provenance must stay minecraft");
        require(milestone.action().weaponCategory().equals("bow"), "weapon category must stay bow");
        require(milestone.action().tags().contains("hit"), "confirmed hit tag missing");
        require(milestone.action().tags().contains("milestone"), "milestone tag missing");
        require(hasAward(milestone.awards(), "epicfight:bow", 10), "canonical epicfight:bow +10 award missing");
        require(hasAward(milestone.awards(), "epicfight:weapon", 5), "shared epicfight:weapon +5 award missing");
    }

    private static void providerOriginsShareTheSameBowDiscoveryIdentity() {
        var vanilla = WeaponMasteryMilestonePolicy.confirmedHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            "bow",
            "minecraft:skeleton",
            4.0D
        );
        var epicFight = WeaponMasteryMilestonePolicy.confirmedHit(
            "epicfight:damage_post",
            "epicfight",
            "bow",
            "minecraft:skeleton",
            4.0D
        );

        require(
            vanilla.discoveryKey().equals(epicFight.discoveryKey()),
            "the same semantic BOW outcome must deduplicate across vanilla and Epic Fight receipts"
        );
        require(!vanilla.action().provider().equals(epicFight.action().provider()), "provider provenance should remain distinct");
    }

    private static void crossbowPhysicalHitUsesCanonicalLaneAndPersistentDiscoveryIdentity() {
        var milestone = WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            "crossbow",
            "minecraft:pillager",
            8.0D,
            true
        );

        require(
            milestone.discoveryKey().equals("mastery:epicfight:weapon/crossbow/hostile_type/minecraft:pillager"),
            "CROSSBOW discovery identity must match the canonical Epic Fight-compatible ledger key"
        );
        require(milestone.action().weaponCategory().equals("crossbow"), "weapon category must stay crossbow");
        require(hasAward(milestone.awards(), "epicfight:crossbow", 10), "canonical epicfight:crossbow +10 award missing");
        require(hasAward(milestone.awards(), "epicfight:weapon", 5), "shared epicfight:weapon +5 award missing");
    }

    private static void providerOriginsShareTheSameCrossbowDiscoveryIdentity() {
        var vanilla = WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            "crossbow",
            "minecraft:witch",
            6.0D,
            true
        );
        var epicFight = WeaponMasteryMilestonePolicy.confirmedHit(
            "epicfight:damage_post",
            "epicfight",
            "crossbow",
            "minecraft:witch",
            6.0D
        );

        require(
            vanilla.discoveryKey().equals(epicFight.discoveryKey()),
            "the same semantic CROSSBOW outcome must deduplicate across vanilla and Epic Fight receipts"
        );
    }

    private static void uncorrelatedPhysicalProjectileFailsClosed() {
        boolean rejected = false;
        try {
            WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(
                "minecraft:projectile_damage_post",
                "minecraft",
                "crossbow",
                "minecraft:pillager",
                6.0D,
                false
            );
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "uncorrelated physical projectiles must not become mastery milestones");
    }

    private static void unsupportedPhysicalProjectileCategoryFailsClosed() {
        boolean rejected = false;
        try {
            WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(
                "minecraft:projectile_damage_post",
                "minecraft",
                "spell_arrow",
                "minecraft:zombie",
                6.0D,
                true
            );
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "unsupported projectile categories must fail closed");
    }

    private static void invalidDamageFailsClosed() {
        boolean rejected = false;
        try {
            WeaponMasteryMilestonePolicy.confirmedHit(
                "minecraft:projectile_damage_post",
                "minecraft",
                "bow",
                "minecraft:zombie",
                0.0D
            );
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "zero-damage attempts must not become mastery milestones");
    }

    private static boolean hasAward(List<MasteryAward> awards, String lane, int experience) {
        return awards.stream().anyMatch(award -> award.laneId().equals(lane) && award.experience() == experience);
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
