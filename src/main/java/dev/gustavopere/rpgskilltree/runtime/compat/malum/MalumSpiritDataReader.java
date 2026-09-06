package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/** Isolates Malum's optional reflective spirit-data boundary from mastery semantics. */
final class MalumSpiritDataReader {
    private static final String SPIRIT_DATA_CLASS = "com.sammy.malum.common.data.custom.spirit.EntitySpiritDropData";

    private MalumSpiritDataReader() {
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target) {
        return read(target, SPIRIT_DATA_CLASS, MalumSpiritDataReader::decodeStacks);
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target, String dataClassName) {
        return read(target, dataClassName, MalumSpiritDataReader::decodeStacks);
    }

    static MalumMasteryLogic.SpiritEvidence read(
        LivingEntity target,
        String dataClassName,
        Function<Object, MalumMasteryLogic.SpiritEvidence> stackDecoder
    ) {
        Objects.requireNonNull(dataClassName, "dataClassName");
        Objects.requireNonNull(stackDecoder, "stackDecoder");
        try {
            Class<?> dataClass = Class.forName(dataClassName);
            Method getSpiritData = dataClass.getMethod("getSpiritData", LivingEntity.class);
            Object rawOptional = getSpiritData.invoke(null, target);
            if (!(rawOptional instanceof Optional<?> optional) || optional.isEmpty()) {
                return MalumMasteryLogic.SpiritEvidence.EMPTY;
            }

            Object data = optional.get();
            Method getSpiritStacks = data.getClass().getMethod("getSpiritStacks");
            return stackDecoder.apply(getSpiritStacks.invoke(data));
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            return MalumMasteryLogic.SpiritEvidence.EMPTY;
        }
    }

    private static MalumMasteryLogic.SpiritEvidence decodeStacks(Object rawStacks) {
        if (!(rawStacks instanceof List<?> stacks)) {
            return MalumMasteryLogic.SpiritEvidence.EMPTY;
        }

        List<MalumMasteryLogic.SpiritStackObservation> observations = new ArrayList<>();
        for (Object rawStack : stacks) {
            if (!(rawStack instanceof ItemStack stack) || stack.isEmpty()) {
                continue;
            }
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (itemId == null) {
                continue;
            }
            observations.add(new MalumMasteryLogic.SpiritStackObservation(itemId.toString(), stack.getCount()));
        }
        return MalumMasteryLogic.evidenceFromObservations(observations);
    }
}
