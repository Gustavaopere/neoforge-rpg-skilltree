package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.EffectKind;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Audited semantic catalog for the first Notion combat-perk batch.
 *
 * <p>A#### is deliberately kept separate from the physical passive-node ResourceLocation graph.
 */
public final class NotionCombatPerkCatalog {
    private static final List<CombatPerkDefinition> ALL = List.of(
        d("A0001", "Treino com Espadas I", WeaponFamily.SWORD, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0002", "Treino com Espadas II", WeaponFamily.SWORD, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0001", 2)),
        d("A0003", "Precisão com Espadas", WeaponFamily.SWORD, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0001", 1)),
        d("A0004", "Ritmo do Duelista", WeaponFamily.SWORD, EffectKind.MOMENTUM_GENERATION, 1, 1, Map.of("A0003", 2)),
        d("A0005", "Abertura de Guarda", WeaponFamily.SWORD, EffectKind.GUARD_OPENING, 1, 1, Map.of("A0002", 2, "A0004", 1)),
        d("A0006", "Maestria de Espadas — Riposta Perfeita", WeaponFamily.SWORD, EffectKind.PERFECT_RIPOSTE, 1, 2, Map.of("A0004", 1, "A0005", 1)),
        d("A0007", "Treino com Machados I", WeaponFamily.AXE, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0008", "Treino com Machados II", WeaponFamily.AXE, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0007", 2)),
        d("A0009", "Precisão com Machados", WeaponFamily.AXE, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0007", 1)),
        d("A0010", "Pressão do Carrasco", WeaponFamily.AXE, EffectKind.FURY_GENERATION, 2, 1, Map.of("A0009", 2)),
        d("A0011", "Ruptura de Guarda", WeaponFamily.AXE, EffectKind.GUARD_RUPTURE, 2, 1, Map.of("A0008", 2, "A0009", 1)),
        d("A0012", "Maestria de Machados — Frenesi do Reaver", WeaponFamily.AXE, EffectKind.REAVER_FRENZY, 1, 2, Map.of("A0010", 1, "A0011", 1)),
        d("A0013", "Treino com Lanças I", WeaponFamily.SPEAR, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0014", "Treino com Lanças II", WeaponFamily.SPEAR, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0013", 2)),
        d("A0015", "Precisão com Lanças", WeaponFamily.SPEAR, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0013", 1)),
        d("A0016", "Distância Ideal", WeaponFamily.SPEAR, EffectKind.DISTANCE_CONTROL, 2, 1, Map.of("A0015", 2)),
        d("A0017", "Interceptação", WeaponFamily.SPEAR, EffectKind.INTERCEPTION, 2, 1, Map.of("A0014", 2, "A0015", 1)),
        d("A0018", "Maestria de Lanças — Linha de Interceptação", WeaponFamily.SPEAR, EffectKind.INTERCEPTION_MASTERY, 1, 2, Map.of("A0016", 1, "A0017", 1)),
        d("A0019", "Treino com Adagas I", WeaponFamily.DAGGER, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0020", "Treino com Adagas II", WeaponFamily.DAGGER, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0019", 2)),
        d("A0021", "Precisão com Adagas", WeaponFamily.DAGGER, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0019", 1)),
        d("A0022", "Ritmo das Sombras", WeaponFamily.DAGGER, EffectKind.FLOW_GENERATION, 2, 1, Map.of("A0021", 2)),
        d("A0023", "Ataque ao Ponto Cego", WeaponFamily.DAGGER, EffectKind.BLIND_SPOT, 2, 1, Map.of("A0020", 2, "A0021", 1)),
        d("A0024", "Maestria de Adagas — Dança das Sombras", WeaponFamily.DAGGER, EffectKind.SHADOW_DANCE, 1, 2, Map.of("A0022", 1, "A0023", 1)),
        d("A0025", "Treino com Martelos I", WeaponFamily.HAMMER, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0026", "Treino com Martelos II", WeaponFamily.HAMMER, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0025", 2)),
        d("A0027", "Precisão com Martelos", WeaponFamily.HAMMER, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0025", 1)),
        d("A0028", "Abalo Crescente", WeaponFamily.HAMMER, EffectKind.SHOCK_GENERATION, 2, 1, Map.of("A0027", 2)),
        d("A0029", "Quebra de Postura", WeaponFamily.HAMMER, EffectKind.POSTURE_BREAK, 2, 1, Map.of("A0026", 2, "A0027", 1)),
        d("A0030", "Maestria de Martelos — Golpe Demolidor", WeaponFamily.HAMMER, EffectKind.DEMOLISHER, 1, 2, Map.of("A0028", 1, "A0029", 1)),
        d("A0031", "Treino com Maças I", WeaponFamily.MACE, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0032", "Treino com Maças II", WeaponFamily.MACE, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0031", 2)),
        d("A0033", "Precisão com Maças", WeaponFamily.MACE, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0031", 1)),
        d("A0034", "Trauma Contundente", WeaponFamily.MACE, EffectKind.TRAUMA_GENERATION, 2, 1, Map.of("A0033", 2)),
        d("A0035", "Armadura Fendida", WeaponFamily.MACE, EffectKind.ARMOR_CRACK, 2, 1, Map.of("A0032", 2, "A0033", 1)),
        d("A0036", "Maestria de Maças — Quebra-Ossos", WeaponFamily.MACE, EffectKind.BONE_BREAKER, 1, 2, Map.of("A0034", 1, "A0035", 1)),
        d("A0037", "Treino com Foices I", WeaponFamily.SCYTHE, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0038", "Treino com Foices II", WeaponFamily.SCYTHE, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0037", 2)),
        d("A0039", "Precisão com Foices", WeaponFamily.SCYTHE, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0037", 1)),
        d("A0040", "Marca da Ceifa", WeaponFamily.SCYTHE, EffectKind.REAPING_MARK, 2, 1, Map.of("A0039", 2)),
        d("A0041", "Corte de Ceifa", WeaponFamily.SCYTHE, EffectKind.REAPING_CUT, 2, 1, Map.of("A0038", 2, "A0039", 1)),
        d("A0042", "Maestria de Foices — Colheita de Batalha", WeaponFamily.SCYTHE, EffectKind.BATTLE_HARVEST, 1, 2, Map.of("A0040", 1, "A0041", 1)),
        d("A0043", "Treino com Arcos I", WeaponFamily.BOW, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0044", "Treino com Arcos II", WeaponFamily.BOW, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0043", 2)),
        d("A0045", "Precisão com Arcos", WeaponFamily.BOW, EffectKind.CRITICAL_TRAINING, 3, 1, Map.of("A0043", 1)),
        d("A0046", "Foco de Mira", WeaponFamily.BOW, EffectKind.FOCUS_GENERATION, 2, 1, Map.of("A0045", 2)),
        d("A0047", "Distância Dominada", WeaponFamily.BOW, EffectKind.RANGE_MASTERY, 2, 1, Map.of("A0044", 2, "A0045", 1)),
        d("A0048", "Maestria de Arcos — Tiro Preparado", WeaponFamily.BOW, EffectKind.PREPARED_SHOT, 1, 2, Map.of("A0046", 1, "A0047", 1)),
        d("A0049", "Treino com Bestas I", WeaponFamily.CROSSBOW, EffectKind.DAMAGE_TRAINING, 3, 1, Map.of()),
        d("A0050", "Treino com Bestas II", WeaponFamily.CROSSBOW, EffectKind.RHYTHM_TRAINING, 3, 1, Map.of("A0049", 2))
    );
    private static final Map<String, CombatPerkDefinition> BY_CODE = index();

    private NotionCombatPerkCatalog() {}

    public static List<CombatPerkDefinition> all() {
        return ALL;
    }

    public static Optional<CombatPerkDefinition> definition(String code) {
        return Optional.ofNullable(BY_CODE.get(code));
    }

    private static CombatPerkDefinition d(
        String code,
        String name,
        WeaponFamily family,
        EffectKind effectKind,
        int maxRank,
        int rankCost,
        Map<String, Integer> dependencies
    ) {
        return new CombatPerkDefinition(code, name, family, effectKind, maxRank, rankCost, dependencies);
    }

    private static Map<String, CombatPerkDefinition> index() {
        if (ALL.size() != 50) throw new IllegalStateException("A0001-A0050 catalog must contain exactly 50 entries");
        Map<String, CombatPerkDefinition> index = new LinkedHashMap<>();
        for (int i = 0; i < ALL.size(); i++) {
            CombatPerkDefinition definition = ALL.get(i);
            String expected = "A%04d".formatted(i + 1);
            if (!definition.code().equals(expected)) {
                throw new IllegalStateException("catalog sequence mismatch: expected " + expected + " but got " + definition.code());
            }
            if (index.put(definition.code(), definition) != null) {
                throw new IllegalStateException("duplicate catalog code: " + definition.code());
            }
        }
        for (CombatPerkDefinition definition : ALL) {
            definition.dependencies().forEach((dependency, requiredRank) -> {
                CombatPerkDefinition dependencyDefinition = index.get(dependency);
                if (dependencyDefinition == null) {
                    throw new IllegalStateException(definition.code() + " depends on unknown catalog code " + dependency);
                }
                if (requiredRank > dependencyDefinition.maxRank()) {
                    throw new IllegalStateException(definition.code() + " requires impossible rank for " + dependency);
                }
            });
        }
        return Map.copyOf(index);
    }
}
