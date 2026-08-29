package dev.gustavopere.rpgskilltree.runtime.loot;

import com.mojang.serialization.MapCodec;
import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** NeoForge serializer registry for RPG global loot modifiers. */
public final class ModLootModifiers {
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
        DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, RpgSkillTreeMod.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> REWARD_RISK = LOOT_MODIFIER_SERIALIZERS.register(
        "reward_risk",
        () -> RewardRiskLootModifier.CODEC
    );

    private ModLootModifiers() {}

    public static void register(IEventBus modBus) {
        LOOT_MODIFIER_SERIALIZERS.register(modBus);
    }
}
