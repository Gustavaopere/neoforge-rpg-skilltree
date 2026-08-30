package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSection;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

/**
 * Stateless decoder/validator for Stage 10.10 pt-BR editorial resources.
 *
 * <p>The pure editorial domain stays free of Gson/Minecraft dependencies. This runtime adapter
 * converts the offline {@code KIND:namespace:path} identity format explicitly into canonical
 * {@link CompendiumEntryId} values and validates the complete candidate before returning a
 * snapshot.</p>
 */
public final class CompendiumEditorialResourceLoader {
    private static final int SCHEMA = 1;
    private static final String LANGUAGE = "pt_br";
    private static final String RESOURCE_NAMESPACE = "rpgskilltree";
    private static final String ROOT = "compendium/editorial/pt_br";
    private static final Pattern ENTRY_ID = Pattern.compile(
        "^(ENTITY|FLORA|TREE|CROP|BIOME|STRUCTURE|DIMENSION):"
            + "([a-z0-9_.-]+):([a-z0-9_./-]+)$"
    );
    private static final Pattern SECTION_ID = Pattern.compile("^[a-z0-9_][a-z0-9_.-]*$");
    private static final Pattern PLACEHOLDER = Pattern.compile(
        "\\b(?:TODO|TBD|FIXME|PLACEHOLDER)\\b",
        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS
    );
    private static final Set<CompendiumEntryKind> SUPPORTED_KINDS = EnumSet.of(
        CompendiumEntryKind.ENTITY,
        CompendiumEntryKind.FLORA,
        CompendiumEntryKind.TREE,
        CompendiumEntryKind.CROP,
        CompendiumEntryKind.BIOME,
        CompendiumEntryKind.STRUCTURE,
        CompendiumEntryKind.DIMENSION
    );

    private CompendiumEditorialResourceLoader() {}

