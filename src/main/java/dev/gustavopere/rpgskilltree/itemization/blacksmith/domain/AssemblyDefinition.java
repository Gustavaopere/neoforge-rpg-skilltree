package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable recipe-independent definition of how functional part slots form one equipment archetype. */
public record AssemblyDefinition(
    ResourceLocation id,
    ResourceLocation outputArchetype,
    Map<String, PartFamily> requiredSlots,
    Map<String, PartFamily> optionalSlots,
    Map<PhysicalStat, Double> baseStats,
    ResourceLocation modelDefinition,
    Set<ResourceLocation> providerTags
) {
    public AssemblyDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(outputArchetype, "outputArchetype");
        requiredSlots = immutableSlots(requiredSlots, "requiredSlots");
        optionalSlots = immutableSlots(optionalSlots, "optionalSlots");
        if (requiredSlots.isEmpty()) {
            throw new IllegalArgumentException("requiredSlots must not be empty");
        }
        for (String slot : requiredSlots.keySet()) {
            if (optionalSlots.containsKey(slot)) {
                throw new IllegalArgumentException("slot cannot be both required and optional: " + slot);
            }
        }
        baseStats = immutableStats(baseStats);
        Objects.requireNonNull(modelDefinition, "modelDefinition");
        providerTags = Set.copyOf(Objects.requireNonNull(providerTags, "providerTags"));
    }

    private static Map<String, PartFamily> immutableSlots(Map<String, PartFamily> source, String name) {
        Objects.requireNonNull(source, name);
        LinkedHashMap<String, PartFamily> copy = new LinkedHashMap<>();
        for (Map.Entry<String, PartFamily> entry : source.entrySet()) {
            String slot = Objects.requireNonNull(entry.getKey(), name + " slot");
            if (slot.isBlank()) {
                throw new IllegalArgumentException(name + " slot must not be blank");
            }
            copy.put(slot, Objects.requireNonNull(entry.getValue(), name + " family"));
        }
        return Collections.unmodifiableMap(copy);
    }

    private static Map<PhysicalStat, Double> immutableStats(Map<PhysicalStat, Double> source) {
        Objects.requireNonNull(source, "baseStats");
        EnumMap<PhysicalStat, Double> copy = new EnumMap<>(PhysicalStat.class);
        for (Map.Entry<PhysicalStat, Double> entry : source.entrySet()) {
            PhysicalStat stat = Objects.requireNonNull(entry.getKey(), "base stat");
            Double value = Objects.requireNonNull(entry.getValue(), "base stat value");
            if (!Double.isFinite(value) || value < 0.0D) {
                throw new IllegalArgumentException("base stat values must be finite and >= 0");
            }
            copy.put(stat, value);
        }
        return Collections.unmodifiableMap(copy);
    }
}
