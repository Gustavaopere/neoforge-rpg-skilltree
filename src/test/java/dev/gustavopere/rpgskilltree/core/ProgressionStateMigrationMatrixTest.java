package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Compatibility matrix for every persisted progression format still accepted by the codec. */
public final class ProgressionStateMigrationMatrixTest {
    public static void main(String[] args) {
        v1MigratesSemanticClassesWithoutDroppingLegacyProgress();
        v2PreservesFinalTriadsDuringSemanticMigration();
        v3PreservesPassiveNodeRanksDuringSemanticMigration();
        v4RoundTripPreservesDiscoveriesAndUnknownIdentities();
        semanticMigrationIsIdempotent();
        System.out.println("ProgressionStateMigrationMatrixTest: PASS");
    }

    static void v1MigratesSemanticClassesWithoutDroppingLegacyProgress() {
        ProgressionState state = ProgressionStateCodec.decode(legacyPayload(1));
        eq(12_345L, state.totalCharacterXp());
        eq(7, state.passivePoints().earned(PassivePointSource.LEVEL));
        eq(3, state.passivePoints().earned(PassivePointSource.BOSS));
        eq(4, state.passivePoints().spent());
        eq(Set.of("boss:alpha"), state.bossProgress().creditedRewardKeys());
        eq(false, state.classProgression().isUnlocked("industrialist"));
        eq(true, state.classProgression().isUnlocked("mage"));
        eq(true, state.classProgression().isUnlocked("legacy_unknown_class"));
        eq(true, state.specializations().isUnlocked("industrialist"));
        eq(true, state.specializations().isUnlocked("existing_specialization"));
        eq(321, state.mastery().experience("irons:fire"));
        eq(Set.of("pact:a"), state.classChoices().selectedInGroup("warlock:pact"));
        eq(true, state.finalTriads().allRanks().isEmpty());
        eq(true, state.passiveNodes().ranks().isEmpty());
        eq(true, state.discoveries().discoveredKeys().isEmpty());
    }

