package dev.gustavopere.volcanoes.geology;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Immutable lookup table mapping concrete blocks and block tags to physical rock profiles. */
public final class RockProfileRegistry {
    private static final ResourceLocation BASALT = id("minecraft", "basalt");
    private static final ResourceLocation TUFF = id("minecraft", "tuff");
    private static final ResourceLocation GRANITE = id("minecraft", "granite");
    private static final ResourceLocation STONE = id("minecraft", "stone");

    private final Map<String, RockProfile> profiles;
    private final Map<ResourceLocation, RockProfile> blocks;
    private final Map<ResourceLocation, RockProfile> tags;

    private RockProfileRegistry(
            Map<String, RockProfile> profiles,
            Map<ResourceLocation, RockProfile> blocks,
            Map<ResourceLocation, RockProfile> tags
    ) {
        this.profiles = Map.copyOf(profiles);
        this.blocks = Map.copyOf(blocks);
        this.tags = Map.copyOf(tags);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static RockProfileRegistry vanillaDefaults() {
        RockProfile basalt = new RockProfile(
                "volcanoes:basalt", RockCategory.IGNEOUS_EXTRUSIVE,
                0.82, 0.18, 1.9, 1.25, 0.85, 0.30);
        RockProfile tuff = new RockProfile(
                "volcanoes:tuff", RockCategory.VOLCANIC_FRAGMENTAL,
                0.45, 0.65, 1.3, 0.85, 0.35, 0.80);
        RockProfile granite = new RockProfile(
                "volcanoes:granite", RockCategory.IGNEOUS_INTRUSIVE,
                0.90, 0.08, 2.8, 0.65, 0.90, 0.35);

        return builder()
                .profile(RockProfile.GENERIC_STONE)
                .profile(basalt)
                .profile(tuff)
                .profile(granite)
                .bindBlock(BASALT, basalt.id())
                .bindBlock(TUFF, tuff.id())
                .bindBlock(GRANITE, granite.id())
                .bindBlock(STONE, RockProfile.GENERIC_STONE.id())
                .build();
    }

    public RockProfile resolve(ResourceLocation blockId, Iterable<ResourceLocation> blockTags) {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(blockTags, "blockTags");

        RockProfile direct = blocks.get(blockId);
        if (direct != null) {
            return direct;
        }

        List<ResourceLocation> orderedTags = new ArrayList<>();
        blockTags.forEach(tag -> orderedTags.add(Objects.requireNonNull(tag, "block tag")));
        orderedTags.sort((left, right) -> left.toString().compareTo(right.toString()));
        for (ResourceLocation tag : orderedTags) {
            RockProfile tagged = tags.get(tag);
            if (tagged != null) {
                return tagged;
            }
        }
        return RockProfile.GENERIC_STONE;
    }

    public RockProfile profile(String profileId) {
        return profiles.get(profileId);
    }

    public static final class Builder {
        private final Map<String, RockProfile> profiles = new HashMap<>();
        private final Map<ResourceLocation, String> blockBindings = new HashMap<>();
        private final Map<ResourceLocation, String> tagBindings = new HashMap<>();

        public Builder profile(RockProfile profile) {
            Objects.requireNonNull(profile, "profile");
            RockProfile previous = profiles.putIfAbsent(profile.id(), profile);
            if (previous != null && !previous.equals(profile)) {
                throw new IllegalArgumentException("Conflicting rock profile id: " + profile.id());
            }
            return this;
        }

        public Builder bindBlock(ResourceLocation blockId, String profileId) {
            bind(blockBindings, Objects.requireNonNull(blockId, "blockId"), requireProfileId(profileId), "block");
            return this;
        }

        public Builder bindTag(ResourceLocation tagId, String profileId) {
            bind(tagBindings, Objects.requireNonNull(tagId, "tagId"), requireProfileId(profileId), "tag");
            return this;
        }

        public RockProfileRegistry build() {
            Map<ResourceLocation, RockProfile> blocks = resolveBindings(blockBindings, "block");
            Map<ResourceLocation, RockProfile> tags = resolveBindings(tagBindings, "tag");
            return new RockProfileRegistry(profiles, blocks, tags);
        }

        private Map<ResourceLocation, RockProfile> resolveBindings(
                Map<ResourceLocation, String> bindings,
                String kind
        ) {
            Map<ResourceLocation, RockProfile> resolved = new HashMap<>();
            bindings.forEach((resourceId, profileId) -> {
                RockProfile profile = profiles.get(profileId);
                if (profile == null) {
                    throw new IllegalStateException("Unknown profile '" + profileId + "' for " + kind + " " + resourceId);
                }
                resolved.put(resourceId, profile);
            });
            return resolved;
        }

        private static void bind(
                Map<ResourceLocation, String> bindings,
                ResourceLocation resourceId,
                String profileId,
                String kind
        ) {
            String previous = bindings.putIfAbsent(resourceId, profileId);
            if (previous != null && !previous.equals(profileId)) {
                throw new IllegalArgumentException(
                        "Conflicting " + kind + " binding for " + resourceId + ": " + previous + " vs " + profileId);
            }
        }

        private static String requireProfileId(String profileId) {
            if (profileId == null || profileId.isBlank()) {
                throw new IllegalArgumentException("profileId must not be blank");
            }
            return profileId;
        }
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
