package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable physical composition of an assembled Blacksmith equipment instance. */
public final class BlacksmithComposition {
    private final ResourceLocation assemblyDefinitionId;
    private final Map<String, BlacksmithPartState> parts;
    private final List<ResourceLocation> resolvedMaterialIds;
    private final double workmanshipScore;
    private final WorkmanshipBand workmanshipBand;
    private final Set<ProcessState> processSummary;
    private final int schemaVersion;

    private BlacksmithComposition(
        ResourceLocation assemblyDefinitionId,
        Map<String, BlacksmithPartState> parts,
        double workmanshipScore,
        WorkmanshipBand workmanshipBand,
        Set<ProcessState> processSummary,
        int schemaVersion
    ) {
        this.assemblyDefinitionId = Objects.requireNonNull(assemblyDefinitionId, "assemblyDefinitionId");
        Objects.requireNonNull(parts, "parts");
        if (parts.isEmpty()) {
            throw new IllegalArgumentException("parts must not be empty");
        }
        LinkedHashMap<String, BlacksmithPartState> partCopy = new LinkedHashMap<>();
        for (Map.Entry<String, BlacksmithPartState> entry : parts.entrySet()) {
            String slot = Objects.requireNonNull(entry.getKey(), "part slot");
            if (slot.isBlank()) {
                throw new IllegalArgumentException("part slot must not be blank");
            }
            partCopy.put(slot, Objects.requireNonNull(entry.getValue(), "part state"));
        }
        this.parts = Collections.unmodifiableMap(partCopy);
        this.resolvedMaterialIds = resolveMaterials(partCopy.values());
        if (!Double.isFinite(workmanshipScore) || workmanshipScore < 0.0D) {
            throw new IllegalArgumentException("workmanshipScore must be finite and >= 0");
        }
        this.workmanshipScore = workmanshipScore;
        this.workmanshipBand = Objects.requireNonNull(workmanshipBand, "workmanshipBand");
        this.processSummary = Collections.unmodifiableSet(
            new LinkedHashSet<>(Objects.requireNonNull(processSummary, "processSummary"))
        );
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be >= 1");
        }
        this.schemaVersion = schemaVersion;
    }

    public static BlacksmithComposition of(
        ResourceLocation assemblyDefinitionId,
        Map<String, BlacksmithPartState> parts,
        double workmanshipScore,
        WorkmanshipBand workmanshipBand,
        Set<ProcessState> processSummary,
        int schemaVersion
    ) {
        return new BlacksmithComposition(
            assemblyDefinitionId, parts, workmanshipScore, workmanshipBand, processSummary, schemaVersion
        );
    }

    private static List<ResourceLocation> resolveMaterials(Iterable<BlacksmithPartState> parts) {
        ArrayList<ResourceLocation> materials = new ArrayList<>();
        for (BlacksmithPartState part : parts) {
            if (!materials.contains(part.materialProfileId())) {
                materials.add(part.materialProfileId());
            }
        }
        materials.sort(Comparator.comparing(ResourceLocation::toString));
        return List.copyOf(materials);
    }

    public ResourceLocation assemblyDefinitionId() { return assemblyDefinitionId; }
    public Map<String, BlacksmithPartState> parts() { return parts; }
    public List<ResourceLocation> resolvedMaterialIds() { return resolvedMaterialIds; }
    public double workmanshipScore() { return workmanshipScore; }
    public WorkmanshipBand workmanshipBand() { return workmanshipBand; }
    public Set<ProcessState> processSummary() { return processSummary; }
    public int schemaVersion() { return schemaVersion; }
}
