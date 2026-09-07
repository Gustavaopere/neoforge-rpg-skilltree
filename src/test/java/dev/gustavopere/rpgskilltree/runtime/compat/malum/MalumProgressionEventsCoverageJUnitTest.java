package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

/** Loaded-NeoForge coverage for the audited Malum 1.8.2 provider boundary. */
final class MalumProgressionEventsCoverageJUnitTest {
    private static final String COLLECT_EVENT = "com.sammy.malum.core.systems.events.CollectSpiritEvent";
    private static final String SPOILS_EVENT = "com.sammy.malum.core.systems.events.ModifySpiritSpoilsEvent";
    private static final String HANDLER = "dev.gustavopere.rpgskilltree.runtime.compat.malum.MalumProgressionEvents";

    @Test
    void collectionEventAwardsThroughCanonicalRuntimeForEligiblePlayer() throws Exception {
        assumeTrue(classPresent(COLLECT_EVENT), "Malum provider is not loaded in this test lane");

        ServerPlayer player = eligiblePlayer();
        Class<?> eventClass = Class.forName(COLLECT_EVENT);
        Constructor<?> constructor = eventClass.getConstructor(LivingEntity.class);
        Object event = constructor.newInstance(player);
        Method handler = Class.forName(HANDLER).getMethod("onSpiritCollected", eventClass);

        try (MockedStatic<PlayerProgressionRuntime> runtime = mockStatic(PlayerProgressionRuntime.class)) {
            assertDoesNotThrow(() -> handler.invoke(null, event));
            runtime.verify(
                () -> PlayerProgressionRuntime.awardMastery(eq(player), anyCollection()),
                times(1)
            );
        }
    }

    @Test
    void reapingWithoutConfirmedSpiritEvidenceFailsClosed() throws Exception {
        assumeTrue(classPresent(SPOILS_EVENT), "Malum provider is not loaded in this test lane");

        ServerPlayer player = eligiblePlayer();
        LivingEntity target = mock(LivingEntity.class);
        doReturn(EntityType.ZOMBIE).when(target).getType();

        Class<?> eventClass = Class.forName(SPOILS_EVENT);
        Constructor<?> constructor = eventClass.getConstructor(LivingEntity.class, LivingEntity.class, int.class);
        Object event = constructor.newInstance(target, player, 0);
        Method handler = Class.forName(HANDLER).getMethod("onSpiritSpoils", eventClass);

        try (MockedStatic<PlayerProgressionRuntime> runtime = mockStatic(PlayerProgressionRuntime.class)) {
            assertDoesNotThrow(() -> handler.invoke(null, event));
            runtime.verifyNoInteractions();
        }
    }

    private static ServerPlayer eligiblePlayer() {
        ServerPlayer player = mock(ServerPlayer.class);
        when(player.isCreative()).thenReturn(false);
        when(player.isSpectator()).thenReturn(false);
        return player;
    }

    private static boolean classPresent(String className) {
        try {
            Class.forName(className, false, MalumProgressionEventsCoverageJUnitTest.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException | LinkageError unavailable) {
            return false;
        }
    }
}
