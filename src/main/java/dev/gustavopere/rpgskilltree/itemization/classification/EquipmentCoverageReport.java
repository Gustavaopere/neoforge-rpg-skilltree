package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record EquipmentCoverageReport(
    List<EquipmentCoverageEntry> entries,
    List<ResourceLocation> ignoredPotentialEquipment
) {
    public EquipmentCoverageReport {
        entries = List.copyOf(Objects.requireNonNull(entries, "entries"));
        ignoredPotentialEquipment = List.copyOf(Objects.requireNonNull(ignoredPotentialEquipment, "ignoredPotentialEquipment"));
    }

    public static EquipmentCoverageReport generate(List<EquipmentProbe> probes, EquipmentClassifier classifier) {
        Objects.requireNonNull(probes, "probes");
        Objects.requireNonNull(classifier, "classifier");

        ArrayList<EquipmentCoverageEntry> entries = new ArrayList<>();
        ArrayList<ResourceLocation> ignored = new ArrayList<>();
        for (EquipmentProbe probe : probes) {
            EquipmentClassification classification = classifier.classify(probe);
            entries.add(new EquipmentCoverageEntry(probe.itemId(), classification));
            if (probe.potentiallyEquipment() && !classification.eligible()) {
                ignored.add(probe.itemId());
            }
        }

        entries.sort(Comparator.comparing(entry -> entry.itemId().toString()));
        ignored.sort(Comparator.comparing(ResourceLocation::toString));
        return new EquipmentCoverageReport(entries, ignored);
    }
}
