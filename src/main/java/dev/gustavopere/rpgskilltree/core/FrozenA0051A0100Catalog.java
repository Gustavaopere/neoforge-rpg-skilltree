package dev.gustavopere.rpgskilltree.core;

import static dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Domain.*;
import static dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Fallback.*;
import static dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Family.*;
import static dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.Kind.*;
import static dev.gustavopere.rpgskilltree.core.FrozenCombatPerkDefinition.SpecialGate.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Canonical structured projection of the frozen A0051-A0100 Notion catalog. */
public final class FrozenA0051A0100Catalog {
    private static final List<FrozenCombatPerkDefinition> ALL = List.of(
        d("A0051", "Precisão com Bestas", MARTIAL, CROSSBOW, BRANCH, 3, 1, m("A0049", 1),
            1, s("epic_crossbow"), Map.of(), Set.of(), NONE, NATIVE),
        d("A0052", "Cadência de Recarga", MARTIAL, CROSSBOW, NOTABLE, 2, 1, m("A0050", 2, "A0051", 2),
            1, s("epic_crossbow"), Map.of(), Set.of(), NONE, NATIVE),
        d("A0053", "Virote Perfurante", MARTIAL, CROSSBOW, NOTABLE, 2, 1, m("A0052", 1),
            1, s("epic_crossbow"), Map.of(), Set.of(), NONE, SAFE_COMPONENT_ONLY),
        d("A0054", "Maestria de Bestas — Mecanismo Ajustado", MARTIAL, CROSSBOW, CAPSTONE, 1, 2,
            m("A0052", 2, "A0053", 1), 1, s("epic_crossbow"), m("combat:crossbow", 80), Set.of(), NONE,
            SAFE_COMPONENT_ONLY),
        d("A0055", "Treino com Armas de Punho I", MARTIAL, FIST, BRANCH, 3, 1, Map.of(),
            8, s(CombatFistPolicy.SPECIALIZATION_ID), m(CombatFistPolicy.MASTERY_ID, 60), Set.of(), NONE, NATIVE),
        d("A0056", "Treino com Armas de Punho II", MARTIAL, FIST, BRANCH, 3, 1, m("A0055", 2),
            8, s(CombatFistPolicy.SPECIALIZATION_ID), m(CombatFistPolicy.MASTERY_ID, 60), Set.of(), NONE, FAIL_CLOSED),
        d("A0057", "Precisão com Armas de Punho", MARTIAL, FIST, BRANCH, 3, 1, m("A0055", 1),
            8, s(CombatFistPolicy.SPECIALIZATION_ID), m(CombatFistPolicy.MASTERY_ID, 60), Set.of(), NONE, NATIVE),
        d("A0058", "Sequência Limpa", MARTIAL, FIST, NOTABLE, 2, 1, m("A0057", 2),
            8, s(CombatFistPolicy.SPECIALIZATION_ID), m(CombatFistPolicy.MASTERY_ID, 60), Set.of(), NONE, NATIVE),
        d("A0059", "Quebra de Ritmo", MARTIAL, FIST, NOTABLE, 2, 1, m("A0056", 2, "A0058", 1),
            8, s(CombatFistPolicy.SPECIALIZATION_ID), m(CombatFistPolicy.MASTERY_ID, 60), Set.of(), NONE, FAIL_CLOSED),
        d("A0060", "Maestria de Armas de Punho — Combinação Final", MARTIAL, FIST, CAPSTONE, 1, 2,
            m("A0058", 2, "A0059", 1), 8, s(CombatFistPolicy.SPECIALIZATION_ID),
            m(CombatFistPolicy.MASTERY_ID, 80), Set.of(), NONE, FAIL_CLOSED),

        d("A0061", "Força Aplicada", MARTIAL, MARTIAL_OFFENSE, BRANCH, 5, 1, Map.of(), g(MARTIAL), NATIVE),
        d("A0062", "Golpe Preciso", MARTIAL, MARTIAL_OFFENSE, BRANCH, 4, 1, Map.of(), g(MARTIAL), NATIVE),
        d("A0063", "Impacto Crítico", MARTIAL, MARTIAL_OFFENSE, BRANCH, 3, 1, m("A0062", 2), g(MARTIAL), NATIVE),
        d("A0064", "Ritmo de Combate", MARTIAL, MARTIAL_OFFENSE, BRANCH, 4, 1, Map.of(), g(MARTIAL), FAIL_CLOSED),
        d("A0065", "Penetração Física", MARTIAL, MARTIAL_OFFENSE, BRANCH, 4, 1, m("A0061", 2), g(MARTIAL), FAIL_CLOSED),
        d("A0066", "Impacto Marcial", MARTIAL, MARTIAL_OFFENSE, BRANCH, 4, 1, m("A0061", 1), g(MARTIAL), FAIL_CLOSED),
        d("A0067", "Firmeza Ofensiva", MARTIAL, MARTIAL_OFFENSE, BRANCH, 4, 1, m("A0066", 1), g(MARTIAL), FAIL_CLOSED),
        d("A0068", "Dano contra Feridos", MARTIAL, MARTIAL_OFFENSE, BRANCH, 3, 1, m("A0061", 1), g(MARTIAL), NATIVE),
        d("A0069", "Dano contra Íntegros", MARTIAL, MARTIAL_OFFENSE, BRANCH, 3, 1, m("A0061", 1), g(MARTIAL), NATIVE),
        d("A0070", "Dano contra Chefes", MARTIAL, MARTIAL_OFFENSE, BRANCH, 5, 1, m("A0061", 1), g(MARTIAL), NATIVE),
        d("A0071", "Dano contra Elites", MARTIAL, MARTIAL_OFFENSE, BRANCH, 5, 1, m("A0061", 1), g(MARTIAL), FAIL_CLOSED),
        d("A0072", "Retaliação", MARTIAL, MARTIAL_OFFENSE, BRANCH, 3, 1, m("A0067", 1), g(MARTIAL), NATIVE),
        d("A0073", "Janela de Execução", MARTIAL, MARTIAL_OFFENSE, NOTABLE, 1, 2, m("A0068", 2), g(MARTIAL), SAFE_COMPONENT_ONLY),
        d("A0074", "Primeiro Sangue", MARTIAL, MARTIAL_OFFENSE, NOTABLE, 1, 2, m("A0069", 2), g(MARTIAL), SAFE_COMPONENT_ONLY),
        d("A0075", "Ritmo Sustentado", MARTIAL, MARTIAL_SUSTAIN, NOTABLE, 1, 2,
            m("A0061", 3, "A0064", 2), g(MARTIAL), FAIL_CLOSED),
        d("A0076", "Postura Agressiva", MARTIAL, MARTIAL_OFFENSE, NOTABLE, 1, 1,
            m("A0061", 3, "A0064", 1), g(MARTIAL), NATIVE),
        d("A0077", "Postura Cautelosa", MARTIAL, MARTIAL_OFFENSE, NOTABLE, 1, 1,
            m("A0067", 2), g(MARTIAL), NATIVE),
        d("A0078", "Ataque em Movimento", MARTIAL, MARTIAL_OFFENSE, BRIDGE, 3, 1,
            m("A0064", 2), g(MARTIAL, AGILITY), NATIVE),
        d("A0079", "Ataque Estacionário", MARTIAL, MARTIAL_OFFENSE, BRIDGE, 3, 1,
            m("A0061", 2), g(MARTIAL, VITALITY), NATIVE),
        d("A0080", "Golpe de Oportunidade", MARTIAL, MARTIAL_OFFENSE, BRIDGE, 1, 2,
            m("A0078", 2), 1, Set.of(), Map.of(), g(MARTIAL), DODGE_BRANCH, FAIL_CLOSED),

        d("A0081", "Recuperação de Combate", MARTIAL, MARTIAL_SUSTAIN, NOTABLE, 3, 1, m("A0075", 1), g(MARTIAL), NATIVE),
        d("A0082", "Vampirismo de Arma", MARTIAL, SUSTAIN, BRIDGE, 3, 1, m("A0061", 2),
            1, Set.of(), Map.of(), g(MARTIAL), ANY_PHYSICAL_WEAPON, NATIVE),
        d("A0083", "Vampirismo Mágico", ARCANE, SUSTAIN, NOTABLE, 3, 1, Map.of(),
            1, Set.of(), Map.of(), g(ARCANE), ARCANE_DIRECT_DAMAGE_BRANCH, FAIL_CLOSED),
        d("A0084", "Sifão Elemental", ARCANE, SUSTAIN, BRIDGE, 3, 1, Map.of(),
            1, Set.of(), Map.of(), g(ARCANE), ELEMENTAL_AFFINITY, FAIL_CLOSED),
        d("A0085", "Sifão de Dano Periódico", OCCULT, SUSTAIN, NOTABLE, 3, 1, Map.of(),
            1, Set.of(), Map.of(), g(OCCULT), ATTRIBUTABLE_PERIODIC_SOURCE, FAIL_CLOSED),
        d("A0086", "Vampirismo Universal", OCCULT, SUSTAIN, KEYSTONE, 1, 3,
            m("A0082", 3, "A0083", 3, "A0085", 2), g(MARTIAL, ARCANE, OCCULT), NATIVE),
        d("A0087", "Sede de Sangue", MARTIAL, MARTIAL_SUSTAIN, CAPSTONE, 1, 2,
            m("A0075", 1, "A0081", 3, "A0082", 2), g(MARTIAL), FAIL_CLOSED),
        d("A0088", "Constituição", VITALITY, VITALITY_DEFENSE, BRANCH, 5, 1, Map.of(), g(VITALITY), NATIVE),
        d("A0089", "Couro Endurecido", VITALITY, VITALITY_DEFENSE, BRANCH, 5, 1, Map.of(), g(VITALITY), NATIVE),
        d("A0090", "Têmpera", VITALITY, VITALITY_DEFENSE, BRANCH, 5, 1, m("A0089", 2), g(VITALITY), NATIVE),
        d("A0091", "Base Firme", VITALITY, VITALITY_DEFENSE, BRANCH, 5, 1, Map.of(), g(VITALITY), NATIVE),
        d("A0092", "Resistência Física", VITALITY, VITALITY_DEFENSE, BRANCH, 4, 1, m("A0089", 2), g(VITALITY), NATIVE),
        d("A0093", "Guarda Econômica", VITALITY, VITALITY_DEFENSE, BRIDGE, 5, 1, Map.of(),
            1, Set.of(), Map.of(), g(VITALITY, MARTIAL), GUARD_CORRIDOR, FAIL_CLOSED),
        d("A0094", "Recuperação de Guarda", VITALITY, VITALITY_DEFENSE, BRANCH, 4, 1, m("A0093", 2), g(VITALITY), FAIL_CLOSED),
        d("A0095", "Tenacidade", VITALITY, VITALITY_DEFENSE, BRANCH, 5, 1,
            m("A0091", 2, "A0094", 1), g(VITALITY), FAIL_CLOSED),
        d("A0096", "Último Fôlego", VITALITY, VITALITY_DEFENSE, BRANCH, 3, 1, m("A0092", 2), g(VITALITY), NATIVE),
        d("A0097", "Primeira Defesa", VITALITY, VITALITY_DEFENSE, BRANCH, 3, 1, m("A0088", 1), g(VITALITY), NATIVE),
        d("A0098", "Defesa em Movimento", VITALITY, VITALITY_DEFENSE, BRIDGE, 3, 1,
            m("A0088", 2), g(VITALITY, AGILITY), NATIVE),
        d("A0099", "Defesa Estacionária", VITALITY, VITALITY_DEFENSE, BRIDGE, 3, 1,
            m("A0089", 2), g(VITALITY, MARTIAL), NATIVE),
        d("A0100", "Anti-Crítico", VITALITY, VITALITY_DEFENSE, BRANCH, 4, 1, m("A0090", 2), g(VITALITY), FAIL_CLOSED)
    );
    private static final Map<String, FrozenCombatPerkDefinition> BY_CODE = index();

