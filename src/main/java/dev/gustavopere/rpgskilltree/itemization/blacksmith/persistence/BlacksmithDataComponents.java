package dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithComposition;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithPartState;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** NeoForge registration boundary for persisted Blacksmith ItemStack state. */
public final class BlacksmithDataComponents {
    private static final DeferredRegister.DataComponents COMPONENTS =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, RpgSkillTreeMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlacksmithPartState>> PART_STATE =
        COMPONENTS.registerComponentType(
            "blacksmith_part_state",
            builder -> builder.persistent(BlacksmithCodecs.PART_STATE).cacheEncoding()
        );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlacksmithComposition>> COMPOSITION =
        COMPONENTS.registerComponentType(
            "blacksmith_composition",
            builder -> builder.persistent(BlacksmithCodecs.COMPOSITION).cacheEncoding()
        );

    private BlacksmithDataComponents() {}

    public static void register(IEventBus modBus) {
        COMPONENTS.register(modBus);
    }
}
