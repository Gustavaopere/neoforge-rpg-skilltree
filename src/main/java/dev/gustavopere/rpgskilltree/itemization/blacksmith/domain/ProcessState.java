package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

/** Ordered, discrete Blacksmith process states. */
public enum ProcessState {
    CAST,
    SHAPED,
    HEAT_TREATED,
    FINISHED;

    public boolean satisfies(ProcessState required) {
        return ordinal() >= required.ordinal();
    }
}
