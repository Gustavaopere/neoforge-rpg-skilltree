package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CanonicalInvestmentProjectionContract {
    private CanonicalInvestmentProjectionContract() {}

    public static void main(String[] args) {
        purchasedRanksAndMasteryThresholdsProjectDeterministically();
        unknownPurchasedNodesFailClosedWithoutIdInference();
        canonicalClassResolutionRefusesIncompleteProjection();
        canonicalClassResolutionResolvesCompleteProjection();
        metadataContractsRejectInvalidContributionData();
        projectionContractsRejectImpossibleResolution();
        System.out.println("CanonicalInvestmentProjectionContract: PASS");
    }

    private static void purchasedRanksAndMasteryThresholdsProjectDeterministically() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "rpgskilltree:arcane_001", 2,
                "rpgskilltree:martial_001", 1
            )))
            .withMastery(MasteryState.of(Map.of("rpgskilltree:spellcraft", 120)));

        CanonicalInvestmentProjection projection = CanonicalInvestmentProjector.project(
            state,
            Map.of(
                "rpgskilltree:arcane_001", new NodeInvestmentMetadata(
                    Map.of(ProgressionDomain.ARCANE, 4), Set.of("school:arcane")
                ),
                "rpgskilltree:martial_001", new NodeInvestmentMetadata(
                    Map.of(ProgressionDomain.MARTIAL, 3), Set.of()
                )
            ),
            List.of(new MasteryInvestmentMetadata(
                "rpgskilltree:spellcraft",
                100,
                Map.of(ProgressionDomain.ARCANE, 2),
                Set.of("mastery:spellcraft:trained")
            ))
        );

        eq(true, projection.complete());
        eq(Set.of(), projection.missingNodeIds());
        eq(10, projection.investmentState().domainScore(ProgressionDomain.ARCANE));
        eq(3, projection.investmentState().domainScore(ProgressionDomain.MARTIAL));
        eq(true, projection.investmentState().hasTag("school:arcane"));
        eq(true, projection.investmentState().hasTag("mastery:spellcraft:trained"));
    }

    private static void unknownPurchasedNodesFailClosedWithoutIdInference() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "rpgskilltree:arcane_999", 1
            )));

        CanonicalInvestmentProjection projection = CanonicalInvestmentProjector.project(
            state,
            Map.of(),
            List.of()
        );

        eq(false, projection.complete());
        eq(Set.of("rpgskilltree:arcane_999"), projection.missingNodeIds());
        eq(0, projection.investmentState().domainScore(ProgressionDomain.ARCANE));
    }

    private static void canonicalClassResolutionRefusesIncompleteProjection() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "rpgskilltree:arcane_001", 2,
                "rpgskilltree:unknown", 1
            )));
        Map<String, NodeInvestmentMetadata> metadata = Map.of(
            "rpgskilltree:arcane_001", new NodeInvestmentMetadata(
                Map.of(ProgressionDomain.ARCANE, 6), Set.of()
            )
        );
        ArchetypeDefinition mage = new ArchetypeDefinition(
            "rpgskilltree:mage", 10, 1,
            Map.of(ProgressionDomain.ARCANE, 12), Set.of(), Set.of()
        );

        CanonicalClassResolutionProjection projection = ClassResolutionQueryService.resolveCanonical(
            state,
            metadata,
            List.of(),
            List.of(mage)
        );

        eq(false, projection.complete());
        eq(Set.of("rpgskilltree:unknown"), projection.missingNodeIds());
        eq(false, projection.resolution().isPresent());
    }

    private static void canonicalClassResolutionResolvesCompleteProjection() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("rpgskilltree:arcane_001", 2)));
        ArchetypeDefinition mage = new ArchetypeDefinition(
            "rpgskilltree:mage", 10, 1,
            Map.of(ProgressionDomain.ARCANE, 12), Set.of(), Set.of()
        );

        CanonicalClassResolutionProjection projection = ClassResolutionQueryService.resolveCanonical(
            state,
            Map.of(
                "rpgskilltree:arcane_001",
                new NodeInvestmentMetadata(Map.of(ProgressionDomain.ARCANE, 6), Set.of("school:arcane"))
            ),
            List.of(),
            List.of(mage)
        );

        eq(true, projection.complete());
        eq(Set.of(), projection.missingNodeIds());
        eq(true, projection.resolution().isPresent());
        eq(Optional.of("rpgskilltree:mage"), projection.resolution().orElseThrow().primaryClassId());
    }

    private static void metadataContractsRejectInvalidContributionData() {
        NodeInvestmentMetadata neutral = NodeInvestmentMetadata.neutral();
        eq(Map.of(), neutral.domainWeightsPerRank());
        eq(Set.of(), neutral.tags());

        expectThrows(IllegalArgumentException.class, () ->
            new NodeInvestmentMetadata(Map.of(ProgressionDomain.ARCANE, 0), Set.of())
        );
        expectThrows(IllegalArgumentException.class, () ->
            new NodeInvestmentMetadata(Map.of(), Set.of(" "))
        );
        expectThrows(IllegalArgumentException.class, () ->
            new MasteryInvestmentMetadata(" ", 1, Map.of(), Set.of())
        );
        expectThrows(IllegalArgumentException.class, () ->
            new MasteryInvestmentMetadata("rpgskilltree:spellcraft", 0, Map.of(), Set.of())
        );
        expectThrows(IllegalArgumentException.class, () ->
            new MasteryInvestmentMetadata(
                "rpgskilltree:spellcraft", 1,
                Map.of(ProgressionDomain.ARCANE, 0), Set.of()
            )
        );
        expectThrows(IllegalArgumentException.class, () ->
            new MasteryInvestmentMetadata("rpgskilltree:spellcraft", 1, Map.of(), Set.of(" "))
        );
    }

    private static void projectionContractsRejectImpossibleResolution() {
        InvestmentState empty = InvestmentState.of(List.of());
        CanonicalInvestmentProjection projection = new CanonicalInvestmentProjection(empty, Set.of("missing"));
        eq(false, projection.complete());

        expectThrows(IllegalArgumentException.class, () ->
            new CanonicalClassResolutionProjection(
                empty,
                Set.of("missing"),
                Optional.of(EmergentClassResolution.fromOrderedMatches(List.of()))
            )
        );
    }

    private static void expectThrows(Class<? extends Throwable> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable failure) {
            if (expected.isInstance(failure)) return;
            throw new AssertionError("expected " + expected.getSimpleName() + " but got " + failure, failure);
        }
        throw new AssertionError("expected " + expected.getSimpleName() + " to be thrown");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
