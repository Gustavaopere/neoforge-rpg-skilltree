package dev.gustavopere.rpgskilltree.core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class AttributeRanksPersistenceTest {
    public static void main(String[] args) throws Exception {
        codecVersionIncludesTypedRewardClaimsAfterBudgetProgression();
        roundTripPreservesSparseUncappedRanks();
        legacyVersionOneDefaultsAttributesToZero();
        legacyVersionTwoDefaultsBudgetProgressionToZero();
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

    private static void codecVersionIncludesTypedRewardClaimsAfterBudgetProgression() {
        eq(4, CoreProgressionStateCodec.CURRENT_VERSION);
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
        eq(ProgressionRewardClaims.empty(), decoded.progressionRewardClaims());
    }

    private static void legacyVersionOneDefaultsAttributesToZero() throws Exception {
        ProgressionRulesSnapshot rules = rules();
        byte[] v1 = legacyEmptyPayload(rules, 1, false);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(v1);

        eq(AttributeRanks.empty(), decoded.attributeRanks());
        eq(0L, decoded.attributeRanks().rank(AttributeId.DETERMINATION));
        eq(MainPerkBudgetProgression.empty(), decoded.mainPerkBudgetProgression());
        eq(ProgressionRewardClaims.empty(), decoded.progressionRewardClaims());
        eq(rules.version(), decoded.rulesVersion());
        eq(rules.fingerprint(), decoded.rulesFingerprint());
    }

    private static void legacyVersionTwoDefaultsBudgetProgressionToZero() throws Exception {
        ProgressionRulesSnapshot rules = rules();
        byte[] v2 = legacyEmptyPayload(rules, 2, true);
        CoreProgressionState decoded = CoreProgressionStateCodec.decode(v2);

        eq(AttributeRanks.empty(), decoded.attributeRanks());
        eq(MainPerkBudgetProgression.empty(), decoded.mainPerkBudgetProgression());
        eq(0L, decoded.mainPerkBudgetProgression().bonus());
        eq(ProgressionRewardClaims.empty(), decoded.progressionRewardClaims());
    }

    private static byte[] legacyEmptyPayload(
        ProgressionRulesSnapshot rules,
        int version,
        boolean includeAttributes
    ) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeInt(version);
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
            if (includeAttributes) out.writeInt(0);
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
