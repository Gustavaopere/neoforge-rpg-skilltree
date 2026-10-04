package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable query boundary for the currently audited Blacksmith material profiles.
 *
 * <p>The catalog owns no provider runtime state and deliberately exposes no mutation API. Provider
 * availability and recipe compatibility remain separate integration concerns.</p>
 */
public final class BlacksmithMaterialCatalog {
    private final Map<ResourceLocation, BlacksmithMaterialProfile> profilesById;

    public BlacksmithMaterialCatalog(Collection<BlacksmithMaterialProfile> profiles) {
        Objects.requireNonNull(profiles, "profiles");

        Map<ResourceLocation, BlacksmithMaterialProfile> indexed = new LinkedHashMap<>();
        for (BlacksmithMaterialProfile profile : profiles) {
            BlacksmithMaterialProfile nonNullProfile = Objects.requireNonNull(profile, "profile");
            BlacksmithMaterialProfile previous = indexed.putIfAbsent(nonNullProfile.id(), nonNullProfile);
            if (previous != null) {
                throw new IllegalArgumentException("Duplicate Blacksmith material profile: " + nonNullProfile.id());
            }
        }
        profilesById = Collections.unmodifiableMap(indexed);
    }

    public Optional<BlacksmithMaterialProfile> find(ResourceLocation id) {
        return Optional.ofNullable(profilesById.get(Objects.requireNonNull(id, "id")));
    }

    public BlacksmithMaterialProfile require(ResourceLocation id) {
        return find(id).orElseThrow(() -> new IllegalArgumentException("Unknown Blacksmith material profile: " + id));
    }

    public Set<ResourceLocation> ids() {
        return profilesById.keySet();
    }

    public Collection<BlacksmithMaterialProfile> profiles() {
        return profilesById.values();
    }
}
