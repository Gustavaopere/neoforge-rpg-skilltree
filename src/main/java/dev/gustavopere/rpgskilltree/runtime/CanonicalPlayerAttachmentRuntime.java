package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

/**
 * Single NeoForge persistence boundary for player RPG state.
 *
 * <p>The legacy progression attachments remain registered only so old saves can be
 * decoded. The first mutating access materializes {@code CANONICAL_PLAYER} and then
 * removes the old attachment copies. Observational access never persists migration.</p>
 */
public final class CanonicalPlayerAttachmentRuntime {
    private CanonicalPlayerAttachmentRuntime() {}

    public static CanonicalPlayerAttachmentData readOrMigrate(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        if (player.hasData(ModAttachments.CANONICAL_PLAYER)) {
            return player.getData(ModAttachments.CANONICAL_PLAYER);
        }

        CanonicalPlayerAttachmentData migrated = migrationInputs(player);
        player.setData(ModAttachments.CANONICAL_PLAYER, migrated);
        removeLegacyCopies(player);
        return migrated;
    }

    public static CanonicalPlayerAttachmentData observe(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        if (player.hasData(ModAttachments.CANONICAL_PLAYER)) {
            return player.getData(ModAttachments.CANONICAL_PLAYER);
        }
        return migrationInputs(player);
    }

    public static void write(ServerPlayer player, CanonicalPlayerAttachmentData attachment) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(attachment, "attachment");
        player.setData(ModAttachments.CANONICAL_PLAYER, attachment);
        removeLegacyCopies(player);
    }

    private static CanonicalPlayerAttachmentData migrationInputs(ServerPlayer player) {
        Optional<CoreProgressionAttachmentData> oldCore = player.hasData(ModAttachments.CORE_PROGRESSION)
            ? Optional.of(player.getData(ModAttachments.CORE_PROGRESSION))
            : Optional.empty();
        Optional<ProgressionState> oldCompatibility = player.hasData(ModAttachments.PROGRESSION)
            ? Optional.of(player.getData(ModAttachments.PROGRESSION))
            : Optional.empty();
        return CanonicalPlayerAttachmentData.fromMigrationInputs(oldCore, oldCompatibility);
    }

    private static void removeLegacyCopies(ServerPlayer player) {
        if (player.hasData(ModAttachments.PROGRESSION)) {
            player.removeData(ModAttachments.PROGRESSION);
        }
        if (player.hasData(ModAttachments.CORE_PROGRESSION)) {
            player.removeData(ModAttachments.CORE_PROGRESSION);
        }
    }
}
