package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;

/** Isolates Malum's optional reflective spirit-data boundary from mastery semantics. */
final class MalumSpiritDataReader {
    private static final String SPIRIT_DATA_CLASS = "com.sammy.malum.common.data.custom.spirit.EntitySpiritDropData";

    private MalumSpiritDataReader() {
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target) {
        return read(target, SPIRIT_DATA_CLASS, MalumMasteryLogic::evidenceFromStacks);
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target, String dataClassName) {
        return read(target, dataClassName, MalumMasteryLogic::evidenceFromStacks);
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
}
