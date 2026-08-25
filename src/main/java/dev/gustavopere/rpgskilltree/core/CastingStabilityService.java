package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A0150 exact receipt claim and atomic extra-debit/cast-extension conversion. */
public final class CastingStabilityService {
    private static final long COOLDOWN_TICKS = 200L;
    private static final double EPSILON = 0.000000001D;
    private final int maxPlayers;
    private final ResourceDebitReceiptService receipts;
    private final Map<String, Long> cooldownUntil = new HashMap<>();

    public CastingStabilityService(int maxPlayers, ResourceDebitReceiptService receipts) {
        if (maxPlayers <= 0) throw new IllegalArgumentException("maxPlayers must be positive");
        this.maxPlayers = maxPlayers;
        this.receipts = Objects.requireNonNull(receipts);
    }

    public synchronized Resolution convert(
        Request request,
        AtomicConversionAdapter adapter,
        int rank,
        long nowTick
    ) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(adapter);
        cooldownUntil.entrySet().removeIf(entry -> entry.getValue() <= nowTick);
        if (rank <= 0 || !eligible(request)
            || cooldownUntil.getOrDefault(request.playerId(), 0L) > nowTick) return Resolution.unchanged(request);
        if (!cooldownUntil.containsKey(request.playerId()) && cooldownUntil.size() >= maxPlayers) {
            return Resolution.unchanged(request);
        }
        ResourceDebitReceipt receipt = receipts.peek(
            request.action(), ResourceDebitReceipt.Kind.MANA, nowTick).orElse(null);
        if (receipt == null) return Resolution.unchanged(request);
        double extra = quantizeUp(receipt.amountPaid() * 0.08D, receipt.minimumDebitUnit());
        if (request.currentManaBalance() + EPSILON < extra) return Resolution.unchanged(request);
        double adjustedTime = request.remainingCastTime() * 1.20D;
        boolean claimed = receipts.claimIf(request.action(), ResourceDebitReceipt.Kind.MANA, nowTick,
            exact -> exact == receipt && adapter.commitAtomically(
                exact.providerResourceId(), extra, adjustedTime)).isPresent();
        if (!claimed) return Resolution.unchanged(request);
        cooldownUntil.put(request.playerId(), nowTick + COOLDOWN_TICKS);
        return new Resolution(true, adjustedTime, extra);
    }

    public synchronized void clearTransient(String playerId) {
        Objects.requireNonNull(playerId);
    }

    public static double quantizeUp(double amount, double minimumUnit) {
        if (!Double.isFinite(amount) || amount <= 0.0D
            || !Double.isFinite(minimumUnit) || minimumUnit <= 0.0D) {
            throw new IllegalArgumentException("quantization inputs must be positive");
        }
        double units = Math.ceil(amount / minimumUnit - EPSILON);
        return Math.max(minimumUnit, units * minimumUnit);
    }

    private static boolean eligible(Request request) {
        if (!request.realInterruption() || request.unavoidable()
            || !request.realPlayerOwner()
            || !request.action().actorId().equals(request.playerId())
            || !ProcGuard.mayTriggerSecondaryEffect(request.action().origin())) return false;
        if (request.tfcPresent() && (request.tfcThirst() < 20.0D || request.foodLevel() == 0)) return false;
        if (request.thermalProviderActive()
            && (!request.thermalSevereAdapterPresent() || request.severeThermalState())) return false;
        return true;
    }

    @FunctionalInterface
    public interface AtomicConversionAdapter {
        boolean commitAtomically(String providerResourceId, double extraDebit, double adjustedRemainingTime);
    }

    public record Request(
        String playerId,
        CanonicalActionIdentity action,
        double remainingCastTime,
        boolean realInterruption,
        boolean unavoidable,
        boolean realPlayerOwner,
        boolean tfcPresent,
        double tfcThirst,
        int foodLevel,
        boolean thermalProviderActive,
        boolean thermalSevereAdapterPresent,
        boolean severeThermalState,
        double currentManaBalance
    ) {
        public Request {
            Objects.requireNonNull(playerId); Objects.requireNonNull(action);
            if (playerId.isBlank()) throw new IllegalArgumentException("playerId must not be blank");
            if (!Double.isFinite(remainingCastTime) || remainingCastTime < 0.0D
                || !Double.isFinite(tfcThirst) || tfcThirst < 0.0D
                || foodLevel < 0 || !Double.isFinite(currentManaBalance) || currentManaBalance < 0.0D) {
                throw new IllegalArgumentException("invalid cast stability request");
            }
        }
    }

    public record Resolution(boolean converted, double adjustedRemainingTime, double extraDebit) {
        private static Resolution unchanged(Request request) {
            return new Resolution(false, request.remainingCastTime(), 0.0D);
        }
    }
}
