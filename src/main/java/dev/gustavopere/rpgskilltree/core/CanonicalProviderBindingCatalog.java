package dev.gustavopere.rpgskilltree.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable authoritative catalog of canonical-stat provider bindings.
 *
 * The catalog deliberately does not encode provider precedence. Resolution still
 * requires explicit runtime availability and selection policies.
 */
public final class CanonicalProviderBindingCatalog {
    private static final Comparator<CanonicalProviderBinding> CANONICAL_ORDER = Comparator
        .comparing((CanonicalProviderBinding binding) -> binding.canonicalStat().serializedId())
        .thenComparing(CanonicalProviderBinding::bindingId)
        .thenComparing(CanonicalProviderBinding::providerTargetId);

    private final List<CanonicalProviderBinding> definitions;
    private final Map<CanonicalStatKey, List<CanonicalProviderBinding>> definitionsByStat;
    private final Map<String, CanonicalProviderBinding> bindingsById;
    private final String fingerprint;

    private CanonicalProviderBindingCatalog(
        List<CanonicalProviderBinding> definitions,
        Map<CanonicalStatKey, List<CanonicalProviderBinding>> definitionsByStat,
        Map<String, CanonicalProviderBinding> bindingsById,
        String fingerprint
    ) {
        this.definitions = definitions;
        this.definitionsByStat = definitionsByStat;
        this.bindingsById = bindingsById;
        this.fingerprint = fingerprint;
    }

    public static CanonicalProviderBindingCatalog of(List<CanonicalProviderBinding> definitions) {
        Objects.requireNonNull(definitions, "definitions");
        if (definitions.isEmpty()) {
            throw new IllegalArgumentException("canonical provider binding catalog must not be empty");
        }

        ArrayList<CanonicalProviderBinding> ordered = new ArrayList<>(definitions.size());
        HashMap<String, CanonicalProviderBinding> byId = new HashMap<>();
        for (CanonicalProviderBinding binding : definitions) {
            Objects.requireNonNull(binding, "binding definition");
            CanonicalProviderBinding previous = byId.putIfAbsent(binding.bindingId(), binding);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate canonical provider binding id: " + binding.bindingId());
            }
            ordered.add(binding);
        }
        ordered.sort(CANONICAL_ORDER);

        HashMap<CanonicalStatKey, List<CanonicalProviderBinding>> grouped = new HashMap<>();
        for (CanonicalProviderBinding binding : ordered) {
            grouped.computeIfAbsent(binding.canonicalStat(), ignored -> new ArrayList<>()).add(binding);
        }
        HashMap<CanonicalStatKey, List<CanonicalProviderBinding>> immutableGrouped = new HashMap<>();
        grouped.forEach((stat, bindings) -> immutableGrouped.put(stat, List.copyOf(bindings)));

        List<CanonicalProviderBinding> immutableDefinitions = List.copyOf(ordered);
        return new CanonicalProviderBindingCatalog(
            immutableDefinitions,
            Map.copyOf(immutableGrouped),
            Map.copyOf(byId),
            fingerprintOf(immutableDefinitions)
        );
    }

    public List<CanonicalProviderBinding> definitions() {
        return definitions;
    }

    public List<CanonicalProviderBinding> definitionsFor(CanonicalStatKey canonicalStat) {
        Objects.requireNonNull(canonicalStat, "canonicalStat");
        List<CanonicalProviderBinding> bindings = definitionsByStat.get(canonicalStat);
        if (bindings == null) {
            throw new IllegalStateException(
                "no provider bindings defined for canonical stat: " + canonicalStat.serializedId()
            );
        }
        return bindings;
    }

    public CanonicalProviderBinding requireBinding(String bindingId) {
        Objects.requireNonNull(bindingId, "bindingId");
        CanonicalProviderBinding binding = bindingsById.get(bindingId);
        if (binding == null) {
            throw new IllegalArgumentException("unknown canonical provider binding id: " + bindingId);
        }
        return binding;
    }

    public CanonicalProviderBindingResolution resolve(
        CanonicalStatKey canonicalStat,
        ProviderBindingAvailability availability,
        ProviderBindingSelectionPolicy selectionPolicy
    ) {
        return CanonicalProviderBindingResolver.resolve(
            canonicalStat,
            definitionsFor(canonicalStat),
            availability,
            selectionPolicy
        );
    }

    public String fingerprint() {
        return fingerprint;
    }

    private static String fingerprintOf(List<CanonicalProviderBinding> definitions) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update("canonical-provider-bindings-v1\n".getBytes(StandardCharsets.UTF_8));
            for (CanonicalProviderBinding binding : definitions) {
                String line = binding.canonicalStat().serializedId()
                    + "\t" + binding.bindingId()
                    + "\t" + binding.providerTargetId()
                    + "\n";
                digest.update(line.getBytes(StandardCharsets.UTF_8));
            }
            return toHex(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            result.append(Character.forDigit((value >>> 4) & 0x0F, 16));
            result.append(Character.forDigit(value & 0x0F, 16));
        }
        return result.toString();
    }
}
