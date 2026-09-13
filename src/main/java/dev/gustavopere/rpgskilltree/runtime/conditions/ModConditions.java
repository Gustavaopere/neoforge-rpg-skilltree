package dev.gustavopere.rpgskilltree.runtime.conditions;

import com.mojang.serialization.MapCodec;
import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** Registration boundary for reusable RPG Skill Tree datapack conditions. */
public final class ModConditions {
    private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
        DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, RpgSkillTreeMod.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ExactModVersionCondition>> EXACT_MOD_VERSION =
        CONDITIONS.register("exact_mod_version", () -> ExactModVersionCondition.CODEC);

    private ModConditions() {}

    public static void register(IEventBus modBus) {
        CONDITIONS.register(modBus);
    }
}
