# Auditoria retroativa de integração — A0011–A0020 — projetos próprios + Mobstein 5.4.4

## Escopo do ciclo

- **INÍCIO:** A0011
- **FIM:** A0020
- **Quantidade:** 10 perks consecutivas.
- **Natureza:** auditoria retroativa de integração/design; nenhum runtime foi alterado neste Chat 1.
- **Providers auditados exclusivamente:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4.
- **Fora de escopo:** reauditoria geral dos demais mods e qualquer perk A0021+.

## Fontes obrigatórias usadas

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` — 9 eixos + 18 critérios técnicos.
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`.
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`.
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`.
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.
- Catálogo Mestre no Notion, com fetch fresco individual de A0011–A0020.
- Dossiês A0011–A0020 no repositório.
- `main` e `plans/STATUS.md` frescos dos quatro projetos próprios.

## Gate de delta de capacidades — projetos próprios

### RPG Skill Tree

- **Main auditada:** `710dfae72aaec30bd906ea89876f81dd396d10c5`.
- **Disposição:** `SEM DELTA MARTIAL RELEVANTE` para A0011–A0020.
- A infraestrutura canônica de Skill Tree, World Scaling, progressão e os serviços do bloco A0001–A0020 continuam sendo as authorities pertinentes.
- Stages 11 (itemização), 12 (corpos/clones) e 13 (cartografia) permanecem planejamento e não foram promovidos a hook.
- Eventual relação entre Stage 12 Bodies e Mobstein continua **SEM HOOK SEGURO** até existir boundary runtime canônico.

### Volcanoes

- **Main fresca:** `7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`.
- O delta fechou/ativou a coexistência segura de prospecção hidrotermal RNS para identidades fisicamente produzidas, mantendo as protections/ownership boundaries documentadas pelo projeto.
- **Classificação provider→árvore:** `PERK PRÓPRIA / CICLO FUTURO DE GEOLOGIA-PROSPECÇÃO`, fora deste lote MARTIAL.
- Esta capacidade não justifica integração com A0011–A0020.
- Para A0012 existe uma composição legítima já canônica: fontes de calor do Volcanoes podem alimentar o mesmo Cold Sweat 2.4.2. A0012 não chama Volcanoes, não lê Atmosphere/gases/pressão e não reaplica calor ambiental; o custo +1,5 CORE da perk permanece uma mutação causal própria no Cold Sweat.
- O `plans/STATUS.md` do Volcanoes consultado estava atrás do commit de coexistência mais recente; a evidência da `main` mais nova prevalece para a disposição deste delta.

### Enshrouded

- **Main fresca:** `f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`.
- O avanço relevante é o fechamento documental/canônico de `06.01 Story State`, já contemplado pelo snapshot consolidado.
- **Classificação:** `NÃO DEVE SER INTEGRADO` às perks A0011–A0020.
- Shroud, Exposure, Madness, Flame, Sanctuary/Story e MagicResistanceService não são Fúria, guarda física, CORE, stamina, critical receipt, alcance, crossing ou autoria marcial.

### Black Arcana

- **Main fresca:** `73c14ce55ff918bb8a81daeb99a352607ef11064`.
- O delta desde o baseline é principalmente reconciliação documental/proveniência do estado já implementado de Stage 05A; nenhuma nova capacidade MARTIAL pertinente foi detectada além das boundaries Arcane Danger já conhecidas.
- **Classificação:** `BOUNDARY / NÃO DEVE SER PROCESSADO COMO AÇÃO MARTIAL DIRETA`.
- `ARCANE_BACKLASH` é terminal: não pode critar, procar, gerar Mastery, Fúria, Controle de Distância, abrir/consumir janelas de Interceptação nem ser reclassificado como hit do jogador.
- Arcane Resistance, Corruption Resistance e Arcane Strain não são guarda/postura/defesa física nem custos corporais de A0011/A0012.

