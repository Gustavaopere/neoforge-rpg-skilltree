package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityRegistryDescriptor;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityRegistryProvider;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class EntityRegistryProviderTest {
    public static void main(String[] args) {
        mapsRegistryDescriptorWithoutConstructingEntity();
        keepsGenericFallbackUseful();
        System.out.println("EntityRegistryProviderTest: PASS");
    }

    private static void mapsRegistryDescriptorWithoutConstructingEntity() {
        EntityRegistryDescriptor descriptor = new EntityRegistryDescriptor(
            "minecraft:zombie",
            "minecraft",
            "entity.minecraft.zombie",
            "monster",
            0.6,
            1.95,
            Set.of(EntityGameplayCategory.HOSTIL),
            Map.of(EntityBaseAttribute.MAX_HEALTH, 20.0, EntityBaseAttribute.ARMOR, 2.0)
        );

        EntitySpeciesFacts facts = EntityRegistryProvider.toSpeciesFacts(descriptor);
        eq("minecraft:zombie", facts.resourceLocation());
        eq("minecraft", facts.sourceModId());
        eq("monster", facts.mobCategory());
        eq(20.0, facts.baseAttributes().get(EntityFactKeys.MAX_HEALTH));
        eq(2.0, facts.baseAttributes().get(EntityFactKeys.ARMOR));
    }

    private static void keepsGenericFallbackUseful() {
        EntityRegistryDescriptor descriptor = new EntityRegistryDescriptor(
            "example:odd_creature",
            "example",
            "entity.example.odd_creature",
            "misc",
            1.0,
            1.0,
            Set.of(EntityGameplayCategory.OUTRO),
            Map.of()
        );

        EntitySpeciesFacts facts = EntityRegistryProvider.toSpeciesFacts(descriptor);
        eq(Map.of(), facts.baseAttributes());
        check(facts.gameplayCategories().contains(EntityGameplayCategory.OUTRO), "fallback category missing");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
