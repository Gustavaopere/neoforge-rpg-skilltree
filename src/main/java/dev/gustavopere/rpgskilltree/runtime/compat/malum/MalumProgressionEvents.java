package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import com.sammy.malum.core.systems.events.CollectSpiritEvent;
import com.sammy.malum.core.systems.events.ModifySpiritSpoilsEvent;
import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.MalumSpiritClassifier;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpiritPracticeAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Optional Malum adapter using Malum's public spirit-system events. */
public final class MalumProgressionEvents {
    private static final String SPIRIT_DATA_CLASS = "com.sammy.malum.common.data.custom.spirit.EntitySpiritDropData";

    private MalumProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpiritSpoils(ModifySpiritSpoilsEvent event) {
        if (!(event.getAttacker() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative()) return;

        LivingEntity target = event.getEntity();
        ResourceLocation targetId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
        if (targetId == null) return;

        SpiritEvidence evidence = readSpiritEvidence(target);
        Set<String> tags = new HashSet<>();
        tags.add("reaping");
        tags.addAll(MalumSpiritClassifier.spiritTags(evidence.spiritItemIds()));

        SpiritPracticeAction action = new SpiritPracticeAction(
            new ActionOrigin("malum:reaping", 0),
            "malum",
            "reap:" + targetId,
            tags,
            evidence.totalSpirits()
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forMalum(action));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpiritCollected(CollectSpiritEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative()) return;

        SpiritPracticeAction action = new SpiritPracticeAction(
            new ActionOrigin("malum:collection", 0),
            "malum",
            "natural_spirit",
            Set.of("collection"),
            1
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forMalum(action));
    }

    /**
     * Malum 1.8 exposes the spirit-drop data API but some of its signatures pull Lodestone types.
     * Reflection keeps this optional adapter isolated from Lodestone at compile time while still
     * reading the exact ItemStacks produced by Malum/Gaze data at runtime.
     */
    private static SpiritEvidence readSpiritEvidence(LivingEntity target) {
        try {
            Class<?> dataClass = Class.forName(SPIRIT_DATA_CLASS);
            Method getSpiritData = dataClass.getMethod("getSpiritData", LivingEntity.class);
            Object rawOptional = getSpiritData.invoke(null, target);
            if (!(rawOptional instanceof Optional<?> optional) || optional.isEmpty()) return SpiritEvidence.EMPTY;

            Object data = optional.get();
            Method getSpiritStacks = data.getClass().getMethod("getSpiritStacks");
            Object rawStacks = getSpiritStacks.invoke(data);
            if (!(rawStacks instanceof List<?> stacks)) return SpiritEvidence.EMPTY;

            List<String> ids = new ArrayList<>();
            int total = 0;
            for (Object rawStack : stacks) {
                if (!(rawStack instanceof ItemStack stack) || stack.isEmpty()) continue;
                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
                if (itemId == null) continue;
                ids.add(itemId.toString());
                total += Math.max(1, stack.getCount());
            }
            return new SpiritEvidence(List.copyOf(ids), Math.max(1, total));
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return SpiritEvidence.EMPTY;
        }
    }

    private record SpiritEvidence(List<String> spiritItemIds, int totalSpirits) {
        private static final SpiritEvidence EMPTY = new SpiritEvidence(List.of(), 1);
    }
}
