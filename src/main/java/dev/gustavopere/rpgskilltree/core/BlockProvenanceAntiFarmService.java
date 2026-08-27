package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Prevents player-placed ore blocks from being recycled into mining XP. */
public final class BlockProvenanceAntiFarmService implements AntiFarmService {
    private final PlacedBlockProvenance provenance;

    public BlockProvenanceAntiFarmService(PlacedBlockProvenance provenance) {
        this.provenance = Objects.requireNonNull(provenance, "provenance");
    }

    @Override
    public AntiFarmDecision evaluate(SemanticAction action) {
        Objects.requireNonNull(action, "action");
        if (!SemanticActionType.ORE_MINED.equals(action.type())) return AntiFarmDecision.allow();
        var position = action.context().packedBlockPosition();
        if (position.isPresent() && provenance.contains(position.getAsLong())) {
            return AntiFarmDecision.reject("player_placed_block");
        }
        return AntiFarmDecision.allow();
    }
}
