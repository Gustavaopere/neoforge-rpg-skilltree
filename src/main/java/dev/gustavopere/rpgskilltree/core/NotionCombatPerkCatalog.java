package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Fresh Notion snapshot for the currently closed implementation range A0001-A0060. */
public final class NotionCombatPerkCatalog {
    private static final Set<String> EPIC_DAMAGE = Set.of("epicfight:weapon_category", "epicfight:damage_pre");
    private static final Set<String> EPIC_CADENCE = Set.of("epicfight:weapon_category", "epicfight:modify_attack_speed");
    private static final Set<String> EPIC_CRIT = Set.of("epicfight:weapon_category", "rpgskilltree:canonical_critical");
    private static final Set<String> PROJECTILE_DAMAGE = Set.of("minecraft:physical_projectile", "minecraft:projectile_owner");
    private static final Set<String> PROJECTILE_CRIT = Set.of("minecraft:physical_projectile", "minecraft:projectile_owner", "rpgskilltree:canonical_critical");
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
        add(map, "A0021", "Precisão com Adagas", WeaponFamily.DAGGER, 3, 1, Map.of("A0019", 1), EPIC_CRIT);
        add(map, "A0022", "Ritmo das Sombras", WeaponFamily.DAGGER, 2, 1, Map.of("A0021", 2), Set.of("epicfight:dodge", "minecraft:server_position"));
        add(map, "A0023", "Ataque ao Ponto Cego", WeaponFamily.DAGGER, 2, 1, Map.of("A0020", 2, "A0021", 1), Set.of("minecraft:server_orientation", "epicfight:physical_penetration"));
        add(map, "A0024", "Maestria de Adagas — Dança das Sombras", WeaponFamily.DAGGER, 1, 2, Map.of("A0022", 1, "A0023", 1), Set.of("epicfight:dodge", "minecraft:server_orientation", "epicfight:impact"));

