package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Fail-closed validator for an atomic assembly candidate. */
public final class BlacksmithCompositionValidator {
    private BlacksmithCompositionValidator() {}

    public static void validate(
        AssemblyDefinition assembly,
        BlacksmithComposition composition,
        Map<ResourceLocation, PartDefinition> partDefinitions,
        Map<ResourceLocation, BlacksmithMaterialProfile> materials
    ) {
        Objects.requireNonNull(assembly, "assembly");
        Objects.requireNonNull(composition, "composition");
        Objects.requireNonNull(partDefinitions, "partDefinitions");
        Objects.requireNonNull(materials, "materials");

        if (!assembly.id().equals(composition.assemblyDefinitionId())) {
            throw new IllegalArgumentException("composition does not target assembly " + assembly.id());
        }

        for (String required : assembly.requiredSlots().keySet()) {
            if (!composition.parts().containsKey(required)) {
                throw new IllegalArgumentException("missing required part slot: " + required);
            }
        }

        Set<String> allowedSlots = new HashSet<>(assembly.requiredSlots().keySet());
        allowedSlots.addAll(assembly.optionalSlots().keySet());
        for (Map.Entry<String, BlacksmithPartState> entry : composition.parts().entrySet()) {
            String slot = entry.getKey();
            if (!allowedSlots.contains(slot)) {
                throw new IllegalArgumentException("unknown assembly slot: " + slot);
            }
            PartFamily expectedFamily = assembly.requiredSlots().get(slot);
            if (expectedFamily == null) {
                expectedFamily = assembly.optionalSlots().get(slot);
            }

            BlacksmithPartState state = entry.getValue();
            PartDefinition definition = partDefinitions.get(state.partDefinitionId());
            if (definition == null) {
                throw new IllegalArgumentException("unknown part definition: " + state.partDefinitionId());
            }
            if (definition.partFamily() != expectedFamily) {
                throw new IllegalArgumentException("wrong part family for slot " + slot);
            }

            BlacksmithMaterialProfile material = materials.get(state.materialProfileId());
            if (material == null) {
                throw new IllegalArgumentException("unknown material profile: " + state.materialProfileId());
            }
            if (!material.allowedPartFamilies().contains(definition.partFamily())) {
                throw new IllegalArgumentException("material does not allow part family " + definition.partFamily());
            }
            if (!definition.allowedMaterialClasses().contains(material.materialClass())) {
                throw new IllegalArgumentException("part does not allow material class " + material.materialClass());
            }
            for (ProcessState requiredState : definition.processRequirements()) {
                if (!state.processState().satisfies(requiredState)) {
                    throw new IllegalArgumentException("part has not reached required process state " + requiredState);
                }
            }
        }
    }
}
