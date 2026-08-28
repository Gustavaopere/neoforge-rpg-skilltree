package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Resolves canonical stats to concrete provider targets without embedding provider precedence. */
public final class CanonicalProviderBindingResolver {
    private CanonicalProviderBindingResolver() {}

    public static CanonicalProviderBindingResolution resolve(
        CanonicalStatKey canonicalStat,
        List<CanonicalProviderBinding> definitions,
        ProviderBindingAvailability availability,
        ProviderBindingSelectionPolicy selectionPolicy
    ) {
        Objects.requireNonNull(canonicalStat, "canonicalStat");
        Objects.requireNonNull(definitions, "definitions");
        Objects.requireNonNull(availability, "availability");
        Objects.requireNonNull(selectionPolicy, "selectionPolicy");

        List<CanonicalProviderBinding> matching = new ArrayList<>();
        Set<String> bindingIds = new HashSet<>();
        for (CanonicalProviderBinding binding : definitions) {
            Objects.requireNonNull(binding, "binding definition");
            if (!binding.canonicalStat().equals(canonicalStat)) continue;
            if (!bindingIds.add(binding.bindingId())) {
                throw new IllegalArgumentException("duplicate canonical provider binding id: " + binding.bindingId());
            }
            matching.add(binding);
        }
        if (matching.isEmpty()) {
            throw new IllegalStateException("no provider bindings defined for canonical stat: " + canonicalStat.serializedId());
        }

        List<CanonicalProviderBinding> available = matching.stream()
            .filter(availability::isAvailable)
            .toList();
        if (available.isEmpty()) {
            return new CanonicalProviderBindingResolution(canonicalStat, matching, available, null);
        }

        CanonicalProviderBinding selected = selectionPolicy.select(canonicalStat, available);
        if (selected == null) {
            throw new IllegalStateException("provider binding selection returned null for: " + canonicalStat.serializedId());
        }
        if (!available.contains(selected)) {
            throw new IllegalStateException("provider binding selection chose an unavailable binding: " + selected.bindingId());
        }
        return new CanonicalProviderBindingResolution(canonicalStat, matching, available, selected);
    }
}
