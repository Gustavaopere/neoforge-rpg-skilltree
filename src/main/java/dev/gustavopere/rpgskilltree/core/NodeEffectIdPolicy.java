package dev.gustavopere.rpgskilltree.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/** Generates deterministic fallback IDs for data-pack effects that omit an explicit effectId. */
public final class NodeEffectIdPolicy {
    private static final HexFormat HEX = HexFormat.of();

    private NodeEffectIdPolicy() {}

    public static ResourceLocation attribute(
        ResourceLocation source,
        ResourceLocation nodeId,
        ResourceLocation attributeId,
        ModifierOperation operation
    ) {
        Objects.requireNonNull(operation);
        return generated("attribute", source, nodeId, attributeId.toString(), operation.name());
    }

    public static ResourceLocation behavior(
        ResourceLocation source,
        ResourceLocation nodeId,
        ResourceLocation handlerId
    ) {
        return generated("behavior", source, nodeId, handlerId.toString());
    }

    private static ResourceLocation generated(
        String kind,
        ResourceLocation source,
        ResourceLocation nodeId,
        String... components
    ) {
        Objects.requireNonNull(kind);
        Objects.requireNonNull(source);
        Objects.requireNonNull(nodeId);
        StringBuilder canonical = new StringBuilder(kind)
            .append('|').append(source)
            .append('|').append(nodeId);
        for (String component : components) {
            canonical.append('|').append(Objects.requireNonNull(component));
        }

        byte[] digest;
        try {
            digest = MessageDigest.getInstance("SHA-256").digest(canonical.toString().getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is required by the Java platform", impossible);
        }
        String stableSuffix = HEX.formatHex(digest, 0, 12);
        return ResourceLocation.fromNamespaceAndPath("rpgskilltree", "generated/node_effect/" + stableSuffix);
    }
}
