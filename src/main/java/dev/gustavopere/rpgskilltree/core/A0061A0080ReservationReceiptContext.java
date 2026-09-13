package dev.gustavopere.rpgskilltree.core;

/**
 * Per-thread handoff from the canonical physical PRE policy to the projectile correlation bridge.
 * The context carries no irreversible state; it only identifies which exact root actually reserved
 * which A0073/A0074/A0080 transition during the current damage event.
 */
public final class A0061A0080ReservationReceiptContext {
    public record PendingHitReceipt(
        String rootActionId,
        boolean executionReserved,
        boolean executionArmCandidate,
        A0061A0080CombatState.FirstBloodReservation firstBloodReservation,
        boolean firstBloodTracked,
        boolean opportunityReserved
    ) {}

    private static final ThreadLocal<MutableReceipt> CURRENT = new ThreadLocal<>();

    private A0061A0080ReservationReceiptContext() {}

    public static void begin(String actorId, String targetId, String rootActionId) {
        MutableReceipt current = CURRENT.get();
        if (current == null
            || !current.actorId.equals(actorId)
            || !current.rootActionId.equals(rootActionId)
            || (targetId != null && current.targetId != null && !current.targetId.equals(targetId))) {
            CURRENT.set(new MutableReceipt(actorId, targetId, rootActionId));
            return;
        }
        if (current.targetId == null && targetId != null) current.targetId = targetId;
    }

    public static void markExecutionReserved() {
        MutableReceipt current = CURRENT.get();
        if (current != null) current.executionReserved = true;
    }

    public static void markExecutionArmCandidate() {
        MutableReceipt current = CURRENT.get();
        if (current != null) current.executionArmCandidate = true;
    }

    public static void markFirstBlood(A0061A0080CombatState.FirstBloodReservation reservation) {
        MutableReceipt current = CURRENT.get();
        if (current != null) {
            current.firstBloodTracked = true;
            current.firstBloodReservation = reservation;
        }
    }

    public static void markOpportunityReserved() {
        MutableReceipt current = CURRENT.get();
        if (current != null) current.opportunityReserved = true;
    }

    /** Consumes only a receipt produced on this thread for this actor and target. */
    public static PendingHitReceipt take(String actorId, String targetId) {
        MutableReceipt current = CURRENT.get();
        CURRENT.remove();
        if (current == null || !current.actorId.equals(actorId)) return null;
        if (current.targetId != null && !current.targetId.equals(targetId)) return null;
        if (!current.executionReserved
            && !current.executionArmCandidate
            && !current.firstBloodTracked
            && !current.opportunityReserved) return null;
        return new PendingHitReceipt(
            current.rootActionId,
            current.executionReserved,
            current.executionArmCandidate,
            current.firstBloodReservation,
            current.firstBloodTracked,
            current.opportunityReserved
        );
    }

    public static void clear() {
        CURRENT.remove();
    }

    private static final class MutableReceipt {
        final String actorId;
        String targetId;
        final String rootActionId;
        boolean executionReserved;
        boolean executionArmCandidate;
        A0061A0080CombatState.FirstBloodReservation firstBloodReservation = A0061A0080CombatState.FirstBloodReservation.NONE;
        boolean firstBloodTracked;
        boolean opportunityReserved;

        MutableReceipt(String actorId, String targetId, String rootActionId) {
            this.actorId = actorId;
            this.targetId = targetId;
            this.rootActionId = rootActionId;
        }
    }
}
