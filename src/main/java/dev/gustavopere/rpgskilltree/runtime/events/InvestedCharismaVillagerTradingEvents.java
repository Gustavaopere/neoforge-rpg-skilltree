package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * A narrow vanilla villager session boundary. On the native merchant menu
 * opening, vanilla has already applied that player's gossip/Hero prices, but
 * has not yet sent the offers packet. The native Villager.stopTrading resets
 * specialPriceDiff on closure; the mod never modifies gossip or base offers.
 *
 * This is not a general merchant/other-mod economy adapter.
 */
public final class InvestedCharismaVillagerTradingEvents {
    private static final Set<MerchantMenu> DISCOUNTED_MENUS =
        Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private InvestedCharismaVillagerTradingEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onOpen(PlayerContainerEvent.Open event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || player.level().isClientSide()
            || player instanceof FakePlayer || player.isCreative() || player.isSpectator()
            || !(event.getContainer() instanceof MerchantMenu menu)
            || menu.getClass() != MerchantMenu.class || player.containerMenu != menu
            || !menu.stillValid(player)) return;

        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = ranks.rank(AttributeId.CHARISMA);
        if (rank < 5L) return;
        double discountRatio = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_VILLAGER_DISCOUNT, rank);
        if (!(discountRatio > 0.0D) || !Double.isFinite(discountRatio)
            || discountRatio > 0.080000001D) return;

        // MerchantMenu does not expose a public getTrader() in MC 1.21.1.
        // Verify *both* the exclusive current trader and the exact offers
        // collection identity, within vanilla interaction reach. Fail closed
        // if multiple entities could claim ownership of the same menu.
        MerchantOffers offers = menu.getOffers();
        if (offers == null || offers.isEmpty()) return;
        List<Villager> owners = player.serverLevel().getEntitiesOfClass(
            Villager.class, player.getBoundingBox().inflate(8.0D),
            v -> v.getClass() == Villager.class && v.isAlive()
                && v.getTradingPlayer() == player && v.getOffers() == offers);
        if (owners.size() != 1) return;

        synchronized (DISCOUNTED_MENUS) {
            if (!DISCOUNTED_MENUS.add(menu)) return;
        }
        // Modify only emerald->goods purchases with one input, after vanilla
        // prices have been computed. Vanilla clamps prices to >=1 emerald.
        // No cashback, additional offers, demand changes or permanent gossip.
        for (MerchantOffer offer : offers) {
            if (offer.isOutOfStock()
                || !offer.getCostA().is(Items.EMERALD)
                || !offer.getItemCostB().isEmpty()
                || offer.getResult().is(Items.EMERALD)) continue;
            int currentCost = offer.getCostA().getCount();
            if (currentCost <= 1) continue;
            int reduction = Math.min(currentCost - 1,
                (int) Math.floor((double) currentCost * discountRatio));
            if (reduction <= 0) continue;
            long newDiff = (long) offer.getSpecialPriceDiff() - reduction;
            if (newDiff < Integer.MIN_VALUE || newDiff > Integer.MAX_VALUE) continue;
            offer.setSpecialPriceDiff((int) newDiff);
        }
    }

    @SubscribeEvent
    public static void onClose(PlayerContainerEvent.Close event) {
        if (event.getContainer() instanceof MerchantMenu menu) {
            synchronized (DISCOUNTED_MENUS) {
                DISCOUNTED_MENUS.remove(menu);
            }
        }
        // The native MerchantMenu.removed -> Villager.setTradingPlayer(null)
        // -> Villager.stopTrading -> resetSpecialPrices chain has already run.
        // Re-applying old offsets here would leak reputation prices.
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (DISCOUNTED_MENUS) {
            DISCOUNTED_MENUS.clear();
        }
    }
}
