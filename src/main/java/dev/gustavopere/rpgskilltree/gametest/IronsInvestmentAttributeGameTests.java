package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsInvestmentAttributePolicy;
import dev.gustavopere.rpgskilltree.runtime.compat.irons.IronsInvestmentAttributesRuntime;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Runs without Iron's installed; when present, verifies native registry effects on a real ServerPlayer. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class IronsInvestmentAttributeGameTests {
    private IronsInvestmentAttributeGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void nativeIronInvestmentIsIdempotentRemovableAndAbsentSafe(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "irons-investment"), ClientInformation.createDefault());
        var ranks = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 300L));
        IronsInvestmentAttributesRuntime.refresh(player, ranks);

        boolean loaded = OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS);
        String version = OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS);
        if (!IronsInvestmentAttributePolicy.operational(loaded, version)) {
            IronsInvestmentAttributesRuntime.refresh(player, AttributeRanks.empty());
            helper.succeed();
            return;
        }

        for (var entry : IronsInvestmentAttributePolicy.targets().entrySet()) {
            Benefit benefit = entry.getKey();
            ResourceLocation target = ResourceLocation.parse(entry.getValue());
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(target).orElse(null);
            helper.assertTrue(holder != null, "audited Iron's registry must have " + target);
            if (holder == null) continue;
            var instance = player.getAttribute(holder);
            helper.assertTrue(instance != null, "player must expose " + target);
            if (instance == null) continue;
            double active = instance.getValue();
            double expected = AttributeInvestmentBonuses.value(benefit, 300L);
            helper.assertTrue(active > 0.0 && expected > 0.0, benefit.name() + " must have native value");
            IronsInvestmentAttributesRuntime.refresh(player, ranks);
            close(helper, active, instance.getValue(), benefit.name() + " idempotency");
            IronsInvestmentAttributesRuntime.refresh(player, AttributeRanks.empty());
            var modifier = ResourceLocation.parse("rpgskilltree:investment/irons/" + benefit.name().toLowerCase(java.util.Locale.ROOT));
            helper.assertTrue(instance.getModifier(modifier) == null, benefit.name() + " modifier must be removable");
            IronsInvestmentAttributesRuntime.refresh(player, ranks);
            close(helper, active, instance.getValue(), benefit.name() + " reapply");
        }
        IronsInvestmentAttributesRuntime.refresh(player, AttributeRanks.empty());
        helper.succeed();
    }

    private static void close(GameTestHelper helper, double expected, double actual, String label) {
        helper.assertTrue(Math.abs(expected - actual) < 1e-8,
            label + " expected " + expected + " observed " + actual);
    }
}
