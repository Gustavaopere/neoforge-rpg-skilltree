package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Adapts normalized semantic gameplay actions to the existing bounded gameplay XP policy. */
public final class GameplaySemanticXpPolicy implements XpPolicy {
    public static final GameplaySemanticXpPolicy INSTANCE = new GameplaySemanticXpPolicy();
    public static final String RARE_ORE_TAG = "rpgskilltree:rare_ore";
    public static final String MAX_HEALTH_METRIC = "max_health";

    private GameplaySemanticXpPolicy() {}

    @Override
    public Optional<CharacterXpAward> resolve(SemanticAction action) {
        Objects.requireNonNull(action, "action");

        if (action.type().equals(SemanticActionType.ORE_MINED)) {
            boolean rare = action.context().tags().contains(RARE_ORE_TAG);
            return Optional.of(GameplayXpPolicy.oreMined(action.subjectId(), rare));
        }

        if (action.type().equals(SemanticActionType.HOSTILE_KILLED)
            || action.type().equals(SemanticActionType.BOSS_DEFEATED)) {
            Double maxHealth = action.context().metrics().get(MAX_HEALTH_METRIC);
            if (maxHealth == null || !Double.isFinite(maxHealth) || maxHealth <= 0.0) {
                return Optional.empty();
            }
            boolean boss = action.type().equals(SemanticActionType.BOSS_DEFEATED);
            return Optional.of(GameplayXpPolicy.combatKill(action.subjectId(), maxHealth, boss));
        }

        if (action.type().equals(SemanticActionType.BIOME_DISCOVERED)) {
            return Optional.of(GameplayXpPolicy.biomeDiscovery(action.subjectId()));
        }

        if (action.type().equals(SemanticActionType.DIMENSION_DISCOVERED)) {
            return Optional.of(GameplayXpPolicy.dimensionDiscovery(action.subjectId()));
        }

        return Optional.empty();
    }
}
