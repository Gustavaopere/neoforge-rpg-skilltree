package dev.gustavopere.rpgskilltree.runtime.effects;

import dev.gustavopere.rpgskilltree.core.A0081A0100CombatPolicy;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsInvestmentAttributesRuntime;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

/**
 * Server-authoritative vanilla investment adapter. No source of state is stored here.
 * Stable transient modifier identities are removed and recomputed on every refresh.
 * Provider-dependent contributions are intentionally not applied in this adapter.
 */
public final class InvestedAttributeEffectsRuntime {
    private record NativeBonus(
        Benefit benefit, ResourceLocation modifierId, ResourceLocation nativeAttributeId,
        AttributeModifier.Operation operation
    ) {}

    private static final List<NativeBonus> NATIVE = List.of(
        bonus(Benefit.STRENGTH_KNOCKBACK_RESISTANCE, "strength_knockback", "minecraft:generic.knockback_resistance",
            AttributeModifier.Operation.ADD_VALUE),
        bonus(Benefit.STRENGTH_MANUAL_MINING, "strength_manual_mining", "minecraft:player.block_break_speed",
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
        bonus(Benefit.CONSTITUTION_MAX_HEALTH, "constitution_health", "minecraft:generic.max_health",
            AttributeModifier.Operation.ADD_VALUE),
        bonus(Benefit.AGILITY_MOVEMENT_SPEED, "agility_movement", "minecraft:generic.movement_speed",
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
        bonus(Benefit.AGILITY_ATTACK_SPEED, "agility_attack_speed", "minecraft:generic.attack_speed",
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );

    private InvestedAttributeEffectsRuntime() {}

    private static NativeBonus bonus(Benefit benefit, String id, String target, AttributeModifier.Operation operation) {
        return new NativeBonus(benefit, ResourceLocation.parse("rpgskilltree:investment/" + id),
            ResourceLocation.parse(target), operation);
    }

    public static void refresh(ServerPlayer player, AttributeRanks investedRanks) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(investedRanks, "investedRanks");
        if (player.level().isClientSide()) throw new IllegalArgumentException("investment modifiers require server authority");

        double oldHealth = player.getHealth();
        double oldMaxHealth = player.getMaxHealth();

        // Always clear stable historical identities before checking current ranks.
        for (NativeBonus nativeBonus : NATIVE) {
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(nativeBonus.nativeAttributeId()).orElse(null);
            if (holder == null) continue;
            var instance = player.getAttribute(holder);
            if (instance != null) instance.removeModifier(nativeBonus.modifierId());
        }

        for (NativeBonus nativeBonus : NATIVE) {
            double amount = AttributeInvestmentBonuses.value(nativeBonus.benefit(),
                investedRanks.rank(nativeBonus.benefit().attribute()));
            if (amount <= 0.0) continue;
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(nativeBonus.nativeAttributeId()).orElse(null);
            if (holder == null) continue; // Fail closed; never replace with an unrelated effect.
            var instance = player.getAttribute(holder);
            if (instance == null) continue;
            instance.addOrUpdateTransientModifier(new AttributeModifier(
                nativeBonus.modifierId(), amount, nativeBonus.operation()));
        }

        IronsInvestmentAttributesRuntime.refresh(player, investedRanks);

        double newMaxHealth = player.getMaxHealth();
        if (oldMaxHealth > 0.0 && newMaxHealth > 0.0 && oldMaxHealth != newMaxHealth) {
            player.setHealth((float) A0081A0100CombatPolicy.preserveHealthRatio(
                oldHealth, oldMaxHealth, newMaxHealth));
        }
    }
}
