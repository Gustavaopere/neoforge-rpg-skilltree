package dev.gustavopere.rpgskilltree.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

/**
 * Short-lived causal handoff from provider-native PRE hooks to NeoForge's post-mitigation damage
 * receipt. It lets A0081/A0082 reuse the provider root instead of synthesizing a second action id.
 */
public final class A0081A0090ProviderHitRegistry {
    private static final WeakHashMap<DamageSource, Map<UUID, PhysicalHitReceipt>> PENDING = new WeakHashMap<>();

    private A0081A0090ProviderHitRegistry() {}

    public static synchronized void remember(
        DamageSource source,
        UUID targetId,
        PhysicalHitReceipt receipt
    ) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(targetId, "targetId");
        Objects.requireNonNull(receipt, "receipt");
        PENDING.computeIfAbsent(source, ignored -> new HashMap<>()).put(targetId, receipt);
    }

    public static synchronized PhysicalHitReceipt take(DamageSource source, UUID targetId) {
        if (source == null || targetId == null) return null;
        Map<UUID, PhysicalHitReceipt> byTarget = PENDING.get(source);
        if (byTarget == null) return null;
        PhysicalHitReceipt receipt = byTarget.remove(targetId);
        if (byTarget.isEmpty()) PENDING.remove(source);
        return receipt;
    }

    public static synchronized void discard(DamageSource source, UUID targetId) {
        take(source, targetId);
    }

    public static synchronized void clearActor(String actorId) {
        Objects.requireNonNull(actorId, "actorId");
        PENDING.values().forEach(byTarget -> byTarget.values().removeIf(receipt -> receipt.actorId().equals(actorId)));
        PENDING.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public static synchronized void clearAll() {
        PENDING.clear();
    }

    public record PhysicalHitReceipt(
        ServerPlayer player,
        String actorId,
        String rootActionId,
        double targetHealthBefore,
        boolean directMelee,
        ItemStack weaponStack
    ) {
        public PhysicalHitReceipt {
            Objects.requireNonNull(player, "player");
            Objects.requireNonNull(actorId, "actorId");
            Objects.requireNonNull(rootActionId, "rootActionId");
            Objects.requireNonNull(weaponStack, "weaponStack");
            if (actorId.isBlank() || rootActionId.isBlank()) throw new IllegalArgumentException("identity");
            if (!Double.isFinite(targetHealthBefore) || targetHealthBefore < 0.0D) {
                throw new IllegalArgumentException("targetHealthBefore");
            }
            weaponStack = weaponStack.copy();
        }
    }
}
