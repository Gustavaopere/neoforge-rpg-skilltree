package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.NodeInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/**
 * Converts explicit skill-resource tags into canonical class-investment metadata.
 *
 * <p>The only scoring convention is explicit and resource-driven: every purchased
 * rank contributes one point to each declared {@code rpgskilltree:domain/<name>}
 * tag. {@code domain/core} is deliberately neutral. Node ids, positions and graph
 * topology are never inspected to infer a domain.</p>
 */
public final class SkillInvestmentMetadataParser {
    private static final String DOMAIN_PREFIX = "rpgskilltree:domain/";

    private SkillInvestmentMetadataParser() {}

    public static Map<String, NodeInvestmentMetadata> parse(Map<ResourceLocation, JsonElement> resources) {
        Map<String, NodeInvestmentMetadata> result = new LinkedHashMap<>();
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.comparing(ResourceLocation::toString)))
            .forEach(entry -> parseOne(entry.getKey(), entry.getValue(), result));
        return Map.copyOf(result);
    }

    private static void parseOne(
        ResourceLocation source,
        JsonElement element,
        Map<String, NodeInvestmentMetadata> result
    ) {
        if (element == null || !element.isJsonObject()) {
            throw validation(source, null, "root", "must be a JSON object");
        }
        JsonObject skill = element.getAsJsonObject();
        String id = requiredString(source, null, skill, "id");
        validateId(source, id);

        Set<String> tags = parseTags(source, id, skill);
        EnumMap<ProgressionDomain, Integer> weights = parseDomainWeights(source, id, tags);

        NodeInvestmentMetadata previous = result.putIfAbsent(
            id,
            new NodeInvestmentMetadata(weights, tags)
        );
        if (previous != null) {
            throw validation(source, id, "id", "duplicate skill investment metadata id");
        }
    }

    private static void validateId(ResourceLocation source, String id) {
        try {
            ResourceLocation.parse(id);
        } catch (RuntimeException failure) {
            throw new SkillTreeDataValidationException(source, id, "id", "must be a namespaced id", failure);
        }
    }

    private static Set<String> parseTags(ResourceLocation source, String id, JsonObject skill) {
        JsonElement tagsElement = skill.get("tags");
        if (tagsElement == null || !tagsElement.isJsonArray()) {
            throw validation(source, id, "tags", "must be a JSON array");
        }
        JsonArray tagsArray = tagsElement.getAsJsonArray();
        Set<String> tags = new LinkedHashSet<>();
        for (JsonElement tagElement : tagsArray) {
            if (!tagElement.isJsonPrimitive() || !tagElement.getAsJsonPrimitive().isString()) {
                throw validation(source, id, "tags", "entries must be strings");
            }
            String tag = tagElement.getAsString();
            if (tag.isBlank()) throw validation(source, id, "tags", "entries must not be blank");
            tags.add(tag);
        }
        return tags;
    }

    private static EnumMap<ProgressionDomain, Integer> parseDomainWeights(
        ResourceLocation source,
        String id,
        Set<String> tags
    ) {
        EnumMap<ProgressionDomain, Integer> weights = new EnumMap<>(ProgressionDomain.class);
        for (String tag : tags) {
            if (!tag.startsWith(DOMAIN_PREFIX)) continue;
            String domainToken = tag.substring(DOMAIN_PREFIX.length());
            if (domainToken.equals("core")) continue;
            weights.put(parseDomain(source, id, tag, domainToken), 1);
        }
        return weights;
    }

    private static ProgressionDomain parseDomain(
        ResourceLocation source,
        String id,
        String tag,
        String domainToken
    ) {
        try {
            return ProgressionDomain.valueOf(domainToken.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException failure) {
            throw new SkillTreeDataValidationException(
                source, id, "tags", "unknown class-investment domain tag " + tag, failure
            );
        }
    }

    private static String requiredString(
        ResourceLocation source,
        String entryId,
        JsonObject object,
        String field
    ) {
        JsonElement element = object.get(field);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            throw validation(source, entryId, field, "must be a string");
        }
        String value = element.getAsString();
        if (value.isBlank()) throw validation(source, entryId, field, "must not be blank");
        return value;
    }

    private static SkillTreeDataValidationException validation(
        ResourceLocation source,
        String entryId,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(source, entryId, field, detail);
    }
}
