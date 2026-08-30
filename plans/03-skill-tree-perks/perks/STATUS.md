# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0020** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. Este índice separa design, código presente e confirmação de implementação. `IMPLEMENTAÇÃO CONFIRMADA` só é usada depois de PR mergeada na `main` com CI verde e `main` pós-merge confirmada.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO após reauditoria | IMPLEMENTAÇÃO CONFIRMADA — PR #221 mergeada; provider-native/fail-closed | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; `ModifyAttackSpeedEvent` provider-native | nenhuma |
| A0003 | Precisão com Espadas | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; crítico no pipeline canônico único | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; hit/dodge/miss/decay/stagger provider-native | nenhuma |
| A0005 | Abertura de Guarda | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA — PR #221; defesa nativa + fallback penetração-only | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; receipt técnico/janela/cooldown/consumo/dedup | nenhuma |
| A0007 | Treino com Machados I | APROVADO após reauditoria | IMPLEMENTAÇÃO CONFIRMADA — PR #221; classificação provider-native/fail-closed | nenhuma |
| A0008 | Treino com Machados II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; `ModifyAttackSpeedEvent` | nenhuma |
| A0009 | Precisão com Machados | APROVADO | IMPLEMENTAÇÃO CONFIRMADA — PR #221; crítico no pipeline canônico único | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO após reauditoria | IMPLEMENTAÇÃO CONFIRMADA — PR #221; receipt server-authoritative + dedup | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO após correção | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; guarda nativa + fallback físico estrito | nenhuma; confirmação após merge |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO após correção + re-fetch | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; Cold Sweat exato 2.4.2 + diagnóstico bounded + transação PRE | nenhuma; confirmação após merge |
| A0013 | Treino com Lanças I | APROVADO após reauditoria | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; SPEAR provider-native/fail-closed | nenhuma; confirmação após merge |
| A0014 | Treino com Lanças II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; `ModifyAttackSpeedEvent` | nenhuma; confirmação após merge |
| A0015 | Precisão com Lanças | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; crítico no pipeline canônico único | nenhuma; confirmação após merge |
| A0016 | Distância Ideal | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; alcance/hit/miss/stagger provider-native | nenhuma; confirmação após merge |
| A0017 | Interceptação | APROVADO / FALLBACK CANÔNICO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; janela + impacto/pressão | nenhuma bloqueante; P-A0017-01 permanece fail-closed para deslocamento ofensivo |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; crossing/consumo/janela/lockout | nenhuma; confirmação após merge |
| A0019 | Treino com Adagas I | APROVADO após reauditoria | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; DAGGER provider-native/fail-closed | nenhuma; confirmação após merge |
| A0020 | Treino com Adagas II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI — PR #224; `ModifyAttackSpeedEvent` | nenhuma; confirmação após merge |

## Correções sistêmicas preservadas

- **Provider-native first:** famílias de arma vêm do Epic Fight; ausência de classificação segura é `FAIL-CLOSED`.
- **Pipeline crítico único:** A0003/A0009/A0015 compartilham a resolução canônica e deduplicação por root action.
- **Stagger forte:** somente `LONG`, `KNOCKDOWN` e `NEUTRALIZE` hostis qualificam os contratos pertinentes.
- **A0005/A0011:** quando guarda/postura não é observável, somente defesa física server-side comprovável autoriza fallback de penetração-only.
- **A0010:** tentativa/animação/heurística não geram Fúria.
- **A0012:** `CORE` é pré-condição no mesmo `DELIVER_DAMAGE_PRE`; exhaustion/benefício/pico só vêm após sucesso da escrita.
- **Mastery Epic Fight:** geração futura permanece baseada em milestones persistentes e não em spam de hits repetidos.

## Evidência comum

