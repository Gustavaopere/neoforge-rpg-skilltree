package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityBehaviorKey;
import dev.gustavopere.rpgskilltree.core.EntityBehaviorSelection;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.MobAffixSelection;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityBehaviorRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityBehaviorRuntimeCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingInitializerCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityBehaviorRuntimeGameTests {
    private static final EntityBehaviorKey FLANKER = EntityBehaviorKey.of("rpgskilltree:combat/flanker");
    private static final EntityBehaviorKey PRESSURE = EntityBehaviorKey.of("rpgskilltree:combat/pressure");
    private static final EntityBehaviorKey MISSING = EntityBehaviorKey.of("example:missing_behavior");

    private EntityBehaviorRuntimeGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedBehaviorsReconcileOnJoinWithoutReroll(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie zombie = Objects.requireNonNull(EntityType.ZOMBIE.create(level), "failed to create zombie");
        EntityScalingState persisted = stateWithBehaviors(FLANKER, PRESSURE);
        EntityScalingRuntime.getOrInitialize(zombie, () -> persisted);

        AtomicInteger initializerCalls = new AtomicInteger();
        ArrayList<EntityBehaviorKey> reconciled = new ArrayList<>();
        EntityScalingInitializerCatalog.install((ignoredLevel, ignoredEntity) -> {
            initializerCalls.incrementAndGet();
            return stateWithBehaviors(MISSING);
        });
        EntityBehaviorRuntimeCatalog.install(Map.of(
            FLANKER, (ignoredLevel, ignoredEntity, ignoredState) -> reconciled.add(FLANKER),
            PRESSURE, (ignoredLevel, ignoredEntity, ignoredState) -> reconciled.add(PRESSURE)
        ));

        try {
            helper.assertTrue(level.addFreshEntity(zombie), "failed to add persisted zombie");
            helper.assertTrue(initializerCalls.get() == 0, "persisted behavior state must not reroll initializer");
            helper.assertTrue(
                reconciled.equals(List.of(FLANKER, PRESSURE)),
                "join must reconcile persisted behaviors in canonical order: " + reconciled
            );

            var repeated = EntityBehaviorRuntime.reconcile(level, zombie, persisted);
            helper.assertTrue(
                reconciled.equals(List.of(FLANKER, PRESSURE, FLANKER, PRESSURE)),
                "repeated reconciliation must revisit the same authoritative behavior selection"
            );
            helper.assertTrue(repeated.applied().equals(List.of(FLANKER, PRESSURE)), "applied behavior trace mismatch");
            helper.assertTrue(repeated.missing().isEmpty(), "registered behaviors must not be missing");
            helper.succeed();
        } finally {
            EntityScalingInitializerCatalog.clear();
            EntityBehaviorRuntimeCatalog.clear();
        }
    }

    @GameTest(template = "foundation_empty")
    public static void missingBehaviorHandlerFailsClosedAndIsTraceable(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie zombie = Objects.requireNonNull(EntityType.ZOMBIE.create(level), "failed to create zombie");
        EntityScalingState persisted = stateWithBehaviors(MISSING);
        EntityBehaviorRuntimeCatalog.clear();

        var result = EntityBehaviorRuntime.reconcile(level, zombie, persisted);
        helper.assertTrue(result.applied().isEmpty(), "missing handler must not report behavior as applied");
        helper.assertTrue(result.missing().equals(List.of(MISSING)), "missing behavior must be traceable");
        helper.succeed();
    }

    private static EntityScalingState stateWithBehaviors(EntityBehaviorKey... behaviors) {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                20L,
                OptionalLong.empty(),
                20L,
                20L,
                20L
            ),
            0L,
            Optional.empty(),
            0x1020304050607080L,
            Optional.empty(),
            MobAffixSelection.empty(),
            new EntityBehaviorSelection(List.of(behaviors))
        );
    }
}
