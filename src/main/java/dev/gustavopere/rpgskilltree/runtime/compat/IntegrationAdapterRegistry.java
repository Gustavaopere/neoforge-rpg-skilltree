package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Provider-neutral registry for optional integration adapters.
 *
 * <p>Factories are evaluated lazily: an absent provider is recorded diagnostically without
 * constructing its adapter. Enabled adapters may own a semantic action exactly once; ownership
 * collisions fail closed rather than allowing duplicate processing/progression.</p>
 */
public final class IntegrationAdapterRegistry {
    private static final int MAX_DIAGNOSTIC_REASON_LENGTH = 96;

    private final Map<SemanticActionId, IntegrationCapability> capabilities;
    private final Map<SemanticActionId, SemanticActionId> owners;
    private final List<AdapterDiagnostic> diagnostics;

    private IntegrationAdapterRegistry(
        Map<SemanticActionId, IntegrationCapability> capabilities,
        Map<SemanticActionId, SemanticActionId> owners,
        List<AdapterDiagnostic> diagnostics
    ) {
        this.capabilities = Map.copyOf(capabilities);
        this.owners = Map.copyOf(owners);
        this.diagnostics = List.copyOf(diagnostics);
    }

    public static IntegrationAdapterRegistry create(
        Predicate<OptionalIntegrations.Provider> providerLoaded,
        List<? extends IntegrationAdapterFactory> factories
    ) {
        Objects.requireNonNull(providerLoaded, "providerLoaded");
        Objects.requireNonNull(factories, "factories");

        Map<SemanticActionId, IntegrationCapability> capabilities = new LinkedHashMap<>();
        Map<SemanticActionId, SemanticActionId> owners = new LinkedHashMap<>();
        List<AdapterDiagnostic> diagnostics = new ArrayList<>();

        for (IntegrationAdapterFactory factory : factories) {
            Objects.requireNonNull(factory, "factory");
            SemanticActionId adapterId = Objects.requireNonNull(factory.adapterId(), "factory.adapterId()");
            OptionalIntegrations.Provider provider = Objects.requireNonNull(factory.provider(), "factory.provider()");

            if (!providerLoaded.test(provider)) {
                diagnostics.add(new AdapterDiagnostic(adapterId, provider, AdapterState.ABSENT, "provider_absent"));
                continue;
            }

            IntegrationAdapter adapter = Objects.requireNonNull(factory.create(), "factory.create()");
            validateFactoryContract(factory, adapter, adapterId, provider);

            if (!adapter.enabled()) {
                diagnostics.add(new AdapterDiagnostic(
                    adapterId,
                    provider,
                    AdapterState.DISABLED,
                    boundedReason(adapter.disabledReason())
                ));
                continue;
            }

            for (IntegrationCapability capability : Objects.requireNonNull(adapter.capabilities(), "adapter.capabilities()")) {
                Objects.requireNonNull(capability, "capability");
                SemanticActionId actionId = Objects.requireNonNull(capability.actionId(), "capability.actionId()");
                SemanticActionId existingOwner = owners.putIfAbsent(actionId, adapterId);
                if (existingOwner != null) {
                    throw new IllegalStateException(
                        "Semantic action ownership collision for " + actionId
                            + ": " + existingOwner + " vs " + adapterId
                    );
                }
                capabilities.put(actionId, capability);
            }

            diagnostics.add(new AdapterDiagnostic(adapterId, provider, AdapterState.ENABLED, ""));
        }

        return new IntegrationAdapterRegistry(capabilities, owners, diagnostics);
    }

    public static IntegrationAdapterRegistry create(List<? extends IntegrationAdapterFactory> factories) {
        return create(OptionalIntegrations::isLoaded, factories);
    }

    public Optional<IntegrationCapability> capability(SemanticActionId actionId) {
        if (actionId == null) return Optional.empty();
        return Optional.ofNullable(capabilities.get(actionId));
    }

    public Optional<SemanticActionId> owner(SemanticActionId actionId) {
        if (actionId == null) return Optional.empty();
        return Optional.ofNullable(owners.get(actionId));
    }

    public List<AdapterDiagnostic> diagnostics() {
        return diagnostics;
    }

    public String summary() {
        return diagnostics.stream()
            .map(AdapterDiagnostic::summary)
            .collect(Collectors.joining(","));
    }

    private static void validateFactoryContract(
        IntegrationAdapterFactory factory,
        IntegrationAdapter adapter,
        SemanticActionId adapterId,
        OptionalIntegrations.Provider provider
    ) {
        SemanticActionId runtimeAdapterId = Objects.requireNonNull(adapter.adapterId(), "adapter.adapterId()");
        OptionalIntegrations.Provider runtimeProvider = Objects.requireNonNull(adapter.provider(), "adapter.provider()");
        if (!adapterId.equals(runtimeAdapterId) || provider != runtimeProvider) {
            throw new IllegalStateException(
                "Adapter factory contract mismatch for " + adapterId
                    + ": factory=" + provider.modId()
                    + ", runtime=" + runtimeAdapterId + "/" + runtimeProvider.modId()
            );
        }
    }

    private static String boundedReason(String reason) {
        if (reason == null || reason.isBlank()) return "unspecified";
        String sanitized = reason
            .replace('\r', '_')
            .replace('\n', '_')
            .replace('\t', '_')
            .trim();
        if (sanitized.length() <= MAX_DIAGNOSTIC_REASON_LENGTH) return sanitized;
        return sanitized.substring(0, MAX_DIAGNOSTIC_REASON_LENGTH);
    }

    public enum AdapterState {
        ABSENT("absent"),
        DISABLED("disabled"),
        ENABLED("enabled");

        private final String diagnosticName;

        AdapterState(String diagnosticName) {
            this.diagnosticName = diagnosticName;
        }

        String diagnosticName() {
            return diagnosticName;
        }
    }

    public record AdapterDiagnostic(
        SemanticActionId adapterId,
        OptionalIntegrations.Provider provider,
        AdapterState state,
        String reason
    ) {
        public AdapterDiagnostic {
            Objects.requireNonNull(adapterId, "adapterId");
            Objects.requireNonNull(provider, "provider");
            Objects.requireNonNull(state, "state");
            reason = reason == null ? "" : reason;
        }

        public String summary() {
            String prefix = adapterId + "=" + state.diagnosticName();
            return reason.isEmpty() ? prefix : prefix + "(" + reason + ")";
        }
    }
}
