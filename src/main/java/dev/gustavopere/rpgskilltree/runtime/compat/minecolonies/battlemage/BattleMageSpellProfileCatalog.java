package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

/** Immutable-at-read runtime catalog of explicitly supported Battle Mage spell profiles. */
public final class BattleMageSpellProfileCatalog {
    private static volatile Map<ResourceLocation, BattleMageSpellProfile> profiles = Map.of();

    private BattleMageSpellProfileCatalog() {
    }

    public static Optional<BattleMageSpellProfile> find(ResourceLocation spellId) {
        return Optional.ofNullable(spellId).map(profiles::get);
    }

    /** Iron's currently exposes spell ids as strings; convert once at the provider boundary. */
    public static Optional<BattleMageSpellProfile> find(String spellId) {
        if (spellId == null || spellId.indexOf(':') <= 0) {
            return Optional.empty();
        }
        ResourceLocation parsed = ResourceLocation.tryParse(spellId);
        return parsed == null ? Optional.empty() : find(parsed);
    }

    /** String-keyed diagnostic snapshot retained for provider-neutral acceptance tests. */
    public static Map<String, BattleMageSpellProfile> snapshot() {
        LinkedHashMap<String, BattleMageSpellProfile> snapshot = new LinkedHashMap<>();
        profiles.forEach((spellId, profile) -> snapshot.put(spellId.toString(), profile));
        return Map.copyOf(snapshot);
    }

    static void replace(Map<?, BattleMageSpellProfile> replacement) {
        Objects.requireNonNull(replacement, "replacement");
        LinkedHashMap<ResourceLocation, BattleMageSpellProfile> copy = new LinkedHashMap<>();
        for (Map.Entry<?, BattleMageSpellProfile> entry : replacement.entrySet()) {
            Object rawKey = Objects.requireNonNull(entry.getKey(), "spellId");
            ResourceLocation spellId;
            if (rawKey instanceof ResourceLocation resourceLocation) {
                spellId = resourceLocation;
            } else if (rawKey instanceof String stringId) {
                spellId = BattleMageSpellProfile.parseNamespacedId(stringId);
            } else {
                throw new IllegalArgumentException("unsupported spell id key type: " + rawKey.getClass().getName());
            }

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