    static void v2PreservesFinalTriadsDuringSemanticMigration() {
        ProgressionState state = ProgressionStateCodec.decode(legacyPayload(2));
        eq(List.of(3, 2, 1), state.finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(true, state.specializations().isUnlocked("industrialist"));
        eq(false, state.classProgression().isUnlocked("industrialist"));
        eq(true, state.passiveNodes().ranks().isEmpty());
        eq(true, state.discoveries().discoveredKeys().isEmpty());
    }

    static void v3PreservesPassiveNodeRanksDuringSemanticMigration() {
        ProgressionState state = ProgressionStateCodec.decode(legacyPayload(3));
        eq(List.of(3, 2, 1), state.finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(2, state.passiveNodes().rank("legacy:node"));
        eq(1, state.passiveNodes().rank("legacy:unknown_node"));
        eq(true, state.discoveries().discoveredKeys().isEmpty());
        eq(true, state.specializations().isUnlocked("industrialist"));
    }

    static void v4RoundTripPreservesDiscoveriesAndUnknownIdentities() {
        ProgressionState source = new ProgressionState(
            99_999L,
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 20).spend(6),
            BossProgress.of(Set.of("boss:known", "boss:unknown")),
            ClassProgressionState.of(Set.of("mage", "future_class")),
            MasteryState.of(Map.of("future:mastery", 777)),
            ClassChoiceState.of(Map.of("future:choice", Set.of("future:value"))),
            SpecializationProgressionState.of(Set.of("future_specialization", "prospector")),
            FinalTriadProgress.of(Map.of(ProgressionDomain.MINING, List.of(1, 2, 3))),
            PassiveNodeProgress.of(Map.of("future:node", 3)),
            DiscoveryProgress.of(Set.of("future:discovery", "structure:test"))
        );

        ProgressionState decoded = ProgressionStateCodec.decode(ProgressionStateCodec.encode(source));
        eq(source.totalCharacterXp(), decoded.totalCharacterXp());
        eq(source.passivePoints().earnedBySource(), decoded.passivePoints().earnedBySource());
        eq(source.passivePoints().spent(), decoded.passivePoints().spent());
        eq(source.bossProgress().creditedRewardKeys(), decoded.bossProgress().creditedRewardKeys());
        eq(source.classProgression().unlockedClassIds(), decoded.classProgression().unlockedClassIds());
        eq(source.mastery().experience(), decoded.mastery().experience());
        eq(source.classChoices().selections(), decoded.classChoices().selections());
        eq(source.specializations().unlockedSpecializationIds(), decoded.specializations().unlockedSpecializationIds());
        eq(source.finalTriads().allRanks(), decoded.finalTriads().allRanks());
        eq(source.passiveNodes().ranks(), decoded.passiveNodes().ranks());
        eq(source.discoveries().discoveredKeys(), decoded.discoveries().discoveredKeys());
    }

    static void semanticMigrationIsIdempotent() {
        ProgressionState first = ProgressionStateCodec.decode(legacyPayload(3));
        ProgressionState second = ProgressionStateMigrations.migrate(first, 3);
        eq(first.totalCharacterXp(), second.totalCharacterXp());
        eq(first.passivePoints().earnedBySource(), second.passivePoints().earnedBySource());
        eq(first.passivePoints().spent(), second.passivePoints().spent());
        eq(first.bossProgress().creditedRewardKeys(), second.bossProgress().creditedRewardKeys());
        eq(first.classProgression().unlockedClassIds(), second.classProgression().unlockedClassIds());
        eq(first.mastery().experience(), second.mastery().experience());
        eq(first.classChoices().selections(), second.classChoices().selections());
        eq(first.specializations().unlockedSpecializationIds(), second.specializations().unlockedSpecializationIds());
        eq(first.finalTriads().allRanks(), second.finalTriads().allRanks());
        eq(first.passiveNodes().ranks(), second.passiveNodes().ranks());
        eq(first.discoveries().discoveredKeys(), second.discoveries().discoveredKeys());
    }

    private static byte[] legacyPayload(int version) {
        if (version < 1 || version > 3) throw new IllegalArgumentException("legacy version must be 1..3");
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeInt(version);
                out.writeLong(12_345L);
                writeLedger(out, Map.of(PassivePointSource.LEVEL, 7, PassivePointSource.BOSS, 3), 4);
                writeStringSet(out, Set.of("boss:alpha"));
                writeStringSet(out, Set.of("industrialist", "mage", "legacy_unknown_class"));
                writeStringIntMap(out, Map.of("irons:fire", 321, "legacy:mastery", 11));
                writeStringSetMap(out, Map.of("warlock:pact", Set.of("pact:a")));
                writeStringSet(out, Set.of("existing_specialization"));
                if (version >= 2) {
                    out.writeInt(1);
                    writeString(out, ProgressionDomain.ARCANE.name());
                    out.writeInt(3);
                    out.writeInt(2);
                    out.writeInt(1);
                }
                if (version >= 3) {
                    writeStringIntMap(out, Map.of("legacy:node", 2, "legacy:unknown_node", 1));
                }
            }
            return bytes.toByteArray();
        } catch (IOException exception) {
            throw new AssertionError(exception);
        }
    }

    private static void writeLedger(DataOutputStream out, Map<PassivePointSource, Integer> earned, int spent) throws IOException {
        out.writeInt(earned.size());
        for (PassivePointSource source : earned.keySet().stream().sorted(java.util.Comparator.comparing(Enum::name)).toList()) {
            writeString(out, source.name());
            out.writeInt(earned.get(source));
        }
        out.writeInt(spent);
    }

    private static void writeStringSet(DataOutputStream out, Set<String> values) throws IOException {
        List<String> sorted = values.stream().sorted().toList();
        out.writeInt(sorted.size());
        for (String value : sorted) writeString(out, value);
    }

    private static void writeStringIntMap(DataOutputStream out, Map<String, Integer> values) throws IOException {
        List<String> keys = values.keySet().stream().sorted().toList();
        out.writeInt(keys.size());
        for (String key : keys) {
            writeString(out, key);
            out.writeInt(values.get(key));
        }
    }

    private static void writeStringSetMap(DataOutputStream out, Map<String, Set<String>> values) throws IOException {
        List<String> keys = values.keySet().stream().sorted().toList();
        out.writeInt(keys.size());
        for (String key : keys) {
            writeString(out, key);
            writeStringSet(out, values.get(key));
        }
    }

    private static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length);
        out.write(encoded);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
