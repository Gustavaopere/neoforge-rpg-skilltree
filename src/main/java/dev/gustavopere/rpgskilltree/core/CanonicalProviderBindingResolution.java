package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Immutable audit result for resolving one canonical stat to a concrete provider target. */
public final class CanonicalProviderBindingResolution {
    private final CanonicalStatKey canonicalStat;
    private final List<CanonicalProviderBinding> definitions;
    private final List<CanonicalProviderBinding> availableBindings;
    private final CanonicalProviderBinding selected;

    CanonicalProviderBindingResolution(
        CanonicalStatKey canonicalStat,
        List<CanonicalProviderBinding> definitions,
        List<CanonicalProviderBinding> availableBindings,
        CanonicalProviderBinding selected
    ) {
        this.canonicalStat = Objects.requireNonNull(canonicalStat, "canonicalStat");
        this.definitions = List.copyOf(Objects.requireNonNull(definitions, "definitions"));
        this.availableBindings = List.copyOf(Objects.requireNonNull(availableBindings, "availableBindings"));
        if (!this.definitions.containsAll(this.availableBindings)) {
            throw new IllegalArgumentException("available bindings must be a subset of definitions");
        }
        if (selected != null && !this.availableBindings.contains(selected)) {
            throw new IllegalArgumentException("selected binding must be available");
        }
        this.selected = selected;
    }

    public CanonicalStatKey canonicalStat() {
        return canonicalStat;
    }

    public List<CanonicalProviderBinding> definitions() {
        return definitions;
    }

    public List<CanonicalProviderBinding> availableBindings() {
        return availableBindings;
    }

    public boolean isResolved() {
        return selected != null;
    }

    public CanonicalProviderBinding requireSelected() {
        if (selected == null) {
            throw new IllegalStateException("no available provider binding for canonical stat: " + canonicalStat.serializedId());
        }
        return selected;
    }
}
