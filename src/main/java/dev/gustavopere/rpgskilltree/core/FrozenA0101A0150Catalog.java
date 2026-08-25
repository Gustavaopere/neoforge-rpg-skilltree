package dev.gustavopere.rpgskilltree.core;

import static dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkDefinition.Domain.*;
import static dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkDefinition.Fallback.*;
import static dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkDefinition.Family.*;
import static dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkDefinition.Kind.*;
import static dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkDefinition.SpecialGate.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Canonical structured projection of the frozen A0101-A0150 Notion catalog. */
public final class FrozenA0101A0150Catalog {
    private static final List<FrozenSurvivalPerkDefinition> ALL = List.of(
        d("A0101", "Fortificação contra Projéteis", VITALITY, DAMAGE_MITIGATION, BRANCH, 4, 1,
            m("A0089", 1), g(VITALITY), NONE, SAFE_COMPONENT_ONLY),
        d("A0102", "Proteção Arcana", VITALITY, DAMAGE_MITIGATION, BRIDGE, 4, 1,
            m("A0088", 2), g(VITALITY, ARCANE), NONE, SAFE_COMPONENT_ONLY),
        d("A0103", "Proteção Ambiental", VITALITY, DAMAGE_MITIGATION, BRIDGE, 4, 1,
            m("A0088", 2), g(VITALITY, SURVIVAL), NONE, SAFE_COMPONENT_ONLY),
        d("A0104", "Segundo Vento", VITALITY, EMERGENCY_DEFENSE, NOTABLE, 1, 2,
            m("A0096", 3), g(VITALITY), NONE, NATIVE),
        d("A0105", "Casca Reativa", VITALITY, EMERGENCY_DEFENSE, NOTABLE, 1, 2,
            m("A0089", 3, "A0090", 2), g(VITALITY), NONE, NATIVE),
        d("A0106", "Guarda de Emergência", VITALITY, EMERGENCY_DEFENSE, CAPSTONE, 1, 3,
            m("A0104", 1, "A0105", 1, "A0095", 3), g(VITALITY), NONE, FAIL_CLOSED),
        d("A0107", "Conversão de Impacto", VITALITY, EMERGENCY_DEFENSE, NOTABLE, 1, 2,
            m("A0093", 3, "A0095", 3), g(VITALITY), IMPACT_STAMINA_PROVIDER, FAIL_CLOSED),
        d("A0108", "Pele de Pedra", VITALITY, LOAD_DEFENSE, KEYSTONE, 1, 2,
            m("A0092", 3, "A0100", 2, "A0090", 2), g(VITALITY), NONE, NATIVE),
        d("A0109", "Fortaleza Ambulante", VITALITY, LOAD_DEFENSE, CAPSTONE, 1, 3,
            m("A0108", 1, "A0091", 3), g(VITALITY), HEAVY_LOAD_PROVIDER, FAIL_CLOSED),
        d("A0110", "Conservação de Equipamento I", SURVIVAL, MAINTENANCE, BRANCH, 5, 1,
            Map.of(), g(SURVIVAL), NONE, SAFE_COMPONENT_ONLY),
        d("A0111", "Conservação de Equipamento II", ENGINEERING, MAINTENANCE, BRIDGE, 5, 1,
            m("A0110", 2), g(ENGINEERING), NONE, FAIL_CLOSED),
        d("A0112", "Auto-Manutenção", ENGINEERING, MAINTENANCE, NOTABLE, 3, 1,
            m("A0111", 2), g(ENGINEERING), NONE, FAIL_CLOSED),
        d("A0113", "Reforço de Campo", SURVIVAL, MAINTENANCE, NOTABLE, 3, 1,
            m("A0110", 2), g(SURVIVAL), NONE, FAIL_CLOSED),
        d("A0114", "Manutenção de Relíquia Vinculada", LOGISTICS, MAINTENANCE, KEYSTONE, 1, 3,
            m("A0112", 3), g(LOGISTICS), ATTUNEMENT_SOCKET, FAIL_CLOSED),

        d("A0115", "Economia Metabólica: Correr", SURVIVAL, METABOLIC, BRANCH, 4, 1,
            Map.of(), g(SURVIVAL), BODY_PROVIDER, SAFE_COMPONENT_ONLY),
        d("A0116", "Conservação Hídrica: Correr", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            Map.of(), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0117", "Economia Metabólica: Saltar", SURVIVAL, METABOLIC, BRANCH, 4, 1,
            m("A0115", 2), g(SURVIVAL), BODY_PROVIDER, SAFE_COMPONENT_ONLY),
        d("A0118", "Conservação Hídrica: Saltar", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0116", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0119", "Economia Metabólica: Nadar", SURVIVAL, METABOLIC, BRANCH, 4, 1,
            m("A0115", 2), g(SURVIVAL), BODY_PROVIDER, SAFE_COMPONENT_ONLY),
        d("A0120", "Conservação Hídrica: Nadar", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0116", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0121", "Economia Metabólica: Escalar", SURVIVAL, METABOLIC, BRANCH, 4, 1,
            m("A0115", 2, "A0117", 2), g(SURVIVAL), BODY_PROVIDER, FAIL_CLOSED),
        d("A0122", "Conservação Hídrica: Escalar", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0116", 2, "A0118", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0123", "Economia Metabólica: Minerar", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL, MINING), BODY_PROVIDER, FAIL_CLOSED),
        d("A0124", "Conservação Hídrica: Minerar", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0123", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0125", "Economia Metabólica: Cortar Madeira", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL), FORESTRY_ACCESS, FAIL_CLOSED),
        d("A0126", "Conservação Hídrica: Cortar Madeira", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0125", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0127", "Economia Metabólica: Lutar Corpo A Corpo", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL, MARTIAL), BODY_PROVIDER, FAIL_CLOSED),
        d("A0128", "Conservação Hídrica: Lutar Corpo A Corpo", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0127", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0129", "Economia Metabólica: Usar Arco/Besta", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL, AGILITY), BODY_PROVIDER, FAIL_CLOSED),
        d("A0130", "Conservação Hídrica: Usar Arco/Besta", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0129", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0131", "Economia Metabólica: Conjurar", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL, ARCANE), BODY_PROVIDER, FAIL_CLOSED),
        d("A0132", "Conservação Hídrica: Conjurar", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0131", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0133", "Economia Metabólica: Carregar Peso", SURVIVAL, METABOLIC, BRIDGE, 4, 1,
            Map.of(), g(SURVIVAL), HEAVY_LOAD_PROVIDER, FAIL_CLOSED),
        d("A0134", "Conservação Hídrica: Carregar Peso", SURVIVAL, HYDRATION, BRANCH, 4, 1,
            m("A0133", 2), g(SURVIVAL), HYDRATION_PROVIDER, FAIL_CLOSED),
        d("A0135", "Economia Metabólica: Trabalhar Em Calor", SURVIVAL, ACCLIMATION, BRANCH, 4, 1,
            Map.of(), g(SURVIVAL), PHYSICAL_METABOLIC_RANK_TWO, FAIL_CLOSED),
        d("A0136", "Conservação Hídrica: Trabalhar Em Calor", SURVIVAL, ACCLIMATION, BRANCH, 4, 1,
            m("A0135", 2), g(SURVIVAL), THERMAL_PROVIDER, FAIL_CLOSED),
        d("A0137", "Economia Metabólica: Trabalhar Em Frio", SURVIVAL, ACCLIMATION, BRANCH, 4, 1,
            Map.of(), g(SURVIVAL), PHYSICAL_METABOLIC_RANK_TWO, FAIL_CLOSED),
        d("A0138", "Conservação Hídrica: Trabalhar Em Frio", SURVIVAL, ACCLIMATION, BRANCH, 4, 1,
            m("A0137", 2), g(SURVIVAL), THERMAL_PROVIDER, FAIL_CLOSED),
        d("A0139", "Metabolismo Eficiente", SURVIVAL, METABOLIC, NOTABLE, 1, 2,
            Map.of(), g(SURVIVAL), THREE_DISTINCT_METABOLIC, FAIL_CLOSED),
        d("A0140", "Adaptação do Deserto", SURVIVAL, ACCLIMATION, KEYSTONE, 1, 2,
            m("A0135", 2, "A0136", 2), g(SURVIVAL), THERMAL_PROVIDER, FAIL_CLOSED),
        d("A0141", "Adaptação Boreal", SURVIVAL, ACCLIMATION, KEYSTONE, 1, 2,
            m("A0137", 2, "A0138", 2), g(SURVIVAL), THERMAL_PROVIDER, FAIL_CLOSED),
        d("A0142", "Digestão Frugal", SURVIVAL, NUTRITION, NOTABLE, 1, 2,
            Map.of(), g(SURVIVAL), TWO_DISTINCT_METABOLIC, FAIL_CLOSED),
        d("A0143", "Nutrição Persistente", SURVIVAL, NUTRITION, KEYSTONE, 1, 2,
            m("A0142", 1), g(SURVIVAL), NUTRITION_PROVIDER, FAIL_CLOSED),
        d("A0144", "Poder Mágico", ARCANE, ARCANE_FUNDAMENTALS, TRUNK, 5, 1,
            Map.of(), g(ARCANE), ARCANE_PROVIDER, SAFE_COMPONENT_ONLY),
        d("A0145", "Eficiência Arcana", ARCANE, ARCANE_FUNDAMENTALS, TRUNK, 5, 1,
            Map.of(), g(ARCANE), ARCANE_PROVIDER, FAIL_CLOSED),
        d("A0146", "Reserva Arcana", ARCANE, ARCANE_FUNDAMENTALS, BRANCH, 5, 1,
            Map.of(), g(ARCANE), ARCANE_RESERVE_OR, FAIL_CLOSED),
        d("A0147", "Fluxo Arcano", ARCANE, ARCANE_FUNDAMENTALS, BRANCH, 5, 1,
            m("A0146", 2), g(ARCANE), ARCANE_PROVIDER, FAIL_CLOSED),
        d("A0148", "Conjuração Rápida", ARCANE, ARCANE_TECHNIQUE, BRANCH, 4, 1,
            m("A0144", 2, "A0145", 2), g(ARCANE), ARCANE_PROVIDER, FAIL_CLOSED),
        d("A0149", "Recuperação de Feitiço", ARCANE, ARCANE_TECHNIQUE, NOTABLE, 1, 2,
            m("A0148", 2, "A0147", 2), g(ARCANE), ARCANE_PROVIDER, FAIL_CLOSED),
        d("A0150", "Estabilidade de Conjuração", ARCANE, ARCANE_TECHNIQUE, NOTABLE, 1, 2,
            m("A0148", 1, "A0144", 2), g(ARCANE), RESOURCE_DEBIT_PROVIDER, FAIL_CLOSED)
    );
    private static final Map<String, FrozenSurvivalPerkDefinition> BY_CODE = index();

    private FrozenA0101A0150Catalog() {}

    public static List<FrozenSurvivalPerkDefinition> all() { return ALL; }

    public static Optional<FrozenSurvivalPerkDefinition> definition(String code) {
        return Optional.ofNullable(BY_CODE.get(code));
    }

    private static FrozenSurvivalPerkDefinition d(
        String code, String name, FrozenSurvivalPerkDefinition.Domain domain,
        FrozenSurvivalPerkDefinition.Family family, FrozenSurvivalPerkDefinition.Kind kind,
        int maxRank, int rankCost, Map<String, Integer> dependencies,
        Set<FrozenSurvivalPerkDefinition.Domain> requiredGateways,
        FrozenSurvivalPerkDefinition.SpecialGate specialGate,
        FrozenSurvivalPerkDefinition.Fallback fallback
    ) {
        return new FrozenSurvivalPerkDefinition(code, name, domain, family, kind, maxRank, rankCost,
            dependencies, requiredGateways, specialGate, fallback);
    }

    private static Set<FrozenSurvivalPerkDefinition.Domain> g(FrozenSurvivalPerkDefinition.Domain... values) {
        return Set.of(values);
    }

    private static Map<String, Integer> m(Object... values) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) result.put((String)values[i], (Integer)values[i + 1]);
        return Map.copyOf(result);
    }

    private static Map<String, FrozenSurvivalPerkDefinition> index() {
        if (ALL.size() != 50) throw new IllegalStateException("frozen catalog must contain exactly A0101-A0150");
        LinkedHashMap<String, FrozenSurvivalPerkDefinition> result = new LinkedHashMap<>();
        for (int i = 0; i < ALL.size(); i++) {
            FrozenSurvivalPerkDefinition definition = ALL.get(i);
            String expected = "A%04d".formatted(i + 101);
            if (!definition.code().equals(expected)) {
                throw new IllegalStateException("catalog sequence mismatch: expected " + expected
                    + " but got " + definition.code());
            }
            if (result.put(definition.code(), definition) != null) {
                throw new IllegalStateException("duplicate " + definition.code());
            }
        }
        for (FrozenSurvivalPerkDefinition definition : ALL) {
            definition.dependencies().forEach((dependency, requiredRank) -> {
                FrozenSurvivalPerkDefinition local = result.get(dependency);
                if (local != null && requiredRank > local.maxRank()) {
                    throw new IllegalStateException(definition.code() + " requires impossible rank for " + dependency);
                }
                if (local == null
                    && FrozenA0051A0100Catalog.definition(dependency).isEmpty()
                    && NotionCombatPerkCatalog.definition(dependency).isEmpty()) {
                    throw new IllegalStateException(definition.code() + " depends on unknown code " + dependency);
                }
            });
        }
        return Map.copyOf(result);
    }
}
