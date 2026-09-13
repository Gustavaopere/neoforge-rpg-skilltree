package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Provider-neutral physical material profile. Provider integrations bind IDs outside this domain. */
public record BlacksmithMaterialProfile(
    ResourceLocation id,
    int schemaVersion,
    String sourceMod,
    Set<ResourceLocation> itemIds,
    Set<ResourceLocation> itemTags,
    Set<ResourceLocation> moltenFluidIds,
    Set<ResourceLocation> moltenFluidTags,
    ResourceLocation materialClass,
    Optional<ResourceLocation> repairIngredient,
    MechanicalProfile mechanicalProfile,
    Set<PartFamily> allowedPartFamilies,
    Set<ResourceLocation> traits,
    String displayKey
) {
    public BlacksmithMaterialProfile {
        Objects.requireNonNull(id, "id");
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be >= 1");
        }
        sourceMod = requireNonBlank(sourceMod, "sourceMod");
        itemIds = Set.copyOf(Objects.requireNonNull(itemIds, "itemIds"));
        itemTags = Set.copyOf(Objects.requireNonNull(itemTags, "itemTags"));
        moltenFluidIds = Set.copyOf(Objects.requireNonNull(moltenFluidIds, "moltenFluidIds"));
        moltenFluidTags = Set.copyOf(Objects.requireNonNull(moltenFluidTags, "moltenFluidTags"));
        Objects.requireNonNull(materialClass, "materialClass");
        repairIngredient = Objects.requireNonNull(repairIngredient, "repairIngredient");
        Objects.requireNonNull(mechanicalProfile, "mechanicalProfile");
        allowedPartFamilies = Set.copyOf(Objects.requireNonNull(allowedPartFamilies, "allowedPartFamilies"));
        if (allowedPartFamilies.isEmpty()) {
            throw new IllegalArgumentException("allowedPartFamilies must not be empty");
        }
        traits = Set.copyOf(Objects.requireNonNull(traits, "traits"));
        displayKey = requireNonBlank(displayKey, "displayKey");
    }

    private static String requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
