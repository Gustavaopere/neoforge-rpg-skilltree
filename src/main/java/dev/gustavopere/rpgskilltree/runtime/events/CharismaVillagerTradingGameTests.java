package dev.gustavopere.rpgskilltree.runtime.events;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CharacterProgressionState;
import dev.gustavopere.rpgskilltree.core.CorePointLedger;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.runtime.ModAttachments;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

// S2187 assumes JUnit, not NeoForge's real @GameTest methods.
@SuppressWarnings("java:S2187")
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class CharismaVillagerTradingGameTests {
    private CharismaVillagerTradingGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void buyingEmeraldOffersDiscountOnlyOnceWithVanillaPriceReset(GameTestHelper helper) {
        ServerPlayer buyer = player(helper, "charisma-buyer");
        setRank(buyer, 300L);
        Villager villager = villager(helper, buyer);
        MerchantOffers offers = villager.getOffers();
        offers.clear();
        MerchantOffer emeraldPurchase = offer(64, Items.BREAD);
        offers.add(emeraldPurchase);
        villager.setTradingPlayer(buyer);
        MerchantMenu menu = new MerchantMenu(32, buyer.getInventory(), villager);
        buyer.containerMenu = menu;

        double fraction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_VILLAGER_DISCOUNT, 300L);
        int expected = 64 - Math.min(63, (int) Math.floor(64D * fraction));
        helper.assertTrue(expected < 64 && expected >= 1, "rank 300 has bounded discount");

        helper.assertTrue(!buyer.isCreative() && !buyer.isSpectator(),
            "detached buyer must be in survival for a legitimate merchant boundary");
        helper.assertTrue(menu.getClass() == MerchantMenu.class && buyer.containerMenu == menu,
            "test fixture must own a native current MerchantMenu");
        helper.assertTrue(menu.stillValid(buyer) && villager.getTradingPlayer() == buyer,
            "merchant must designate buyer as the exclusive trading player");
        helper.assertTrue(menu.getOffers() == villager.getOffers()
            && menu.getOffers().contains(emeraldPurchase),
            "merchant offers must be exactly the villager's native shared collection");
        helper.assertTrue(helper.getLevel().getEntitiesOfClass(Villager.class,
            buyer.getBoundingBox().inflate(8.0D),
            v -> v.getClass() == Villager.class && v.isAlive()
                && v.getTradingPlayer() == buyer && v.getOffers() == menu.getOffers()).size() == 1,
            "authoritative nearby villager owner must be uniquely identified");
        helper.assertTrue(emeraldPurchase.getCostA().is(Items.EMERALD)
            && emeraldPurchase.getItemCostB().isEmpty() && !emeraldPurchase.isOutOfStock(),
            "native merchant offer must be a simple emerald purchase");
        PlayerContainerEvent.Open open = new PlayerContainerEvent.Open(buyer, menu);
        InvestedCharismaVillagerTradingEvents.onOpen(open);
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == expected,
            "pre-transaction offer price must be reduced in native merchant inventory");
        helper.assertTrue(emeraldPurchase.getBaseCostA().getCount() == 64,
            "Charisma must never rewrite the underlying vanilla base cost");
        helper.assertTrue(emeraldPurchase.satisfiedBy(
            new ItemStack(Items.EMERALD, expected), ItemStack.EMPTY),
            "server-side MerchantOffer must accept the exact discounted payment");
        helper.assertTrue(!emeraldPurchase.satisfiedBy(
            new ItemStack(Items.EMERALD, expected - 1), ItemStack.EMPTY),
            "server-side merchant must reject a single emerald less than the discount");
        ItemStack chargedEmeralds = new ItemStack(Items.EMERALD, expected);
        helper.assertTrue(emeraldPurchase.take(chargedEmeralds, ItemStack.EMPTY),
            "native MerchantOffer.take must collect the discounted payment exactly");
        helper.assertTrue(chargedEmeralds.isEmpty(),
            "the server must consume the entire validated payment");
        InvestedCharismaVillagerTradingEvents.onOpen(open);
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == expected,
            "replayed open must not compound a price discount");

        // Villager.stopTrading clears player reputation and session prices in
        // Minecraft's own lifecycle; avoid any persisted or globally modified offer.
        buyer.containerMenu = buyer.inventoryMenu;
        villager.setTradingPlayer(null);
        InvestedCharismaVillagerTradingEvents.onClose(new PlayerContainerEvent.Close(buyer, menu));
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == 64,
            "closing vanilla merchant must restore unmodified price");

        ServerPlayer other = player(helper, "charisma-rank-zero");
        setRank(other, 0L);
        villager.setTradingPlayer(other);
        MerchantMenu otherMenu = new MerchantMenu(33, other.getInventory(), villager);
        other.containerMenu = otherMenu;
        InvestedCharismaVillagerTradingEvents.onOpen(new PlayerContainerEvent.Open(other, otherMenu));
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == 64,
            "a second rank-zero player must not inherit the prior buyer's discount");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void excludesSalesDoubleCostOneEmeraldAndStaleMenus(GameTestHelper helper) {
        ServerPlayer buyer = player(helper, "charisma-restrictions");
        setRank(buyer, Long.MAX_VALUE);
        Villager villager = villager(helper, buyer);
        MerchantOffers offers = villager.getOffers();
        offers.clear();
        MerchantOffer purchase = offer(64, Items.BREAD);
        MerchantOffer sale = new MerchantOffer(new ItemCost(Items.WHEAT, 20),
            new ItemStack(Items.EMERALD), 10, 2, 0.0F);
        MerchantOffer oneEmerald = offer(1, Items.BREAD);
        MerchantOffer twoCosts = new MerchantOffer(
            new ItemCost(Items.EMERALD, 64), java.util.Optional.of(new ItemCost(Items.BREAD, 1)),
            new ItemStack(Items.COOKIE), 10, 2, 0.0F);
        offers.add(purchase);
        offers.add(sale);
        offers.add(oneEmerald);
        offers.add(twoCosts);
        villager.setTradingPlayer(buyer);
        MerchantMenu menu = new MerchantMenu(34, buyer.getInventory(), villager);
        buyer.containerMenu = buyer.inventoryMenu;
        InvestedCharismaVillagerTradingEvents.onOpen(new PlayerContainerEvent.Open(buyer, menu));
        helper.assertTrue(purchase.getCostA().getCount() == 64,
            "stale menu must not alter an offer");

        buyer.containerMenu = menu;
        InvestedCharismaVillagerTradingEvents.onOpen(new PlayerContainerEvent.Open(buyer, menu));
        helper.assertTrue(purchase.getCostA().getCount() >= 1
            && purchase.getCostA().getCount() < 64,
            "only a current eligible purchase receives bounded discount");
        helper.assertTrue(sale.getCostA().getCount() == 20,
            "sale of items for emeralds is excluded");
        helper.assertTrue(oneEmerald.getCostA().getCount() == 1,
            "offer price must never become zero");
        helper.assertTrue(twoCosts.getCostA().getCount() == 64,
            "dual-input exchanges are not audited for the narrow initial boundary");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void preexistingVanillaSpecialPriceIsPreservedAndNotInherited(GameTestHelper helper) {
        ServerPlayer buyer = player(helper, "charisma-gossip");
        setRank(buyer, 300L);
        Villager villager = villager(helper, buyer);
        MerchantOffers offers = villager.getOffers();
        offers.clear();
        MerchantOffer withReputation = offer(64, Items.BREAD);
        withReputation.setSpecialPriceDiff(-5);
        offers.add(withReputation);
        villager.setTradingPlayer(buyer);
        MerchantMenu menu = new MerchantMenu(35, buyer.getInventory(), villager);
        buyer.containerMenu = menu;

        helper.assertTrue(withReputation.getCostA().getCount() == 59,
            "vanilla reputation-like offset must be present before opening");
        double fraction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_VILLAGER_DISCOUNT, 300L);
        int expected = 59 - (int) Math.floor(59.0D * fraction);
        InvestedCharismaVillagerTradingEvents.onOpen(new PlayerContainerEvent.Open(buyer, menu));
        helper.assertTrue(withReputation.getCostA().getCount() == expected,
            "Charisma should be additive after vanilla pricing, never replacing gossip");

        buyer.containerMenu = buyer.inventoryMenu;
        villager.setTradingPlayer(null);
        InvestedCharismaVillagerTradingEvents.onClose(
            new PlayerContainerEvent.Close(buyer, menu));
        helper.assertTrue(withReputation.getCostA().getCount() == 64,
            "vanilla merchant must clear all session-specific price differences");

        ServerPlayer other = player(helper, "charisma-gossip-other");
        setRank(other, 0L);
        villager.setTradingPlayer(other);
        MerchantMenu nextMenu = new MerchantMenu(36, other.getInventory(), villager);
        other.containerMenu = nextMenu;
        InvestedCharismaVillagerTradingEvents.onOpen(
            new PlayerContainerEvent.Open(other, nextMenu));
        helper.assertTrue(withReputation.getCostA().getCount() == 64,
            "second player inherits neither Charisma nor prior gossip offsets");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void emeraldIntegerDiscountThresholdsRemainStableAcrossSessions(GameTestHelper helper) {
        // Expected counts are independent, fixed acceptance values. This
        // catches accidental ceil/rounding, rank off-by-one, price-zero and
        // leaked session offsets without reproducing the production formula.
        int[] baseCosts = {12, 13, 16, 20, 32, 64};
        long[] ranks = {0L, 4L, 5L, 25L, 30L, 60L, 65L, 120L, 125L, 185L,
            190L, Long.MAX_VALUE};
        int[][] expectedCosts = {
            {12, 13, 16, 20, 32, 64},
            {12, 13, 16, 20, 32, 64},
            {12, 13, 16, 20, 32, 64},
            {12, 13, 16, 20, 32, 64},
            {12, 13, 16, 20, 32, 63},
            {12, 13, 16, 20, 32, 63},
            {12, 13, 16, 20, 31, 62},
            {12, 13, 16, 20, 31, 61},
            {12, 13, 16, 19, 31, 61},
            {12, 13, 16, 19, 31, 61},
            {12, 13, 15, 19, 30, 60},
            {12, 12, 15, 19, 30, 59}
        };
        ServerPlayer origin = player(helper, "charisma-price-origin");
        Villager villager = villager(helper, origin);
        MerchantOffers offers = villager.getOffers();
        offers.clear();
        for (int baseCost : baseCosts) {
            offers.add(offer(baseCost, Items.BREAD));
        }

        for (int i = 0; i < ranks.length; i++) {
            ServerPlayer buyer = player(helper, "charisma-price-" + i);
            setRank(buyer, ranks[i]);
            villager.setTradingPlayer(buyer);
            MerchantMenu menu = new MerchantMenu(40 + i, buyer.getInventory(), villager);
            buyer.containerMenu = menu;
            InvestedCharismaVillagerTradingEvents.onOpen(new PlayerContainerEvent.Open(buyer, menu));

            for (int j = 0; j < baseCosts.length; j++) {
                MerchantOffer offer = offers.get(j);
                helper.assertTrue(offer.getCostA().getCount() == expectedCosts[i][j],
                    "integer price mismatch for CHA " + ranks[i] + " and cost " + baseCosts[j]);
                helper.assertTrue(offer.getBaseCostA().getCount() == baseCosts[j],
                    "base price must remain immutable after the session discount");
            }

            buyer.containerMenu = buyer.inventoryMenu;
            villager.setTradingPlayer(null);
            InvestedCharismaVillagerTradingEvents.onClose(new PlayerContainerEvent.Close(buyer, menu));
            for (int j = 0; j < baseCosts.length; j++) {
                helper.assertTrue(offers.get(j).getCostA().getCount() == baseCosts[j],
                    "closing a priced session must restore all vanilla offers");
            }
        }
        helper.succeed();
    }

    private static MerchantOffer offer(int emeralds, net.minecraft.world.item.Item result) {
        return new MerchantOffer(new ItemCost(Items.EMERALD, emeralds),
            new ItemStack(result), 10, 2, 0.0F);
    }

    private static Villager villager(GameTestHelper helper, ServerPlayer buyer) {
        // Spawn through the GameTest structure coordinates, not detached-player
        // world coordinates: provider suites may place structures far apart.
        Villager entity = helper.spawn(EntityType.VILLAGER, new BlockPos(2, 2, 1));
        helper.assertTrue(entity != null && entity.isAlive(),
            "villager must be spawned by the native GameTest helper");
        helper.assertTrue(helper.getLevel().getEntitiesOfClass(
            Villager.class, buyer.getBoundingBox().inflate(8.0D)).contains(entity),
            "spawned villager must be discoverable inside the loaded GameTest structure");
        return entity;
    }

    private static ServerPlayer player(GameTestHelper helper, String name) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), name), ClientInformation.createDefault());
        BlockPos insideStructure = helper.absolutePos(new BlockPos(1, 2, 1));
        player.setPos(insideStructure.getX() + 0.5D,
            insideStructure.getY(), insideStructure.getZ() + 0.5D);
        return player;
    }

    private static void setRank(ServerPlayer player, long rank) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.CHARISMA, rank));
        CoreProgressionState state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }
}
