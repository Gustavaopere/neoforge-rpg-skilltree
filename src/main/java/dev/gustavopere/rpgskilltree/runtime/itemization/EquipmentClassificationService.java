package dev.gustavopere.rpgskilltree.runtime.itemization;

import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassification;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassificationAdapter;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassifier;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentProbe;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;

/** Canonical runtime entry point for classifying equipment against the active datapack snapshot. */
public final class EquipmentClassificationService {
    private EquipmentClassificationService() {}

    public static EquipmentClassification classify(ItemStack stack) {
        return classify(stack, List.of());
    }

    public static EquipmentClassification classify(
        ItemStack stack,
        List<EquipmentClassificationAdapter> adapters
    ) {
        Objects.requireNonNull(stack, "stack");
        return classify(MinecraftEquipmentProbeFactory.from(stack), adapters);
    }

    public static EquipmentClassification classify(EquipmentProbe probe) {
        return classify(probe, List.of());
    }

    public static EquipmentClassification classify(
        EquipmentProbe probe,
        List<EquipmentClassificationAdapter> adapters
    ) {
        Objects.requireNonNull(probe, "probe");
        Objects.requireNonNull(adapters, "adapters");
        return new EquipmentClassifier(EquipmentClassificationOverrides.snapshot(), adapters).classify(probe);
    }
}
