# Auditoria de implementação — Chat 2 — A0101–A0110

**Data:** 2026-09-02  
**Lote exato:** A0101–A0110  
**Branch:** `docs/chat1-a0101-a0110-audit`  
**PR:** #340  
**Papel:** Chat 2 — implementação. Esta auditoria não declara `IMPLEMENTAÇÃO CONFIRMADA`, CI green nem merge.

## Resultado executivo

| Código | Estado após Chat 2 | Implementação / fail-closed |
|---|---|---|
| A0101 | CÓDIGO PRESENTE | `PROJECTILE + PHYSICAL` no resolver canônico; 2%/rank, uma contribuição por evento/root |
| A0102 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | reducer `IS_MAGIC` existe e fixture Ars foi reconciliada para 5.13.1, mas falta adapter/tag causal comprovado para excluir todos os resource-cost/backlash provider-owned exigidos pelo contrato |
| A0103 | CÓDIGO PRESENTE | tag `rpgskilltree:environmental` materializa exatamente os 7 DamageTypes vanilla aprovados; uma contribuição por resolver |
| A0104 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | crossing/scheduler/cancelamento/cooldown implementados, porém o envelope persistido canônico atual ainda não persiste schedule/cooldown por restart; compra bloqueada até fechar essa seam |
| A0105 | CÓDIGO PRESENTE | 3 hits/80 ticks, ativação 120 ticks, cooldown 400, +15% Armor/+8% Toughness relativos por IDs transitórios estáveis |
| A0106 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | lógica `LivingDamageEvent.Pre`, threshold, 0,65, token e clamp existem; indisponível por A0104 e por persistência canônica do cooldown ainda não fechada |
| A0107 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | A0093 indisponível + P-0035; nenhum impacto→Stamina inventado |
| A0108 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | A0100 indisponível; nenhum tradeoff parcial aplicado |
| A0109 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | A0108 indisponível + nenhum provider real de body encumbrance |
| A0110 | CÓDIGO PRESENTE EM FAIL-CLOSED / `UNAVAILABLE_NODE` | P-0036 sem seam pós-Unbreaking/pré-decremento; nenhum repair/refund/polling/armor-only fallback |

## Infraestrutura implementada

- `DamageMitigationResolver`: combiner multiplicativo ordenado, deduplicação por contributor id, clamp [0,1] e dano nunca negativo.
- `A0101A0110DefenseState`: state machine server-side para A0104/A0105/A0106, bounded por actor/root e com reconciliação de rank.
- `A0101A0110RuntimeState`: effective ranks via `CombatPerkAvailabilityRuntime` e owner do state do lote.
- `A0101A0110DefenseRuntime`: integra A0092/A0096/A0097/A0098/A0099 e A0101/A0102/A0103 no mesmo `LivingDamageEvent.Pre`; A0106 roda depois do resolver.
- `A0081A0100CombatEvents`: continua sendo o único handler registrado e orquestra o runtime novo; não foi criado segundo event pipeline.
- `NotionCombatPerkCatalog`, `CombatPerkDefinition` e `CombatPerkTreeModel`: estendidos exatamente até A0110; A0111 permanece fora.
- `rpgskilltree:environmental`: allowlist exato de A0103.
- fixture Ars Nouveau: `5.13.1` / file id `5z8HqXgT`, alinhada ao contrato auditado.

## Decisões de segurança

### A0102

O reducer genérico existe, mas o contrato exige excluir explicitamente canais provider-owned como `ARCANE_BACKLASH` e `BLOOD_MAGIC_COST`. Sem uma identidade causal/versionada comprovada para todos esses canais no branch, o node permanece indisponível. Não foi usada heurística por namespace, nome de spell ou ausência de atacante.

### A0104 e A0106

As máquinas de estado implementadas preservam cooldown em boundaries do objeto-player durante o mesmo processo, mas `CanonicalPlayerAttachmentData` atual não contém seção persistida para esses estados. Como o contrato proíbe reset exploit/restart inconsistente, ambos ficam indisponíveis até o Chat 3 completar a persistência canônica ou registrar bloqueio técnico se isso exigir redesign de schema além do escopo.

### A0107–A0110

Todos permanecem indisponíveis exatamente pelas razões aprovadas no Chat 1. Nenhum bônus substituto foi criado.

## Pipeline e deduplicação

A ordem implementada é:

1. vanilla/NeoForge antes de `LivingDamageEvent.Pre`;
2. contribuições RPG tipadas/gerais no `DamageMitigationResolver` — A0092/A0096/A0101/A0102/A0103 e A0097/A0098/A0099;
3. A0106 sobre o dano já resolvido, quando um dia estiver disponível;
4. pipeline NeoForge normal, sem prediction de absorption ou resurrection pós-fato.

O resolver deduplica contributor id por chamada. A0097 usa reservation PRE e commit/rollback no POST correlacionado pelo mesmo `DamageSource`+target/root. A0104/A0105 consomem apenas POST positivo associado ao root registrado.

## RED TDD preservado

O RED foi observado anteriormente na PR temporária #397: Core tests falharam porque `DamageMitigationResolver` ainda não existia. A PR temporária foi fechada sem merge; a implementação ocorreu exclusivamente na PR #340.

O Chat 2 não executou a bateria final após a implementação. Os arquivos de teste estruturais permanecem como seam para o Chat 3.

## Pendências obrigatórias do Chat 3

- executar unit tests/contract tests do resolver e catálogo;
- validar compile/build NeoForge 21.1.248 e fixture Ars 5.13.1;
- validar A0101 physical+projectile, magic projectile negativo, unknown modded fail-closed e dedup;
- resolver/provar adapter/tag causal de exclusão provider-owned para A0102 antes de torná-la disponível;
- validar os sete IDs exatos e exclusões A0103, incluindo ausência de classifier por Volcanoes atmosphere/pressure;
- completar/provar persistência canônica de A0104/A0106 antes de disponibilizá-las; validar restart/logout/death/dimension/rank loss/respec/rules reload;
- validar A0105 3/80, 120 ticks, 400 ticks, no refresh, zero-base e modifier uniqueness;
- validar A0106 threshold estrito, ordering, 0,65, token único, clamp 1 HP e exclusions somente após availability fechar;
- provar purchase fail-before-spend/effective rank zero para A0102/A0104/A0106/A0107/A0108/A0109/A0110;
- executar GameTests/integrações pertinentes, JAR verification, dedicated-server smoke e CI.

## Estado de handoff

**CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**, com as perks explicitamente listadas acima em fail-closed onde o contrato integral ainda não está disponível.

Chat 2 não fez merge e não iniciou A0111+.
