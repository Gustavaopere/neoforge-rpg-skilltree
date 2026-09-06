package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

public final class EruptionController {
    public EruptionEvent begin(UUID volcanoId, MagmaChamber chamber, long startedTick) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        Objects.requireNonNull(chamber, "chamber");
        if (startedTick < 0L) {
            throw new IllegalArgumentException("startedTick must be non-negative");
        }
        return new EruptionEvent(
                volcanoId,
                EruptionPhase.PRECURSORS,
                EruptionProfile.fromChamber(chamber),
                startedTick,
                0L);
    }

    public EruptionEvent advance(EruptionEvent event, long deltaTicks) {
        Objects.requireNonNull(event, "event");
        if (deltaTicks < 0L) {
            throw new IllegalArgumentException("deltaTicks must be non-negative");
        }
        if (event.isComplete() || deltaTicks == 0L) {
            return event;
        }
        long elapsed = safeAdd(event.elapsedTicks(), deltaTicks);
        return new EruptionEvent(
                event.volcanoId(),
                phaseFor(event.profile(), elapsed),
                event.profile(),
                event.startedTick(),
                elapsed);
    }

    private static EruptionPhase phaseFor(EruptionProfile profile, long elapsedTicks) {
        long threshold = profile.precursorsTicks();
        if (elapsedTicks < threshold) {
            return EruptionPhase.PRECURSORS;
        }
        threshold = safeAdd(threshold, profile.openingTicks());
        if (elapsedTicks < threshold) {
            return EruptionPhase.OPENING;
        }
        threshold = safeAdd(threshold, profile.sustainedTicks());
        if (elapsedTicks < threshold) {
            return EruptionPhase.SUSTAINED;
        }
        threshold = safeAdd(threshold, profile.waningTicks());
        return elapsedTicks < threshold ? EruptionPhase.WANING : EruptionPhase.DORMANT;
    }

    private static long safeAdd(long left, long right) {
        return left > Long.MAX_VALUE - right ? Long.MAX_VALUE : left + right;
    }
}
