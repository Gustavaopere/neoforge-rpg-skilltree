package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionBootstrap;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/**
 * NeoForge boundary for initializing the new Core progression beside the legacy runtime.
 *
 * <p>This class deliberately requires an explicit rules snapshot. Automatic login
 * bootstrap is not enabled until the runtime has an authoritative rules provider.</p>
 */
public final class CorePlayerProgressionRuntime {
    private CorePlayerProgressionRuntime() {}

    public static CoreProgressionState bootstrap(
        ServerPlayer player,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(rules);

        if (player.hasData(ModAttachments.CORE_PROGRESSION)) {
            CoreProgressionAttachmentData existing = player.getData(ModAttachments.CORE_PROGRESSION);
            if (existing.isInitialized()) {
                return CoreProgressionBootstrap.resume(existing.state().orElseThrow(), rules);
            }
        }

        final CoreProgressionState initialized;
        if (player.hasData(ModAttachments.PROGRESSION)) {
            initialized = CoreProgressionBootstrap.migrateDecodedLegacy(
                player.getData(ModAttachments.PROGRESSION),
                rules
            );
        } else {
            initialized = CoreProgressionBootstrap.newPlayer(rules);
        }

        player.setData(
            ModAttachments.CORE_PROGRESSION,
            CoreProgressionAttachmentData.initialized(initialized)
        );
        return initialized;
    }
}
