# Status dos Dossiês de Perks

A fonte canônica de design permanece o Notion. O protocolo do Chat 1 trabalha em **lotes exatos de 10 perks** e somente formaliza `LOTE FECHADO` após dossiês/auditoria/status, PR verde, merge e confirmação da `main`.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0002 | Treino com Espadas II | APROVADO/FECHADO Chat 1 V3 | Presente | nenhuma |
| A0003 | Precisão com Espadas | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO/FECHADO Chat 1 V3 | Presente; hit, dodge, miss, decay e stagger forte provider-native | nenhuma |
| A0005 | Abertura de Guarda | APROVADO/FECHADO Chat 1 V3 | Presente; defesa nativa ou fallback estrito de penetração | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO/FECHADO Chat 1 V3 | Presente; defesa técnica confirmada, janela, cooldown e dedup | nenhuma |
| A0007 | Treino com Machados I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0008 | Treino com Machados II | APROVADO/FECHADO Chat 1 V3 | Presente | nenhuma |
| A0009 | Precisão com Machados | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO/FECHADO Chat 1 V3 | Presente no receipt server-authoritative; demais rotas fail-closed | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO/FECHADO Chat 1 V3 | Presente; sem heurística de “alvo pesado” | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO/FECHADO Chat 1 V3 | Transação PRE CORE→exhaustion→benefício; falha = fail-closed | nenhuma |
| A0013 | Treino com Lanças I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO/FECHADO Chat 1 V3 | Presente via `ModifyAttackSpeedEvent` | nenhuma |
| A0015 | Precisão com Lanças | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0016 | Distância Ideal | APROVADO/FECHADO Chat 1 V3 | Presente; reach, hit, miss, expiração e stagger provider-native | nenhuma |
| A0017 | Interceptação | APROVADO/FECHADO EM FALLBACK Chat 1 V3 | Janela + impacto/pressão ativos; redução de deslocamento omitida | nenhuma bloqueante; P-A0017-01 aberta para componente opcional |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO/FECHADO Chat 1 V3 | Presente; crossing, consumo, janela e lockout por alvo | nenhuma |
| A0019 | Treino com Adagas I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO/FECHADO Chat 1 V3 | Presente via `ModifyAttackSpeedEvent` | nenhuma |

## Regras sistêmicas consolidadas

- **Provider-native first:** famílias `sword/axe/spear/dagger` vêm de capabilities do Epic Fight; ausência de classificação segura = fail-closed.
- **Crítico:** uma única resolução canônica/root action; providers de atributos ou bridges não criam segunda rolagem.
- **Stagger forte:** Epic Fight `ON_STUNNED`; apenas `LONG`, `KNOCKDOWN` e `NEUTRALIZE` com fonte hostil.
- **A0012:** Cold Sweat 2.4.2 `CORE` é pré-condição causal; exhaustion e benefícios só vêm depois do sucesso da escrita real. Thirst Was Reclaimed não participa.
- **Mastery Epic Fight:** milestones persistentes por tipo hostil/skill; dano repetido, fake player, AFK e spam não geram Mastery.
- **A0017:** aproximação geométrica pode abrir janela, mas redução de deslocamento exige receipt ofensivo provider-native. Sem ele, o componente fica omitido.
- **NeoVitae:** não existe como provider ativo; busca em `src/` retorna zero ocorrências.

## Evidência comum

- `NotionCombatPerkRules` — coeficientes, thresholds e durações.
- `CombatPerkTreeModel` — gates, mastery e topologia de aquisição.
- `A0001A0020CombatPolicy` — política provider-independent, custos e deduplicação.
- `NotionCombatPerkState` — Ímpeto, Fúria, Controle de Distância, janelas e lockouts.
- `A0001A0020CriticalService` — resolução crítica única.
- `A0001A0020EpicFightHooks` — PRE/POST, attack speed, dodge, miss, stagger, reach e tick server-side.
- `ColdSweatFrenzyBridge` — Cold Sweat 2.4.2 `Temperature.Trait.CORE`, fail-closed.
- `EpicFightProgressionHooks` + `MasteryPolicies` — Mastery por milestones persistentes e atribuíveis.
- `A0001A0020CombatPolicyTest` — regressões dos contratos.

## Chat 1 V3 — A0001–A0010

**Estado operacional:** `LOTE FECHADO`.

- 10/10 re-fetch Notion PASS; 0 mutações adicionais necessárias.
- PR **#217** mergeada com CI verde.
- `main` pós-merge confirmada em `fc6686725369cd703169ca59bde69a3a0ee80dc3` antes do início do ciclo seguinte.
- A0011+ não fez parte daquele ciclo.

## Chat 1 V3 — ciclo exato A0011–A0020

**Estado de design:** `LOTE FECHADO NO DESIGN` — a conclusão operacional depende do PR/CI/merge deste closeout.

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **A0021+:** fora do escopo; não iniciado neste ciclo.
- **Re-fetch Notion:** A0011–A0020 = 10/10 PASS em 2026-08-30.
- **Correções adicionais no Notion:** 0; não houve drift nem contrato contraditório.
- **Dossiês V3:** 10/10 atualizados com providers/versionamento, hooks, gates, fallback/fail-closed, deduplicação, integração global e testes.
- **Auditoria exata:** `AUDITORIA-A0011-A0020-V3.md`.
- **Nove eixos:** 10/10 PASS; A0012 mantém fail-closed causal e A0017 fallback legítimo.
- **18 critérios técnicos:** PASS/N/A justificado; nenhum bloqueio de design.
- **Provider principal:** Epic Fight `21.17.3.1`; A0012 também usa Cold Sweat `2.4.2` e exhaustion Minecraft/NeoForge.
- **Cobertura periférica:** Weapons of Miracles/Epic Fight Compat só entram via capability do Epic Fight; Protection Pixel foi rechecado e não fornece hook pertinente; Weight 1.2.0 não é encumbrance do jogador; stacks mágicos não devem ser conectados artificialmente.
- **P-A0017-01:** aberta e não bloqueante — redução de deslocamento ofensivo só poderá ser ativada após receipt provider-native real e retorno ao Chat 1.
- **Pendência para Chat 2:** preservar rigorosamente provider-native first, pipeline crítico único, causalidade de A0012 e o fail-closed de A0017; não redesenhar o lote.

Após a PR deste closeout ficar verde, ser mergeada e a `main` pós-merge ser confirmada, este ciclo passa de `LOTE FECHADO NO DESIGN` para **`LOTE FECHADO`**. O Chat 1 deve então **PARAR em A0020**.