    private FrozenA0051A0100Catalog() {}

    public static List<FrozenCombatPerkDefinition> all() { return ALL; }

    public static Optional<FrozenCombatPerkDefinition> definition(String code) {
        return Optional.ofNullable(BY_CODE.get(code));
    }

    private static FrozenCombatPerkDefinition d(
        String code, String name, FrozenCombatPerkDefinition.Domain domain,
        FrozenCombatPerkDefinition.Family family, FrozenCombatPerkDefinition.Kind kind,
        int maxRank, int cost, Map<String, Integer> dependencies,
        int minLevel, Set<String> specializations, Map<String, Integer> mastery,
        Set<FrozenCombatPerkDefinition.Domain> gateways,
        FrozenCombatPerkDefinition.SpecialGate specialGate,
        FrozenCombatPerkDefinition.Fallback fallback
    ) {
        return new FrozenCombatPerkDefinition(code, name, domain, family, kind, maxRank, cost,
            dependencies, minLevel, specializations, mastery, gateways, specialGate, fallback);
    }

    private static FrozenCombatPerkDefinition d(
        String code, String name, FrozenCombatPerkDefinition.Domain domain,
        FrozenCombatPerkDefinition.Family family, FrozenCombatPerkDefinition.Kind kind,
        int maxRank, int cost, Map<String, Integer> dependencies,
        Set<FrozenCombatPerkDefinition.Domain> gateways,
        FrozenCombatPerkDefinition.Fallback fallback
    ) {
        return d(code, name, domain, family, kind, maxRank, cost, dependencies,
            1, Set.of(), Map.of(), gateways, NONE, fallback);
    }

