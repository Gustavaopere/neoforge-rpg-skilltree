package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

public final class GameplaySemanticXpPolicyTest {
    public static void main(String[] args) {
        miningPreservesLegacyGameplayValues();
        combatPreservesLegacyGameplayValues();
        explorationPreservesLegacyGameplayValues();
        incompleteCombatEvidenceFailsClosed();
        unsupportedSemanticActionHasNoAward();
        System.out.println("GameplaySemanticXpPolicyTest: PASS");
    }

    static void miningPreservesLegacyGameplayValues() {
        XpPolicy policy = GameplaySemanticXpPolicy.INSTANCE;

        var common = action(
            SemanticActionType.ORE_MINED,
            "minecraft:iron_ore",
            Map.of(),
            Set.of()
        );
        eq(Optional.of(GameplayXpPolicy.oreMined("minecraft:iron_ore", false)), policy.evaluate(common));

        var rare = action(
            SemanticActionType.ORE_MINED,
            "minecraft:diamond_ore",
            Map.of(),
            Set.of(GameplaySemanticXpPolicy.RARE_ORE_TAG)
        );
        eq(Optional.of(GameplayXpPolicy.oreMined("minecraft:diamond_ore", true)), policy.evaluate(rare));
    }

    static void combatPreservesLegacyGameplayValues() {
        XpPolicy policy = GameplaySemanticXpPolicy.INSTANCE;

        var hostile = action(
            SemanticActionType.HOSTILE_KILLED,
            "minecraft:zombie",
            Map.of(GameplaySemanticXpPolicy.MAX_HEALTH_METRIC, 20.0),
            Set.of()
        );
        eq(Optional.of(GameplayXpPolicy.combatKill("minecraft:zombie", 20.0, false)), policy.evaluate(hostile));

        var boss = action(
            SemanticActionType.BOSS_DEFEATED,
            "minecraft:wither",
            Map.of(GameplaySemanticXpPolicy.MAX_HEALTH_METRIC, 300.0),
            Set.of()
        );
        eq(Optional.of(GameplayXpPolicy.combatKill("minecraft:wither", 300.0, true)), policy.evaluate(boss));
    }

    static void explorationPreservesLegacyGameplayValues() {
        XpPolicy policy = GameplaySemanticXpPolicy.INSTANCE;

        var biome = action(SemanticActionType.BIOME_DISCOVERED, "minecraft:plains", Map.of(), Set.of());
        eq(Optional.of(GameplayXpPolicy.biomeDiscovery("minecraft:plains")), policy.evaluate(biome));

        var dimension = action(SemanticActionType.DIMENSION_DISCOVERED, "minecraft:the_nether", Map.of(), Set.of());
        eq(Optional.of(GameplayXpPolicy.dimensionDiscovery("minecraft:the_nether")), policy.evaluate(dimension));
    }

    static void incompleteCombatEvidenceFailsClosed() {
        XpPolicy policy = GameplaySemanticXpPolicy.INSTANCE;
        var missing = action(SemanticActionType.HOSTILE_KILLED, "minecraft:zombie", Map.of(), Set.of());
        eq(Optional.empty(), policy.evaluate(missing));

        var invalid = action(
            SemanticActionType.HOSTILE_KILLED,
            "minecraft:zombie",
            Map.of(GameplaySemanticXpPolicy.MAX_HEALTH_METRIC, 0.0),
            Set.of()
        );
        eq(Optional.empty(), policy.evaluate(invalid));
    }

    static void unsupportedSemanticActionHasNoAward() {
        XpPolicy policy = GameplaySemanticXpPolicy.INSTANCE;
        var crafted = action(SemanticActionType.ITEM_CRAFTED, "minecraft:diamond_pickaxe", Map.of(), Set.of());
        eq(Optional.empty(), policy.evaluate(crafted));
    }

    private static SemanticAction action(
        SemanticActionType type,
        String subject,
        Map<String, Double> metrics,
        Set<String> tags
    ) {
        return new SemanticAction(
            type,
            subject,
            new ActionOrigin("test:semantic_gameplay_policy", 0),
            SemanticActionAuthorship.DIRECT_PLAYER,
            new SemanticActionContext(OptionalLong.empty(), metrics, tags)
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
