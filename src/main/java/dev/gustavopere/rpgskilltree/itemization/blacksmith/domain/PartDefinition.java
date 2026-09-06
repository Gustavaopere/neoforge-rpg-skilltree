package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Geometry/function definition for one modular part family, independent from concrete materials. */
public record PartDefinition(
    ResourceLocation id,
    PartFamily partFamily,
    boolean castable,
    Optional<ResourceLocation> castItem,
    long requiredFluidUnits,
    Set<ResourceLocation> allowedMaterialClasses,
    Map<PhysicalStat, Double> statWeights,
    Set<ProcessState> processRequirements,
    ResourceLocation modelKey,
    String displayKey
) {
    public PartDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(partFamily, "partFamily");
        castItem = Objects.requireNonNull(castItem, "castItem");
        if (castable) {
            if (castItem.isEmpty()) {
                throw new IllegalArgumentException("castable part requires castItem");
            }
            if (requiredFluidUnits <= 0L) {
                throw new IllegalArgumentException("castable part requires positive fluid units");
            }
        } else {
            if (castItem.isPresent()) {
                throw new IllegalArgumentException("non-castable part cannot declare castItem");
            }
            if (requiredFluidUnits != 0L) {
                throw new IllegalArgumentException("non-castable part cannot require casting fluid");
            }
        }
        allowedMaterialClasses = Set.copyOf(Objects.requireNonNull(allowedMaterialClasses, "allowedMaterialClasses"));
        if (allowedMaterialClasses.isEmpty()) {
            throw new IllegalArgumentException("allowedMaterialClasses must not be empty");
        }
        statWeights = immutableStats(statWeights, "statWeights");
        processRequirements = Set.copyOf(Objects.requireNonNull(processRequirements, "processRequirements"));
        Objects.requireNonNull(modelKey, "modelKey");
        displayKey = requireNonBlank(displayKey, "displayKey");
    }

    private static Map<PhysicalStat, Double> immutableStats(Map<PhysicalStat, Double> source, String name) {
        Objects.requireNonNull(source, name);
        EnumMap<PhysicalStat, Double> copy = new EnumMap<>(PhysicalStat.class);
        for (Map.Entry<PhysicalStat, Double> entry : source.entrySet()) {
            PhysicalStat stat = Objects.requireNonNull(entry.getKey(), name + " stat");
            Double value = Objects.requireNonNull(entry.getValue(), name + " value");
            if (!Double.isFinite(value) || value < 0.0D) {
                throw new IllegalArgumentException(name + " values must be finite and >= 0");
            }
            copy.put(stat, value);
        }
        return Collections.unmodifiableMap(copy);
    }

    private static String requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