    private static Set<FrozenCombatPerkDefinition.Domain> g(FrozenCombatPerkDefinition.Domain... domains) {
        return Set.of(domains);
    }

    private static Set<String> s(String... values) { return Set.of(values); }

    private static Map<String, Integer> m(Object... values) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) result.put((String)values[i], (Integer)values[i + 1]);
        return Map.copyOf(result);
    }

    private static Map<String, FrozenCombatPerkDefinition> index() {
        if (ALL.size() != 50) throw new IllegalStateException("frozen catalog must contain exactly A0051-A0100");
        LinkedHashMap<String, FrozenCombatPerkDefinition> index = new LinkedHashMap<>();
        for (int i = 0; i < ALL.size(); i++) {
            FrozenCombatPerkDefinition definition = ALL.get(i);
            String expected = "A%04d".formatted(i + 51);
            if (!definition.code().equals(expected)) {
                throw new IllegalStateException("catalog sequence mismatch: expected " + expected + " but got " + definition.code());
            }
            if (index.put(definition.code(), definition) != null) throw new IllegalStateException("duplicate " + definition.code());
        }
        for (FrozenCombatPerkDefinition definition : ALL) {
            definition.dependencies().forEach((dependency, requiredRank) -> {
                FrozenCombatPerkDefinition local = index.get(dependency);
                if (local != null && requiredRank > local.maxRank()) {
                    throw new IllegalStateException(definition.code() + " requires impossible rank for " + dependency);
                }
                if (local == null && NotionCombatPerkCatalog.definition(dependency).isEmpty()) {
                    throw new IllegalStateException(definition.code() + " depends on unknown code " + dependency);
                }
            });
        }
        return Map.copyOf(index);
    }
}
