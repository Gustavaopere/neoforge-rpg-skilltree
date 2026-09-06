package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.WeaponMasteryMilestonePolicy.Milestone;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.List;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Server-thread boundary that persists a weapon milestone exactly once per discovery identity. */
public final class WeaponMasteryMilestoneRuntime {
    private WeaponMasteryMilestoneRuntime() {}

    public static boolean awardIfNew(ServerPlayer player, Milestone milestone) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(milestone, "milestone");
        if (PlayerProgressionRuntime.get(player).discoveries().contains(milestone.discoveryKey())) {
            return false;
        }
        PlayerProgressionRuntime.awardMasteryAndDiscoveries(
            player,
            milestone.awards(),
            List.of(milestone.discoveryKey())
        );
        return true;
    }
}
