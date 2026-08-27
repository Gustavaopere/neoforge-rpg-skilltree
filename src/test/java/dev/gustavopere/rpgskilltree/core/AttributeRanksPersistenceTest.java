package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class AttributeRanksPersistenceTest {
    public static void main(String[] args) throws Exception {
        codecVersionIsBumpedForPersistedAttributes();
        roundTripPreservesSparseUncappedRanks();
        legacyVersionOneDefaultsAttributesToZero();
        System.out.println("AttributeRanksPersistenceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            7L,
            "rpgskilltree:attribute_persistence_test",
            List.of(new LevelCurveBand(0L, 100L, 2L)),
            new MainPerkBudget(30L)
        );
    }

    private static void codecVersionIsBumpedForPersistedAttributes() {
        eq(2, CoreProgressionStateCodec.CURRENT_VERSION);
    }

    private static void roundTripPreservesSparseUncappedRanks() {
        AttributeRanks attributes = AttributeRanks.of(Map.of(
            AttributeId.STRENGTH, 8L,
            AttributeId.INTELLIGENCE, 5_000_000_000L
        ));
        CoreProgressionState source = new CoreProgressionState(
            new CharacterProgressionState(500L, 12L),
            CorePointLedger.empty(),
            attributes,
            rules().version(),
            rules().fingerprint(),
            0,
            0L
        );

        CoreProgressionState decoded = CoreProgressionStateCodec.decode(CoreProgressionStateCodec.encode(source));
        eq(attributes, decoded.attributeRanks());
        eq(8L, decoded.attributeRanks().rank(AttributeId.STRENGTH));
        eq(5_000_000_000L, decoded.attributeRanks().rank(AttributeId.INTELLIGENCE));
        eq(0L, decoded.attributeRanks().rank(AttributeId.CHARISMA));
    }

    private static void legacyVersionOneDefaultsAttributesToZero() throws Exception {
        ProgressionRulesSnapshot rules = rules();
        byte[] v1 = legacyV1EmptyPayload(rules);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(v1);

        eq(AttributeRanks.empty(), decoded.attributeRanks());
        eq(0L, decoded.attributeRanks().rank(AttributeId.DETERMINATION));
        eq(rules.version(), decoded.rulesVersion());
        eq(rules.fingerprint(), decoded.rulesFingerprint());
    }

    private static byte[] legacyV1EmptyPayload(ProgressionRulesSnapshot rules) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeInt(1);
            out.writeLong(0L);
            out.writeLong(0L);
            out.writeLong(rules.version());
            writeString(out, rules.fingerprint());
            out.writeInt(0);
            out.writeLong(0L);
            out.writeInt(0);
            out.writeLong(0L);
            out.writeLong(0L);
            out.writeInt(0);
        }
        return bytes.toByteArray();
    }

    private static void writeString(DataOutputStream out, String value) throws Exception {
        byte[] encoded = value.getBytes(StandardCharsets.UTF_8);
        out.writeInt(encoded.length);
        out.write(encoded);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
