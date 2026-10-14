package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightInvestmentAttributePolicy;
import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightInvestmentAttributesRuntime;
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

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EpicFightInvestmentAttributeGameTests {
    private EpicFightInvestmentAttributeGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void determinationStaminaModifiesOnlyNativeProviderAndRestoresBaseline(GameTestHelper helper) {
        var player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "epic-invest"), ClientInformation.createDefault());
        var invested = AttributeRanks.of(Map.of(AttributeId.DETERMINATION, 300L));
        boolean active = EpicFightInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT),
            OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT));
        EpicFightInvestmentAttributesRuntime.refresh(player, invested);
        if (!active) {
            EpicFightInvestmentAttributesRuntime.refresh(player, AttributeRanks.empty());
            helper.succeed();
            return;
        }
        var holder = BuiltInRegistries.ATTRIBUTE.getHolder(
            ResourceLocation.parse(EpicFightInvestmentAttributePolicy.target())).orElse(null);
        helper.assertTrue(holder != null, "exact Epic Fight release must register stamina regen");
        if (holder == null) return;
        var attribute = player.getAttribute(holder);
        helper.assertTrue(attribute != null, "Epic Fight must install stamina regen on players");
        if (attribute == null) return;

        double bonus = AttributeInvestmentBonuses.value(Benefit.DETERMINATION_STAMINA_REGEN, 300L);
        double boosted = attribute.getValue();
        helper.assertTrue(boosted > 1.0, "native stamina regen must increase with investment");
        helper.assertTrue(Math.abs(boosted - (1.0 + bonus)) < 1e-8, "expected exactly one relative modifier");

        EpicFightInvestmentAttributesRuntime.refresh(player, invested);
        helper.assertTrue(Math.abs(attribute.getValue() - boosted) < 1e-8, "refresh must be idempotent");
        EpicFightInvestmentAttributesRuntime.refresh(player, AttributeRanks.empty());
        helper.assertTrue(Math.abs(attribute.getValue() - 1.0) < 1e-8, "refund must restore original regen");
        helper.assertTrue(attribute.getModifier(
            ResourceLocation.parse(EpicFightInvestmentAttributePolicy.modifierId())) == null,
            "transient investment modifier must be removable");
        helper.succeed();
    }
}
