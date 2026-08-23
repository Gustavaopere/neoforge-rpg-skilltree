package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.ApothicBossRewardKeyPolicy;
import dev.gustavopere.rpgskilltree.core.BossIdentity;
import dev.gustavopere.rpgskilltree.core.BossRewardDefinition;
import dev.gustavopere.rpgskilltree.core.BossRewardKeyPolicy;
import dev.gustavopere.rpgskilltree.core.BossRewardRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public final class BossRewardKeyResolver {
    public static final String APOTH_ELITE_MARKER = "apoth.miniboss";
    public static final String APOTH_INVADER_MARKER = "apoth.boss";
    public static final String APOTH_INVADER_RARITY = "apoth.boss.rarity";
    public static final String CAPTURED_APOTH_ELITE_ID = "rpgskilltree.apoth_elite_key";

    public static final TagKey<EntityType<?>> BOSSES = TagKey.create(
        Registries.ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "bosses")
    );

    private BossRewardKeyResolver() {}

    public static boolean isBoss(LivingEntity entity) {
        return entity.getType().is(BOSSES) || isApothicBoss(entity);
    }

    public static boolean isApothicBoss(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        return data.getBoolean(APOTH_ELITE_MARKER)
            || data.getBoolean(APOTH_INVADER_MARKER)
            || data.contains(CAPTURED_APOTH_ELITE_ID, Tag.TAG_STRING);
    }

    public static BossIdentity identity(LivingEntity entity) {
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (entityId == null) throw new IllegalArgumentException("unregistered boss entity type: " + entity.getType());

        // Explicit datapack boss identities (vanilla/Cataclysm/etc.) have precedence over Apothic wrappers.
        if (entity.getType().is(BOSSES)) return new BossIdentity(entityId.toString(), null);

        CompoundTag data = entity.getPersistentData();
        if (data.contains(CAPTURED_APOTH_ELITE_ID, Tag.TAG_STRING)) {
            String eliteId = data.getString(CAPTURED_APOTH_ELITE_ID);
            return new BossIdentity(entityId.toString(), ApothicBossRewardKeyPolicy.elite(eliteId, entityId.toString()));
        }
        if (data.getBoolean(APOTH_INVADER_MARKER) && data.contains(APOTH_INVADER_RARITY, Tag.TAG_STRING)) {
            String rarityId = data.getString(APOTH_INVADER_RARITY);
            return new BossIdentity(entityId.toString(), ApothicBossRewardKeyPolicy.invader(rarityId));
        }
        if (data.getBoolean(APOTH_ELITE_MARKER)) {
            return new BossIdentity(entityId.toString(), ApothicBossRewardKeyPolicy.elite(null, entityId.toString()));
        }
        if (data.getBoolean(APOTH_INVADER_MARKER)) {
            return new BossIdentity(entityId.toString(), "apotheosis:invader/unknown");
        }
        return new BossIdentity(entityId.toString(), null);
    }

    public static String rewardKey(BossIdentity identity) {
        return BossRewardKeyPolicy.resolve(identity);
    }

    public static BossRewardDefinition rewardDefinition(BossIdentity identity) {
        String key = rewardKey(identity);
        int separator = key.indexOf(':');
        if (separator <= 0) throw new IllegalArgumentException("invalid boss reward key: " + key);
        return BossRewardRegistry.defaults().resolveForNamespace(key.substring(0, separator));
    }
}
