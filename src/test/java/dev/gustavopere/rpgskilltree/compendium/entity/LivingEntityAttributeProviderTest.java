package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.provider.entity.LivingEntityAttributeProvider;
import java.util.Map;
import java.util.Objects;

public final class LivingEntityAttributeProviderTest {
    public static void main(String[] args) {
        mapsOnlyPresentStandardAttributes();
        ignoresUnknownOrMissingValues();
        System.out.println("LivingEntityAttributeProviderTest: PASS");
    }

    private static void mapsOnlyPresentStandardAttributes() {
        Map<String, Double> facts = LivingEntityAttributeProvider.toFactValues(Map.of(
            EntityBaseAttribute.MAX_HEALTH, 40.0,
            EntityBaseAttribute.ATTACK_DAMAGE, 7.0,
            EntityBaseAttribute.MOVEMENT_SPEED, 0.23
        ));

        eq(40.0, facts.get(EntityFactKeys.MAX_HEALTH));
        eq(7.0, facts.get(EntityFactKeys.ATTACK_DAMAGE));
        eq(0.23, facts.get(EntityFactKeys.MOVEMENT_SPEED));
        check(!facts.containsKey(EntityFactKeys.ARMOR), "absent armor must not be invented");
    }

    private static void ignoresUnknownOrMissingValues() {
        Map<String, Double> facts = LivingEntityAttributeProvider.toFactValues(Map.of());
        eq(Map.of(), facts);
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
