package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** Frozen formulas and P-0031 body coupling for A0081-A0090. */
public final class FrozenA0081A0090PolicyTest {
    public static void main(String[] args) {
        coefficientsAreFrozenAndUniversalIsOnlyFallback();
        periodicProvidersRequireExplicitClassificationAndPersistentOrigin();
        periodicPulseIdentityDeduplicatesMultiTargetCallbacks();
        bloodThirstCannotStartOrRemainWithoutBodyCosts();
        vitalityAttributesAreRelativeAndBounded();
        System.out.println("FrozenA0081A0090PolicyTest: PASS");
    }

    private static void coefficientsAreFrozenAndUniversalIsOnlyFallback() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0082", 3, "A0083", 3, "A0084", 3, "A0085", 3, "A0086", 1));
        require(close(FrozenSustainPolicy.weaponCoefficient(ranks), 0.018D), "A0082");
        require(close(FrozenSustainPolicy.magicCoefficient(ranks), 0.018D), "A0083");
        require(close(FrozenSustainPolicy.elementalCoefficient(ranks), 0.015D), "A0084");
        require(close(FrozenSustainPolicy.periodicCoefficient(ranks), 0.0105D), "A0085");
        require(close(FrozenSustainPolicy.coefficientFor(ranks, true, true, false, false), 0.018D), "specialized maximum wins");
        require(close(FrozenSustainPolicy.coefficientFor(FrozenCombatPerkRanks.of(Map.of("A0086", 1)), true, false, false, false), 0.01D), "universal fills uncovered source");
    }

    private static void periodicProvidersRequireExplicitClassificationAndPersistentOrigin() {
        require(FrozenPeriodicProviderPolicy.classify(
            "io.redspace.ironsspellbooks.damage.SpellDamageSource",
            "net.minecraft.server.level.ServerPlayer",
            "io.redspace.ironsspellbooks.spells.blood.RayOfSiphoningSpell",
            false,
            true
        ) == FrozenPeriodicProviderPolicy.Classification.IRONS_RAY_OF_SIPHONING,
            "Iron's continuous siphon is explicitly periodic");
        require(FrozenPeriodicProviderPolicy.classify(
            "io.redspace.ironsspellbooks.damage.SpellDamageSource",
            "net.minecraft.server.level.ServerPlayer",
            "io.redspace.ironsspellbooks.spells.blood.BloodSlashSpell",
            false,
            true
        ) == FrozenPeriodicProviderPolicy.Classification.NONE,
            "blood school alone never implies periodic damage");
        require(FrozenPeriodicProviderPolicy.classify(
            "net.minecraft.world.damagesource.DamageSource",
            "com.Polarice3.Goety.common.entities.projectiles.AcidPool",
            "",
            false,
            true
        ) == FrozenPeriodicProviderPolicy.Classification.GOETY_ACID_POOL,
            "Goety AcidPool carries application ownership into every pulse");
        require(FrozenPeriodicProviderPolicy.classify(
            "net.minecraft.world.damagesource.DamageSource",
            "",
            "",
            true,
            false
        ) == FrozenPeriodicProviderPolicy.Classification.NONE,
            "configured tag without persistent origin fails closed");
    }

    private static void periodicPulseIdentityDeduplicatesMultiTargetCallbacks() {
        var firstTarget = CanonicalPeriodicPulseIdentity.forPulse(
            "player", "goety_acid_pool", "pool-uuid", 40L);
        var secondTarget = CanonicalPeriodicPulseIdentity.forPulse(
            "player", "goety_acid_pool", "pool-uuid", 40L);
        require(firstTarget.sameAction(secondTarget),
            "one application pulse hitting several targets has one action identity");
        require(!firstTarget.sameAction(CanonicalPeriodicPulseIdentity.forPulse(
            "player", "goety_acid_pool", "pool-uuid", 41L)),
            "the next pulse is a new action");
        require(!firstTarget.sameAction(CanonicalPeriodicPulseIdentity.forPulse(
            "player", "goety_acid_pool", "other-pool", 40L)),
            "different applications remain independent");
    }

    private static void bloodThirstCannotStartOrRemainWithoutBodyCosts() {
        var absent = new BloodThirstService(new CanonicalBodyTradeoffService(null));
        absent.recordHostileDamage("player", 30.0D, 100.0D, true, 0L);
        require(!absent.active("player", 1L), "provider absent: no minimum lifesteal or healing bonus");
        var provider = new ToggleProvider();
        var service = new BloodThirstService(new CanonicalBodyTradeoffService(provider));
        service.recordHostileDamage("player", 10, 100, true, 0L);
        service.recordHostileDamage("player", 15, 100, true, 100L);
        require(service.active("player", 101L), "25% hostile loss in six seconds activates coupled lease");
        require(close(provider.acquired.hydrationMultiplier(), 0.15D), "provider receives the inseparable hydration cost");
        require(close(service.weaponMinimumCoefficient("player", 101L), 0.03D) && close(service.healingMultiplier("player", 101L), 1.08D), "benefits while costs active");
        provider.available = false;
        require(!service.active("player", 102L) && close(service.weaponMinimumCoefficient("player", 102L), 0.0D), "provider loss ends all benefits");
    }

    private static void vitalityAttributesAreRelativeAndBounded() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0088", 5, "A0089", 5, "A0090", 5));
        require(close(FrozenVitalityAttributePolicy.maxHealthMultiplier(ranks), 1.10D), "A0088 +10%");
        require(close(FrozenVitalityAttributePolicy.armorMultiplier(ranks), 1.10D), "A0089 +10% relative");
        require(close(FrozenVitalityAttributePolicy.toughnessMultiplier(ranks), 1.10D), "A0090 +10% relative");
        require(close(FrozenVitalityAttributePolicy.applyRelative(0.0D, 1.10D), 0.0D), "zero base remains zero");
    }

    private static final class ToggleProvider implements CanonicalBodyTradeoffService.Provider {
        boolean available = true;
        CanonicalBodyTradeoffService.LeaseRequest acquired;
        public boolean acquire(String actor, CanonicalBodyTradeoffService.LeaseRequest request) {
            acquired = request;
            return available;
        }
        public boolean maintain(String actor, CanonicalBodyTradeoffService.LeaseRequest request) { return available; }
        public void release(String actor, CanonicalBodyTradeoffService.LeaseRequest request) {}
    }
    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
