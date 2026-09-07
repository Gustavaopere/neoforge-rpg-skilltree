package dev.gustavopere.rpgskilltree.runtime.itemization;

import dev.gustavopere.rpgskilltree.itemization.classification.EligibilityOverride;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentAdapterContribution;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentCategory;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassificationAdapter;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentProbe;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.resources.ResourceLocation;

/**
 * Classifies Curios wearables through Curios' vanilla item-tag contract without linking provider API classes.
 */
public final class CuriosTagEquipmentClassificationAdapter implements EquipmentClassificationAdapter {
    private static final String CURIOS_NAMESPACE = "curios";
    private static final ResourceLocation PROVIDER_ID = ResourceLocation.fromNamespaceAndPath(
        CURIOS_NAMESPACE,
        "item_tag_slots"
    );
    private static final int PRIORITY = 100;

    private final BooleanSupplier curiosLoaded;

    public CuriosTagEquipmentClassificationAdapter() {
        this(() -> OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CURIOS));
    }

    public CuriosTagEquipmentClassificationAdapter(BooleanSupplier curiosLoaded) {
        this.curiosLoaded = curiosLoaded;
    }

    @Override
    public ResourceLocation providerId() {
        return PROVIDER_ID;
    }

    @Override
    public int priority() {
        return PRIORITY;
    }

    @Override
    public Optional<EquipmentAdapterContribution> classify(EquipmentProbe probe) {
        if (!curiosLoaded.getAsBoolean()) {
            return Optional.empty();
        }

        EnumSet<EquipmentCategory> categories = EnumSet.noneOf(EquipmentCategory.class);
        for (ResourceLocation tag : probe.tags()) {
            if (!CURIOS_NAMESPACE.equals(tag.getNamespace())) {
                continue;
            }
            categories.add(categoryForSlot(tag.getPath()));
        }

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new EquipmentAdapterContribution(EligibilityOverride.WHITELIST, categories));
    }

    private static EquipmentCategory categoryForSlot(String slotId) {
        return switch (slotId) {
            case "ring" -> EquipmentCategory.WEARABLE_RING;
            case "necklace" -> EquipmentCategory.WEARABLE_NECKLACE;
            case "amulet" -> EquipmentCategory.WEARABLE_AMULET;
            default -> EquipmentCategory.WEARABLE_OTHER;
        };
    }
}
