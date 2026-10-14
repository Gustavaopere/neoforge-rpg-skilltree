package dev.gustavopere.rpgskilltree.gametest;

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
import dev.gustavopere.rpgskilltree.runtime.events.PhysicalProjectileMasteryEvents;
import java.util.Map;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Tests the actual NeoForge release/join events and native arrow velocity. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class AgilityProjectileInvestmentGameTests {
    private AgilityProjectileInvestmentGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void agilityAppliesOnceToConfirmedVanillaBowArrowsOnly(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "agility-projectiles"), ClientInformation.createDefault());

        // Ownership and an Arrow entity alone are not sufficient: the launcher must
        // first have a non-cancelled, ammunition-backed bow release receipt.
        setAgilityRank(player, 300L);
        Arrow ownerOnly = arrow(helper, player, Items.BOW);
        EntityJoinLevelEvent uncorrelated = new EntityJoinLevelEvent(ownerOnly, helper.getLevel());
        PhysicalProjectileMasteryEvents.onEntityJoin(uncorrelated);
        PhysicalProjectileMasteryEvents.onAgilityArrowJoin(uncorrelated);
        close(helper, 2.0D, ownerOnly.getDeltaMovement().z, "unproven owner-only projectile");

        for (long rank : new long[] {0L, 9L, 10L, 300L, Long.MAX_VALUE}) {
            setAgilityRank(player, rank);
            PhysicalProjectileMasteryEvents.onArrowLoose(new ArrowLooseEvent(
                player, new ItemStack(Items.BOW), helper.getLevel(), 20, true));
            Arrow arrow = arrow(helper, player, Items.BOW);
            EntityJoinLevelEvent joining = new EntityJoinLevelEvent(arrow, helper.getLevel());
            PhysicalProjectileMasteryEvents.onEntityJoin(joining);
            PhysicalProjectileMasteryEvents.onAgilityArrowJoin(joining);
            double bonus = AttributeInvestmentBonuses.value(
                AttributeInvestmentBonuses.Benefit.AGILITY_PROJECTILE_SPEED, rank);
            close(helper, 2.0D * (1.0D + bonus), arrow.getDeltaMovement().z,
                "rank " + rank + " first launch velocity");
            PhysicalProjectileMasteryEvents.onEntityJoin(joining);
            PhysicalProjectileMasteryEvents.onAgilityArrowJoin(joining);
            close(helper, 2.0D * (1.0D + bonus), arrow.getDeltaMovement().z,
                "rank " + rank + " repeated join must not compound");
        }

        setAgilityRank(player, 300L);
        PhysicalProjectileMasteryEvents.onArrowLoose(new ArrowLooseEvent(
            player, new ItemStack(Items.BOW), helper.getLevel(), 20, true));

        Arrow crossbow = arrow(helper, player, Items.CROSSBOW);
        EntityJoinLevelEvent crossbowJoin = new EntityJoinLevelEvent(crossbow, helper.getLevel());
        PhysicalProjectileMasteryEvents.onEntityJoin(crossbowJoin);
        PhysicalProjectileMasteryEvents.onAgilityArrowJoin(crossbowJoin);
        close(helper, 2.0D, crossbow.getDeltaMovement().z, "crossbow outside verified boundary");

        Arrow reloaded = arrow(helper, player, Items.BOW);
        EntityJoinLevelEvent diskJoin = new EntityJoinLevelEvent(reloaded, helper.getLevel(), true);
        PhysicalProjectileMasteryEvents.onEntityJoin(diskJoin);
        PhysicalProjectileMasteryEvents.onAgilityArrowJoin(diskJoin);
        close(helper, 2.0D, reloaded.getDeltaMovement().z, "reloaded projectile");

        Arrow canceled = arrow(helper, player, Items.BOW);
        EntityJoinLevelEvent canceledJoin = new EntityJoinLevelEvent(canceled, helper.getLevel());
        canceledJoin.setCanceled(true);
        PhysicalProjectileMasteryEvents.onEntityJoin(canceledJoin);
        PhysicalProjectileMasteryEvents.onAgilityArrowJoin(canceledJoin);
        close(helper, 2.0D, canceled.getDeltaMovement().z, "canceled spawn");

        helper.succeed();
    }

    private static Arrow arrow(GameTestHelper helper, ServerPlayer player, net.minecraft.world.item.Item weapon) {
        Arrow arrow = new Arrow(helper.getLevel(), player, new ItemStack(Items.ARROW), new ItemStack(weapon));
        arrow.setDeltaMovement(new Vec3(0.0D, 0.0D, 2.0D));
        return arrow;
    }

    private static void setAgilityRank(ServerPlayer player, long rank) {
        var ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(AttributeId.AGILITY, rank));
        var state = new CoreProgressionState(CharacterProgressionState.empty(),
            CorePointLedger.empty(), ranks, 1L, "0".repeat(64), 0, 0L);
        player.setData(ModAttachments.CANONICAL_PLAYER,
            CanonicalPlayerAttachmentData.empty().withCoreProgression(
                CoreProgressionAttachmentData.initialized(state)));
    }

    private static void close(GameTestHelper helper, double expected, double actual, String label) {
        helper.assertTrue(Math.abs(expected - actual) < 1e-8,
            label + " expected " + expected + " got " + actual);
    }
}
