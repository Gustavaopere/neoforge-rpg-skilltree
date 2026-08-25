package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.DamageMitigationResolver;
import dev.gustavopere.rpgskilltree.core.EmergencyGuardService;
import dev.gustavopere.rpgskilltree.core.FrozenDefensiveTradeoffPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkRanks;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ReactiveShellService;
import dev.gustavopere.rpgskilltree.core.SecondWindService;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Server owner for A0101-A0150 runtime state; transient clears retain cooldowns and claims. */
public final class FrozenSurvivalRuntimeState {
    private static final int MAX_PLAYERS = 256;
    private static final ResourceLocation A0105_ARMOR = id("a0105_reactive_armor");
    private static final ResourceLocation A0105_TOUGHNESS = id("a0105_reactive_toughness");
    private static final ResourceLocation A0108_MOVEMENT = id("a0108_stone_skin_movement_cost");
    private static final DamageMitigationResolver DAMAGE_MITIGATION = new DamageMitigationResolver(8_192);
    private static final SecondWindService SECOND_WIND = new SecondWindService(MAX_PLAYERS);
    private static final ReactiveShellService REACTIVE_SHELL = new ReactiveShellService(MAX_PLAYERS);
    private static final EmergencyGuardService EMERGENCY_GUARD = new EmergencyGuardService(MAX_PLAYERS);

    private FrozenSurvivalRuntimeState() {}

    public static DamageMitigationResolver damageMitigation() { return DAMAGE_MITIGATION; }
    public static SecondWindService secondWind() { return SECOND_WIND; }
    public static ReactiveShellService reactiveShell() { return REACTIVE_SHELL; }
    public static EmergencyGuardService emergencyGuard() { return EMERGENCY_GUARD; }

    public static FrozenSurvivalPerkRanks ranks(ProgressionState progression) {
        Objects.requireNonNull(progression);
        return FrozenSurvivalPerkNodeBinding.ranks(progression.passiveNodes());
    }

    public static FrozenSurvivalPerkRanks ranks(ServerPlayer player) {
        return ranks(PlayerProgressionRuntime.get(player));
    }

    public static void revalidate(ServerPlayer player, ProgressionState progression) {
        Objects.requireNonNull(player);
        FrozenSurvivalPerkRanks ranks = ranks(progression);
        String playerId = player.getUUID().toString();
        if (!ranks.learned("A0104")) SECOND_WIND.clearTransient(playerId);
        if (!ranks.learned("A0105")) REACTIVE_SHELL.clearTransient(playerId);
        if (!ranks.learned("A0106")) EMERGENCY_GUARD.clearTransient(playerId);
        refreshStoneSkin(player, ranks);
        refreshReactiveShell(player, ranks, player.level().getGameTime());
    }

    public static boolean stoneSkinCostApplied(ServerPlayer player) {
        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        return movement != null && movement.getModifier(A0108_MOVEMENT) != null;
    }

    public static void refreshReactiveShell(
        ServerPlayer player,
        FrozenSurvivalPerkRanks ranks,
        long nowTick
    ) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        AttributeInstance toughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armor != null) armor.removeModifier(A0105_ARMOR);
        if (toughness != null) toughness.removeModifier(A0105_TOUGHNESS);
        if (!ranks.learned("A0105")) return;

        double baseArmor = armor == null ? 0.0D : armor.getValue();
        double baseToughness = toughness == null ? 0.0D : toughness.getValue();
        ReactiveShellService.Bonuses bonuses = REACTIVE_SHELL.bonuses(
            player.getUUID().toString(), baseArmor, baseToughness, nowTick);
        if (armor != null && bonuses.armor() > 0.0D) {
            armor.addOrUpdateTransientModifier(new AttributeModifier(
                A0105_ARMOR, bonuses.armor(), AttributeModifier.Operation.ADD_VALUE));
        }
        if (toughness != null && bonuses.toughness() > 0.0D) {
            toughness.addOrUpdateTransientModifier(new AttributeModifier(
                A0105_TOUGHNESS, bonuses.toughness(), AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void clearTransient(ServerPlayer player) {
        Objects.requireNonNull(player);
        String playerId = player.getUUID().toString();
        SECOND_WIND.clearTransient(playerId);
        REACTIVE_SHELL.clearTransient(playerId);
        EMERGENCY_GUARD.clearTransient(playerId);
        remove(player.getAttribute(Attributes.ARMOR), A0105_ARMOR);
        remove(player.getAttribute(Attributes.ARMOR_TOUGHNESS), A0105_TOUGHNESS);
        remove(player.getAttribute(Attributes.MOVEMENT_SPEED), A0108_MOVEMENT);
    }

    private static void refreshStoneSkin(ServerPlayer player, FrozenSurvivalPerkRanks ranks) {
        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;
        movement.removeModifier(A0108_MOVEMENT);
        FrozenDefensiveTradeoffPolicy.Tradeoff tradeoff = FrozenDefensiveTradeoffPolicy.stoneSkin(
            ranks.rank("A0108"), true, true);
        if (tradeoff.active()) {
            movement.addOrUpdateTransientModifier(new AttributeModifier(
                A0108_MOVEMENT,
                tradeoff.movementSpeedMultiplierDelta(),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }
    }

    private static void remove(AttributeInstance attribute, ResourceLocation id) {
        if (attribute != null) attribute.removeModifier(id);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("rpgskilltree", path);
    }
}
