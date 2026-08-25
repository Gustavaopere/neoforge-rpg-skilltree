package dev.gustavopere.rpgskilltree.core;

/** A0081 delayed reserve is independent from sustain and subtracts only confirmed healing. */
public final class CombatRecoveryServiceTest {
    public static void main(String[] args) {
        storesPostMitigationDamageOnlyDuringRhythm();
        freezesSnapshotAndConfirmsFourBoundedInstallments();
        hostileDamageInterruptsAndOutOfCombatReserveExpires();
        System.out.println("CombatRecoveryServiceTest: PASS");
    }

    private static void storesPostMitigationDamageOnlyDuringRhythm() {
        var service = new CombatRecoveryService();
        require(close(service.recordDamage(request("hit", 100, 30, 3, false), 0L), 0.0D), "rhythm required");
        require(close(service.recordDamage(request("hit2", 100, 30, 3, true), 1L), 7.5D), "rank 3 stores 25% actual damage");
        require(close(service.reserve("player", 1L), 7.5D), "reserve stored");
        require(close(service.recordDamage(request("overkill", 100, 100, 3, true, 2.0D), 2L), 0.5D), "overkill excluded and reserve capped at 8% max health");
        require(close(service.reserve("player", 2L), 8.0D), "8% maximum reserve");
    }

    private static void freezesSnapshotAndConfirmsFourBoundedInstallments() {
        var service = new CombatRecoveryService();
        service.recordDamage(request("hit", 100, 32, 3, true), 0L);
        service.recordHostileDamage("player", true, 0L);
        require(service.offerInstallment("player", 100, 20, 2_999L).isEmpty(), "wait three seconds without damage");
        var first = service.offerInstallment("player", 100, 20, 3_000L).orElseThrow();
        require(close(first.attemptedHealing(), 2.0D), "25% frozen snapshot per parcel");
        service.confirmHealed(first, 1.5D);
        require(close(service.reserve("player", 3_000L), 6.5D), "subtract actual heal only");
        for (int i = 1; i < 4; i++) {
            var parcel = service.offerInstallment("player", 100, 20, 3_000L + i * 1_000L).orElseThrow();
            service.confirmHealed(parcel, parcel.attemptedHealing());
        }
        require(service.offerInstallment("player", 100, 20, 7_000L).isEmpty(), "maximum four parcels from snapshot");
    }

    private static void hostileDamageInterruptsAndOutOfCombatReserveExpires() {
        var service = new CombatRecoveryService();
        service.recordDamage(request("hit", 100, 20, 1, true), 0L);
        service.recordHostileDamage("player", true, 0L);
        require(service.offerInstallment("player", 100, 20, 3_000L).isPresent(), "phase starts");
        service.recordHostileDamage("player", true, 3_001L);
        require(service.offerInstallment("player", 100, 20, 3_002L).isEmpty(), "new hostile damage interrupts phase");
        require(close(service.reserve("player", 13_002L), 0.0D), "reserve expires after ten seconds out of combat");
    }

    private static CombatRecoveryService.DamageRequest request(String id, double maxHealth, double damage, int rank, boolean rhythm) {
        return request(id, maxHealth, damage, rank, rhythm, damage);
    }
    private static CombatRecoveryService.DamageRequest request(String id, double maxHealth, double damage, int rank, boolean rhythm, double targetHealthBefore) {
        return new CombatRecoveryService.DamageRequest(root(id), true, true, true, true, rhythm, maxHealth, damage, targetHealthBefore, rank);
    }
    private static CanonicalActionIdentity root(String id) { return CanonicalActionIdentity.root("player", id, "test"); }
    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
