package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/** Pure bounded reconciliation for one loaded chunk's durable geothermal receipts. */
public final class GeothermalDurableRecoveryBatch {
    private GeothermalDurableRecoveryBatch() {
    }

    public static Result process(
            List<GeothermalChunkHandoff> handoffs,
            int maxAttempts,
            Predicate<GeothermalChunkHandoff> acknowledge
    ) {
        Objects.requireNonNull(handoffs, "handoffs");
        Objects.requireNonNull(acknowledge, "acknowledge");
        if (maxAttempts < 0) {
            throw new IllegalArgumentException("maxAttempts must be non-negative");
        }

        int attempted = 0;
        int acknowledged = 0;
        List<GeothermalChunkHandoff> remaining = new ArrayList<>(handoffs.size());
        for (GeothermalChunkHandoff handoff : handoffs) {
            if (attempted >= maxAttempts) {
                remaining.add(handoff);
                continue;
            }
            attempted++;
            boolean accepted = false;
            try {
                accepted = acknowledge.test(handoff);
            } catch (RuntimeException | LinkageError failure) {
                accepted = false;
            }
            if (accepted) {
                acknowledged++;
            } else {
                remaining.add(handoff);
            }
        }
        return new Result(attempted, acknowledged, List.copyOf(remaining));
    }

    public record Result(int attempted, int acknowledged, List<GeothermalChunkHandoff> remaining) {
        public Result {
            remaining = List.copyOf(Objects.requireNonNull(remaining, "remaining"));
        }
    }
}
