package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Versioned player-facing PT-BR snapshot for combat perks that completed the mandatory design audit.
 *
 * <p>This catalog is presentation-only. It does not define gameplay behavior, coefficients, gates or
 * provider fallbacks; those remain server-authoritative in the existing combat/runtime contracts.
 * Entries are added only after the corresponding perk audit is approved.</p>
 */
public final class CombatPerkPlayerTextCatalog {
    private static final Map<String, PlayerText> ENTRIES = build();

    private CombatPerkPlayerTextCatalog() {}

    public static Optional<PlayerText> entry(String code) {
        return Optional.ofNullable(ENTRIES.get(code));
    }

    public static Map<String, PlayerText> all() {
        return ENTRIES;
    }

    public record PlayerText(String effect, String gate) {
        public PlayerText {
            if (effect == null || effect.isBlank()) {
                throw new IllegalArgumentException("effect must not be blank");
            }
            if (gate == null || gate.isBlank()) {
                throw new IllegalArgumentException("gate must not be blank");
            }
        }
    }

    private static Map<String, PlayerText> build() {
        LinkedHashMap<String, PlayerText> entries = new LinkedHashMap<>();
        add(entries, "A0001",
            "+3% de dano com espadas por rank, máximo +9%.",
            "nível 8 + `epicfight:sword` ≥60 + Gateway `epic_sword`.");
        add(entries, "A0002",
            "+2% de velocidade/ritmo efetivo com espadas por rank, até +6%, respeitando os limites do moveset/provider.",
            "Gateway `epic_sword` acessível + A0001 ≥ 2 ranks; o gateway pertence à Árvore Exterior.");
        add(entries, "A0003",
            "+3% de chance de crítico com espadas por rank, até +9%.",
            "Gateway `epic_sword` acessível + A0001 ≥ 1 rank; gateway da Árvore Exterior.");
        add(entries, "A0004",
            "Acertos diretos limpos com espada geram 1 de Ímpeto, até 5 cargas. Aparo, riposta ou esquiva que realmente evitou uma ameaça podem gerar 1 carga em seu próprio evento quando o provider expuser confirmação segura. Um ataque de espada iniciado pelo jogador que termine sem acertar alvo elegível remove 1 carga, uma ocorrência de desequilíbrio/stagger pesado causada por fonte hostil remove 2 cargas e, após 5 s sem acerto válido de espada nem defesa técnica elegível, Ímpeto perde 1 carga por segundo até chegar a 0.",
            "Gateway de disciplina de Espadas (`epic_sword`) acessível + A0003 Precisão com Espadas ≥ 2 ranks. O gateway pertence à Árvore Exterior, não à Árvore de Especialista.");
        add(entries, "A0005",
            "Com pelo menos 3 de Ímpeto, um acerto direto de espada contra o mesmo alvo após sequência limpa pode consumir 2 de Ímpeto para criar Abertura: o golpe recebe +12% de penetração física elegível e +8% de impacto/pressão de guarda. Recarga de 6 s por alvo.",
            "Gateway de disciplina de Espadas (`epic_sword`) acessível + A0002 Treino com Espadas II ≥ 2 ranks + A0004 Ritmo do Duelista adquirido. O gateway pertence à Árvore Exterior.");
        add(entries, "A0006",
            "ao atingir 5 de Ímpeto, uma defesa técnica confirmada — aparo, guarda perfeita ou esquiva que realmente evitou ataque elegível — prepara Riposta Perfeita por 3 s. O próximo acerto direto de espada consome todo o Ímpeto, recebe +20% de dano crítico elegível e +20% de impacto/pressão de guarda, e não gera Ímpeto no mesmo resultado. Recarga de 10 s.",
            "Gateway `epic_sword` acessível + A0004 + A0005 + mastery de espada ≥ 80.");
        add(entries, "A0007",
            "+3% de dano com machados por rank, máximo +9%.",
            "nível 8 + `epicfight:axe` ≥60 + Gateway `epic_axe`.");
        add(entries, "A0008",
            "+2% de velocidade/ritmo efetivo com machados por rank, até +6%, respeitando o moveset/provider.",
            "Gateway `epic_axe` acessível + A0007 ≥ 2 ranks; gateway da Árvore Exterior.");
        add(entries, "A0009",
            "+3% de chance de crítico com machados por rank, até +9%.",
            "Gateway `epic_axe` acessível + A0007 ≥ 1 rank; gateway da Árvore Exterior.");
        add(entries, "A0010",
            "Cada acerto corpo a corpo direto e válido com machado contra inimigo hostil gera 8 de Fúria como ganho-base. O multiplicador do rank é aplicado primeiro; se o alvo for diferente do último alvo hostil legitimamente atingido pelo jogador, o resultado recebe depois ×1,5. Dano autoinfligido, alvo passivo/de treino, entidade invulnerável, tentativa sem dano confirmado, proc secundário e ação sem autoria real não geram Fúria. Fúria é limitada a 100.",
            "Gateway de disciplina de Machados (`epic_axe`) acessível + A0009 Precisão com Machados ≥ 2 ranks. O gateway pertence à Árvore Exterior.");
        add(entries, "A0011",
            "Com pelo menos 40 de Fúria, um golpe direto de machado contra alvo em guarda ou com postura defensiva ativa pode consumir 20 de Fúria para aplicar Ruptura de Guarda: +20%/+35% de pressão de guarda ou impacto naquele golpe e até +6%/+10% de penetração física elegível. Quando o provider não expuser guarda/postura, somente defesa física server-side comprovável pode qualificar o fallback de penetração. Não ativa contra alvos sem defesa relevante apenas para obter penetração gratuita.",
            "Gateway de disciplina de Machados (`epic_axe`) acessível + A0008 Treino com Machados II ≥ 2 ranks + A0009 Precisão com Machados ≥ 1 rank. O gateway pertence à Árvore Exterior.");
        add(entries, "A0012",
            "Ao alcançar 75 de Fúria usando machado, entra em Frenesi enquanto permanecer ≥75 e o bridge versionado do Cold Sweat estiver operacional. Em cada `DELIVER_DAMAGE_PRE` direto, hostil e elegível de machado, o runtime deve primeiro aplicar, no mesmo evento server-authoritative, o custo corporal explícito da perk: +1,5 unidade à temperatura corporal CORE do Cold Sweat e +0,015 de exhaustion do Minecraft. Somente se esse pagamento causal for confirmado o golpe recebe +10% de impacto; a perk não cria sweep nem alvos extras. Ao chegar a 100 de Fúria, o próximo evento direto elegível pode, após o mesmo pagamento corporal confirmado, consumir atomicamente 40 de Fúria para um golpe de pico: impacto total de +20% e, se houver guarda/postura provider-native no alvo, pressão de guarda total de +40%; esses valores substituem, e não multiplicam, o +10% basal. Qualquer transição de Fúria de ≥75 para <75 enquanto o bridge CORE estiver operacional encerra Frenesi e aplica Queda de Ritmo por 6 s, reduzindo em 15% o atributo provider-native `epicfight:stamina_regen`.",
            "Gateway de disciplina de Machados (`epic_axe`) acessível + A0010 Pressão do Carrasco + A0011 Ruptura de Guarda + maestria de machados (`epicfight:axe`) ≥ 80. O gateway pertence à Árvore Exterior.");
        add(entries, "A0013",
            "+3% de dano com lanças por rank, máximo +9%.",
            "nível 8 + `epicfight:spear` ≥60 + Gateway `epic_spear`.");
        add(entries, "A0014",
            "+2% de velocidade/ritmo efetivo com lanças por rank, até +6%, respeitando o moveset/provider.",
            "Gateway `epic_spear` acessível + A0013 ≥ 2 ranks; gateway da Árvore Exterior.");
        add(entries, "A0015",
            "+3% de chance de crítico com lanças por rank, até +9%.",
            "Gateway `epic_spear` acessível + A0013 ≥ 1 rank; gateway da Árvore Exterior.");
        add(entries, "A0016",
            "Acertos diretos de lança realizados na faixa ideal — entre 70% e 100% do alcance efetivo da arma — geram 1 carga de Controle de Distância, até 3. Golpes abaixo de 70% do alcance não geram carga. Um ataque de lança iniciado que termine sem acertar alvo elegível remove 1 carga quando o provider permitir identificar o erro com segurança; sofrer impacto/stagger pesado de fonte hostil remove 1 carga.",
            "Gateway de disciplina de Lanças (`epic_spear`) acessível + A0015 Precisão com Lanças ≥ 2 ranks. O gateway pertence à Árvore Exterior; proximidade visual com outra disciplina não satisfaz este gate.");
        add(entries, "A0017",
            "quando inimigo entra na faixa ideal da lança avançando em direção ao jogador, o próximo golpe direto de lança em até 2 s pode consumir 1 Controle de Distância e receber +20%/+35% de impacto e pressão de guarda. Somente quando o provider confirmar corrida/investida ou movimento ofensivo com deslocamento próprio, o mesmo golpe reduz em 20%/30% o deslocamento ofensivo reconhecido, sem enraizamento.",
            "Gateway `epic_spear` + A0014 ≥ 2 + A0015 ≥ 1; gateway da Árvore Exterior.");
        add(entries, "A0018",
            "com 3 cargas de Controle de Distância, quando inimigo cruza de fora para dentro da faixa ideal, abre Janela de Interceptação. O próximo hit direto de lança dentro da janela consome todas as cargas, recebe +15% de dano físico elegível e +40% de impacto/pressão de guarda. O mesmo alvo não pode gerar nova janela por 8 s.",
            "Gateway `epic_spear` + A0016 + A0017 + mastery `epicfight:spear` ≥80; terminal da Árvore Exterior.");
        add(entries, "A0019",
            "+3% de dano com adagas por rank, máximo +9%.",
            "nível 8 + `epicfight:dagger` ≥60 + Gateway `epic_dagger`.");
        add(entries, "A0020",
            "+2% de velocidade/ritmo efetivo com adagas por rank, até +6%, respeitando o moveset/provider.",
            "Gateway `epic_dagger` acessível + A0019 ≥ 2 ranks; gateway da Árvore Exterior.");
        return Map.copyOf(entries);
    }

    private static void add(Map<String, PlayerText> entries, String code, String effect, String gate) {
        if (entries.putIfAbsent(code, new PlayerText(effect, gate)) != null) {
            throw new IllegalStateException("duplicate player-text code: " + code);
        }
    }
}
