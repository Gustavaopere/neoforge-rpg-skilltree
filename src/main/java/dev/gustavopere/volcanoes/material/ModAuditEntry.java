package dev.gustavopere.volcanoes.material;

import java.util.List;
import java.util.Objects;

/** One immutable top-level modlist row in the canonical material-provider audit manifest. */
public record ModAuditEntry(
    String jar,
    String modId,
    String modName,
    String version,
    ModAuditDisposition disposition,
    String notionRecord,
    List<String> evidence,
    String notes
) {
    public ModAuditEntry {
        jar = Objects.requireNonNull(jar, "jar");
        modId = Objects.requireNonNull(modId, "modId");
        modName = Objects.requireNonNull(modName, "modName");
        version = Objects.requireNonNull(version, "version");
        disposition = Objects.requireNonNull(disposition, "disposition");
        notionRecord = Objects.requireNonNull(notionRecord, "notionRecord");
        evidence = List.copyOf(Objects.requireNonNull(evidence, "evidence"));
        notes = Objects.requireNonNull(notes, "notes");
    }

    public boolean isComplete() {
        return !jar.isBlank()
            && !modId.isBlank()
            && !version.isBlank()
            && disposition != null;
    }
}
