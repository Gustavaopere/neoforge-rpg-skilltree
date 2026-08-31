package dev.gustavopere.volcanoes.environment;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/** Resolves only passive filtration from equipped tagged items; oxygen supply is intentionally absent. */
public final class TaggedRespirationProtectionProvider {
    private TaggedRespirationProtectionProvider() {
    }

    public static RespirationProtection fromEntity(LivingEntity entity) {
        Objects.requireNonNull(entity, "entity");
        boolean particulate = false;
        boolean acidGas = false;
        boolean toxicGas = false;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (stack.isEmpty()) {
                continue;
            }
            particulate |= stack.is(AtmosphereTags.PARTICULATE_FILTERS);
            acidGas |= stack.is(AtmosphereTags.ACID_GAS_FILTERS);
            toxicGas |= stack.is(AtmosphereTags.TOXIC_GAS_FILTERS);
        }
        return fromMatches(particulate, acidGas, toxicGas);
    }

    static RespirationProtection fromMatches(boolean particulate, boolean acidGas, boolean toxicGas) {
        return RespirationProtection.of(
                particulate ? 1.0 : 0.0,
                acidGas ? 1.0 : 0.0,
                toxicGas ? 1.0 : 0.0,
                0.0);
    }
}
