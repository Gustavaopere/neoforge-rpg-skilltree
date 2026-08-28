package dev.gustavopere.rpgskilltree.compendium.entity;

import java.util.Map;

public final class EntityVariantProviderTest {
    public static void main(String[] args) {
        snapshotIsTypedAndImmutable();
        rejectsBlankFamily();
        System.out.println("EntityVariantProviderTest: PASS");
    }

    private static void snapshotIsTypedAndImmutable() {
        EntityVariantSnapshot snapshot = new EntityVariantSnapshot(
            "horse",
            Map.of("variant", "brown", "markings", "white_dots"),
            Map.of("temper", 50L),
            Map.of("tame", true)
        );
        check(snapshot.textFacts().get("variant").equals("brown"), "text fact");
        check(snapshot.numericFacts().get("temper") == 50L, "numeric fact");
        check(snapshot.booleanFacts().get("tame"), "boolean fact");
        try {
            snapshot.textFacts().put("bad", "bad");
            throw new AssertionError("variant facts must be immutable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void rejectsBlankFamily() {
        try {
            new EntityVariantSnapshot(" ", Map.of(), Map.of(), Map.of());
            throw new AssertionError("blank family must fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
