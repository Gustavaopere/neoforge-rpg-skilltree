package dev.gustavopere.rpgskilltree.core;

public final class CanonicalTargetDebuffServiceTest {
    public static void main(String[] args) {
        desyncIsTargetSideTemporaryAndDoesNotStackAcrossSources();
        bossHalfEffectIsPreserved();
        sourceAndTargetLifecycleCleanupAreIndependent();
        System.out.println("CanonicalTargetDebuffServiceTest: PASS");
    }

    private static void desyncIsTargetSideTemporaryAndDoesNotStackAcrossSources() {
        var service = new CanonicalTargetDebuffService();
        long now = 1_000L;
        service.applyDesync("p1", "mob", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.92D, 0.90D, now + 3_000L));

        var active = service.desync("mob", now + 2_999L).orElseThrow();
        require(close(active.outgoingPhysicalDamageMultiplier(), 0.92D), "A0036 reduces only physical output by 8%");
        require(close(active.movementSpeedMultiplier(), 0.90D), "A0036 reduces movement by 10%");
        require(service.desync("mob", now + 3_000L).isEmpty(), "A0036 expires exactly after three seconds");

        service.applyDesync("p1", "mob", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.92D, 0.90D, now + 3_000L));
        service.applyDesync("p2", "mob", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.92D, 0.90D, now + 3_500L));
        var multiSource = service.desync("mob", now + 2_000L).orElseThrow();
        require(close(multiSource.outgoingPhysicalDamageMultiplier(), 0.92D), "multiple players cannot stack A0036 damage penalties");
        require(close(multiSource.movementSpeedMultiplier(), 0.90D), "multiple players cannot stack A0036 movement penalties");
        require(multiSource.expiresAtMillis() == now + 3_500L, "same debuff can remain while any legitimate source is active");
    }

    private static void bossHalfEffectIsPreserved() {
        var service = new CanonicalTargetDebuffService();
        service.applyDesync("p", "boss", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.96D, 0.95D, 4_000L));
        var boss = service.desync("boss", 1_000L).orElseThrow();
        require(close(boss.outgoingPhysicalDamageMultiplier(), 0.96D), "boss receives half physical penalty");
        require(close(boss.movementSpeedMultiplier(), 0.95D), "boss receives half movement penalty");
    }

    private static void sourceAndTargetLifecycleCleanupAreIndependent() {
        var service = new CanonicalTargetDebuffService();
        service.applyDesync("p1", "mob", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.92D, 0.90D, 4_000L));
        service.applyDesync("p2", "mob", new CombatPerkFinalizationPolicy.BoneBreakerEffect(0.92D, 0.90D, 5_000L));
        service.clearSource("p1");
        require(service.desync("mob", 2_000L).isPresent(), "clearing one source preserves another source's live debuff");
        service.clearTarget("mob");
        require(service.desync("mob", 2_000L).isEmpty(), "target recreation/logout clears target-side transient debuff");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
