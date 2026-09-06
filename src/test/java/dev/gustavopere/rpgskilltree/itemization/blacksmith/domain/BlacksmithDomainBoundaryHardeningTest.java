package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BlacksmithDomainBoundaryHardeningTest {
    private static final ResourceLocation METAL = id("blacksmith", "metal");
    private static final ResourceLocation WOOD = id("blacksmith", "wood");

    @Test
    void snapshotRecordComponentsDoNotDependOnRpgItemizationDomain() {
        assertTrue(
            Arrays.stream(BlacksmithCompositionSnapshot.class.getRecordComponents())
                .map(RecordComponent::getGenericType)
                .map(type -> type.getTypeName())
                .noneMatch(typeName -> typeName.contains("dev.gustavopere.rpgskilltree.itemization.domain.")),
            "Blacksmith physical snapshot must not embed Stage 11 RPG itemization state"
        );
    }

    @Test
    void validatorRejectsKnownMaterialsWithWrongFamilyOrMaterialClass() {
        PartDefinition blade = new PartDefinition(
            id("blacksmith", "blade"),
            PartFamily.BLADE,
            true,
            Optional.of(id("blacksmith", "blade_cast")),
            90L,
            Set.of(METAL),
            Map.of(PhysicalStat.ATTACK_DAMAGE, 1.0D),
            Set.of(ProcessState.CAST),
            id("blacksmith", "blade"),
            "part.blacksmith.blade"
        );
        AssemblyDefinition sword = new AssemblyDefinition(
            id("blacksmith", "modular_sword"),
            id("minecraft", "sword"),
            Map.of("blade", PartFamily.BLADE),
            Map.of(),
            Map.of(PhysicalStat.ATTACK_DAMAGE, 3.0D),
            id("blacksmith", "modular_sword"),
            Set.of(id("minecraft", "swords"))
        );

        ResourceLocation guardOnlyId = id("blacksmith", "guard_only_metal");
        BlacksmithMaterialProfile guardOnly = material(
            guardOnlyId,
            METAL,
            Set.of(PartFamily.GUARD)
        );
        ResourceLocation woodBladeId = id("blacksmith", "wood_blade_material");
        BlacksmithMaterialProfile woodBlade = material(
            woodBladeId,
            WOOD,
            Set.of(PartFamily.BLADE)
        );
        Map<ResourceLocation, PartDefinition> parts = Map.of(blade.id(), blade);
        Map<ResourceLocation, BlacksmithMaterialProfile> materials = Map.of(
            guardOnly.id(), guardOnly,
            woodBlade.id(), woodBlade
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithCompositionValidator.validate(
                sword,
                composition(sword.id(), blade.id(), guardOnlyId),
                parts,
                materials
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithCompositionValidator.validate(
                sword,
                composition(sword.id(), blade.id(), woodBladeId),
                parts,
                materials
            )
        );
    }

    private static BlacksmithMaterialProfile material(
        ResourceLocation materialId,
        ResourceLocation materialClass,
        Set<PartFamily> families
    ) {
        return new BlacksmithMaterialProfile(
            materialId,
            1,
            "test_provider",
            Set.of(id("minecraft", "iron_ingot")),
            Set.of(),
            Set.of(id("productivemetalworks", "molten_test")),
            Set.of(),
            materialClass,
            Optional.of(id("c", "ingots/test")),
            new MechanicalProfile(Map.of(PhysicalStat.ATTACK_DAMAGE, 1.0D)),
            families,
            Set.of(),
            "material.blacksmith.test"
        );
    }

    private static BlacksmithComposition composition(
        ResourceLocation assemblyId,
        ResourceLocation partDefinitionId,
        ResourceLocation materialId
    ) {
        return BlacksmithComposition.of(
            assemblyId,
            Map.of(
                "blade",
                new BlacksmithPartState(
                    partDefinitionId,
                    materialId,
                    1,
                    ProcessState.FINISHED,
                    1.0D,
                    new BlacksmithProvenance(
                        id("productivemetalworks", "provider"),
                        id("blacksmith", "casting/test")
                    )
                )
            ),
            80.0D,
            WorkmanshipBand.PRECISE,
            Set.of(ProcessState.CAST, ProcessState.FINISHED),
            1
        );
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
