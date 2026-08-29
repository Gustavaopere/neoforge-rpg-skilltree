package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingInitializerCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingStateApplierCatalog;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityScalingReapplicationGameTests {
    private EntityScalingReapplicationGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedStateIsReappliedWithoutReroll(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("failed to create zombie");

        EntityScalingState persisted = sampleState();
        EntityScalingRuntime.getOrInitialize(zombie, () -> persisted);

        AtomicInteger initializerCalls = new AtomicInteger();
        AtomicInteger applierCalls = new AtomicInteger();
        AtomicReference<EntityScalingState> applied = new AtomicReference<>();
        EntityScalingInitializerCatalog.install((ignoredLevel, ignoredEntity) -> {
            initializerCalls.incrementAndGet();
            return sampleState();
        });
        EntityScalingStateApplierCatalog.install((ignoredLevel, ignoredEntity, state) -> {
            applierCalls.incrementAndGet();
            applied.set(state);
        });

        try {
            if (!level.addFreshEntity(zombie)) throw new AssertionError("failed to add zombie to level");
            eq(0, initializerCalls.get());
            eq(1, applierCalls.get());
            same(persisted, applied.get());
            same(persisted, EntityScalingRuntime.current(zombie).orElseThrow());
            helper.succeed();
        } finally {
            EntityScalingInitializerCatalog.clear();
            EntityScalingStateApplierCatalog.clear();
        }
    }

    @GameTest(template = "foundation_empty")
    public static void newStateInitializesThenAppliesExactlyOnce(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("failed to create zombie");

        EntityScalingState created = sampleState();
        AtomicInteger initializerCalls = new AtomicInteger();
        AtomicInteger applierCalls = new AtomicInteger();
        AtomicReference<EntityScalingState> applied = new AtomicReference<>();
        EntityScalingInitializerCatalog.install((ignoredLevel, ignoredEntity) -> {
            initializerCalls.incrementAndGet();
            return created;
        });
        EntityScalingStateApplierCatalog.install((ignoredLevel, ignoredEntity, state) -> {
            applierCalls.incrementAndGet();
            applied.set(state);
        });

        try {
            if (!level.addFreshEntity(zombie)) throw new AssertionError("failed to add zombie to level");
            eq(1, initializerCalls.get());
            eq(1, applierCalls.get());
            same(created, applied.get());
            same(created, EntityScalingRuntime.current(zombie).orElseThrow());
            helper.succeed();
        } finally {
            EntityScalingInitializerCatalog.clear();
            EntityScalingStateApplierCatalog.clear();
        }
    }

    private static EntityScalingState sampleState() {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                20L,
                OptionalLong.of(20L),
                20L,
                22L,
                22L
            ),
            2L,
            Optional.empty(),
            123456789L
        );
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
