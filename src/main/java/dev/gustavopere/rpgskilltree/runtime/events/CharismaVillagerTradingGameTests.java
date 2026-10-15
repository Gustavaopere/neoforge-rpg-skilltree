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
        MerchantOffers offers = new MerchantOffers();
        MerchantOffer emeraldPurchase = offer(64, Items.BREAD);
        offers.add(emeraldPurchase);
        villager.overrideOffers(offers);
        villager.setTradingPlayer(buyer);
        MerchantMenu menu = new MerchantMenu(32, buyer.getInventory(), villager);
        buyer.containerMenu = menu;

        double fraction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_VILLAGER_DISCOUNT, 300L);
        int expected = 64 - Math.min(63, (int) Math.floor(64D * fraction));
        helper.assertTrue(expected < 64 && expected >= 1, "rank 300 has bounded discount");

        PlayerContainerEvent.Open open = new PlayerContainerEvent.Open(buyer, menu);
        InvestedCharismaVillagerTradingEvents.onOpen(open);
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == expected,
            "pre-transaction offer price must be reduced in native merchant inventory");
        InvestedCharismaVillagerTradingEvents.onOpen(open);
        helper.assertTrue(emeraldPurchase.getCostA().getCount() == expected,
            "replayed open must not compound a price discount");

        // Villager.stopTrading clears player reputation and session prices in
        // Minecraft's own lifecycle; avoid any persisted or globally modified offer.
        buyer.containerMenu = buyer.inventoryMenu;
        villager.setTradingPlayer(null);
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
        MerchantOffers offers = new MerchantOffers();
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
        villager.overrideOffers(offers);
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

    private static MerchantOffer offer(int emeralds, net.minecraft.world.item.Item result) {
        return new MerchantOffer(new ItemCost(Items.EMERALD, emeralds),
            new ItemStack(result), 10, 2, 0.0F);
    }

    private static Villager villager(GameTestHelper helper, ServerPlayer buyer) {
        Villager entity = new Villager(EntityType.VILLAGER, helper.getLevel());
        entity.moveTo(buyer.getX() + 1.0D, buyer.getY(), buyer.getZ());
        helper.assertTrue(helper.getLevel().addFreshEntity(entity),
            "villager must be present in the level for authoritative lookup");
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
