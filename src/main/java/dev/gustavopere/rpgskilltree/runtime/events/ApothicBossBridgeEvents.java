package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * Captures Apotheosis Elite identity before Apotheosis rewrites {@code apoth.miniboss} from the
 * selected Elite registry id to a boolean marker during delayed initialization.
 *
 * <p>This bridge deliberately has no compile-time Apotheosis dependency; the persistent-data keys
 * are part of the entity data written by Apotheosis itself.</p>
 */
public final class ApothicBossBridgeEvents {
    private ApothicBossBridgeEvents() {}

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void captureEliteIdentity(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Mob mob)) return;
        CompoundTag data = mob.getPersistentData();
        if (!data.contains(BossRewardKeyResolver.APOTH_ELITE_MARKER, Tag.TAG_STRING)) return;
        String eliteId = data.getString(BossRewardKeyResolver.APOTH_ELITE_MARKER);
        if (eliteId == null || eliteId.isBlank()) return;
        data.putString(BossRewardKeyResolver.CAPTURED_APOTH_ELITE_ID, eliteId);
    }
}
