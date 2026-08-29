package dev.gustavopere.rpgskilltree.runtime;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;

/** Explicit target classification for A0070/A0071. No visual/stat heuristic is permitted. */
public final class MartialTargetClassifier {
    public enum TargetClass { HOSTILE, ELITE, BOSS }

    private MartialTargetClassifier() {}

    public static TargetClass classify(LivingEntity target) {
        if (target.getType().is(BossRewardKeyResolver.BOSSES)) return TargetClass.BOSS;
        CompoundTag data = target.getPersistentData();
        if (data.getBoolean(BossRewardKeyResolver.APOTH_INVADER_MARKER)) return TargetClass.BOSS;
        if (data.contains(BossRewardKeyResolver.CAPTURED_APOTH_ELITE_ID, Tag.TAG_STRING)
            || data.getBoolean(BossRewardKeyResolver.APOTH_ELITE_MARKER)) {
            return TargetClass.ELITE;
        }
        return TargetClass.HOSTILE;
    }
}
