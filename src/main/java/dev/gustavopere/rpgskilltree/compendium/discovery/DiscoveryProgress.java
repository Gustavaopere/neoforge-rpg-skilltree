package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class DiscoveryProgress {
    private static final DiscoveryProgress EMPTY = new DiscoveryProgress(Map.of());

    private final Map<CompendiumEntryId, DiscoveryRecord> records;

    public DiscoveryProgress(Map<CompendiumEntryId, DiscoveryRecord> records) {
        if (records == null || records.isEmpty()) {
            this.records = Map.of();
            return;
        }
        LinkedHashMap<CompendiumEntryId, DiscoveryRecord> copy = new LinkedHashMap<>();
        records.forEach((id, record) -> {
            Objects.requireNonNull(id, "discovery entry id");
            Objects.requireNonNull(record, "discovery record");
            if (!id.equals(record.entryId())) {
                throw new IllegalArgumentException("discovery map key must match record entry id: " + id.serializedId());
            }
            if (copy.put(id, record) != null) {
                throw new IllegalArgumentException("duplicate discovery entry id: " + id.serializedId());
            }
        });
        this.records = Map.copyOf(copy);
    }

    public static DiscoveryProgress empty() {
        return EMPTY;
    }

    public Optional<DiscoveryRecord> record(CompendiumEntryId entryId) {
        Objects.requireNonNull(entryId, "entryId");
        return Optional.ofNullable(records.get(entryId));
    }

    public DiscoveryProgress withRecord(DiscoveryRecord record) {
        Objects.requireNonNull(record, "record");
        DiscoveryRecord current = records.get(record.entryId());
        if (record.equals(current)) return this;
        LinkedHashMap<CompendiumEntryId, DiscoveryRecord> next = new LinkedHashMap<>(records);
        next.put(record.entryId(), record);
        return new DiscoveryProgress(next);
    }

    public Map<CompendiumEntryId, DiscoveryRecord> records() {
        return records;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DiscoveryProgress progress && records.equals(progress.records);
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }

    @Override
    public String toString() {
        return "DiscoveryProgress" + records;
    }
}
