package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerQueryService;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerSnapshot;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerState;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesCatalog;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Server-side observational access to the canonical player RPG state. */
public final class CanonicalPlayerQueryRuntime {
    private CanonicalPlayerQueryRuntime() {}

    /**
     * Returns the unified read-only RPG projection without materializing migration,
     * writing attachments or synchronizing the client as a side effect of observation.
     */
    public static CanonicalPlayerSnapshot query(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();

        CanonicalPlayerAttachmentData observed = CanonicalPlayerAttachmentRuntime.observe(player);
        CanonicalPlayerAttachmentData projected = observed.initializeCore(rules);
        CanonicalPlayerState state = projected.initializedState().orElseThrow();
        return CanonicalPlayerQueryService.snapshot(state, rules);
    }
}
