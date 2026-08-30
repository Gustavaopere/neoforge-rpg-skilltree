package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public interface EquipmentClassificationAdapter {
    ResourceLocation providerId();

    int priority();

    Optional<EquipmentAdapterContribution> classify(EquipmentProbe probe);
}