## Delta externo — Mobstein 5.4.4

- Ressurreição corporal, corpos/órgãos, experimentos, allies/bodyguards, estruturas e boss permanecem authority do Mobstein.
- `Attack/Health/Speed/Template perks` internas são `PROGRESSÃO NATIVA AUTORITATIVA`, não nodes do RPG Skill Tree.
- Ataques diretos do jogador contra mobs/bosses Mobstein são `COBERTO POR SISTEMA UNIVERSAL`: as perks marciais funcionam normalmente se o receipt Epic Fight direto e provider-native for satisfeito.
- Ataques de allies/bodyguards ressuscitados são **Mobstein-owned** e não herdam autoria marcial do dono para Fúria, crítico, Controle de Distância, Interceptação ou Mastery.
- Relação futura entre corpses/bodies Mobstein e Stage 12 do RPG Skill Tree permanece `SEM HOOK SEGURO`.
- Tecnologia: `NÃO APLICÁVEL` neste lote.

## Matriz perk → provider / provider → árvore

| Perk | RPG Skill Tree | Volcanoes | Enshrouded | Black Arcana | Mobstein 5.4.4 | Resultado |
|---|---|---|---|---|---|---|
| A0011 | Fúria/dedup authority | N/A | estados não são defesa física | Arcane Danger não é guarda | companion não gasta Fúria do dono | APROVADA + boundary |
| A0012 | Fúria/Frenesi/dedup authority | calor compõe apenas via Cold Sweat; sem bridge direta | Exposure/Madness não são CORE | Strain/Backlash não são CORE | sem custo corporal/ownership alternativo | APROVADA + clarificação térmica |
| A0013 | dano MARTIAL direto | N/A | N/A | N/A | alvo direto coberto; companion sem autoria | APROVADA |
| A0014 | rank consumer; cadência Epic Fight | N/A | N/A | N/A | sem cadência herdada | APROVADA |
| A0015 | crítico canônico único | N/A | N/A | Backlash terminal inelegível | companion sem crítico do dono | APROVADA + boundary |
| A0016 | Controle de Distância authority | N/A | N/A | Backlash não gera carga | companion não gera carga | APROVADA + boundary |
| A0017 | janela/consumo MARTIAL | sem receipt ofensivo | sem receipt ofensivo | Backlash não aciona | companion não aciona | APROVADA em fallback |
| A0018 | janela/lockout authority | N/A | N/A | Backlash não abre/consome | companion não abre/consome | APROVADA + boundary |
| A0019 | dano MARTIAL direto | N/A | N/A | N/A | alvo direto coberto; companion sem autoria | APROVADA |
| A0020 | rank consumer; cadência Epic Fight | N/A | N/A | N/A | sem cadência herdada | APROVADA |

## Alterações no Notion

Fetch fresco foi realizado para as dez páginas A0011–A0020.

### Páginas alteradas

- **A0011:** `Hook`, `Fallback`, `Regra` — defesa física/guarda separada de Arcane Danger/Shroud; companions Mobstein sem gasto de Fúria do dono.
- **A0012:** `Hook`, `Fallback`, `Regra` — composição Volcanoes→Cold Sweat sem double-charge; separação explícita de Shroud/Exposure/Madness/Strain/Backlash/Mobstein.
- **A0015:** `Hook`, `Fallback`, `Regra` — crítico somente para ação direta/root action do jogador; Backlash/companions inelegíveis.
- **A0016:** `Hook`, `Fallback`, `Regra` — Controle de Distância somente por hit direto do jogador; Backlash/companions inelegíveis.
- **A0017:** `Hook`, `Fallback`, `Regra` — nenhum provider retroauditado preenche o receipt ofensivo faltante; Backlash/companions não acionam/consomem.
- **A0018:** `Hook`, `Fallback`, `Regra` — janela/crossing/consumo exigem causalidade marcial direta; Backlash/companions inelegíveis.

