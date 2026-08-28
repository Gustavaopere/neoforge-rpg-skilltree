package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable deterministic index of all declared canonical provider bindings. */
public final class CanonicalProviderBindingCatalog {
    private static final CanonicalProviderBindingCatalog EMPTY =
        new CanonicalProviderBindingCatalog(List.of(), Map.of());

    private final List<CanonicalProviderBinding> allBindings;
    private final Map<CanonicalStatKey, List<CanonicalProviderBinding>> byCanonicalStat;

    private CanonicalProviderBindingCatalog(
        List<CanonicalProviderBinding> allBindings,
        Map<CanonicalStatKey, List<CanonicalProviderBinding>> byCanonicalStat
    ) {
        this.allBindings = allBindings;
        this.byCanonicalStat = byCanonicalStat;
    }

    public static CanonicalProviderBindingCatalog empty() {
        return EMPTY;
    }

    public static CanonicalProviderBindingCatalog of(Collection<CanonicalProviderBinding> bindings) {
        Objects.requireNonNull(bindings, "bindings");
        if (bindings.isEmpty()) return EMPTY;

        ArrayList<CanonicalProviderBinding> sorted = new ArrayList<>(bindings.size());
        Set<String> bindingIds = new HashSet<>();
        for (CanonicalProviderBinding binding : bindings) {
            Objects.requireNonNull(binding, "binding");
            if (!bindingIds.add(binding.bindingId())) {
                throw new IllegalArgumentException("duplicate canonical provider binding id: " + binding.bindingId());
            }
            sorted.add(binding);
        }
        sorted.sort(Comparator.comparing(CanonicalProviderBinding::bindingId));

        HashMap<CanonicalStatKey, List<CanonicalProviderBinding>> grouped = new HashMap<>();
        for (CanonicalProviderBinding binding : sorted) {
            grouped.computeIfAbsent(binding.canonicalStat(), ignored -> new ArrayList<>()).add(binding);
        }
        HashMap<CanonicalStatKey, List<CanonicalProviderBinding>> immutableGroups = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, List<CanonicalProviderBinding>> entry : grouped.entrySet()) {
            immutableGroups.put(entry.getKey(), List.copyOf(entry.getValue()));
        }

        return new CanonicalProviderBindingCatalog(
            List.copyOf(sorted),
            Map.copyOf(immutableGroups)
        );
    }

    public List<CanonicalProviderBinding> allBindings() {
        return allBindings;
    }

    public List<CanonicalProviderBinding> definitions(CanonicalStatKey canonicalStat) {
        Objects.requireNonNull(canonicalStat, "canonicalStat");
        return byCanonicalStat.getOrDefault(canonicalStat, List.of());
    }

    public CanonicalProviderBindingResolution resolve(
        CanonicalStatKey canonicalStat,
        ProviderBindingAvailability availability,
        ProviderBindingSelectionPolicy selectionPolicy
    ) {
        return CanonicalProviderBindingResolver.resolve(
            canonicalStat,
            definitions(canonicalStat),
            availability,
            selectionPolicy
        );
    }
}
