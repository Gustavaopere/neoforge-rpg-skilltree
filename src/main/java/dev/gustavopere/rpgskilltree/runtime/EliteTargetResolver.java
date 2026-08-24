package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

/** Deduplicates explicit curated and Apothic elite identities into one canonical boolean. */
public final class EliteTargetResolver {
    public static final TagKey<EntityType<?>> ELITES = TagKey.create(
        Registries.ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "elites")
    );

    private EliteTargetResolver() {}

    public static boolean isElite(LivingEntity entity) {
        return entity.getType().is(ELITES) || BossRewardKeyResolver.isApothicBoss(entity);
    }
}
