package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public final class CompendiumEntryIdTest {
    public static void main(String[] args) {
        kindsMayShareResourceLocation();
        serializedIdentityIsStable();
        blockFeatureIsCanonicalKind();
        malformedResourceLocationIsRejected();
        System.out.println("CompendiumEntryIdTest: PASS");
    }

    private static void kindsMayShareResourceLocation() {
        CompendiumEntryId entity = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "example:same_id");
        CompendiumEntryId flora = CompendiumEntryId.of(CompendiumEntryKind.FLORA, "example:same_id");
        notEq(entity, flora);
    }

    private static void serializedIdentityIsStable() {
        CompendiumEntryId id = CompendiumEntryId.of(CompendiumEntryKind.TREE, "terrafirmacraft:douglas_fir");
        eq("TREE|terrafirmacraft:douglas_fir", id.serializedId());
        eq("terrafirmacraft", id.namespace());
    }

    private static void blockFeatureIsCanonicalKind() {
        CompendiumEntryId id = CompendiumEntryId.of(CompendiumEntryKind.BLOCK_FEATURE, "minecraft:spawner");
        eq(CompendiumEntryKind.BLOCK_FEATURE, id.kind());
    }

    private static void malformedResourceLocationIsRejected() {
        throwsIllegal(() -> CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "MissingNamespace"));
        throwsIllegal(() -> CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "Minecraft:Zombie"));
        throwsIllegal(() -> CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:"));
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void notEq(Object first, Object second) {
        if (Objects.equals(first, second)) throw new AssertionError("values unexpectedly equal: " + first);
    }
}
