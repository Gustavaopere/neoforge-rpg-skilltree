package dev.gustavopere.rpgskilltree.runtime.loot;

import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;

/** Bounded, deterministic transformation for stackable generated loot. */
public final class EntityRewardLootRuntime {
    private static final BigDecimal TWO_POW_53 = new BigDecimal("9007199254740992");
    private static final long GOLDEN_GAMMA = 0x9E3779B97F4A7C15L;

    private EntityRewardLootRuntime() {}

    /**
     * Scales stackable loot without mutating the upstream list or its stacks.
     *
     * <p>Non-stackable items are intentionally copied unchanged so generic risk scaling cannot
     * duplicate or delete unique equipment, trophies or component-rich one-off rewards. Each
     * stackable input may create at most {@code maxExtraStacksPerInput} additional stacks.</p>
     */
    public static ObjectArrayList<ItemStack> scaleGeneratedLoot(
        ObjectArrayList<ItemStack> generatedLoot,
        EntityRewardScalingResult scaling,
        long deterministicSeed,
        int maxExtraStacksPerInput
    ) {
        Objects.requireNonNull(generatedLoot, "generatedLoot");
        Objects.requireNonNull(scaling, "scaling");
        if (maxExtraStacksPerInput < 0) {
            throw new IllegalArgumentException("maxExtraStacksPerInput must be non-negative");
        }

        ObjectArrayList<ItemStack> output = new ObjectArrayList<>();
        for (int index = 0; index < generatedLoot.size(); index++) {
            ItemStack original = Objects.requireNonNull(generatedLoot.get(index), "generatedLoot contains null stack");
            if (original.isEmpty()) continue;

            int maxStackSize = original.getMaxStackSize();
            if (maxStackSize <= 1) {
                output.add(original.copy());
                continue;
            }

            BigDecimal exactTarget = BigDecimal.valueOf(original.getCount()).multiply(scaling.finalMultiplier());
            BigInteger floor = exactTarget.setScale(0, RoundingMode.DOWN).toBigIntegerExact();
            BigDecimal fraction = exactTarget.subtract(new BigDecimal(floor));
            BigInteger target = floor;
            if (fraction.signum() > 0 && deterministicUnitInterval(deterministicSeed, index).compareTo(fraction) < 0) {
                target = target.add(BigInteger.ONE);
            }

            long technicalCap = Math.addExact(
                original.getCount(),
                Math.multiplyExact((long) maxExtraStacksPerInput, maxStackSize)
            );
            target = target.min(BigInteger.valueOf(technicalCap));

            long remaining = target.longValueExact();
            while (remaining > 0L) {
                int count = (int) Math.min(remaining, maxStackSize);
                output.add(original.copyWithCount(count));
                remaining -= count;
            }
        }
        return output;
    }

    private static BigDecimal deterministicUnitInterval(long seed, int index) {
        long mixed = mix64(seed + GOLDEN_GAMMA * (index + 1L));
        long mantissa53 = mixed >>> 11;
        return BigDecimal.valueOf(mantissa53).divide(TWO_POW_53);
    }

    private static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }
}
