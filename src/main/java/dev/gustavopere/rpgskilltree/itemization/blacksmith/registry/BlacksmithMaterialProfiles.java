package dev.gustavopere.rpgskilltree.itemization.blacksmith.registry;

import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithMaterialCatalog;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithMaterialProfile;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.MechanicalProfile;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.PartFamily;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.PhysicalStat;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Pack-audited Stage 11.16.C material profiles.
 *
 * <p>The provider/item/tag identities here are evidence-backed against the current modpack. The
 * mechanical factors are Blacksmith-owned, bounded initial balance coefficients with iron = 1.0;
 * they are not claims about provider APIs or capabilities.</p>
 */
public final class BlacksmithMaterialProfiles {
    private static final int SCHEMA_VERSION = 1;
    private static final ResourceLocation METAL = id("rpgskilltree", "metal");
    private static final Set<PartFamily> METALLIC_PART_FAMILIES = Set.of(
        PartFamily.BLADE,
        PartFamily.GUARD,
        PartFamily.TOOL_HEAD,
        PartFamily.SPEAR_HEAD,
        PartFamily.HAMMER_HEAD,
        PartFamily.ARMOR_PLATE
    );

    private static final BlacksmithMaterialCatalog AUDITED_PACK_CATALOG = new BlacksmithMaterialCatalog(List.of(
        material("iron", "minecraft", id("minecraft", "iron_ingot"), factors(1.00D, 1.00D, 1.00D, 1.00D, 1.00D, 1.00D, 1.00D, 1.00D, 1.00D)),
        material("copper", "minecraft", id("minecraft", "copper_ingot"), factors(0.85D, 0.92D, 1.04D, 0.95D, 0.90D, 0.80D, 0.90D, 1.08D, 0.95D)),
        material("gold", "minecraft", id("minecraft", "gold_ingot"), factors(0.30D, 0.82D, 1.10D, 1.15D, 0.70D, 0.50D, 0.80D, 1.25D, 1.10D)),
        material("netherite", "minecraft", id("minecraft", "netherite_ingot"), factors(1.35D, 1.20D, 0.95D, 1.10D, 1.20D, 1.25D, 1.15D, 0.90D, 1.20D)),
        material("zinc", "create", id("create", "zinc_ingot"), factors(0.82D, 0.90D, 1.05D, 0.95D, 0.85D, 0.75D, 0.85D, 1.05D, 0.90D)),
        material("brass", "create", id("create", "brass_ingot"), factors(0.90D, 0.95D, 1.05D, 1.00D, 0.90D, 0.85D, 0.90D, 1.10D, 1.05D)),
        material("steel", "tfmg", id("tfmg", "steel_ingot"), factors(1.20D, 1.12D, 0.98D, 1.05D, 1.12D, 1.12D, 1.08D, 0.95D, 1.12D)),
        material("aluminum", "tfmg", id("tfmg", "aluminum_ingot"), factors(0.80D, 0.88D, 1.10D, 1.00D, 0.82D, 0.72D, 0.82D, 1.00D, 0.70D)),
        material("lead", "tfmg", id("tfmg", "lead_ingot"), factors(0.78D, 1.00D, 0.80D, 0.82D, 0.95D, 0.95D, 1.05D, 0.85D, 1.35D)),
        material("nickel", "tfmg", id("tfmg", "nickel_ingot"), factors(1.05D, 1.02D, 1.00D, 1.02D, 1.02D, 1.05D, 1.00D, 1.00D, 1.03D)),
        material("constantan", "tfmg", id("tfmg", "constantan_ingot"), factors(0.95D, 0.98D, 1.03D, 0.98D, 0.92D, 0.90D, 0.95D, 1.12D, 1.00D))
    ));

    private BlacksmithMaterialProfiles() {
    }

    public static BlacksmithMaterialCatalog auditedPackCatalog() {
        return AUDITED_PACK_CATALOG;
    }

    private static BlacksmithMaterialProfile material(
        String commonName,
        String sourceMod,
        ResourceLocation canonicalItem,
        MechanicalProfile mechanicalProfile
    ) {
        return new BlacksmithMaterialProfile(
            id("rpgskilltree", commonName),
            SCHEMA_VERSION,
            sourceMod,
            Set.of(canonicalItem),
            Set.of(id("c", "ingots/" + commonName)),
            Set.of(),
            Set.of(id("c", "molten_" + commonName)),
            METAL,
            Optional.of(canonicalItem),
            mechanicalProfile,
            METALLIC_PART_FAMILIES,
            Set.of(),
            "material.rpgskilltree.blacksmith." + commonName
        );
    }

    private static MechanicalProfile factors(
        double durability,
        double attackDamage,
        double attackSpeed,
        double miningSpeed,
        double armor,
        double armorToughness,
        double knockbackResistance,
        double enchantability,
        double mass
    ) {
        Map<PhysicalStat, Double> values = new EnumMap<>(PhysicalStat.class);
        values.put(PhysicalStat.DURABILITY, durability);
        values.put(PhysicalStat.ATTACK_DAMAGE, attackDamage);
        values.put(PhysicalStat.ATTACK_SPEED, attackSpeed);
        values.put(PhysicalStat.MINING_SPEED, miningSpeed);
        values.put(PhysicalStat.ARMOR, armor);
        values.put(PhysicalStat.ARMOR_TOUGHNESS, armorToughness);
        values.put(PhysicalStat.KNOCKBACK_RESISTANCE, knockbackResistance);
        values.put(PhysicalStat.ENCHANTABILITY, enchantability);
        values.put(PhysicalStat.MASS, mass);
        if (values.size() != PhysicalStat.values().length) {
            throw new IllegalStateException("Blacksmith mechanical profile is incomplete");
        }
        return new MechanicalProfile(values);
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
