package dev.gustavopere.rpgskilltree.runtime.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.Objects;

/**
 * Datapack condition that fails closed unless one optional mod is loaded at the exact audited version.
 *
 * <p>The condition intentionally depends only on NeoForge metadata and never loads provider API classes.</p>
 */
public record ExactModVersionCondition(String modid, String version) implements ICondition {
    public static final MapCodec<ExactModVersionCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
        Codec.STRING.fieldOf("modid").forGetter(ExactModVersionCondition::modid),
        Codec.STRING.fieldOf("version").forGetter(ExactModVersionCondition::version)
    ).apply(builder, ExactModVersionCondition::new));

    public ExactModVersionCondition {
        modid = requireNonBlank(modid, "modid");
        version = requireNonBlank(version, "version");
    }

    @Override
    public boolean test(IContext context) {
        return ModList.get()
            .getModContainerById(modid)
            .map(container -> version.equals(container.getModInfo().getVersion().toString()))
            .orElse(false);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    private static String requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
