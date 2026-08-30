package dev.gustavopere.rpgskilltree.runtime.itemization;

import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassification;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassificationAdapter;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassifier;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;

/** Canonical runtime entry point for classifying an ItemStack against the active datapack snapshot. */
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
        Objects.requireNonNull(adapters, "adapters");
        return new EquipmentClassifier(EquipmentClassificationOverrides.snapshot(), adapters)
            .classify(MinecraftEquipmentProbeFactory.from(stack));
    }
}