        add(map, "A0025", "Treino com Martelos I", WeaponFamily.HAMMER, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0026", "Treino com Martelos II", WeaponFamily.HAMMER, 3, 1, Map.of("A0025", 2), EPIC_CADENCE);
        add(map, "A0027", "Precisão com Martelos", WeaponFamily.HAMMER, 3, 1, Map.of("A0025", 1), EPIC_CRIT);
        add(map, "A0028", "Abalo Crescente", WeaponFamily.HAMMER, 2, 1, Map.of("A0027", 2), Set.of("epicfight:guard_posture_pressure"));
        add(map, "A0029", "Quebra de Postura", WeaponFamily.HAMMER, 2, 1, Map.of("A0026", 2, "A0027", 1), Set.of("epicfight:heavy_attack", "epicfight:impact", "epicfight:guard_posture_pressure"));
        add(map, "A0030", "Maestria de Martelos — Golpe Demolidor", WeaponFamily.HAMMER, 1, 2, Map.of("A0028", 1, "A0029", 1), Set.of("epicfight:confirmed_guard_break", "epicfight:heavy_attack", "epicfight:impact"));

        add(map, "A0031", "Treino com Maças I", WeaponFamily.MACE, 3, 1, Map.of(), Set.of("minecraft:mace", "epicfight:weapon_category"));
        add(map, "A0032", "Treino com Maças II", WeaponFamily.MACE, 3, 1, Map.of("A0031", 2), EPIC_CADENCE);
        add(map, "A0033", "Precisão com Maças", WeaponFamily.MACE, 3, 1, Map.of("A0031", 1), EPIC_CRIT);
        add(map, "A0034", "Trauma Contundente", WeaponFamily.MACE, 2, 1, Map.of("A0033", 2), Set.of("minecraft:armor", "epicfight:active_guard_posture"));
        add(map, "A0035", "Armadura Fendida", WeaponFamily.MACE, 2, 1, Map.of("A0032", 2, "A0033", 1), Set.of("minecraft:temporary_armor_modifier"));
        add(map, "A0036", "Maestria de Maças — Quebra-Ossos", WeaponFamily.MACE, 1, 2, Map.of("A0034", 1, "A0035", 1), Set.of("epicfight:heavy_attack", "minecraft:temporary_damage_and_movement_modifier"));

        add(map, "A0037", "Treino com Foices I", WeaponFamily.SCYTHE, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0038", "Treino com Foices II", WeaponFamily.SCYTHE, 3, 1, Map.of("A0037", 2), EPIC_CADENCE);
        add(map, "A0039", "Precisão com Foices", WeaponFamily.SCYTHE, 3, 1, Map.of("A0037", 1), EPIC_CRIT);
        add(map, "A0040", "Marca da Ceifa", WeaponFamily.SCYTHE, 2, 1, Map.of("A0039", 2), Set.of("minecraft:health", "epicfight:damage_post"));
        add(map, "A0041", "Corte de Ceifa", WeaponFamily.SCYTHE, 2, 1, Map.of("A0038", 2, "A0039", 1), Set.of("rpgskilltree:mature_reap_mark", "epicfight:impact"));
        add(map, "A0042", "Maestria de Foices — Colheita de Batalha", WeaponFamily.SCYTHE, 1, 2, Map.of("A0040", 1, "A0041", 1), Set.of("minecraft:legitimate_kill", "rpgskilltree:reap_mark_transfer", "epicfight:stamina_receipt_optional"));

        add(map, "A0043", "Treino com Arcos I", WeaponFamily.BOW, 3, 1, Map.of(), PROJECTILE_DAMAGE);
        add(map, "A0044", "Treino com Arcos II", WeaponFamily.BOW, 3, 1, Map.of("A0043", 2), Set.of("provider:bow_preparation_speed_optional"));
        add(map, "A0045", "Precisão com Arcos", WeaponFamily.BOW, 3, 1, Map.of("A0043", 1), PROJECTILE_CRIT);
        add(map, "A0046", "Foco de Mira", WeaponFamily.BOW, 2, 1, Map.of("A0045", 2), Set.of("minecraft:bow_use_fraction", "minecraft:server_orientation", "minecraft:projectile_origin"));
        add(map, "A0047", "Distância Dominada", WeaponFamily.BOW, 2, 1, Map.of("A0044", 2, "A0045", 1), Set.of("minecraft:projectile_correlation", "provider:projectile_speed_optional", "provider:physical_penetration_optional"));
        add(map, "A0048", "Maestria de Arcos — Tiro Preparado", WeaponFamily.BOW, 1, 2, Map.of("A0046", 1, "A0047", 1), Set.of("minecraft:projectile_correlation", "rpgskilltree:focus", "provider:physical_penetration_optional"));

        add(map, "A0049", "Treino com Bestas I", WeaponFamily.CROSSBOW, 3, 1, Map.of(), PROJECTILE_DAMAGE);
        add(map, "A0050", "Treino com Bestas II", WeaponFamily.CROSSBOW, 3, 1, Map.of("A0049", 2), Set.of("provider:crossbow_reload_speed_optional"));
        add(map, "A0051", "Precisão com Bestas", WeaponFamily.CROSSBOW, 3, 1, Map.of("A0049", 1), PROJECTILE_CRIT);
        add(map, "A0052", "Cadência de Recarga", WeaponFamily.CROSSBOW, 2, 1, Map.of("A0050", 2, "A0051", 2), Set.of("minecraft:crossbow_hit", "minecraft:native_reload_completion"));
        add(map, "A0053", "Virote Perfurante", WeaponFamily.CROSSBOW, 2, 1, Map.of("A0052", 1), Set.of("rpgskilltree:cadence", "provider:physical_penetration_optional", "epicfight:impact_optional"));
        add(map, "A0054", "Maestria de Bestas — Mecanismo Ajustado", WeaponFamily.CROSSBOW, 1, 2, Map.of("A0052", 2, "A0053", 1), Set.of("minecraft:native_reload_completion", "provider:crossbow_reload_speed_optional"));

        add(map, "A0055", "Treino com Armas de Punho I", WeaponFamily.FIST, 3, 1, Map.of(), EPIC_DAMAGE);
        add(map, "A0056", "Treino com Armas de Punho II", WeaponFamily.FIST, 3, 1, Map.of("A0055", 2), EPIC_CADENCE);
        add(map, "A0057", "Precisão com Armas de Punho", WeaponFamily.FIST, 3, 1, Map.of("A0055", 1), EPIC_CRIT);
        add(map, "A0058", "Sequência Limpa", WeaponFamily.FIST, 2, 1, Map.of("A0057", 2), Set.of("epicfight:damage_post", "epicfight:confirmed_miss_optional"));
        add(map, "A0059", "Quebra de Ritmo", WeaponFamily.FIST, 2, 1, Map.of("A0058", 1, "A0056", 2), Set.of("epicfight:heavy_or_finisher", "epicfight:guard_posture_optional", "epicfight:impact_optional"));
        add(map, "A0060", "Maestria de Armas de Punho — Combinação Final", WeaponFamily.FIST, 1, 2, Map.of("A0058", 2, "A0059", 1), Set.of("epicfight:heavy_or_finisher", "epicfight:impact_optional", "epicfight:stamina_receipt_optional"));
        return Map.copyOf(map);
    }

    private static void add(Map<String, CombatPerkDefinition> map, String code, String name, WeaponFamily family,
                            int ranks, int cost, Map<String, Integer> dependencies, Set<String> capabilities) {
        CombatPerkDefinition previous = map.put(code, new CombatPerkDefinition(code, name, family, ranks, cost, dependencies, capabilities));
        if (previous != null) throw new IllegalStateException("duplicate perk code: " + code);
    }
}
