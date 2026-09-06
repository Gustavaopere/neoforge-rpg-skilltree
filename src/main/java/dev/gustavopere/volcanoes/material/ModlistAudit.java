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
        JsonObject root = JsonParser.parseReader(
            new InputStreamReader(input, StandardCharsets.UTF_8)
        ).getAsJsonObject();

        int schemaVersion = requiredInt(root, "schemaVersion");
        String snapshotDate = requiredString(root, "snapshotDate");
        String sourceFile = requiredString(root, "sourceFile");
        String sourceSha256 = requiredString(root, "sourceSha256");
        int topLevelJarCount = requiredInt(root, "topLevelJarCount");
        String notionDatabase = requiredString(root, "notionDatabase");
        JsonArray rawEntries = requiredArray(root, "entries");

        List<ModAuditEntry> entries = new ArrayList<>(rawEntries.size());
        Set<String> uniqueTuples = new HashSet<>(rawEntries.size());
        for (JsonElement element : rawEntries) {
            if (!element.isJsonObject()) {
                throw new IllegalArgumentException("material audit entry must be an object");
            }
            JsonObject object = element.getAsJsonObject();
            ModAuditEntry entry = new ModAuditEntry(
                requiredString(object, "jar"),
                requiredString(object, "modId"),
                requiredString(object, "modName"),
                requiredString(object, "version"),
                parseDisposition(requiredString(object, "disposition")),
                requiredString(object, "notionRecord"),
                stringList(requiredArray(object, "evidence")),
                requiredString(object, "notes")
            );
            String tuple = entry.jar() + '\u0000' + entry.modId() + '\u0000' + entry.version();
            if (!uniqueTuples.add(tuple)) {
                throw new IllegalArgumentException("duplicate material audit tuple: " + entry.jar());
            }
            entries.add(entry);
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

    private static ModAuditDisposition parseDisposition(String value) {
        try {
            return ModAuditDisposition.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("unknown material audit disposition: " + value, exception);
        }
    }

    private static String requiredString(JsonObject object, String key) {
        JsonElement value = object.get(key);
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("missing or non-string material audit field: " + key);
        }
        String text = value.getAsString();
        if (text.isBlank()) {
            throw new IllegalArgumentException("blank material audit field: " + key);
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

    private static List<String> stringList(JsonArray array) {
        List<String> values = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("material audit evidence entries must be strings");
            }
            String value = element.getAsString();
            if (value.isBlank()) {
                throw new IllegalArgumentException("material audit evidence entries must not be blank");
            }
            values.add(value);
        }
        return List.copyOf(values);
    }
}
