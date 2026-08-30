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
        add(entries, "A0021",
            "+3% de chance crítica com adagas por rank, máximo +9%. Aplica-se somente a acertos diretos do jogador classificados pelo provider como DAGGER; o crítico é resolvido uma única vez por ação-raiz, sem proc secundário nem segunda rolagem.",
            "Gateway `epic_dagger` acessível + A0019 ≥ 1 rank; gateway da Árvore Exterior.");
        add(entries, "A0022",
            "Um acerto direto de adaga até 2,5 s após esquiva ou reposicionamento válido gera 1 de Fluxo, até 4 cargas. A confirmação provider-native tem prioridade; no fallback geométrico server-side, o deslocamento horizontal deve ser ≥1,5 bloco e a mudança angular ≥60° antes do acerto. Giro de câmera, teleporte, knockback, deslocamento sem acerto real ou movimento não confirmado não geram Fluxo. As cargas expiram após 5 s/7 s sem novo ganho válido conforme o rank; após 3 s sem movimento horizontal relevante, perde 1 Fluxo por segundo. Stagger hostil forte LONG, KNOCKDOWN ou NEUTRALIZE remove 2 Fluxo.",
            "Gateway `epic_dagger` acessível + A0021 Precisão com Adagas ≥ 2 ranks.");
        add(entries, "A0023",
            "Com pelo menos 2 de Fluxo, um acerto direto de adaga confirmado na lateral ou retaguarda do alvo pode consumir 2 Fluxo. Rank 1: +15% de dano crítico elegível e até +6% de penetração física; rank 2: +25% e até +10%. Recarga de 4 s por alvo. A orientação é server-authoritative; sem receipt seguro de flanco/retaguarda, a ativação falha fechada.",
            "Gateway `epic_dagger` acessível + A0020 Treino com Adagas II ≥ 2 ranks + A0021 Precisão com Adagas ≥ 1 rank.");
        add(entries, "A0024",
            "Ao atingir 4 de Fluxo, um ataque de adaga até 2 s após esquiva ou reposicionamento válido consome todo o Fluxo e ativa Dança das Sombras por 4 s; mastery 90/100 amplia para 4,5 s/5 s. A primeira reposição válida pode custar 30% menos stamina do Epic Fight somente quando o custo puder ser modulado e confirmado com segurança. O primeiro acerto lateral/traseiro válido recebe +20% de impacto e +15% de dano físico elegível. Cada benefício ocorre no máximo uma vez por ativação; se não houver hook seguro de stamina, apenas essa parcela é omitida.",
            "Gateway `epic_dagger` acessível + A0022 Ritmo das Sombras + A0023 Ataque ao Ponto Cego + mastery `epicfight:dagger` ≥ 80.");
        add(entries, "A0025",
            "+3% de dano com martelos por rank, máximo +9%, somente para família provider-native HAMMER/heavy. Categoria desconhecida falha fechada: tag, nome, material ou aparência não transformam uma arma em martelo. A mastery `epicfight:heavy` desta disciplina é finita: concede +10 uma única vez por tipo distinto de entidade hostil atingida diretamente com HAMMER/heavy, persistido em DiscoveryProgress; 7 tipos alcançam 70 e 8 tipos alcançam 80.",
            "nível 10 + mastery `epicfight:heavy` ≥ 70 + Gateway `epic_hammer`.");
        add(entries, "A0026",
            "+2% de cadência/velocidade de ataque efetiva com martelos por rank, máximo +6%, exclusivamente por mecanismo server-authoritative do Epic Fight. O bônus não é convertido em stamina, movimento, dano ou velocidade de animação; sem hook estável de cadência, o efeito fica inativo.",
            "Gateway `epic_hammer` acessível + A0025 Treino com Martelos I ≥ 2 ranks.");
        add(entries, "A0027",
            "+3% de chance crítica com martelos por rank, máximo +9%. Aplica-se somente a acertos diretos do jogador classificados provider-native como HAMMER/heavy e usa uma única resolução crítica por ação-raiz.",
            "Gateway `epic_hammer` acessível + A0025 Treino com Martelos I ≥ 1 rank.");
        add(entries, "A0028",
            "Cada acerto direto HAMMER/heavy gera 1 de Abalo vinculado ao alvo, até 3 cargas. Cada carga aumenta a pressão de guarda/postura do próximo golpe de martelo contra o mesmo alvo em +8%/+12% por rank; Abalo expira 6 s após o último ganho e não é transferido entre alvos. Sem receipt seguro de pressão de guarda/postura, o benefício fica inativo: Abalo não é convertido em dano, impacto, knockback, crítico ou Armor Negation. No runtime atual, `guardPressureAvailable=false`.",
            "Gateway `epic_hammer` acessível + A0027 Precisão com Martelos ≥ 2 ranks.");
        add(entries, "A0029",
            "Com 3 de Abalo, o próximo acerto direto HAMMER/heavy inequivocamente confirmado como heavy consome as três cargas. Rank 1/2: +30%/+45% de pressão de guarda/postura e +10%/+15% de impacto. Se o heavy for confirmado mas pressão de guarda não estiver disponível, permanece somente a parcela de impacto. Se a mesma ação causar quebra real e o custo exato de stamina pago for observável, restaura 10% desse custo, com recarga de 8 s; sem receipt do custo, apenas a restauração é omitida. Sem receipt inequívoco de heavy, a perk permanece fail-closed e não infere heavy por animação, dano, arma lenta, impacto, `shouldChargeWeapon` ou carga estimada.",
            "Gateway `epic_hammer` acessível + A0026 Treino com Martelos II ≥ 2 ranks + A0027 Precisão com Martelos ≥ 1 rank.");
        add(entries, "A0030",
            "Uma quebra real de guarda/postura causada pelo jogador com martelo abre Janela de Demolição por 4 s naquele alvo. O próximo heavy direto confirmado contra o mesmo alvo recebe +20% de dano físico elegível e +25% de impacto, fecha a janela e não pode reabri-la no mesmo resultado. Lockout por alvo de 12 s; mastery 90/100 reduz para 11 s/10 s. Sem receipt nativo de guard-break, o capstone fica indisponível; sem receipt inequívoco de heavy, um acerto comum não consome a janela. O runtime atual carece dos dois receipts e permanece fail-closed.",
            "Gateway `epic_hammer` acessível + A0028 Abalo Crescente + A0029 Quebra de Postura + mastery `epicfight:heavy` ≥ 80.");
        add(entries, "A0031",
            "+3% de dano com maças por rank, máximo +9%. A maça vanilla só qualifica por identidade exata `minecraft:mace`; armas externas exigem categoria/capability MACE do Epic Fight ou mapping versionado explícito. Sem classificação MACE segura, a disciplina permanece fail-closed: tag paralela, nome, material, dano ou semelhança com martelo não qualificam. A mastery canônica `combat:mace` concede +10 uma única vez por tipo hostil inédito persistido em DiscoveryProgress; enquanto a classificação e a mastery legadas por hit não forem substituídas, a implementação permanece não confirmada.",
            "nível 8 + mastery `combat:mace` ≥ 60 + Gateway `combat_mace`; 60 corresponde a 6 tipos hostis distintos no modelo anti-farm.");
        add(entries, "A0032",
            "+2% de velocidade/ritmo efetivo com maças por rank, máximo +6%, somente quando o moveset/provider expõe cadência server-authoritative. Sem hook estável de cadência, essa parcela fica inativa e não é convertida em stamina, movimento, dano ou edição de animação. A aplicação depende de classificação MACE segura.",
            "Gateway `combat_mace` acessível + A0031 Treino com Maças I ≥ 2 ranks.");
        add(entries, "A0033",
            "+3% de chance crítica com maças por rank, máximo +9%, somente para acerto direto do jogador com família MACE seguramente classificada. Uma ação-raiz produz no máximo uma resolução crítica canônica; `ARCANE_BACKLASH`, proc terminal/secundário e dano de ally/bodyguard Mobstein são inelegíveis.",
            "Gateway `combat_mace` acessível + A0031 Treino com Maças I ≥ 1 rank.");
        add(entries, "A0034",
            "Acerto direto MACE contra proteção física comprovada gera 1 Trauma, até 3 cargas, com duração de 6 s/8 s conforme o rank. O fallback atualmente confirmado é somente Armor do Minecraft > 0; guarda/postura provider-native ou redução física explícita só qualificam quando houver receipt seguro. Resistências arcanas/mágicas, Corruption Resistance, Arcane Strain, Shroud/Exposure/Madness, STUN_ARMOR/poise e hazards ambientais não qualificam. Dano de companion Mobstein não cria Trauma do dono.",
            "Gateway `combat_mace` acessível + A0033 Precisão com Maças ≥ 2 ranks.");
        add(entries, "A0035",
            "Com 3 de Trauma, o próximo acerto direto MACE confirmado deve consumir as três cargas e aplicar −8%/−12% de `Attributes.ARMOR` por 4 s/6 s; boss recebe metade somente com classificação server-side comprovada. O efeito exige commit pós-hit confirmado: cancelamento ou dano zero não podem consumir Trauma nem marcar Armadura Fendida. O runtime ainda prepara/consome estado no PRE, portanto a implementação completa permanece não confirmada. Não substituir por Armor Negation do atacante, dano extra ou redução mágica/arcana; a atenuação específica de boss Mobstein também permanece não confirmada sem classificação canônica.",
            "Gateway `combat_mace` acessível + A0032 Treino com Maças II ≥ 2 ranks + A0033 Precisão com Maças ≥ 1 rank.");
        add(entries, "A0036",
            "Capstone: contra alvo que já estava sob Armadura Fendida antes da ação atual, um golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s: −8% de dano físico causado e −10% de velocidade de movimento; boss recebe metade. Recarga por alvo de 12 s/11 s/10 s em mastery 80/90/100. Sem heavy receipt provider-native ou sem ponto seguro para aplicar ambos os debuffs, o capstone permanece fail-closed; o mesmo golpe não pode criar Armadura Fendida e satisfazer Quebra-Ossos. Não substituir por stun, dano ou penetração.",
            "A0034 Trauma Contundente + A0035 Armadura Fendida + mastery `combat:mace` ≥ 80; 80 corresponde a 8 tipos hostis distintos no modelo anti-farm.");
        add(entries, "A0037",
            "+3% de dano com foices por rank, máximo +9%. Somente categoria/capability SCYTHE do Epic Fight ou mapping versionado explícito qualificam. Sem classificação SCYTHE segura, a disciplina permanece fail-closed: tag paralela, nome, aparência ou uma enxada vanilla não transformam a arma em foice de combate. A mastery canônica `combat:scythe` concede +10 uma única vez por tipo hostil inédito persistido em DiscoveryProgress; enquanto a classificação e a mastery legadas por hit não forem substituídas, a implementação permanece não confirmada.",
            "nível 8 + mastery `combat:scythe` ≥ 60 + Gateway `combat_scythe`; 60 corresponde a 6 tipos hostis distintos no modelo anti-farm.");
        add(entries, "A0038",
            "+2% de velocidade/ritmo efetivo com foices por rank, máximo +6%, somente quando o moveset/provider expõe cadência server-authoritative pelo Epic Fight. Sem hook estável, a parcela fica inativa e não é convertida em stamina, movimento, dano ou edição de animação. A aplicação depende de classificação SCYTHE segura.",
            "Gateway `combat_scythe` acessível + A0037 Treino com Foices I ≥ 2 ranks.");
        add(entries, "A0039",
            "+3% de chance crítica com foices por rank, máximo +9%, somente para acerto direto do jogador com família SCYTHE seguramente classificada. Uma ação-raiz produz no máximo uma resolução crítica canônica; Backlash terminal/secundário e companion-owned damage são inelegíveis.",
            "Gateway `combat_scythe` acessível + A0037 Treino com Foices I ≥ 1 rank.");
        add(entries, "A0040",
            "O primeiro hit direto SCYTHE aplica Marca da Ceifa por 8 s/10 s; reaplicar renova a mesma marca jogador→alvo. A Marca só amadurece quando o alvo já estava marcado e a vida cruza de ≥50% para <50%. Dano periódico, projétil derivado, proc encadeado, reflexão, companion/summon, fake player e callback duplicado não aplicam nem duplicam a Marca. A classificação SCYTHE continua fail-closed. Aplicação e maturação estão presentes, mas o cleanup bounded em unload/despawn ainda não está confirmado; A0040 é Notable, não terminal.",
            "Gateway `combat_scythe` acessível + A0039 Precisão com Foices ≥ 2 ranks.");
        return Map.copyOf(entries);
    }

    private static void add(Map<String, PlayerText> entries, String code, String effect, String gate) {
        if (entries.putIfAbsent(code, new PlayerText(effect, gate)) != null) {
            throw new IllegalStateException("duplicate player-text code: " + code);
        }
    }
}
