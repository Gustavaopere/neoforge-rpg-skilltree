package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure entity-level floor/roll resolver. Multiplayer relevance and rarity selection are external policies. */
public final class EntityLevelService {
    private EntityLevelService() {}

    public static EntityLevelResolution resolve(EntityLevelContext context, EntityLevelAdjustment adjustment) {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(adjustment, "adjustment");

        long baseFloor = context.baseFloor();
        long rolled = Math.addExact(baseFloor, adjustment.variance());
        rolled = Math.addExact(rolled, adjustment.rarityBonus());

        long finalLevel;
        if (context.relevantPlayerLevel().isPresent()) {
            finalLevel = Math.max(context.relevantPlayerLevel().getAsLong(), rolled);
        } else {
            finalLevel = Math.max(0L, rolled);
        }

        return new EntityLevelResolution(
            context.archetype(),
            context.nativeAreaLevel(),
            context.relevantPlayerLevel(),
            baseFloor,
            rolled,
            finalLevel
        );
    }
}
