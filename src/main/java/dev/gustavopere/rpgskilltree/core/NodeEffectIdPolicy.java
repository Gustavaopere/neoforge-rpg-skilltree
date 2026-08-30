package dev.gustavopere.rpgskilltree.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;

/** Generates deterministic namespaced fallback IDs without depending on Minecraft runtime types. */
public final class NodeEffectIdPolicy {
    private static final HexFormat HEX = HexFormat.of();

    private NodeEffectIdPolicy() {}

    public static String attribute(
        String source,
        String nodeId,
        String attributeId,
        ModifierOperation operation
    ) {
        Objects.requireNonNull(operation);
        return generated("attribute", source, nodeId, attributeId, operation.name());
    }

    public static String behavior(
        String source,
        String nodeId,
        String handlerId
    ) {
        return generated("behavior", source, nodeId, handlerId);
    }

    private static String generated(
        String kind,
        String source,
        String nodeId,
        String... components
    ) {
        requireNonBlank(kind, "kind");
        requireNonBlank(source, "source");
        requireNonBlank(nodeId, "nodeId");
        StringBuilder canonical = new StringBuilder(kind)
            .append('|').append(source)
            .append('|').append(nodeId);
        for (String component : components) {
            requireNonBlank(component, "component");
            canonical.append('|').append(component);
        }

        byte[] digest;
        try {
            digest = MessageDigest.getInstance("SHA-256").digest(canonical.toString().getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is required by the Java platform", impossible);
        }
        String stableSuffix = HEX.formatHex(digest, 0, 12);
        return "rpgskilltree:generated/node_effect/" + stableSuffix;
    }

    private static void requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
    }
}