- `NotionCombatPerkRules` — coeficientes, thresholds e durações.
- `A0001A0020CombatPolicy` — política provider-independent, gastos, fallback e deduplicação.
- `NotionCombatPerkState` — Ímpeto, Fúria, Controle de Distância, janelas, lockouts e Queda de Ritmo.
- `A0001A0020CriticalService` — resolução crítica única.
- `A0001A0020EpicFightHooks` — PRE/POST, attack speed, dodge, miss, stagger, alcance e tick server-side.
- `ColdSweatFrenzyBridge` — A0012/Cold Sweat 2.4.2 `Temperature.Trait.CORE`.
- `A0001A0020CombatPolicyTest`, `A0011A0020ImplementationContractJUnitTest` e `ColdSweatFrenzyBridgeTest` — regressões de implementação.

## Chat 2 — A0001–A0010 — fechamento confirmado

- **PR:** #221.
- **Merge:** `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`.
- **Resultado:** A0001–A0010 = `IMPLEMENTAÇÃO CONFIRMADA`.
- **CI final do ciclo:** GREEN, incluindo JUnit, GameTests, build, JAR e dedicated-server smoke.

## Chat 1 V3 — A0011–A0020 — fonte de design usada pelo Chat 2

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **Design:** 10/10 APROVADAS / FECHADAS.
- **Evidência V3 mais nova:** PR #219 (`audit/a0011-a0020-v3-closeout`), ainda aberta por concorrência da `main` durante este ciclo.
- **Pendências entregues ao Chat 2:** P-A0012-01, P-A0012-02 e P-A0017-01.
- **Regra:** as duas pendências de A0012 eram bloqueantes para confirmação; P-A0017-01 é fallback aprovado e não autoriza heurística.

## Chat 2 — implementação, testes e merge — A0011–A0020

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **PR de trabalho:** #224 — draft original, fechado sem merge exclusivamente porque o conector falhou ao executar `markPullRequestReadyForReview`.
- **PR sucessora de merge:** #226 — mesmo branch e mesmo conteúdo do #224, aberta não-draft para contornar o erro administrativo do conector.
- **P-A0012-01:** RESOLVIDA — Cold Sweat aceita somente `2.4.2` exato; RED CI #2028.
- **P-A0012-02:** RESOLVIDA — diagnóstico bounded one-shot para versão/API/invocação + probe no bootstrap; RED CI #2033.
- **P-A0017-01:** ABERTA / NÃO BLOQUEANTE — redução de deslocamento omitida; fallback aprovado janela + impacto/pressão permanece ativo.
- **Regressões novas:** `ColdSweatFrenzyBridgeTest` e `A0011A0020ImplementationContractJUnitTest`.
- **Validação de runtime/testes:** `RPG Skill Tree CI` #2036 GREEN no HEAD `bda08ca9748ad16d3352d0872f753976731424f8`; todos os 9 workflows associados ficaram GREEN, incluindo JUnit, NeoForge GameTests, build, built-JAR verification e dedicated-server smoke.
- **Validação documental do #224:** `RPG Skill Tree CI` #2052 GREEN no HEAD `a6ae8b954c2af23738938700f8232154f2374c56`.
- **Estado pré-merge:** A0011–A0020 = `IMPLEMENTAÇÃO VALIDADA EM CI`; A0017 explicitamente em fallback canônico.
- **Estado definitivo:** somente após CI verde do HEAD final da sucessora #226, review limpo, merge da #226 e confirmação da `main` pós-merge.

### Nota administrativa #224 → #226

As seções dos dez dossiês que dizem “torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #224” foram escritas antes de o bug do conector `markPullRequestReadyForReview` ser confirmado. O #224 foi fechado **sem merge e sem mudança de branch/HEAD**; o #226 é seu sucessor operacional não-draft. Para este lote, essas referências são formalmente supersedidas por: **confirmação após merge da PR #226**. Isso não altera design, runtime, testes nem o status de P-A0017-01.

O próximo lote A0021–A0030 **não deve ser iniciado neste ciclo**.
