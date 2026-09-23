package dev.gustavopere.rpgskilltree.runtime.compat;

import dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauVersionContract;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Production registry facade for every optional provider known to RPG Skill Tree.
 *
 * <p>The factories in this class are provider-neutral and never reference provider API types.
 * {@link IntegrationAdapterRegistry} checks presence before invoking a factory, so absent mods do
 * not cause adapter construction or provider classloading. Provider-specific runtime hooks remain
 * in their own compat packages and are gated by {@link #isActive(IntegrationAdapterRegistry,
 * OptionalIntegrations.Provider)}.</p>
 */
public final class OptionalIntegrationAdapterRegistry {
    private static final String ADAPTER_PREFIX = "rpgskilltree:adapter/";
    private static final String SOURCE_PREFIX = "rpgskilltree:provider_source/";
    private static final String UNSUPPORTED_VERSION = "unsupported_version";

    private OptionalIntegrationAdapterRegistry() {}

    public static IntegrationAdapterRegistry create(
        Predicate<OptionalIntegrations.Provider> providerLoaded,
        Function<OptionalIntegrations.Provider, String> disabledReason
    ) {
        return create(providerLoaded, disabledReason, OptionalIntegrations::version);
    }

    static IntegrationAdapterRegistry create(
        Predicate<OptionalIntegrations.Provider> providerLoaded,
        Function<OptionalIntegrations.Provider, String> disabledReason,
        Function<OptionalIntegrations.Provider, String> providerVersion
    ) {
        Objects.requireNonNull(providerLoaded, "providerLoaded");
        Objects.requireNonNull(disabledReason, "disabledReason");
        Objects.requireNonNull(providerVersion, "providerVersion");
        List<IntegrationAdapterFactory> factories = Arrays.stream(OptionalIntegrations.Provider.values())
            .map(provider -> factory(provider, disabledReason, providerVersion))
            .toList();
        return IntegrationAdapterRegistry.create(providerLoaded, factories);
    }

    public static IntegrationAdapterRegistry create(Function<OptionalIntegrations.Provider, String> disabledReason) {
        return create(OptionalIntegrations::isLoaded, disabledReason);
    }

    public static SemanticActionId adapterId(OptionalIntegrations.Provider provider) {
        Objects.requireNonNull(provider, "provider");
        return SemanticActionId.of(ADAPTER_PREFIX + provider.modId());
    }

    public static SemanticActionId sourceAction(OptionalIntegrations.Provider provider) {
        Objects.requireNonNull(provider, "provider");
        return SemanticActionId.of(SOURCE_PREFIX + provider.modId());
    }

    public static boolean isActive(
        IntegrationAdapterRegistry registry,
        OptionalIntegrations.Provider provider
    ) {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(provider, "provider");
        return registry.owner(sourceAction(provider))
            .filter(adapterId(provider)::equals)
            .isPresent();
    }

    private static IntegrationAdapterFactory factory(
        OptionalIntegrations.Provider provider,
        Function<OptionalIntegrations.Provider, String> disabledReason,
        Function<OptionalIntegrations.Provider, String> providerVersion
    ) {
        SemanticActionId adapterId = adapterId(provider);
        SemanticActionId sourceAction = sourceAction(provider);
        return new IntegrationAdapterFactory() {
            @Override
            public SemanticActionId adapterId() {
                return adapterId;
            }

            @Override
            public OptionalIntegrations.Provider provider() {
                return provider;
            }

            @Override
            public IntegrationAdapter create() {
                String reason = normalizedReason(disabledReason.apply(provider));
                if (reason.isBlank()) {
                    reason = builtInDisabledReason(provider, providerVersion);
                }
                boolean enabled = reason.isBlank();
                String finalReason = reason;
                return new IntegrationAdapter() {
                    @Override
                    public SemanticActionId adapterId() {
                        return adapterId;
                    }

                    @Override
                    public OptionalIntegrations.Provider provider() {
                        return provider;
                    }

                    @Override
                    public Set<? extends IntegrationCapability> capabilities() {
                        return Set.of((IntegrationCapability) () -> sourceAction);
                    }

                    @Override
                    public boolean enabled() {
                        return enabled;
                    }

                    @Override
                    public String disabledReason() {
                        return enabled ? "" : finalReason;
                    }
                };
            }
        };
    }

    private static String builtInDisabledReason(
        OptionalIntegrations.Provider provider,
        Function<OptionalIntegrations.Provider, String> providerVersion
    ) {
        return switch (provider) {
            case ARS_NOUVEAU -> ArsNouveauVersionContract.supports(providerVersion.apply(provider))
                ? ""
                : UNSUPPORTED_VERSION;
            default -> "";
        };
    }

    private static String normalizedReason(String reason) {
        return reason == null ? "" : reason.trim();
    }
}
