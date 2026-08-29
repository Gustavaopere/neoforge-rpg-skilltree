package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;

public final class WorldDiscoveryTest {
    public static void main(String[] args) {
        serverConfirmedStructureProducesCanonicalId();
        structureWithoutServerPieceIsRejected();
        forgedRequestedStructureDoesNotMatchServerObservation();
        System.out.println("WorldDiscoveryTest: PASS");
    }

    private static void serverConfirmedStructureProducesCanonicalId() {
        var id = WorldDiscoveryPolicy.confirmStructure("minecraft:village_plains", true).orElseThrow();
        eq(CompendiumEntryKind.STRUCTURE, id.kind());
        eq("minecraft:village_plains", id.resourceLocation());
    }

    private static void structureWithoutServerPieceIsRejected() {
        truth(WorldDiscoveryPolicy.confirmStructure("minecraft:village_plains", false).isEmpty());
    }

    private static void forgedRequestedStructureDoesNotMatchServerObservation() {
        truth(!WorldDiscoveryPolicy.matchesRequestedStructure(
            "minecraft:stronghold", "minecraft:village_plains", true
        ));
        truth(WorldDiscoveryPolicy.matchesRequestedStructure(
            "minecraft:village_plains", "minecraft:village_plains", true
        ));
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) { if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual); }
}
