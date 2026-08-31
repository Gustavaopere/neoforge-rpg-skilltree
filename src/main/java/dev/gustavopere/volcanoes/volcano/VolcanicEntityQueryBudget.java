package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/** Shared hard-cap primitive for Stage 03 living-entity discovery. */
public final class VolcanicEntityQueryBudget {
    private VolcanicEntityQueryBudget() {
    }

    public static List<LivingEntity> collect(
            int maxResults,
            Level level,
            AABB bounds,
            Predicate<? super LivingEntity> predicate
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(bounds, "bounds");
        Objects.requireNonNull(predicate, "predicate");
        if (maxResults < 0) {
            throw new IllegalArgumentException("maxResults must be non-negative");
        }
        if (maxResults == 0) {
            return List.of();
        }
        List<LivingEntity> output = new ArrayList<>(Math.min(maxResults, 32));
        level.getEntities(
                EntityTypeTest.forClass(LivingEntity.class),
                bounds,
                predicate,
                output,
                maxResults);
        return List.copyOf(output);
    }
}