**Re-fetch pós-escrita:** 6/6 PASS em 2026-08-30.

### Páginas sem mutação

- A0013, A0014, A0019 e A0020.
- Motivo: o contrato já exige família/cadência provider-native e ação direta do jogador; adicionar nomes dos providers seria alteração cosmética sem ganho de implementabilidade.

## A0017 — fallback preservado

`P-A0017-01` permanece correta e não bloqueia o design aprovado:

- janela + impacto/pressão operam pelo caminho seguro;
- redução de deslocamento ofensivo continua omitida;
- nenhum estado de Volcanoes, Enshrouded, Black Arcana ou Mobstein substitui receipt nativo de corrida/investida do Epic Fight;
- é proibido usar `deltaMovement`, velocidade vanilla ou associação temática como autorização de reescrita.

## A0012 — composição ambiental correta

A capacidade canônica de Volcanoes→Cold Sweat **complementa o contexto corporal**, mas não cria uma bridge direta A0012↔Volcanoes:

1. Volcanoes pode produzir calor ambiental e entregá-lo ao Cold Sweat pelo seu adapter próprio.
2. Cold Sweat permanece owner da temperatura corporal.
3. A0012 adiciona +1,5 CORE como custo próprio da ação elegível.
4. A0012 não reprocessa a contribuição ambiental do Volcanoes.
5. Falha do bridge CORE da própria perk continua fail-closed e não é substituída por leitura de Volcanoes.

## Nove eixos obrigatórios

| Critério | Estado do lote |
|---|---|
| 1. Dependências e bloqueios | ✅ PASS |
| 2. Integrações globais/modlist/corpo/recursos | ✅ PASS |
| 3. Qualidade/identidade | ✅ PASS |
| 4. Ramificação/distância/topologia | ✅ PASS |
| 5. Especializações | ✅ PASS |
| 6. PT-BR | ✅ PASS |
| 7. Notion completo + re-fetch | ✅ PASS |
| 8. NeoVitae removido | ✅ PASS |
| 9. Cobertura provider→árvore | ✅ PASS |

## 18 critérios técnicos

Os 18 critérios foram reaplicados com foco nas cinco fontes retroauditadas. Resultado geral: **PASS ou N/A justificado**.

Pontos de maior risco explicitamente fechados:

- provider-native first preservado;
- nenhuma mecânica de Volcanoes/Enshrouded/Black Arcana/Mobstein foi inventada;
- planned RPG Stage 12 não foi promovido;
- uma ação mantém uma única autoria/root action;
- crítico, Fúria, Controle de Distância e Mastery não recebem double-processing;
- companion-owned damage não vira player-authored damage;
- A0012 não cria segunda temperatura nem double-charge de calor ambiental;
- A0017 mantém fail-closed onde o provider não expõe receipt ofensivo seguro.

## Entrega ao Chat 2

O Chat 2 não deve redesenhar essas perks. Deve apenas revalidar a implementação existente contra os boundaries novos:

- A0011: Arcane/Shroud states não podem qualificar defesa física; companions não gastam Fúria do dono.
- A0012: preservar `CORE → exhaustion → benefício/pico`; não reaplicar calor Volcanoes; companions/Backlash sem autoria.
- A0015/A0016/A0017/A0018: Backlash e companions não podem entrar como ação direta/root action do jogador.
- A0017: manter redução de deslocamento omitida até existir receipt provider-native seguro.

Se o runtime atual já satisfizer essas regras pela provenance/dedup existentes, não alterar gameplay: adicionar ou reforçar apenas testes de regressão. Se algum caminho atravessar essas boundaries, corrigir fail-closed no Chat 2.

## Fechamento

**A0011–A0020 — LOTE RETROATIVO APROVADO/FECHADO NO DESIGN.**

Nenhuma perk A0021+ foi auditada ou alterada neste ciclo.
