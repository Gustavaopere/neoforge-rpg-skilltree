package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import dev.gustavopere.rpgskilltree.itemization.blacksmith.registry.BlacksmithMaterialProfiles;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BlacksmithMaterialCatalogContractTest {
    private static final Set<PartFamily> METALLIC_FAMILIES = Set.of(
        PartFamily.BLADE,
        PartFamily.GUARD,
        PartFamily.TOOL_HEAD,
        PartFamily.SPEAR_HEAD,
        PartFamily.HAMMER_HEAD,
        PartFamily.ARMOR_PLATE
    );

    @Test
    void auditedPackCatalogContainsOnlyProviderProvenMvpMaterials() {
        BlacksmithMaterialCatalog catalog = BlacksmithMaterialProfiles.auditedPackCatalog();

        assertEquals(
            Set.of(
                id("rpgskilltree", "iron"),
                id("rpgskilltree", "copper"),
                id("rpgskilltree", "gold"),
                id("rpgskilltree", "netherite"),
                id("rpgskilltree", "zinc"),
                id("rpgskilltree", "brass"),
                id("rpgskilltree", "steel"),
                id("rpgskilltree", "aluminum"),
                id("rpgskilltree", "lead"),
                id("rpgskilltree", "nickel"),
                id("rpgskilltree", "constantan")
            ),
            catalog.ids()
        );
        assertFalse(catalog.find(id("rpgskilltree", "bronze")).isPresent());
        assertFalse(catalog.find(id("rpgskilltree", "cast_iron")).isPresent());
        assertFalse(catalog.find(id("rpgskilltree", "tungsten")).isPresent());
        assertFalse(catalog.find(id("rpgskilltree", "obdurium")).isPresent());
        assertFalse(catalog.find(id("rpgskilltree", "lithium")).isPresent());
    }

    @Test
    void vanillaMaterialsUseCommonTagsAndConcreteRepairIngredients() {
        BlacksmithMaterialCatalog catalog = BlacksmithMaterialProfiles.auditedPackCatalog();

        assertMaterial(catalog.require(id("rpgskilltree", "iron")), "minecraft", id("minecraft", "iron_ingot"), "iron");
        assertMaterial(catalog.require(id("rpgskilltree", "copper")), "minecraft", id("minecraft", "copper_ingot"), "copper");
        assertMaterial(catalog.require(id("rpgskilltree", "gold")), "minecraft", id("minecraft", "gold_ingot"), "gold");
        assertMaterial(catalog.require(id("rpgskilltree", "netherite")), "minecraft", id("minecraft", "netherite_ingot"), "netherite");
    }

    @Test
    void createMetalsUseCreateAsMaterialProviderAndCommonUnificationTags() {
        BlacksmithMaterialCatalog catalog = BlacksmithMaterialProfiles.auditedPackCatalog();

        assertMaterial(catalog.require(id("rpgskilltree", "zinc")), "create", id("create", "zinc_ingot"), "zinc");
        assertMaterial(catalog.require(id("rpgskilltree", "brass")), "create", id("create", "brass_ingot"), "brass");
    }

    @Test
    void tfmgMetalsUseExactCommunityProviderItemsAndProductiveMetalworksCommonTags() {
        BlacksmithMaterialCatalog catalog = BlacksmithMaterialProfiles.auditedPackCatalog();

        assertMaterial(catalog.require(id("rpgskilltree", "steel")), "tfmg", id("tfmg", "steel_ingot"), "steel");
        assertMaterial(catalog.require(id("rpgskilltree", "aluminum")), "tfmg", id("tfmg", "aluminum_ingot"), "aluminum");
        assertMaterial(catalog.require(id("rpgskilltree", "lead")), "tfmg", id("tfmg", "lead_ingot"), "lead");
        assertMaterial(catalog.require(id("rpgskilltree", "nickel")), "tfmg", id("tfmg", "nickel_ingot"), "nickel");
        assertMaterial(catalog.require(id("rpgskilltree", "constantan")), "tfmg", id("tfmg", "constantan_ingot"), "constantan");
    }

    @Test
    void initialProfilesAreBoundedTraitFreeAndLimitedToMetallicPartFamilies() {
        for (BlacksmithMaterialProfile profile : BlacksmithMaterialProfiles.auditedPackCatalog().profiles()) {
            assertEquals(METALLIC_FAMILIES, profile.allowedPartFamilies());
            assertTrue(profile.traits().isEmpty(), () -> profile.id() + " must fail closed until a real trait hook exists");
            for (PhysicalStat stat : PhysicalStat.values()) {
                double factor = profile.mechanicalProfile().value(stat);
                assertTrue(factor >= 0.25D && factor <= 1.35D, () -> profile.id() + " has unbounded " + stat + "=" + factor);
            }
        }
    }

    @Test
    void catalogRejectsDuplicateIdsAndExposesImmutableSnapshots() {
        BlacksmithMaterialProfile iron = BlacksmithMaterialProfiles.auditedPackCatalog().require(id("rpgskilltree", "iron"));

        assertThrows(IllegalArgumentException.class, () -> new BlacksmithMaterialCatalog(List.of(iron, iron)));

        BlacksmithMaterialCatalog catalog = new BlacksmithMaterialCatalog(List.of(iron));
        assertThrows(UnsupportedOperationException.class, () -> catalog.profiles().clear());
        assertThrows(IllegalArgumentException.class, () -> catalog.require(id("rpgskilltree", "missing")));
    }

    private static void assertMaterial(
        BlacksmithMaterialProfile profile,
        String sourceMod,
        ResourceLocation canonicalItem,
        String commonName
    ) {
        assertEquals(sourceMod, profile.sourceMod());
        assertEquals(Set.of(canonicalItem), profile.itemIds());
        assertEquals(Set.of(id("c", "ingots/" + commonName)), profile.itemTags());
        assertTrue(profile.moltenFluidIds().isEmpty());
        assertEquals(Set.of(id("c", "molten_" + commonName)), profile.moltenFluidTags());
        assertEquals(id("rpgskilltree", "metal"), profile.materialClass());
        assertEquals(canonicalItem, profile.repairIngredient().orElseThrow());
        assertEquals(1, profile.schemaVersion());
        assertTrue(profile.displayKey().startsWith("material.rpgskilltree.blacksmith."));
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
