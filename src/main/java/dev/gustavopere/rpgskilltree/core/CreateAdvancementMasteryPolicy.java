package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Provider-free semantic policy for finite Create engineering milestones.
 *
 * <p>The catalog is intentionally an allowlist pinned to the audited Create 6.0.10 advancement
 * surface. Possession-only, recipe/background, throughput-farm, accident/combat-gimmick and
 * unknown advancements fail closed. The base Create adapter only owns kinetics, logistics and
 * automation; artillery, aeronautics and power remain reserved for their actual addon providers.
 */
public final class CreateAdvancementMasteryPolicy {
    private static final String CREATE_NAMESPACE = "create";
    private static final String DISCOVERY_PREFIX = "mastery:create:engineering/advancement/";

    private static final Map<String, String> AUDITED_LANES = auditedLanes();

    private CreateAdvancementMasteryPolicy() {}

    public static Optional<Milestone> confirmed(String namespace, String advancementPath) {
        if (!CREATE_NAMESPACE.equals(namespace) || advancementPath == null || advancementPath.isBlank()) {
            return Optional.empty();
        }
        String lane = AUDITED_LANES.get(advancementPath);
        if (lane == null) return Optional.empty();

        EngineeringAction action = new EngineeringAction(
            new ActionOrigin("create:advancement_earned", 0),
            CREATE_NAMESPACE,
            CREATE_NAMESPACE + ":" + advancementPath,
            Set.of(lane),
            1.0D
        );
        List<MasteryAward> awards = MasteryPolicies.forCreate(action);
        if (awards.isEmpty()) {
            throw new IllegalStateException("audited Create milestone produced no mastery awards: " + advancementPath);
        }
        return Optional.of(new Milestone(DISCOVERY_PREFIX + advancementPath, action, awards));
    }

    public static Set<String> auditedAdvancementPaths() {
        return AUDITED_LANES.keySet();
    }

    private static Map<String, String> auditedLanes() {
        Map<String, String> out = new LinkedHashMap<>();

        // Kinetics: confirmed generation, transmission or rotational-control practice.
        lane(out, "water_wheel", "kinetics");
        lane(out, "windmill", "kinetics");
        lane(out, "shifting_gears", "kinetics");
        lane(out, "belt", "kinetics");
        lane(out, "stressometer", "kinetics");
        lane(out, "windmill_maxed", "kinetics");
        lane(out, "stressometer_maxed", "kinetics");
        lane(out, "steam_engine", "kinetics");
        lane(out, "steam_engine_maxed", "kinetics");
        lane(out, "speed_controller", "kinetics");

        // Logistics: confirmed movement, routing, storage exchange, fluid transport or rail use.
        lane(out, "funnel", "logistics");
        lane(out, "chute", "logistics");
        lane(out, "portable_storage_interface", "logistics");
        lane(out, "packager", "logistics");
        lane(out, "stock_ticker", "logistics");
        lane(out, "frogport", "logistics");
        lane(out, "table_cloth_shop", "logistics");
        lane(out, "factory_gauge", "logistics");
        lane(out, "mechanical_pump_0", "logistics");
        lane(out, "glass_pipe", "logistics");
        lane(out, "water_supply", "logistics");
        lane(out, "hose_pulley", "logistics");
        lane(out, "honey_drain", "logistics");
        lane(out, "hose_pulley_lava", "logistics");
        lane(out, "mechanical_arm", "logistics");
        lane(out, "arm_many_targets", "logistics");
        lane(out, "train_casing_00", "logistics");
        lane(out, "train", "logistics");
        lane(out, "conductor", "logistics");
        lane(out, "track_signal", "logistics");
        lane(out, "display_board_0", "logistics");
        lane(out, "train_whistle", "logistics");
        lane(out, "train_portal", "logistics");
        lane(out, "long_train", "logistics");

        // Automation: confirmed powered processing, assembly or autonomous machine operation.
        lane(out, "mechanical_press", "automation");
        lane(out, "encased_fan", "automation");
        lane(out, "fan_processing", "automation");
        lane(out, "saw_processing", "automation");
        lane(out, "compacting", "automation");
        lane(out, "mechanical_mixer", "automation");
        lane(out, "millstone", "automation");
        lane(out, "contraption_actors", "automation");
        lane(out, "spout", "automation");
        lane(out, "drain", "automation");
        lane(out, "deployer", "automation");
        lane(out, "mechanical_crafter", "automation");
        lane(out, "crushing_wheel", "automation");
        lane(out, "linked_controller", "automation");
        lane(out, "arm_blaze_burner", "automation");
        lane(out, "crusher_maxed_0000", "automation");
        lane(out, "self_deploying", "automation");

        return Map.copyOf(out);
    }

    private static void lane(Map<String, String> out, String path, String lane) {
        Objects.requireNonNull(out, "out");
        if (!Set.of("kinetics", "logistics", "automation").contains(lane)) {
            throw new IllegalArgumentException("base Create cannot own mastery lane: " + lane);
        }
        if (out.put(path, lane) != null) {
            throw new IllegalStateException("duplicate audited Create advancement path: " + path);
        }
    }

    public record Milestone(String discoveryKey, EngineeringAction action, List<MasteryAward> awards) {
        public Milestone {
            Objects.requireNonNull(discoveryKey, "discoveryKey");
            Objects.requireNonNull(action, "action");
            awards = List.copyOf(Objects.requireNonNull(awards, "awards"));
            if (discoveryKey.isBlank()) throw new IllegalArgumentException("discoveryKey must not be blank");
            if (awards.isEmpty()) throw new IllegalArgumentException("awards must not be empty");
        }
    }
}
