package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Immutable-at-read runtime catalog of explicitly supported Battle Mage spell profiles. */
public final class BattleMageSpellProfileCatalog {
    private static volatile Map<String, BattleMageSpellProfile> profiles = Map.of();

    private BattleMageSpellProfileCatalog() {
    }

    public static Optional<BattleMageSpellProfile> find(String spellId) {
        if (spellId == null || spellId.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(profiles.get(spellId));
    }

    public static Map<String, BattleMageSpellProfile> snapshot() {
        return profiles;
    }

    static void replace(Map<String, BattleMageSpellProfile> replacement) {
        Objects.requireNonNull(replacement, "replacement");
        LinkedHashMap<String, BattleMageSpellProfile> copy = new LinkedHashMap<>();
        for (Map.Entry<String, BattleMageSpellProfile> entry : replacement.entrySet()) {
            String spellId = Objects.requireNonNull(entry.getKey(), "spellId");
            BattleMageSpellProfile profile = Objects.requireNonNull(entry.getValue(), "profile");
            if (!spellId.equals(profile.spellId())) {
                throw new IllegalArgumentException("profile key does not match spell id: " + spellId);
            }
            if (copy.putIfAbsent(spellId, profile) != null) {
                throw new IllegalArgumentException("duplicate Battle Mage profile: " + spellId);
            }
        }
        profiles = Map.copyOf(copy);
    }
}
