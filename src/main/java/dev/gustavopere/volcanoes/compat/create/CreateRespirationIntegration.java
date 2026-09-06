package dev.gustavopere.volcanoes.compat.create;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import dev.gustavopere.volcanoes.pressure.PressureNeoForgeRuntime;
import dev.gustavopere.volcanoes.pressure.ProtectionCapability;
import dev.gustavopere.volcanoes.pressure.ProtectionContribution;
import dev.gustavopere.volcanoes.pressure.ProtectionUseRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Exact Create 6.0.10 bridge for pressurized-air respiration.
 *
 * <p>The Stage05 {@link ProtectionUseRegistry} remains transaction authority. Create contributes
 * oxygen to the shared Pressure session; Atmosphere consumes that same session through the generic
 * Pressure respiration bridge. One physical {@code resourceDebitKey} therefore cannot be consumed
 * twice by overlapping callbacks. Create air supplies oxygen only and never implies filtration.</p>
 */
public final class CreateRespirationIntegration {
    private static final String SOURCE_ID = "create:pressurized_air";
    private static boolean installed;

    private CreateRespirationIntegration() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        PressureNeoForgeRuntime.registerHostProtectionProvider(
                CreateRespirationIntegration::hostContributions);
        installed = true;
    }

    private static List<ProtectionContribution> hostContributions(
            ServerPlayer player,
            long gameTick
    ) {
        if (!DivingHelmetItem.isWornBy(player)) {
            return List.of();
        }
        List<ItemStack> backtanks = BacktankUtil.getAllWithAir(player);
        if (backtanks.isEmpty()) {
            return List.of();
        }

        ItemStack selected = backtanks.getFirst();
        String resourceDebitKey = resourceDebitKey(player, selected);
        ProtectionContribution oxygen = ProtectionContribution.consumable(
                SOURCE_ID,
                resourceDebitKey,
                Map.of(
                        ProtectionCapability.OXYGEN_SUPPLY,
                        CreateRespirationDecision.BREATHABLE_OXYGEN_PARTIAL_PRESSURE_ATM),
                () -> consumeAirForTick(player, selected, gameTick));
        return List.of(oxygen);
    }

    private static boolean consumeAirForTick(
            ServerPlayer player,
            ItemStack backtank,
            long gameTick
    ) {
        if (!BacktankUtil.hasAirRemaining(backtank)) {
            return false;
        }
        if (Math.floorMod(gameTick, CreateRespirationDecision.CREATE_AIR_DEBIT_INTERVAL_TICKS) == 0) {
            BacktankUtil.consumeAir(player, backtank, 1);
        }
        return true;
    }

    private static String resourceDebitKey(ServerPlayer player, ItemStack selected) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (player.getItemBySlot(slot) == selected) {
                return "create:backtank:" + player.getUUID() + ":" + slot.getName();
            }
        }
        // Create allows optional suppliers (e.g. accessory slots). Object identity is stable for the
        // captured stack during this one-tick transaction and prevents two supplied tanks collapsing.
        return "create:backtank:" + player.getUUID() + ":host-" + System.identityHashCode(selected);
    }
}
