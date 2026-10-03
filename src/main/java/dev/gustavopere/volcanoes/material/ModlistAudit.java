package dev.gustavopere.volcanoes.material;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Immutable, fail-closed snapshot of the canonical top-level modlist material audit. */
public final class ModlistAudit {
    private static final String AUDIT_RESOURCE_ROOT = "data/volcanoes/materials/audit/";

    private final int schemaVersion;
    private final String snapshotDate;
    private final String sourceFile;
    private final String sourceSha256;
    private final int topLevelJarCount;
    private final String notionDatabase;
    private final List<ModAuditEntry> entries;

    private ModlistAudit(
        int schemaVersion,
        String snapshotDate,
        String sourceFile,
        String sourceSha256,
        int topLevelJarCount,
        String notionDatabase,
        List<ModAuditEntry> entries
    ) {
        this.schemaVersion = schemaVersion;
        this.snapshotDate = Objects.requireNonNull(snapshotDate, "snapshotDate");
        this.sourceFile = Objects.requireNonNull(sourceFile, "sourceFile");
        this.sourceSha256 = Objects.requireNonNull(sourceSha256, "sourceSha256");
        this.topLevelJarCount = topLevelJarCount;
        this.notionDatabase = Objects.requireNonNull(notionDatabase, "notionDatabase");
        this.entries = List.copyOf(entries);
    }

    public static ModlistAudit load(InputStream input) {
        Objects.requireNonNull(input, "input");
        JsonObject root = parseObject(input, "material audit index");

        int schemaVersion = requiredInt(root, "schemaVersion");
        String snapshotDate = requiredString(root, "snapshotDate");
        String sourceFile = requiredString(root, "sourceFile");
        String sourceSha256 = requiredString(root, "sourceSha256");
        int topLevelJarCount = requiredInt(root, "topLevelJarCount");
        String notionDatabase = requiredString(root, "notionDatabase");

        EntryDefaults defaults = parseDefaults(requiredObject(root, "defaultEntry"));
        JsonArray shardNames = requiredArray(root, "entryShards");

        List<ModAuditEntry> entries = new ArrayList<>(topLevelJarCount);
        Set<String> uniqueTuples = new HashSet<>(topLevelJarCount);
        for (JsonElement shardElement : shardNames) {
            String shardName = requiredArrayString(shardElement, "entryShards");
            String resourcePath = AUDIT_RESOURCE_ROOT + shardName;
            try (InputStream shardInput = ModlistAudit.class.getClassLoader().getResourceAsStream(resourcePath)) {
                if (shardInput == null) {
                    throw new IllegalArgumentException("missing material audit shard: " + resourcePath);
                }
                JsonObject shard = parseObject(shardInput, "material audit shard " + shardName);
                for (JsonElement rawEntry : requiredArray(shard, "entries")) {
                    ModAuditEntry entry = parseCompactEntry(rawEntry, defaults);
                    String tuple = entry.jar() + '\u0000' + entry.modId() + '\u0000' + entry.version();
                    if (!uniqueTuples.add(tuple)) {
                        throw new IllegalArgumentException("duplicate material audit tuple: " + entry.jar());
                    }
                    entries.add(entry);
                }
            } catch (java.io.IOException exception) {
                throw new IllegalArgumentException("failed to close material audit shard: " + resourcePath, exception);
            }
        }

        if (topLevelJarCount != entries.size()) {
            throw new IllegalArgumentException(
                "material audit count mismatch: declared=" + topLevelJarCount + ", parsed=" + entries.size()
            );
        }

        return new ModlistAudit(
            schemaVersion,
            snapshotDate,
            sourceFile,
            sourceSha256,
            topLevelJarCount,
            notionDatabase,
            entries
        );
    }

    public int schemaVersion() {
        return schemaVersion;
    }

    public String snapshotDate() {
        return snapshotDate;
    }

    public String sourceFile() {
        return sourceFile;
    }

    public String sourceSha256() {
        return sourceSha256;
    }

    public int topLevelJarCount() {
        return topLevelJarCount;
    }

    public String notionDatabase() {
        return notionDatabase;
    }

    public List<ModAuditEntry> entries() {
        return entries;
    }

    private static ModAuditEntry parseCompactEntry(JsonElement element, EntryDefaults defaults) {
        if (!element.isJsonArray()) {
            throw new IllegalArgumentException("material audit compact entry must be an array");
        }
        JsonArray row = element.getAsJsonArray();
        if (row.size() < 5 || row.size() > 6) {
            throw new IllegalArgumentException("material audit compact entry must contain 5 or 6 values");
        }

        JsonObject override = row.size() == 6 ? row.get(5).getAsJsonObject() : null;
        String notionRecord = overrideString(override, "notionRecord", defaults.notionRecord());
        List<String> evidence = overrideArray(override, "evidence", defaults.evidence());
        String notes = overrideString(override, "notes", defaults.notes());

        return new ModAuditEntry(
            requiredArrayString(row.get(0), "jar"),
            requiredArrayString(row.get(1), "modId"),
            requiredArrayString(row.get(2), "modName"),
            requiredArrayString(row.get(3), "version"),
            parseDisposition(requiredArrayString(row.get(4), "disposition")),
            notionRecord,
            evidence,
            notes
        );
    }

    private static EntryDefaults parseDefaults(JsonObject object) {
        return new EntryDefaults(
            requiredString(object, "notionRecord"),
            stringList(requiredArray(object, "evidence")),
            requiredString(object, "notes")
        );
    }

    private static JsonObject parseObject(InputStream input, String label) {
        JsonElement parsed = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        if (!parsed.isJsonObject()) {
            throw new IllegalArgumentException(label + " must be a JSON object");
        }
        return parsed.getAsJsonObject();
    }

    private static ModAuditDisposition parseDisposition(String value) {
        try {
            return ModAuditDisposition.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown material audit disposition: " + value, exception);
        }
    }

    private static String overrideString(JsonObject override, String key, String fallback) {
        return override != null && override.has(key) ? requiredString(override, key) : fallback;
    }

    private static List<String> overrideArray(JsonObject override, String key, List<String> fallback) {
        return override != null && override.has(key) ? stringList(requiredArray(override, key)) : fallback;
    }

    private static String requiredString(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("missing or non-string material audit field: " + key);
        }
        return requiredArrayString(value, key);
    }

    private static String requiredArrayString(JsonElement value, String key) {
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("missing or non-string material audit value: " + key);
        }
        String text = value.getAsString();
        if (text.isBlank()) {
            throw new IllegalArgumentException("blank material audit value: " + key);
        }
        return text;
    }

    private static int requiredInt(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
            throw new IllegalArgumentException("missing or non-integer material audit field: " + key);
        }
        return value.getAsInt();
    }

    private static JsonArray requiredArray(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonArray()) {
            throw new IllegalArgumentException("missing or non-array material audit field: " + key);
        }
        return value.getAsJsonArray();
    }

    private static JsonObject requiredObject(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonObject()) {
            throw new IllegalArgumentException("missing or non-object material audit field: " + key);
        }
        return value.getAsJsonObject();
    }

    private static List<String> stringList(JsonArray array) {
        List<String> values = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            values.add(requiredArrayString(element, "evidence"));
        }
        return List.copyOf(values);
    }

    private record EntryDefaults(String notionRecord, List<String> evidence, String notes) {
        private EntryDefaults {
            Objects.requireNonNull(notionRecord, "notionRecord");
            evidence = List.copyOf(Objects.requireNonNull(evidence, "evidence"));
            Objects.requireNonNull(notes, "notes");
        }
    }
}
