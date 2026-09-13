package dev.gustavopere.rpgskilltree.itemization.blacksmith.persistence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithComposition;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithPartState;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.BlacksmithProvenance;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.ProcessState;
import dev.gustavopere.rpgskilltree.itemization.blacksmith.domain.WorkmanshipBand;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Persistent codecs for Blacksmith physical state. Derived values are intentionally not serialized. */
public final class BlacksmithCodecs {
    private static final Codec<ProcessState> PROCESS_STATE = enumCodec(ProcessState.class, "process state");
    private static final Codec<WorkmanshipBand> WORKMANSHIP_BAND = enumCodec(WorkmanshipBand.class, "workmanship band");

    public static final Codec<BlacksmithProvenance> PROVENANCE = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("provider_id").forGetter(BlacksmithProvenance::providerId),
        ResourceLocation.CODEC.fieldOf("recipe_id").forGetter(BlacksmithProvenance::recipeId)
    ).apply(instance, BlacksmithProvenance::new));

    private static final Codec<BlacksmithPartState> PART_STATE_BASE = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("part_definition_id").forGetter(BlacksmithPartState::partDefinitionId),
        ResourceLocation.CODEC.fieldOf("material_profile_id").forGetter(BlacksmithPartState::materialProfileId),
        Codec.INT.fieldOf("schema_version").forGetter(BlacksmithPartState::schemaVersion),
        PROCESS_STATE.fieldOf("process_state").forGetter(BlacksmithPartState::processState),
        Codec.DOUBLE.fieldOf("workmanship_input").forGetter(BlacksmithPartState::workmanshipInput),
        PROVENANCE.fieldOf("provenance").forGetter(BlacksmithPartState::provenance)
    ).apply(instance, BlacksmithPartState::new));

    public static final Codec<BlacksmithPartState> PART_STATE = PART_STATE_BASE.comapFlatMap(
        BlacksmithSchemaMigrator::validateForCodec,
        state -> state
    );

    private static final Codec<Map<String, BlacksmithPartState>> PARTS = Codec.unboundedMap(Codec.STRING, PART_STATE);
    private static final Codec<List<ProcessState>> PROCESS_SUMMARY = PROCESS_STATE.listOf();

    private static final Codec<BlacksmithComposition> COMPOSITION_BASE = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("assembly_definition_id").forGetter(BlacksmithComposition::assemblyDefinitionId),
        PARTS.fieldOf("parts").forGetter(BlacksmithComposition::parts),
        Codec.DOUBLE.fieldOf("workmanship_score").forGetter(BlacksmithComposition::workmanshipScore),
        WORKMANSHIP_BAND.fieldOf("workmanship_band").forGetter(BlacksmithComposition::workmanshipBand),
        PROCESS_SUMMARY.fieldOf("process_summary").forGetter(BlacksmithCodecs::orderedProcessSummary),
        Codec.INT.fieldOf("schema_version").forGetter(BlacksmithComposition::schemaVersion)
    ).apply(instance, (assemblyId, parts, score, band, processSummary, schemaVersion) -> BlacksmithComposition.of(
        assemblyId,
        parts,
        score,
        band,
        immutableProcessSummary(processSummary),
        schemaVersion
    )));

    public static final Codec<BlacksmithComposition> COMPOSITION = COMPOSITION_BASE.comapFlatMap(
        BlacksmithSchemaMigrator::validateForCodec,
        composition -> composition
    );

    private BlacksmithCodecs() {}

    private static List<ProcessState> orderedProcessSummary(BlacksmithComposition composition) {
        return composition.processSummary().stream()
            .sorted(Comparator.comparingInt(Enum::ordinal))
            .toList();
    }

    private static Set<ProcessState> immutableProcessSummary(List<ProcessState> states) {
        LinkedHashSet<ProcessState> unique = new LinkedHashSet<>(states);
        if (unique.size() != states.size()) {
            throw new IllegalArgumentException("process_summary contains duplicate states");
        }
        return Set.copyOf(unique);
    }

    private static <E extends Enum<E>> Codec<E> enumCodec(Class<E> enumClass, String label) {
        return Codec.STRING.comapFlatMap(
            value -> {
                try {
                    return DataResult.success(Enum.valueOf(enumClass, value));
                } catch (IllegalArgumentException exception) {
                    return DataResult.error(() -> "unknown Blacksmith " + label + ": " + value);
                }
            },
            Enum::name
        );
    }
}
