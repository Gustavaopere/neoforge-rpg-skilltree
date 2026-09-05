package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MasteryInvestmentMetadataPolicyTest {
    public static void main(String[] args) {
        acceptsExplicitCanonicalMetadataDeterministically();
        rejectsNonCanonicalLane();
        rejectsDuplicateLaneThreshold();
        rejectsNoOpContribution();
        permitsDistinctThresholdsForSameLane();
    }

    private static void acceptsExplicitCanonicalMetadataDeterministically() {
        var arcane80 = metadata("magic:casting", 80, ProgressionDomain.ARCANE, 1);
        var arcane60 = metadata("magic:casting", 60, ProgressionDomain.ARCANE, 2);

        var validated = MasteryInvestmentMetadataPolicy.validate(List.of(arcane80, arcane60));

        require(validated.equals(List.of(arcane60, arcane80)), "validated metadata must be stable by lane then threshold");
    }

    private static void rejectsNonCanonicalLane() {
        expectFailure(() -> MasteryInvestmentMetadataPolicy.validate(List.of(
            metadata("unknown:casting", 60, ProgressionDomain.ARCANE, 1)
        )), "non-canonical mastery lane must fail closed");
    }

    private static void rejectsDuplicateLaneThreshold() {
        expectFailure(() -> MasteryInvestmentMetadataPolicy.validate(List.of(
            metadata("magic:casting", 60, ProgressionDomain.ARCANE, 1),
            metadata("magic:casting", 60, ProgressionDomain.ARCANE, 2)
        )), "duplicate lane+threshold must fail closed");
    }

    private static void rejectsNoOpContribution() {
        expectFailure(() -> MasteryInvestmentMetadataPolicy.validate(List.of(
            new MasteryInvestmentMetadata("magic:casting", 60, Map.of(), Set.of())
        )), "explicit mastery metadata must contribute a domain weight or tag");
    }

    private static void permitsDistinctThresholdsForSameLane() {
        var validated = MasteryInvestmentMetadataPolicy.validate(List.of(
            metadata("magic:casting", 60, ProgressionDomain.ARCANE, 1),
            metadata("magic:casting", 80, ProgressionDomain.ARCANE, 1)
        ));
        require(validated.size() == 2, "distinct thresholds for the same lane must remain explicit contributions");
    }

    private static MasteryInvestmentMetadata metadata(
        String lane,
        int threshold,
        ProgressionDomain domain,
        int weight
    ) {
        return new MasteryInvestmentMetadata(
            lane,
            threshold,
            Map.of(domain, weight),
            Set.of("rpgskilltree:mastery/explicit")
        );
    }

    private static void expectFailure(Runnable action, String message) {
        boolean failed = false;
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            failed = true;
        }
        require(failed, message);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
