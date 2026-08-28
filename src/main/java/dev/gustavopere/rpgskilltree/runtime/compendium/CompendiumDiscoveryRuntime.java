package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryCriterion;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryProgress;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryRuntime;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoverySignal;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryTransition;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** Server-authoritative NeoForge boundary for per-player Compendium discovery. */
public final class CompendiumDiscoveryRuntime {
    private CompendiumDiscoveryRuntime() {}

    public static DiscoveryProgress progress(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        return player.getData(ModAttachments.COMPENDIUM_DISCOVERY);
    }

    public static DiscoveryTransition apply(
        ServerPlayer player,
        DiscoveryCriterion criterion,
        DiscoverySignal signal
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(criterion, "criterion");
        Objects.requireNonNull(signal, "signal");
        if (player.level().isClientSide()) {
            throw new IllegalStateException("Compendium discovery mutations are server-only");
        }

        DiscoveryProgress current = progress(player);
        DiscoveryTransition transition = DiscoveryRuntime.apply(current, criterion, signal);
        if (!transition.changed()) return transition;

        for (var reward : transition.newRewards()) {
            CompendiumDiscoveryRewardBridge.apply(player, reward);
        }
        player.setData(ModAttachments.COMPENDIUM_DISCOVERY, transition.progress());
        return transition;
    }
}
