package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/** Normalizes Malum spirit shard registry ids into addon-safe affinity keys. */
public final class MalumSpiritClassifier {
    private MalumSpiritClassifier() {}

    public static String affinityKey(String itemId) {
        Objects.requireNonNull(itemId);
        String value = itemId.trim().toLowerCase(Locale.ROOT);
        if (value.isBlank()) throw new IllegalArgumentException("blank spirit item id");
        int separator = value.indexOf(':');
        String namespace = separator >= 0 ? value.substring(0, separator) : "minecraft";
        String path = separator >= 0 ? value.substring(separator + 1) : value;
        if (path.endsWith("_spirit")) path = path.substring(0, path.length() - "_spirit".length());
        if (path.isBlank()) throw new IllegalArgumentException("invalid spirit item id: " + itemId);
        return namespace + "/" + path;
    }

    public static Set<String> spiritTags(Iterable<String> itemIds) {
        Objects.requireNonNull(itemIds);
        LinkedHashSet<String> tags = new LinkedHashSet<>();
        for (String id : itemIds) {
            if (id == null || id.isBlank()) continue;
            tags.add("spirit:" + affinityKey(id));
        }
        return Set.copyOf(tags);
    }
}
