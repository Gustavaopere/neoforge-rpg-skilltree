package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Aggregates adapter and host-resolved contributions without coupling the pressure core to optional equipment mods. */
public final class EquipmentProtectionResolver {
    private volatile List<EquipmentProtectionAdapter> adapters;

    public EquipmentProtectionResolver(List<EquipmentProtectionAdapter> adapters) {
        Objects.requireNonNull(adapters, "adapters");
        ArrayList<EquipmentProtectionAdapter> initial = new ArrayList<>(adapters);
        if (initial.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("adapters must not contain null");
        }
        this.adapters = List.copyOf(initial);
    }

    /**
     * Registers an optional integration adapter without rebuilding consumers that already reference this resolver.
     * Registration is expected during setup; resolve() only traverses an immutable snapshot on the gameplay path.
     */
    public synchronized void register(EquipmentProtectionAdapter adapter) {
        Objects.requireNonNull(adapter, "adapter");
        ArrayList<EquipmentProtectionAdapter> updated = new ArrayList<>(adapters);
        updated.add(adapter);
        adapters = List.copyOf(updated);
    }

    public ProtectionSnapshot resolve(EquipmentProtectionContext context) {
        return resolve(context, List.of());
    }

    public ProtectionSnapshot resolve(
            EquipmentProtectionContext context,
            List<ProtectionContribution> hostResolvedContributions
    ) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(hostResolvedContributions, "hostResolvedContributions");
        List<ProtectionContribution> contributions = new ArrayList<>(hostResolvedContributions.size());
        for (ProtectionContribution contribution : hostResolvedContributions) {
            contributions.add(Objects.requireNonNull(contribution, "hostResolvedContributions must not contain null"));
        }

        List<EquipmentProtectionAdapter> adapterSnapshot = adapters;
        for (EquipmentProtectionAdapter adapter : adapterSnapshot) {
            List<ProtectionContribution> resolved;
            try {
                resolved = adapter.resolve(context);
            } catch (RuntimeException | LinkageError adapterFailure) {
                // Optional equipment integrations fail closed for their own capabilities. Independent adapters
                // remain usable because equipment contributions are modular rather than exclusive authorities.
                continue;
            }
            if (resolved == null) {
                continue;
            }
            for (ProtectionContribution contribution : resolved) {
                if (contribution != null) {
                    contributions.add(contribution);
                }
            }
        }

        EnumMap<ProtectionCapability, List<ProtectionContribution>> candidates =
                new EnumMap<>(ProtectionCapability.class);
        for (ProtectionContribution contribution : contributions) {
            for (ProtectionCapability capability : contribution.ratings().keySet()) {
                candidates.computeIfAbsent(capability, ignored -> new ArrayList<>()).add(contribution);
            }
        }
        for (Map.Entry<ProtectionCapability, List<ProtectionContribution>> entry : candidates.entrySet()) {
            entry.getValue().sort(candidateOrder(entry.getKey()));
        }
        return ProtectionSnapshot.fromCandidates(candidates);
    }

    private static Comparator<ProtectionContribution> candidateOrder(ProtectionCapability capability) {
        return Comparator
                .<ProtectionContribution>comparingDouble(
                        contribution -> contribution.ratings().getOrDefault(capability, 0.0))
                .reversed()
                .thenComparing(contribution -> contribution.resourceConsumer().isPresent())
                .thenComparing(ProtectionContribution::sourceId)
                .thenComparing(ProtectionContribution::resourceDebitKey);
    }
}
