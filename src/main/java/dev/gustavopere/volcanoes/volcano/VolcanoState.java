package dev.gustavopere.volcanoes.volcano;

/** Persisted lifecycle state. Detailed transition logic belongs to the magma-lifecycle round. */
public enum VolcanoState {
    EXTINCT,
    DORMANT,
    ACTIVE,
    ERUPTING
}
