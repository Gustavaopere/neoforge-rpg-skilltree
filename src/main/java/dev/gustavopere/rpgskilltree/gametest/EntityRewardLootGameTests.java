package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import dev.gustavopere.rpgskilltree.runtime.loot.EntityRewardLootRuntime;
import dev.gustavopere.rpgskilltree.runtime.loot.ModLootModifiers;
import dev.gustavopere.rpgskilltree.runtime.loot.RewardRiskLootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.math.BigDecimal;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityRewardLootGameTests {
    private EntityRewardLootGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void stackableLootScalesAndSplitsWithoutMutatingInput(GameTestHelper helper) {
        ObjectArrayList<ItemStack> input = new ObjectArrayList<>();
        input.add(new ItemStack(Items.ROTTEN_FLESH, 40));

        ObjectArrayList<ItemStack> output = EntityRewardLootRuntime.scaleGeneratedLoot(
            input,
            result("2"),
            1234L,
            2
        );

        helper.assertTrue(totalCount(output, Items.ROTTEN_FLESH) == 80, "40 stackable items at x2 must yield 80");
        helper.assertTrue(output.size() == 2, "80 items must be split to respect vanilla max stack size");
        helper.assertTrue(output.stream().allMatch(stack -> stack.getCount() <= stack.getMaxStackSize()), "no output stack may exceed max stack size");
        helper.assertTrue(input.size() == 1 && input.getFirst().getCount() == 40, "loot scaling must not mutate the upstream list or stack");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void subOneMultiplierReducesStackableLootButPreservesUniqueItems(GameTestHelper helper) {
        ObjectArrayList<ItemStack> input = new ObjectArrayList<>();
        input.add(new ItemStack(Items.ROTTEN_FLESH, 10));
        input.add(new ItemStack(Items.DIAMOND_SWORD, 1));

        ObjectArrayList<ItemStack> output = EntityRewardLootRuntime.scaleGeneratedLoot(
            input,
            result("0.5"),
            99L,
            2
        );

        helper.assertTrue(totalCount(output, Items.ROTTEN_FLESH) == 5, "10 stackable items at x0.5 must yield 5");
        helper.assertTrue(totalCount(output, Items.DIAMOND_SWORD) == 1, "non-stackable/unique loot must never be duplicated or deleted by generic risk scaling");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void fractionalScalingIsDeterministicAndExpansionIsTechnicallyBounded(GameTestHelper helper) {
        ObjectArrayList<ItemStack> one = new ObjectArrayList<>();
        one.add(new ItemStack(Items.ROTTEN_FLESH, 1));

        ObjectArrayList<ItemStack> first = EntityRewardLootRuntime.scaleGeneratedLoot(one, result("1.5"), 777L, 2);
        ObjectArrayList<ItemStack> second = EntityRewardLootRuntime.scaleGeneratedLoot(one, result("1.5"), 777L, 2);
        helper.assertTrue(
            totalCount(first, Items.ROTTEN_FLESH) == totalCount(second, Items.ROTTEN_FLESH),
            "fractional loot rounding must be deterministic for the persisted entity seed"
        );
        helper.assertTrue(
            totalCount(first, Items.ROTTEN_FLESH) >= 1 && totalCount(first, Items.ROTTEN_FLESH) <= 2,
            "x1.5 on one stackable item must resolve to one or two items"
        );

        ObjectArrayList<ItemStack> full = new ObjectArrayList<>();
        full.add(new ItemStack(Items.ROTTEN_FLESH, 64));
        ObjectArrayList<ItemStack> bounded = EntityRewardLootRuntime.scaleGeneratedLoot(full, result("100"), 777L, 2);
        helper.assertTrue(bounded.size() == 3, "maxExtraStacksPerInput=2 must cap one input entry to three output stacks");
        helper.assertTrue(totalCount(bounded, Items.ROTTEN_FLESH) == 192, "technical expansion cap must bound total generated items");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void glmCodecAndDeferredRegistrationExist(GameTestHelper helper) {
        helper.assertTrue(RewardRiskLootModifier.CODEC != null, "reward-risk GLM must expose a MapCodec");
        helper.assertTrue(ModLootModifiers.REWARD_RISK != null, "reward-risk GLM codec must be deferred-registered");
        helper.succeed();
    }

    private static int totalCount(ObjectArrayList<ItemStack> stacks, Item item) {
        return stacks.stream().filter(stack -> stack.is(item)).mapToInt(ItemStack::getCount).sum();
    }

    private static EntityRewardScalingResult result(String multiplier) {
        BigDecimal value = new BigDecimal(multiplier);
        return new EntityRewardScalingResult(value, BigDecimal.ONE, BigDecimal.ONE, value, value);
    }
}
