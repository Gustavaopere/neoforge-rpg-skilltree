package dev.gustavopere.rpgskilltree.itemization.domain;

import java.util.Objects;

/** Read-only Stage 11 boundary. Queries never materialize or regenerate itemization. */
public final class ItemizationQueryService {
    private ItemizationQueryService() {}

    public static ItemizationSnapshot snapshot(ItemizationState state) {
        Objects.requireNonNull(state, "state");
        return new ItemizationSnapshot(
            state.identity(),
            state.rank(),
            state.itemPower(),
            state.generationSource(),
            state.modifiers()
        );
    }
}
