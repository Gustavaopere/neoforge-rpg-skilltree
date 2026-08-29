package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import java.util.Objects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Enemy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.TamableAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;

/**
 * Server-side fallback classifier for LivingEntity scaling archetypes.
 *
 * <p>Explicit datapack semantics take precedence over broad vanilla type hierarchy checks so
 * modpacks can classify guards without a hard dependency on the providing mod. Unknown living
 * entities intentionally fall back to {@link EntityArchetype#SPECIAL} rather than being guessed
 * hostile/passive.</p>
 */
public final class EntityArchetypeRuntimeClassifier {
    public static final TagKey<EntityType<?>> GUARDS = TagKey.create(
        Registries.ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "guards")
    );

    private EntityArchetypeRuntimeClassifier() {}

    public static EntityArchetype classify(LivingEntity entity) {
        Objects.requireNonNull(entity, "entity");

        if (BossRewardKeyResolver.isBoss(entity)) return EntityArchetype.BOSS;
        if (entity.getType().is(GUARDS)) return EntityArchetype.GUARD;
        if (entity instanceof TamableAnimal tamable && tamable.isTame()) return EntityArchetype.TAMED;
        if (entity instanceof Villager) return EntityArchetype.VILLAGER;
        if (entity instanceof WanderingTrader) return EntityArchetype.CIVILIAN;
        if (entity instanceof Enemy) return EntityArchetype.HOSTILE;
        if (entity instanceof NeutralMob) return EntityArchetype.NEUTRAL;
        if (entity instanceof Animal) return EntityArchetype.PASSIVE;
        return EntityArchetype.SPECIAL;
    }
}
