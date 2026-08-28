package dev.gustavopere.rpgskilltree.compendium.discovery;

/** Eligible/discovered pair for one current catalog scope. */
public record DiscoveryCompletionCount(int eligible, int discovered) {
    public DiscoveryCompletionCount {
        if (eligible < 0) throw new IllegalArgumentException("eligible must not be negative");
        if (discovered < 0) throw new IllegalArgumentException("discovered must not be negative");
        if (discovered > eligible) throw new IllegalArgumentException("discovered must not exceed eligible");
    }

    public double fraction() {
        return eligible == 0 ? 0.0D : (double) discovered / (double) eligible;
    }
}
