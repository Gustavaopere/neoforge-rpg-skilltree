package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.AntiFarmService;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticXpPipeline;
import dev.gustavopere.rpgskilltree.core.SemanticXpResult;
import dev.gustavopere.rpgskilltree.core.XpPolicy;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesCatalog;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/**
 * Transitional single routing boundary for repeatable gameplay semantic XP.
 *
 * <p>When an authoritative Core rules snapshot is installed, the uncapped Core is
 * the only XP mutation target. Until a production Core ruleset is configured, the
 * legacy XP runtime remains a compatibility fallback so gameplay does not crash or
 * silently lose progression. Event adapters never choose the backend.</p>
 */
public final class GameplaySemanticXpRuntime {
    private GameplaySemanticXpRuntime() {}

    public static SemanticXpResult apply(
        ServerPlayer player,
        SemanticAction action,
        AntiFarmService antiFarmService,
        XpPolicy xpPolicy
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(antiFarmService, "antiFarmService");
        Objects.requireNonNull(xpPolicy, "xpPolicy");

        var rules = Objects.requireNonNull(
            CoreProgressionRulesCatalog.provider().current(),
            "Core progression rules provider returned null"
        );
        if (rules.isPresent()) {
            return CorePlayerProgressionRuntime.applySemanticAction(
                player,
                action,
                antiFarmService,
                xpPolicy,
                rules.get()
            ).semanticXp();
        }

        SemanticXpResult semantic = SemanticXpPipeline.evaluate(action, antiFarmService, xpPolicy);
        if (semantic.award().isPresent()) {
            PlayerProgressionRuntime.applyXp(player, semantic.award().orElseThrow());
        }
        return semantic;
    }
}
