package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;
import java.util.Optional;

/** Pure policy for accepting only server-confirmed structure observations. */
public final class WorldDiscoveryPolicy {
    private WorldDiscoveryPolicy() {}

    public static Optional<CompendiumEntryId> confirmStructure(String serverObservedStructureId, boolean serverHasPieceAtPlayer) {
        if (!serverHasPieceAtPlayer) return Optional.empty();
        return Optional.of(CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, serverObservedStructureId));
    }

    public static boolean matchesRequestedStructure(
        String requestedStructureId,
        String serverObservedStructureId,
        boolean serverHasPieceAtPlayer
    ) {
        if (!serverHasPieceAtPlayer) return false;
        return Objects.equals(requestedStructureId, serverObservedStructureId);
    }
}
