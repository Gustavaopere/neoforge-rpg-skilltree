package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BlacksmithDomainContractTest {
    private static final ResourceLocation STEEL = id("blacksmith", "steel");
    private static final ResourceLocation BRONZE = id("blacksmith", "bronze");
    private static final ResourceLocation METAL = id("blacksmith", "metal");

    @Test
    void vocabularyIsCanonicalAndIndependentFromRpgRank() {
        assertEquals(
            List.of("BLADE", "GUARD", "GRIP", "TOOL_HEAD", "HANDLE", "SPEAR_HEAD", "HAMMER_HEAD", "ARMOR_PLATE", "ARMOR_LINING", "BINDING"),
            List.of(PartFamily.values()).stream().map(Enum::name).toList()
        );
        assertEquals(
            List.of("CAST", "SHAPED", "HEAT_TREATED", "FINISHED"),
            List.of(ProcessState.values()).stream().map(Enum::name).toList()
        );
        assertEquals(
            List.of("IMPERFECT", "STANDARD", "PRECISE", "MASTERFUL", "MASTERPIECE"),
            List.of(WorkmanshipBand.values()).stream().map(Enum::name).toList()
        );
        assertEquals(
            List.of("DURABILITY", "ATTACK_DAMAGE", "ATTACK_SPEED", "MINING_SPEED", "ARMOR", "ARMOR_TOUGHNESS", "KNOCKBACK_RESISTANCE", "ENCHANTABILITY", "MASS"),
            List.of(PhysicalStat.values()).stream().map(Enum::name).toList()
        );
    }

    @Test
    void materialProfilesAreValidatedAndDefensivelyCopied() {
        HashMap<PhysicalStat, Double> factors = new HashMap<>();
        factors.put(PhysicalStat.DURABILITY, 1.25D);
        MechanicalProfile mechanical = new MechanicalProfile(factors);

        HashMap<ResourceLocation, Boolean> itemIds = new HashMap<>();
        itemIds.put(id("minecraft", "iron_ingot"), true);

        BlacksmithMaterialProfile steel = new BlacksmithMaterialProfile(
            STEEL,
            1,
            "examplemetals",
            itemIds.keySet(),
            Set.of(id("c", "ingots/steel")),
            Set.of(id("productivemetalworks", "molten_steel")),
            Set.of(id("c", "molten_steel")),
            METAL,
            Optional.of(id("c", "ingots/steel")),
            mechanical,
            Set.of(PartFamily.BLADE, PartFamily.TOOL_HEAD),
            Set.of(id("blacksmith", "edge_retention")),
            "material.blacksmith.steel"
        );

        itemIds.clear();
        factors.put(PhysicalStat.DURABILITY, 99.0D);

        assertEquals(STEEL, steel.id());
        assertEquals(1.25D, steel.mechanicalProfile().value(PhysicalStat.DURABILITY));
        assertEquals(Set.of(id("minecraft", "iron_ingot")), steel.itemIds());
        assertThrows(UnsupportedOperationException.class, () -> steel.itemIds().add(id("minecraft", "gold_ingot")));
        assertThrows(IllegalArgumentException.class, () -> new MechanicalProfile(Map.of(PhysicalStat.MASS, -0.1D)));
        assertThrows(IllegalArgumentException.class, () -> new MechanicalProfile(Map.of(PhysicalStat.MASS, Double.NaN)));
    }

    @Test
    void castablePartsRequireProviderCastContract() {
        PartDefinition blade = bladeDefinition();
        assertTrue(blade.castable());
        assertEquals(90L, blade.requiredFluidUnits());
        assertEquals(Optional.of(id("blacksmith", "blade_cast")), blade.castItem());

        assertThrows(
            IllegalArgumentException.class,
            () -> new PartDefinition(
                id("blacksmith", "broken_blade"), PartFamily.BLADE, true, Optional.empty(), 90L,
                Set.of(METAL), Map.of(PhysicalStat.ATTACK_DAMAGE, 1.0D), Set.of(ProcessState.CAST),
                id("blacksmith", "blade"), "part.blacksmith.blade"
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new PartDefinition(
                id("blacksmith", "free_blade"), PartFamily.BLADE, true, Optional.of(id("blacksmith", "blade_cast")), 0L,
                Set.of(METAL), Map.of(PhysicalStat.ATTACK_DAMAGE, 1.0D), Set.of(ProcessState.CAST),
                id("blacksmith", "blade"), "part.blacksmith.blade"
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new PartDefinition(
                id("blacksmith", "wood_grip"), PartFamily.GRIP, false, Optional.of(id("blacksmith", "grip_cast")), 0L,
                Set.of(id("blacksmith", "wood")), Map.of(PhysicalStat.ATTACK_SPEED, 1.0D), Set.of(),
                id("blacksmith", "grip"), "part.blacksmith.grip"
            )
        );
    }

    @Test
    void assemblyDefinitionsRejectAmbiguousSlotsAndRemainImmutable() {
        LinkedHashMap<String, PartFamily> required = new LinkedHashMap<>();
        required.put("blade", PartFamily.BLADE);
        required.put("guard", PartFamily.GUARD);
        HashMap<String, PartFamily> optional = new HashMap<>();
        optional.put("pommel", PartFamily.BINDING);

        AssemblyDefinition sword = new AssemblyDefinition(
            id("blacksmith", "modular_sword"),
            id("minecraft", "sword"),
            required,
            optional,
            Map.of(PhysicalStat.ATTACK_DAMAGE, 3.0D, PhysicalStat.ATTACK_SPEED, 1.6D),
            id("blacksmith", "modular_sword"),
            Set.of(id("minecraft", "swords"))
        );

        required.clear();
        optional.clear();
        assertEquals(Set.of("blade", "guard"), sword.requiredSlots().keySet());
        assertEquals(Set.of("pommel"), sword.optionalSlots().keySet());
        assertThrows(UnsupportedOperationException.class, () -> sword.requiredSlots().put("grip", PartFamily.GRIP));

        assertThrows(
            IllegalArgumentException.class,
            () -> new AssemblyDefinition(
                id("blacksmith", "ambiguous"), id("minecraft", "sword"),
                Map.of("part", PartFamily.BLADE), Map.of("part", PartFamily.GUARD),
                Map.of(), id("blacksmith", "ambiguous"), Set.of()
            )
        );
    }

    @Test
    void compositionValidationIsAtomicAndRejectsWrongFamiliesOrUnknownMaterials() {
        PartDefinition blade = bladeDefinition();
        PartDefinition guard = guardDefinition();
        AssemblyDefinition assembly = swordAssembly();
        BlacksmithMaterialProfile steel = metal(STEEL, Set.of(PartFamily.BLADE, PartFamily.GUARD));
        BlacksmithMaterialProfile bronze = metal(BRONZE, Set.of(PartFamily.BLADE, PartFamily.GUARD));

        Map<ResourceLocation, PartDefinition> partDefinitions = Map.of(blade.id(), blade, guard.id(), guard);
        Map<ResourceLocation, BlacksmithMaterialProfile> materials = Map.of(STEEL, steel, BRONZE, bronze);

        BlacksmithPartState bladeState = partState(blade.id(), STEEL, ProcessState.FINISHED);
        BlacksmithPartState guardState = partState(guard.id(), BRONZE, ProcessState.FINISHED);
        BlacksmithComposition valid = BlacksmithComposition.of(
            assembly.id(),
            Map.of("blade", bladeState, "guard", guardState),
            84.0D,
            WorkmanshipBand.MASTERFUL,
            Set.of(ProcessState.CAST, ProcessState.SHAPED, ProcessState.HEAT_TREATED, ProcessState.FINISHED),
            1
        );

        BlacksmithCompositionValidator.validate(assembly, valid, partDefinitions, materials);
        assertEquals(List.of(BRONZE, STEEL), valid.resolvedMaterialIds());
        assertThrows(UnsupportedOperationException.class, () -> valid.parts().put("other", bladeState));

        BlacksmithComposition wrongFamily = BlacksmithComposition.of(
            assembly.id(),
            Map.of("blade", guardState, "guard", guardState),
            80.0D, WorkmanshipBand.PRECISE, Set.of(ProcessState.FINISHED), 1
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithCompositionValidator.validate(assembly, wrongFamily, partDefinitions, materials)
        );

        BlacksmithComposition unknownMaterial = BlacksmithComposition.of(
            assembly.id(),
            Map.of("blade", partState(blade.id(), id("blacksmith", "missing"), ProcessState.FINISHED), "guard", guardState),
            80.0D, WorkmanshipBand.PRECISE, Set.of(ProcessState.FINISHED), 1
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithCompositionValidator.validate(assembly, unknownMaterial, partDefinitions, materials)
        );
    }

    @Test
    void compositionSnapshotIsReadOnlyAndContainsOnlyPhysicalBlacksmithState() {
        BlacksmithComposition composition = BlacksmithComposition.of(
            id("blacksmith", "modular_sword"),
            Map.of(
                "blade", partState(id("blacksmith", "blade"), STEEL, ProcessState.FINISHED),
                "guard", partState(id("blacksmith", "guard"), BRONZE, ProcessState.SHAPED)
            ),
            91.0D,
            WorkmanshipBand.MASTERPIECE,
            Set.of(ProcessState.CAST, ProcessState.SHAPED, ProcessState.FINISHED),
            1
        );

        BlacksmithCompositionSnapshot snapshot = BlacksmithCompositionQueryService.snapshot(composition);
        assertEquals(composition.assemblyDefinitionId(), snapshot.assemblyDefinitionId());
        assertEquals(composition.parts(), snapshot.parts());
        assertEquals(List.of(BRONZE, STEEL), snapshot.resolvedMaterialIds());
        assertEquals(WorkmanshipBand.MASTERPIECE, snapshot.workmanshipBand());
        assertFalse(snapshot.getClass().getName().contains("ItemizationState"));
        assertThrows(UnsupportedOperationException.class, () -> snapshot.parts().clear());
    }

    @Test
    void invalidPersistentStateFailsClosed() {
        BlacksmithProvenance provenance = new BlacksmithProvenance(
            id("productivemetalworks", "provider"),
            id("blacksmith", "casting/blade_steel")
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new BlacksmithPartState(id("blacksmith", "blade"), STEEL, 0, ProcessState.CAST, 1.0D, provenance)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new BlacksmithPartState(id("blacksmith", "blade"), STEEL, 1, ProcessState.CAST, Double.NaN, provenance)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> BlacksmithComposition.of(
                id("blacksmith", "modular_sword"), Map.of(), 1.0D, WorkmanshipBand.STANDARD, Set.of(), 1
            )
        );
    }

    private static PartDefinition bladeDefinition() {
        return new PartDefinition(
            id("blacksmith", "blade"), PartFamily.BLADE, true, Optional.of(id("blacksmith", "blade_cast")), 90L,
            Set.of(METAL),
            Map.of(PhysicalStat.ATTACK_DAMAGE, 1.0D, PhysicalStat.DURABILITY, 0.7D, PhysicalStat.MASS, 0.5D),
            Set.of(ProcessState.CAST),
            id("blacksmith", "blade"), "part.blacksmith.blade"
        );
    }

    private static PartDefinition guardDefinition() {
        return new PartDefinition(
            id("blacksmith", "guard"), PartFamily.GUARD, true, Optional.of(id("blacksmith", "guard_cast")), 30L,
            Set.of(METAL),
            Map.of(PhysicalStat.DURABILITY, 0.3D, PhysicalStat.MASS, 0.3D),
            Set.of(ProcessState.CAST),
            id("blacksmith", "guard"), "part.blacksmith.guard"
        );
    }

    private static AssemblyDefinition swordAssembly() {
        return new AssemblyDefinition(
            id("blacksmith", "modular_sword"), id("minecraft", "sword"),
            Map.of("blade", PartFamily.BLADE, "guard", PartFamily.GUARD), Map.of(),
            Map.of(PhysicalStat.ATTACK_DAMAGE, 3.0D, PhysicalStat.ATTACK_SPEED, 1.6D),
            id("blacksmith", "modular_sword"), Set.of(id("minecraft", "swords"))
        );
    }

    private static BlacksmithMaterialProfile metal(ResourceLocation materialId, Set<PartFamily> families) {
        return new BlacksmithMaterialProfile(
            materialId, 1, "test_provider",
            Set.of(id("minecraft", "iron_ingot")), Set.of(id("c", "ingots/test")),
            Set.of(id("productivemetalworks", "molten_test")), Set.of(id("c", "molten_test")),
            METAL, Optional.of(id("c", "ingots/test")),
            new MechanicalProfile(Map.of(PhysicalStat.DURABILITY, 1.0D, PhysicalStat.ATTACK_DAMAGE, 1.0D)),
            families, Set.of(), "material.blacksmith.test"
        );
    }

    private static BlacksmithPartState partState(ResourceLocation definition, ResourceLocation material, ProcessState state) {
        return new BlacksmithPartState(
            definition,
            material,
            1,
            state,
            1.0D,
            new BlacksmithProvenance(id("productivemetalworks", "provider"), id("blacksmith", "casting/test"))
        );
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
