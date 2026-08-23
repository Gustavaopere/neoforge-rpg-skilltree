package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Set;

public final class MorphEcologyPolicyTest {
    public static void main(String[] args) {
        perceivedIdentityCarriesSpeciesFactionAndPhysiology();
        explicitFactionRelationsDriveAllianceHostilityAndFear();
        speciesFallbackWorksWithoutFactionMapping();
        hostilityMemoryBreaksDisguiseThenExpires();
        System.out.println("MorphEcologyPolicyTest: PASS");
    }

    static void perceivedIdentityCarriesSpeciesFactionAndPhysiology() {
        var form = new MorphFormDescriptor("minecraft:cat", MorphFormCategory.NATURAL_LAND, Set.of("animal"));
        var identity = MorphEcologyPolicy.perceivedIdentity(
            form,
            Map.of("minecraft:cat", Set.of("minecraft:feline")),
            Map.of("minecraft:cat", Set.of("rpgskilltree:natural", "rpgskilltree:predator"))
        );
        eq("minecraft:cat", identity.speciesId());
        eq(MorphFormCategory.NATURAL_LAND, identity.category());
        eq(Set.of("minecraft:feline"), identity.factions());
        eq(Set.of("rpgskilltree:natural", "rpgskilltree:predator"), identity.traits());
    }

    static void explicitFactionRelationsDriveAllianceHostilityAndFear() {
        var relations = Map.of(
            "minecraft:illager", new MorphFactionRelations(
                Set.of("minecraft:illager"),
                Set.of("minecraft:villager"),
                Set.of()
            ),
            "minecraft:creeper", new MorphFactionRelations(
                Set.of("minecraft:creeper"),
                Set.of(),
                Set.of("minecraft:feline")
            )
        );
        var villager = new MorphPerceivedIdentity(
            "minecraft:villager", MorphFormCategory.HUMANOID,
            Set.of("minecraft:villager"), Set.of("rpgskilltree:humanoid")
        );
        var illager = new MorphPerceivedIdentity(
            "minecraft:pillager", MorphFormCategory.HUMANOID,
            Set.of("minecraft:illager"), Set.of("rpgskilltree:humanoid")
        );
        var cat = new MorphPerceivedIdentity(
            "minecraft:cat", MorphFormCategory.NATURAL_LAND,
            Set.of("minecraft:feline"), Set.of("rpgskilltree:natural")
        );

        eq(MorphFactionDisposition.HOSTILE, MorphEcologyPolicy.disposition(
            "minecraft:pillager", Set.of("minecraft:illager"), villager, relations, MorphHostilityMemory.empty(), 0L
        ));
        eq(MorphFactionDisposition.ALLY, MorphEcologyPolicy.disposition(
            "minecraft:pillager", Set.of("minecraft:illager"), illager, relations, MorphHostilityMemory.empty(), 0L
        ));
        eq(MorphFactionDisposition.FEAR, MorphEcologyPolicy.disposition(
            "minecraft:creeper", Set.of("minecraft:creeper"), cat, relations, MorphHostilityMemory.empty(), 0L
        ));
    }

    static void speciesFallbackWorksWithoutFactionMapping() {
        var cow = new MorphPerceivedIdentity("minecraft:cow", MorphFormCategory.NATURAL_LAND, Set.of(), Set.of());
        eq(MorphFactionDisposition.ALLY, MorphEcologyPolicy.disposition(
            "minecraft:cow", Set.of(), cow, Map.of(), MorphHostilityMemory.empty(), 10L
        ));
        eq(MorphFactionDisposition.NEUTRAL, MorphEcologyPolicy.disposition(
            "minecraft:sheep", Set.of(), cow, Map.of(), MorphHostilityMemory.empty(), 10L
        ));
    }

    static void hostilityMemoryBreaksDisguiseThenExpires() {
        var relations = Map.of(
            "minecraft:villager", new MorphFactionRelations(
                Set.of("minecraft:villager"), Set.of(), Set.of()
            )
        );
        var identity = new MorphPerceivedIdentity(
            "minecraft:villager", MorphFormCategory.HUMANOID,
            Set.of("minecraft:villager"), Set.of("rpgskilltree:humanoid")
        );
        long now = 1_000L;
        var memory = MorphHostilityMemory.empty().compromise(Set.of("minecraft:villager"), now, 45_000L);
        eq(MorphFactionDisposition.HOSTILE, MorphEcologyPolicy.disposition(
            "minecraft:villager", Set.of("minecraft:villager"), identity, relations, memory, now + 1L
        ));
        eq(MorphFactionDisposition.ALLY, MorphEcologyPolicy.disposition(
            "minecraft:villager", Set.of("minecraft:villager"), identity, relations, memory, now + 45_001L
        ));
        eq(true, memory.prune(now + 45_001L).compromisedUntilMillis().isEmpty());
    }

    static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
