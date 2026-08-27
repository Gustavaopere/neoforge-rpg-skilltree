package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class SemanticXpPipelineTest {
    public static void main(String[] args) {
        directActionFlowsThroughAntiFarmThenPolicy();
        unattributedAutomationIsRejectedBeforeAntiFarmAndPolicy();
        antiFarmRejectionStopsXpPolicy();
        playerPlacedOreCannotBeMinedForXp();
        semanticContextDefensivelyCopiesExtensibleData();
        System.out.println("SemanticXpPipelineTest: PASS");
    }

    static void directActionFlowsThroughAntiFarmThenPolicy() {
        var action = oreAction(101L, SemanticActionAuthorship.DIRECT_PLAYER);
        var award = new CharacterXpAward("test:semantic/diamond", 37L, Set.of(ProgressionDomain.MINING));
        var antiFarmCalls = new AtomicInteger();
        var policyCalls = new AtomicInteger();

        AntiFarmService antiFarm = candidate -> {
            antiFarmCalls.incrementAndGet();
            eq(action, candidate);
            return AntiFarmDecision.allow();
        };
        XpPolicy policy = candidate -> {
            policyCalls.incrementAndGet();
            eq(action, candidate);
            return Optional.of(award);
        };

        var result = SemanticXpPipeline.evaluate(action, antiFarm, policy);
        eq(SemanticXpDecision.AWARDED, result.decision());
        eq(Optional.of(award), result.award());
        eq(1, antiFarmCalls.get());
        eq(1, policyCalls.get());
    }

    static void unattributedAutomationIsRejectedBeforeAntiFarmAndPolicy() {
        var action = oreAction(102L, SemanticActionAuthorship.UNATTRIBUTED_AUTOMATION);
        var antiFarmCalls = new AtomicInteger();
        var policyCalls = new AtomicInteger();

        var result = SemanticXpPipeline.evaluate(
            action,
            candidate -> {
                antiFarmCalls.incrementAndGet();
                return AntiFarmDecision.allow();
            },
            candidate -> {
                policyCalls.incrementAndGet();
                return Optional.of(new CharacterXpAward("test:should_not_happen", 1L, Set.of()));
            }
        );

        eq(SemanticXpDecision.REJECTED_AUTHORSHIP, result.decision());
        eq(Optional.empty(), result.award());
        eq(0, antiFarmCalls.get());
        eq(0, policyCalls.get());
    }

    static void antiFarmRejectionStopsXpPolicy() {
        var action = oreAction(103L, SemanticActionAuthorship.DIRECT_PLAYER);
        var policyCalls = new AtomicInteger();

        var result = SemanticXpPipeline.evaluate(
            action,
            candidate -> AntiFarmDecision.reject("test:repeat_window"),
            candidate -> {
                policyCalls.incrementAndGet();
                return Optional.of(new CharacterXpAward("test:should_not_happen", 1L, Set.of()));
            }
        );

        eq(SemanticXpDecision.REJECTED_ANTI_FARM, result.decision());
        eq("test:repeat_window", result.reason());
        eq(Optional.empty(), result.award());
        eq(0, policyCalls.get());
    }

    static void playerPlacedOreCannotBeMinedForXp() {
        var provenance = new PlacedBlockProvenance();
        provenance.mark(200L);
        var antiFarm = new BlockProvenanceAntiFarmService(provenance);

        var placed = antiFarm.evaluate(oreAction(200L, SemanticActionAuthorship.DIRECT_PLAYER));
        eq(false, placed.allowed());
        eq("player_placed_block", placed.reason());

        var natural = antiFarm.evaluate(oreAction(201L, SemanticActionAuthorship.DIRECT_PLAYER));
        eq(true, natural.allowed());

        var unrelated = new SemanticAction(
            SemanticActionType.ITEM_CRAFTED,
            "minecraft:diamond_pickaxe",
            new ActionOrigin("neoforge:item_crafted", 0),
            SemanticActionAuthorship.DIRECT_PLAYER,
            SemanticActionContext.atBlock(200L)
        );
        eq(true, antiFarm.evaluate(unrelated).allowed());
    }

    static void semanticContextDefensivelyCopiesExtensibleData() {
        var metrics = new HashMap<String, Double>();
        metrics.put("max_health", 20.0);
        var tags = new HashSet<String>();
        tags.add("hostile");

        var context = new SemanticActionContext(OptionalLong.of(300L), metrics, tags);
        metrics.put("max_health", 999.0);
        tags.add("mutated");

        eq(Map.of("max_health", 20.0), context.metrics());
        eq(Set.of("hostile"), context.tags());
    }

    private static SemanticAction oreAction(long packedPosition, SemanticActionAuthorship authorship) {
        return new SemanticAction(
            SemanticActionType.ORE_MINED,
            "minecraft:diamond_ore",
            new ActionOrigin("neoforge:block_break", 0),
            authorship,
            new SemanticActionContext(
                OptionalLong.of(packedPosition),
                Map.of("hardness", 3.0),
                Set.of("forge:ores/diamond")
            )
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
