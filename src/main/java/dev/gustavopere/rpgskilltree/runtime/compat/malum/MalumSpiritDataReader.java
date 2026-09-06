package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;

/** Isolates Malum's optional reflective spirit-data boundary from mastery semantics. */
final class MalumSpiritDataReader {
    private static final String SPIRIT_DATA_CLASS = "com.sammy.malum.common.data.custom.spirit.EntitySpiritDropData";

    private MalumSpiritDataReader() {
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target) {
        return read(target, SPIRIT_DATA_CLASS);
    }

    static MalumMasteryLogic.SpiritEvidence read(LivingEntity target, String dataClassName) {
        try {
            Class<?> dataClass = Class.forName(dataClassName);
            Method getSpiritData = dataClass.getMethod("getSpiritData", LivingEntity.class);
            Object rawOptional = getSpiritData.invoke(null, target);
            if (!(rawOptional instanceof Optional<?> optional) || optional.isEmpty()) {
                return MalumMasteryLogic.SpiritEvidence.EMPTY;
            }

            Object data = optional.get();
            Method getSpiritStacks = data.getClass().getMethod("getSpiritStacks");
            return MalumMasteryLogic.evidenceFromStacks(getSpiritStacks.invoke(data));
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            return MalumMasteryLogic.SpiritEvidence.EMPTY;
        }
    }
}