    public static CompendiumEditorialSnapshot load(
        ResourceManager resourceManager,
        Collection<CompendiumEntry> technicalEntries
    ) {
        Objects.requireNonNull(resourceManager, "resourceManager");
        Map<ResourceLocation, JsonElement> parsed = new LinkedHashMap<>();
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
            ROOT,
            id -> RESOURCE_NAMESPACE.equals(id.getNamespace()) && id.getPath().endsWith(".json")
        );
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.comparing(ResourceLocation::toString)))
            .forEach(entry -> {
                ResourceLocation id = entry.getKey();
                try (var reader = entry.getValue().openAsReader()) {
                    parsed.put(id, JsonParser.parseReader(reader));
                } catch (IOException | RuntimeException failure) {
                    if (failure instanceof CompendiumEditorialValidationException validation) throw validation;
                    throw validation(id, "resource", "could not read/parse editorial JSON resource", failure);
                }
            });
        return prepare(parsed, technicalEntries);
    }

    public static CompendiumEditorialSnapshot prepare(
        Map<ResourceLocation, JsonElement> resources,
        Collection<CompendiumEntry> technicalEntries
    ) {
        Objects.requireNonNull(resources, "resources");
        Objects.requireNonNull(technicalEntries, "technicalEntries");

        Set<CompendiumEntryId> runtimeIds = new HashSet<>();
        for (CompendiumEntry entry : technicalEntries) {
            runtimeIds.add(Objects.requireNonNull(entry, "technicalEntry").id());
        }

        List<Map.Entry<ResourceLocation, JsonElement>> orderedResources = resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.comparing(ResourceLocation::toString)))
            .toList();
        List<LoadedContent> loaded = new ArrayList<>();
        Map<CompendiumEntryId, ResourceLocation> seen = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> resource : orderedResources) {
            ResourceLocation source = Objects.requireNonNull(resource.getKey(), "resourceId");
            JsonElement element = Objects.requireNonNull(resource.getValue(), "resourceJson");
            String physicalNamespace = physicalPackageNamespace(source);
            JsonObject root = requireObject(element, source, "root");

            int schema = requireInt(root, "schema", source, "schema");
            if (schema != SCHEMA) {
                throw validation(source, "schema", "schema must be " + SCHEMA);
            }
            String language = requireString(root, "language", source, "language");
            if (!LANGUAGE.equals(language)) {
                throw validation(source, "language", "language must be " + LANGUAGE);
            }
            String declaredNamespace = requireString(root, "namespace", source, "namespace");
            if (!physicalNamespace.equals(declaredNamespace)) {
                throw validation(
                    source,
                    "namespace",
                    "directory namespace mismatch: directory=" + physicalNamespace + ", declared=" + declaredNamespace
                );
            }
            CompendiumEntryKind packageKind = parsePackageKind(
                requireString(root, "kind", source, "kind"),
                source
            );
            JsonArray entries = requireArray(root, "entries", source, "entries");
            for (int index = 0; index < entries.size(); index++) {
                String prefix = "entries[" + index + "]";
                JsonObject raw = requireObject(entries.get(index), source, prefix);
                CompendiumEditorialContent content = parseEntry(
                    raw,
                    source,
                    prefix,
                    declaredNamespace,
                    packageKind,
                    runtimeIds
                );
                ResourceLocation previous = seen.putIfAbsent(content.entryId(), source);
                if (previous != null) {
                    throw validation(
                        source,
                        prefix + ".entry_id",
                        "duplicate editorial entry " + editorialId(content.entryId())
                            + ": " + previous + " and " + source
                    );
                }
                loaded.add(new LoadedContent(source, content));
            }
        }

        Set<CompendiumEntryId> knownIds = new HashSet<>(runtimeIds);
        knownIds.addAll(seen.keySet());
        for (LoadedContent item : loaded) {
            for (CompendiumEntryId reference : item.content().references()) {
                if (!knownIds.contains(reference)) {
                    throw validation(
                        item.source(),
                        editorialId(item.content().entryId()) + ".references",
                        "unresolved reference " + editorialId(reference)
                    );
                }
            }
        }

        return CompendiumEditorialSnapshot.fromEntries(
            loaded.stream().map(LoadedContent::content).toList()
        );
    }

    private static CompendiumEditorialContent parseEntry(
        JsonObject raw,
        ResourceLocation source,
        String prefix,
        String packageNamespace,
        CompendiumEntryKind packageKind,
        Set<CompendiumEntryId> runtimeIds
    ) {
        ParsedId parsedId = parseEditorialId(
            requireString(raw, "entry_id", source, prefix + ".entry_id"),
            source,
            prefix + ".entry_id"
        );
        if (!packageNamespace.equals(parsedId.namespace())) {
            throw validation(
                source,
                prefix + ".entry_id",
                "namespace mismatch: package=" + packageNamespace + ", entry=" + parsedId.namespace()
            );
        }
        if (packageKind != parsedId.kind()) {
            throw validation(
                source,
                prefix + ".entry_id",
                "kind mismatch: package=" + packageKind + ", entry=" + parsedId.kind()
            );
        }

        String title = requireProse(raw, "title", source, prefix + ".title");
        CompendiumEditorialBlock summary = parseBlock(
            requireElement(raw, "summary", source, prefix + ".summary"),
            source,
            prefix + ".summary"
        );

        List<CompendiumEditorialSection> sections = new ArrayList<>();
        if (raw.has("sections")) {
            JsonObject sectionObject = requireObject(raw.get("sections"), source, prefix + ".sections");
            sectionObject.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(section -> {
                    String sectionName = section.getKey();
                    if (!SECTION_ID.matcher(sectionName).matches()) {
                        throw validation(
                            source,
                            prefix + ".sections",
                            "invalid section key " + sectionName
                        );
                    }
                    sections.add(new CompendiumEditorialSection(
                        sectionName,
                        parseBlock(section.getValue(), source, prefix + ".sections." + sectionName)
                    ));
                });
        }

        List<CompendiumEntryId> references = new ArrayList<>();
        if (raw.has("references")) {
            JsonArray array = requireArray(raw, "references", source, prefix + ".references");
            for (int index = 0; index < array.size(); index++) {
                String label = prefix + ".references[" + index + "]";
                String value = requireString(array.get(index), source, label);
                references.add(parseEditorialId(value, source, label).id());
            }
        }

        EditorialReviewStatus reviewStatus = parseEnum(
            EditorialReviewStatus.class,
            requireString(raw, "review_status", source, prefix + ".review_status"),
            source,
            prefix + ".review_status",
            "review_status"
        );
        EditorialAvailability availability = parseEnum(
            EditorialAvailability.class,
            requireString(raw, "availability", source, prefix + ".availability"),
            source,
            prefix + ".availability",
            "availability"
        );

        boolean present = runtimeIds.contains(parsedId.id());
        String availabilityReason = null;
        if (availability == EditorialAvailability.RUNTIME) {
            if (!present) {
                throw validation(
                    source,
                    prefix + ".availability",
                    editorialId(parsedId.id()) + " is absent from the current technical catalog"
                );
            }
        } else {
            if (present) {
                throw validation(
                    source,
                    prefix + ".availability",
                    editorialId(parsedId.id()) + " is present in the current technical catalog and must use RUNTIME"
                );
            }
            availabilityReason = requireProse(
                raw,
                "availability_reason",
                source,
                prefix + ".availability_reason"
            );
        }

        try {
            return new CompendiumEditorialContent(
                parsedId.id(),
                title,
                summary,
                sections,
                references,
                reviewStatus,
                availability,
                availabilityReason
            );
        } catch (IllegalArgumentException failure) {
            throw validation(source, prefix, "invalid editorial entry: " + failure.getMessage(), failure);
        }
    }

    private static CompendiumEditorialBlock parseBlock(
        JsonElement element,
        ResourceLocation source,
        String label
    ) {
        JsonObject object = requireObject(element, source, label);
        String text = requireProse(object, "text", source, label + ".text");
        JsonArray rawSources = requireArray(object, "sources", source, label + ".sources");
        if (rawSources.isEmpty()) {
            throw validation(source, label + ".sources", "sources must contain at least one explicit source");
        }
        List<CompendiumEditorialSource> sources = new ArrayList<>();
        for (int index = 0; index < rawSources.size(); index++) {
            String sourceLabel = label + ".sources[" + index + "]";
            JsonObject rawSource = requireObject(rawSources.get(index), source, sourceLabel);
            String typeText = requireString(rawSource, "type", source, sourceLabel + ".type");
            EditorialSourceType type = parseEnum(
                EditorialSourceType.class,
                typeText,
                source,
                sourceLabel + ".type",
                "source type"
            );
            String ref = requireString(rawSource, "ref", source, sourceLabel + ".ref");
            rejectSourcePlaceholder(ref, source, sourceLabel + ".ref");
            String note = null;
            if (rawSource.has("note")) {
                note = requireString(rawSource, "note", source, sourceLabel + ".note");
                rejectPlaceholder(note, source, sourceLabel + ".note");
            }
            try {
                sources.add(new CompendiumEditorialSource(type, ref, note));
            } catch (IllegalArgumentException failure) {
                throw validation(source, sourceLabel, "invalid source: " + failure.getMessage(), failure);
            }
        }
        try {
            return new CompendiumEditorialBlock(text, sources);
        } catch (IllegalArgumentException failure) {
            throw validation(source, label, "invalid prose block: " + failure.getMessage(), failure);
        }
    }

    private static ParsedId parseEditorialId(String value, ResourceLocation source, String label) {
        Matcher matcher = ENTRY_ID.matcher(value);
        if (!matcher.matches()) {
            throw validation(
                source,
                label,
                "entry id must be KIND:namespace:path using a supported Compendium kind: " + value
            );
        }
        CompendiumEntryKind kind = CompendiumEntryKind.valueOf(matcher.group(1));
        if (!SUPPORTED_KINDS.contains(kind)) {
            throw validation(source, label, "unsupported editorial kind: " + kind);
        }
        String namespace = matcher.group(2);
        String path = matcher.group(3);
        try {
            CompendiumEntryId id = CompendiumEntryId.of(kind, namespace + ":" + path);
            return new ParsedId(id, namespace, kind);
        } catch (IllegalArgumentException failure) {
            throw validation(source, label, "invalid KIND:namespace:path entry id: " + value, failure);
        }
    }

    private static CompendiumEntryKind parsePackageKind(String value, ResourceLocation source) {
        final CompendiumEntryKind kind;
        try {
            kind = CompendiumEntryKind.valueOf(value);
        } catch (IllegalArgumentException failure) {
            throw validation(source, "kind", "unsupported editorial kind: " + value, failure);
        }
        if (!SUPPORTED_KINDS.contains(kind)) {
            throw validation(source, "kind", "unsupported editorial kind: " + value);
        }
        return kind;
    }

    private static String physicalPackageNamespace(ResourceLocation source) {
        if (!RESOURCE_NAMESPACE.equals(source.getNamespace())) {
            throw validation(
                source,
                "resource",
                "editorial resources must use data namespace " + RESOURCE_NAMESPACE
            );
        }
        String prefix = ROOT + "/";
        String path = source.getPath();
        if (!path.startsWith(prefix) || !path.endsWith(".json")) {
            throw validation(source, "resource", "resource must be under " + prefix + "<namespace>/*.json");
        }
        String relative = path.substring(prefix.length());
        int slash = relative.indexOf('/');
        if (slash <= 0 || slash == relative.length() - 1) {
            throw validation(source, "namespace", "resource path must contain a package namespace directory");
        }
        return relative.substring(0, slash);
    }

    private static String requireProse(
        JsonObject object,
        String key,
        ResourceLocation source,
        String label
    ) {
        String text = requireString(object, key, source, label);
        rejectPlaceholder(text, source, label);
        return text;
    }

    private static void rejectPlaceholder(String text, ResourceLocation source, String label) {
        if (PLACEHOLDER.matcher(text).find()) {
            throw validation(source, label, "contains a forbidden placeholder");
        }
    }

    private static void rejectSourcePlaceholder(String text, ResourceLocation source, String label) {
        if ("...".equals(text) || PLACEHOLDER.matcher(text).find()) {
            throw validation(source, label, "contains a forbidden placeholder");
        }
    }

    private static JsonElement requireElement(
        JsonObject object,
        String key,
        ResourceLocation source,
        String label
    ) {
        if (!object.has(key) || object.get(key) == null || object.get(key).isJsonNull()) {
            throw validation(source, label, label + " is required");
        }
        return object.get(key);
    }

    private static JsonObject requireObject(JsonElement element, ResourceLocation source, String label) {
        if (element == null || !element.isJsonObject()) {
            throw validation(source, label, label + " must be an object");
        }
        return element.getAsJsonObject();
    }

    private static JsonArray requireArray(
        JsonObject object,
        String key,
        ResourceLocation source,
        String label
    ) {
        JsonElement element = requireElement(object, key, source, label);
        if (!element.isJsonArray()) {
            throw validation(source, label, label + " must be an array");
        }
        return element.getAsJsonArray();
    }

    private static int requireInt(JsonObject object, String key, ResourceLocation source, String label) {
        JsonElement element = requireElement(object, key, source, label);
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber()) {
            throw validation(source, label, label + " must be an integer");
        }
        try {
            java.math.BigDecimal value = element.getAsBigDecimal();
            if (value.stripTrailingZeros().scale() > 0) {
                throw validation(source, label, label + " must be an integer");
            }
            return value.intValueExact();
        } catch (CompendiumEditorialValidationException failure) {
            throw failure;
        } catch (RuntimeException failure) {
            throw validation(source, label, label + " must be an integer", failure);
        }
    }

    private static String requireString(
        JsonObject object,
        String key,
        ResourceLocation source,
        String label
    ) {
        return requireString(requireElement(object, key, source, label), source, label);
    }

    private static String requireString(JsonElement element, ResourceLocation source, String label) {
        try {
            if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw validation(source, label, label + " must be a non-blank string");
            }
            String value = element.getAsString().trim();
            if (value.isEmpty()) {
                throw validation(source, label, label + " must be a non-blank string");
            }
            return value;
        } catch (CompendiumEditorialValidationException failure) {
            throw failure;
        } catch (RuntimeException failure) {
            throw validation(source, label, label + " must be a non-blank string", failure);
        }
    }

    private static <E extends Enum<E>> E parseEnum(
        Class<E> type,
        String value,
        ResourceLocation source,
        String label,
        String humanName
    ) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException failure) {
            throw validation(source, label, "unsupported " + humanName + ": " + value, failure);
        }
    }

    private static String editorialId(CompendiumEntryId id) {
        String resource = id.resourceLocation();
        return id.kind().name() + ":" + resource;
    }

    private static CompendiumEditorialValidationException validation(
        ResourceLocation source,
        String field,
        String message
    ) {
        return new CompendiumEditorialValidationException(source + " [" + field + "]: " + message);
    }

    private static CompendiumEditorialValidationException validation(
        ResourceLocation source,
        String field,
        String message,
        Throwable cause
    ) {
        return new CompendiumEditorialValidationException(source + " [" + field + "]: " + message, cause);
    }

    private record ParsedId(CompendiumEntryId id, String namespace, CompendiumEntryKind kind) {}

    private record LoadedContent(ResourceLocation source, CompendiumEditorialContent content) {}
}
