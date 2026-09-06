package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadataPolicy;
import dev.gustavopere.rpgskilltree.core.MasteryLaneCatalog;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/** Parses the explicit datapack authority for Mastery-to-class investment contributions. */
public final class MasteryInvestmentMetadataParser {
    private MasteryInvestmentMetadataParser() {}

    public static List<MasteryInvestmentMetadata> parse(Map<ResourceLocation, JsonElement> resources) {
        List<Map.Entry<ResourceLocation, JsonElement>> ordered = new ArrayList<>(resources.entrySet());
        ordered.sort(Map.Entry.comparingByKey(Comparator.comparing(ResourceLocation::toString)));

        List<MasteryInvestmentMetadata> parsed = new ArrayList<>();
        Map<ThresholdIdentity, ResourceLocation> sources = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> resource : ordered) {
            ResourceLocation resourceId = resource.getKey();
            JsonObject root = requireObject(resourceId, resource.getValue());
            String lane = requireString(resourceId, root, "lane");
            int minimumExperience = requirePositiveInt(
                resourceId,
                null,
                "minimum_experience",
                root.get("minimum_experience")
            );

            if (!MasteryLaneCatalog.isCanonical(lane)) {
                throw invalid(resourceId, lane, "lane", "non-canonical mastery lane");
            }

            ThresholdIdentity identity = new ThresholdIdentity(lane, minimumExperience);
            ResourceLocation previous = sources.putIfAbsent(identity, resourceId);
            if (previous != null) {
                throw invalid(
                    resourceId,
                    lane,
                    "minimum_experience",
                    "duplicate mastery threshold " + lane + "@" + minimumExperience + " already defined by " + previous
                );
            }

            Map<ProgressionDomain, Integer> weights = parseDomainWeights(resourceId, lane, root);
            Set<String> tags = parseTags(resourceId, lane, root);
            try {
                parsed.add(new MasteryInvestmentMetadata(lane, minimumExperience, weights, tags));
            } catch (IllegalArgumentException ex) {
                throw new SkillTreeDataValidationException(resourceId, lane, "entry", ex.getMessage(), ex);
            }
        }

        try {
            return MasteryInvestmentMetadataPolicy.validate(parsed);
        } catch (IllegalArgumentException ex) {
            ResourceLocation resourceId = ordered.isEmpty()
                ? ResourceLocation.fromNamespaceAndPath("rpgskilltree", "mastery_investments")
                : ordered.getFirst().getKey();
            throw new SkillTreeDataValidationException(resourceId, null, "entries", ex.getMessage(), ex);
        }
    }

    private static JsonObject requireObject(ResourceLocation resourceId, JsonElement element) {
        if (element == null || !element.isJsonObject()) {
            throw invalid(resourceId, null, "root", "expected JSON object");
        }
        return element.getAsJsonObject();
    }

    private static String requireString(ResourceLocation resourceId, JsonObject root, String field) {
        JsonElement element = root.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            throw invalid(resourceId, null, field, "expected string");
        }
        String value = element.getAsString();
        if (value.isBlank()) throw invalid(resourceId, null, field, "must not be blank");
        return value;
    }

    private static int requirePositiveInt(
        ResourceLocation resourceId,
        String entryId,
        String field,
        JsonElement element
    ) {
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            throw invalid(resourceId, entryId, field, "expected positive integer");
        }
        try {
            int value = new BigDecimal(element.getAsString()).intValueExact();
            if (value <= 0) throw invalid(resourceId, entryId, field, "must be positive");
            return value;
        } catch (NumberFormatException | ArithmeticException ex) {
            throw new SkillTreeDataValidationException(
                resourceId,
                entryId,
                field,
                "expected positive integer",
                ex
            );
        }
    }

    private static Map<ProgressionDomain, Integer> parseDomainWeights(
        ResourceLocation resourceId,
        String lane,
        JsonObject root
    ) {
        JsonElement element = root.get("domain_weights");
        if (element == null || !element.isJsonObject()) {
            throw invalid(resourceId, lane, "domain_weights", "expected object");
        }

        EnumMap<ProgressionDomain, Integer> weights = new EnumMap<>(ProgressionDomain.class);
        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            ProgressionDomain domain;
            try {
                domain = ProgressionDomain.valueOf(entry.getKey().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                throw new SkillTreeDataValidationException(
                    resourceId,
                    lane,
                    "domain_weights." + entry.getKey(),
                    "unknown progression domain",
                    ex
                );
            }
            if (weights.containsKey(domain)) {
                throw invalid(
                    resourceId,
                    lane,
                    "domain_weights." + entry.getKey(),
                    "duplicate progression domain after normalization"
                );
            }
            int weight = requirePositiveInt(
                resourceId,
                lane,
                "domain_weights." + entry.getKey(),
                entry.getValue()
            );
            weights.put(domain, weight);
        }
        return Map.copyOf(weights);
    }

    private static Set<String> parseTags(ResourceLocation resourceId, String lane, JsonObject root) {
        JsonElement element = root.get("tags");
        if (element == null || !element.isJsonArray()) {
            throw invalid(resourceId, lane, "tags", "expected array");
        }

        Set<String> tags = new LinkedHashSet<>();
        int index = 0;
        for (JsonElement tagElement : element.getAsJsonArray()) {
            if (!tagElement.isJsonPrimitive() || !tagElement.getAsJsonPrimitive().isString()) {
                throw invalid(resourceId, lane, "tags[" + index + "]", "expected string");
            }
            String tag = tagElement.getAsString();
            if (tag.isBlank()) throw invalid(resourceId, lane, "tags[" + index + "]", "must not be blank");
            tags.add(tag);
            index++;
        }
        return Set.copyOf(tags);
    }

    private static SkillTreeDataValidationException invalid(
        ResourceLocation resourceId,
        String entryId,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(resourceId, entryId, field, detail);
    }

    private record ThresholdIdentity(String laneId, int minimumExperience) {}
}
