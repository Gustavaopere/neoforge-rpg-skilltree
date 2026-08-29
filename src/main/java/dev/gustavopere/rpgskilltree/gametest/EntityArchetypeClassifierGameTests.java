package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.runtime.EntityArchetypeRuntimeClassifier;
import java.util.Objects;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityArchetypeClassifierGameTests {
    private EntityArchetypeClassifierGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void classifiesVanillaAndFallbackArchetypes(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();

        assertArchetype(EntityArchetype.BOSS, create(EntityType.WITHER, level));
        assertArchetype(EntityArchetype.GUARD, create(EntityType.IRON_GOLEM, level));
        assertArchetype(EntityArchetype.HOSTILE, create(EntityType.ZOMBIE, level));
        assertArchetype(EntityArchetype.NEUTRAL, create(EntityType.BEE, level));
        assertArchetype(EntityArchetype.PASSIVE, create(EntityType.PIG, level));
        assertArchetype(EntityArchetype.VILLAGER, create(EntityType.VILLAGER, level));
        assertArchetype(EntityArchetype.CIVILIAN, create(EntityType.WANDERING_TRADER, level));

        Wolf wolf = create(EntityType.WOLF, level);
        wolf.setTame(true, false);
        assertArchetype(EntityArchetype.TAMED, wolf);

        assertArchetype(EntityArchetype.SPECIAL, create(EntityType.ARMOR_STAND, level));
        helper.succeed();
    }

    private static <T extends LivingEntity> T create(EntityType<T> type, ServerLevel level) {
        return Objects.requireNonNull(type.create(level), () -> "failed to create test entity " + type);
    }

    private static void assertArchetype(EntityArchetype expected, LivingEntity entity) {
        EntityArchetype actual = EntityArchetypeRuntimeClassifier.classify(entity);
        if (actual != expected) {
            throw new AssertionError(entity.getType() + ": expected " + expected + " but got " + actual);
        }
    }
}
