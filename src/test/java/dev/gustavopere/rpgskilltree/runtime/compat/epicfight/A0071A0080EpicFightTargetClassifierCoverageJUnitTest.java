package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.gustavopere.rpgskilltree.runtime.BossRewardKeyResolver;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier.TargetClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

final class A0071A0080EpicFightTargetClassifierCoverageJUnitTest {
    @Test
    void explicitApothicMarkersAndCapturedKeyClassifyWithoutHeuristics() {
        LivingEntity target = mock(LivingEntity.class);
        EntityType<?> type = mock(EntityType.class);
        CompoundTag data = new CompoundTag();
        when(target.getType()).thenReturn(type);
        when(type.is(BossRewardKeyResolver.BOSSES)).thenReturn(false);
        when(target.getPersistentData()).thenReturn(data);

        assertEquals(TargetClass.HOSTILE, MartialTargetClassifier.classify(target));

        data.putBoolean(BossRewardKeyResolver.APOTH_ELITE_MARKER, true);
        assertEquals(TargetClass.ELITE, MartialTargetClassifier.classify(target));

        data.remove(BossRewardKeyResolver.APOTH_ELITE_MARKER);
        data.putString(BossRewardKeyResolver.CAPTURED_APOTH_ELITE_ID, "apothic_attributes:test_elite");
        assertEquals(TargetClass.ELITE, MartialTargetClassifier.classify(target));

        data.putBoolean(BossRewardKeyResolver.APOTH_INVADER_MARKER, true);
        assertEquals(TargetClass.BOSS, MartialTargetClassifier.classify(target));
    }
}
