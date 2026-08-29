package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Fresh Notion snapshot for the implementation range A0001-A0020 only. */
public final class NotionCombatPerkCatalog {
    private static final Set<String> EPIC_DAMAGE = Set.of("epicfight:weapon_category", "epicfight:damage_pre");
    private static final Set<String> EPIC_CADENCE = Set.of("epicfight:weapon_category", "epicfight:modify_attack_speed");
    private static final Set<String> EPIC_CRIT = Set.of("epicfight:weapon_category", "rpgskilltree:canonical_critical");
    private static final Map<String, CombatPerkDefinition> DEFINITIONS = build();

    private NotionCombatPerkCatalog() {}

    public static Optional<CombatPerkDefinition> definition(String code) { return Optional.ofNullable(DEFINITIONS.get(code)); }
    public static Map<String, CombatPerkDefinition> all() { return DEFINITIONS; }

    private static Map<String, CombatPerkDefinition> build() {
        LinkedHashMap<String, CombatPerkDefinition> map = new LinkedHashMap<>();
        add(map, "A0001", "Treino com Espadas I", WeaponFamily.SWORD, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0002", "Treino com Espadas II", WeaponFamily.SWORD, 3, 1, Map.of("A0001", 2), EPIC_CADENCE);
        add(map, "A0003", "Precisão com Espadas", WeaponFamily.SWORD, 3, 1, Map.of("A0001", 1), EPIC_CRIT);
        add(map, "A0004", "Ritmo do Duelista", WeaponFamily.SWORD, 1, 1, Map.of("A0003", 2), Set.of("epicfight:damage_post"));
        add(map, "A0005", "Abertura de Guarda", WeaponFamily.SWORD, 1, 1, Map.of("A0002", 2, "A0004", 1), Set.of("epicfight:guard_posture", "epicfight:impact"));
        add(map, "A0006", "Maestria de Espadas — Riposta Perfeita", WeaponFamily.SWORD, 1, 2, Map.of("A0004", 1, "A0005", 1), Set.of("epicfight:dodge_or_technical_defense", "epicfight:impact"));

        add(map, "A0007", "Treino com Machados I", WeaponFamily.AXE, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0008", "Treino com Machados II", WeaponFamily.AXE, 3, 1, Map.of("A0007", 2), EPIC_CADENCE);
        add(map, "A0009", "Precisão com Machados", WeaponFamily.AXE, 3, 1, Map.of("A0007", 1), EPIC_CRIT);
        add(map, "A0010", "Pressão do Carrasco", WeaponFamily.AXE, 2, 1, Map.of("A0009", 2), Set.of("epicfight:damage_post"));
        add(map, "A0011", "Ruptura de Guarda", WeaponFamily.AXE, 2, 1, Map.of("A0008", 2, "A0009", 1), Set.of("epicfight:guard_posture", "epicfight:impact", "epicfight:physical_penetration"));
        add(map, "A0012", "Maestria de Machados — Frenesi do Saqueador", WeaponFamily.AXE, 1, 2, Map.of("A0010", 1, "A0011", 1), Set.of("epicfight:impact", "cold_sweat:thermal_activity", "minecraft:exhaustion", "thirstwasreclaimed:water_cost"));

        add(map, "A0013", "Treino com Lanças I", WeaponFamily.SPEAR, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0014", "Treino com Lanças II", WeaponFamily.SPEAR, 3, 1, Map.of("A0013", 2), EPIC_CADENCE);
        add(map, "A0015", "Precisão com Lanças", WeaponFamily.SPEAR, 3, 1, Map.of("A0013", 1), EPIC_CRIT);
        add(map, "A0016", "Distância Ideal", WeaponFamily.SPEAR, 2, 1, Map.of("A0015", 2), Set.of("epicfight:reach", "epicfight:damage_post"));
        add(map, "A0017", "Interceptação", WeaponFamily.SPEAR, 2, 1, Map.of("A0014", 2, "A0015", 1), Set.of("epicfight:reach", "epicfight:impact"));
        add(map, "A0018", "Maestria de Lanças — Linha de Interceptação", WeaponFamily.SPEAR, 1, 2, Map.of("A0016", 1, "A0017", 1), Set.of("epicfight:reach", "epicfight:impact"));

        add(map, "A0019", "Treino com Adagas I", WeaponFamily.DAGGER, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0020", "Treino com Adagas II", WeaponFamily.DAGGER, 3, 1, Map.of("A0019", 2), EPIC_CADENCE);
        return Map.copyOf(map);
    }

    private static void add(Map<String, CombatPerkDefinition> map, String code, String name, WeaponFamily family,
                            int ranks, int cost, Map<String, Integer> dependencies, Set<String> capabilities) {
        CombatPerkDefinition previous = map.put(code, new CombatPerkDefinition(code, name, family, ranks, cost, dependencies, capabilities));
        if (previous != null) throw new IllegalStateException("duplicate perk code: " + code);
    }
}
