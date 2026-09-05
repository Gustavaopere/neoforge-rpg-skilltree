# Auditoria de implementação — Chat 2 — A0101–A0110

**Data de fechamento Chat 2:** 2026-09-05  
**Lote exato:** A0101–A0110  
**Branch:** `docs/chat1-a0101-a0110-audit`  
**PR:** #340  
**Papel:** Chat 2 — implementação. Este documento **não** declara `IMPLEMENTAÇÃO CONFIRMADA`, CI green ou merge.

## Resultado executivo

| Código | Estado após Chat 2 | Implementação / fail-closed |
|---|---|---|
| A0101 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | `PROJECTILE + PHYSICAL` no `DamageMitigationResolver`; 2%/rank, uma contribuição por evento/root |
| A0102 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | `IS_MAGIC` + autoria hostil causal explícita; self/attackerless/technical/resource-cost desconhecido falha fechado; Ars fixture 5.13.1 |
| A0103 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | tag `rpgskilltree:environmental` materializa exatamente os 7 DamageTypes vanilla aprovados |
| A0104 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | crossing estrito, scheduler de 5 pulsos, cancelamento/root, cooldown e persistência canônica anti-restart |
| A0105 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | 3 hits/80 ticks, duração 120, cooldown 400, +15% Armor/+8% Toughness relativos com IDs estáveis e cooldown persistido |
| A0106 | CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO | `LivingDamageEvent.Pre`, ordem após reducers anteriores, threshold, ×0,65, token fatal único, clamp 1 HP e cooldown persistido |
| A0107 | CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO | `UNAVAILABLE_NODE`; A0093/P-0035 continuam blockers; nenhum impacto→Stamina inventado |
| A0108 | CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO | `UNAVAILABLE_NODE` transitivo por A0100; nenhum benefício/penalidade parcial |
| A0109 | CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO | `UNAVAILABLE_NODE`; A0108 + ausência de body-encumbrance provider; nenhum falso provider promovido |
| A0110 | CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO | `UNAVAILABLE_NODE`; P-0036 sem seam pós-Unbreaking/pré-decremento; nenhum repair/refund/polling/armor-only fallback |

## Infraestrutura implementada

- `DamageMitigationResolver`: composição multiplicativa, deduplicação por contributor id, clamp [0,1] e dano nunca negativo.
- `A0101A0110DefenseState`: state machine server-side para A0104/A0105/A0106, bounded por actor/root, com reconciliação de rank e snapshots de cooldown.
- `A0101A0110RuntimeState`: effective ranks via `CombatPerkAvailabilityRuntime`, hidratação dos cooldowns persistidos e owner do estado transitório do lote.
- `A0101A0110DefenseRuntime`: integra A0092/A0096/A0097/A0098/A0099 e A0101/A0102/A0103 no mesmo `LivingDamageEvent.Pre`; A0106 executa depois do resolver.
- `A0081A0100CombatEvents`: permanece o único handler registrado que orquestra o runtime defensivo; não foi criado segundo pipeline de eventos.
- `NotionCombatPerkCatalog`, `CombatPerkDefinition` e `CombatPerkTreeModel`: estendidos exatamente até A0110; A0111 permanece fora.
- `rpgskilltree:environmental`: allowlist exato de A0103.
- fixture Ars Nouveau: reconciliada para `5.13.1`.

## Persistência canônica anti-reset — A0104/A0105/A0106

Durante a implementação foi encontrada uma pendência real: os deadlines existiam apenas na state machine em memória, de modo que shutdown/restart resetaria cooldowns. Isso violaria os contratos de A0104/A0106 e abriria reset exploit.

A correção foi feita sem storage paralelo:

1. criado `CombatPerkCooldownState` com os três deadlines;
2. criado `CombatPerkCooldownStateCodec`;
3. `CanonicalPlayerAttachmentData` passou a carregar a seção de cooldowns no mesmo envelope persistente do jogador;
4. `CanonicalPlayerAttachmentDataCodec` avançou para schema **v2**;
5. `CanonicalPlayerAttachmentMigrations` recebeu migração explícita **v1 → v2**, preservando Core/compatibilidade e iniciando cooldowns antigos em zero;
6. o runtime hidrata deadlines do attachment após reload/restart;
7. cada ativação de A0104/A0105/A0106 persiste imediatamente o snapshot atualizado;
8. schedules, receipts, modifiers e active windows permanecem transitórios/reconciliáveis e não são reexecutados após restart.

Isso preserva um único storage canônico, evita duplicação de efeito e impede reset de cooldown por restart.

## A0102 — boundary conservador

O design exclui `ARCANE_BACKLASH`, `BLOOD_MAGIC_COST` e resource costs. O código disponível não forneceu identidade/tag versionada universal de Black Arcana para esses canais. O Chat 2 não inventou namespace/ID.

O classificador genérico foi endurecido para exigir simultaneamente:

- `DamageSource.is(Tags.DamageTypes.IS_MAGIC)`;
- atacante causal `LivingEntity` explícito;
- atacante diferente do jogador e não aliado;
- fonte não `IS_TECHNICAL`.

Consequência: magia hostil causal entra; self damage, attackerless roots, custos próprios/Backlash sem atacante causal e fontes desconhecidas falham fechado. Adapters futuros só poderão ampliar isso por identidade causal/versionada explícita.

## Pipeline e deduplicação

Ordem implementada:

1. vanilla/NeoForge antes de `LivingDamageEvent.Pre`;
2. contribuições RPG gerais/tipadas em `DamageMitigationResolver` — A0092/A0096/A0101/A0102/A0103 e A0097/A0098/A0099;
3. A0106 sobre o dano já resolvido;
4. pipeline NeoForge normal, sem prever absorption futuro nem ressuscitar em Post.

O resolver deduplica contributor id por chamada. A0097 usa reservation PRE e commit/rollback correlacionado; A0104/A0105 consomem somente Post confirmado com root; os cooldowns persistidos não são uma segunda máquina de efeito.

## A0107–A0110 — fail-closed aprovado

- **A0107:** indisponível por A0093 + P-0035 não canônico. Nenhuma taxa universal impact→Stamina.
- **A0108:** indisponível por A0100. Não há reducer físico ou movement penalty parcial.
- **A0109:** indisponível por A0108 + ausência de provider real de encumbrance corporal. Weight/Create/Sable/inventário/Armor/Protection Pixel não são promovidos.
- **A0110:** indisponível por P-0036. `damageItem`, repair/refund, polling e `ArmorHurtEvent` não são usados como seam falso.

Persisted ranks de nodes indisponíveis permanecem recuperáveis no storage, mas `CombatPerkAvailabilityRuntime.effectiveRanks(...)` os mascara para rank 0 no gameplay; purchase é rejeitada antes da mutação.

## RED TDD preservado

A PR temporária #397 havia observado RED porque `DamageMitigationResolver` ainda não existia. Ela foi fechada sem merge. A implementação foi feita exclusivamente na PR operacional #340.

O Chat 2 **não executou a bateria final depois da implementação**. Os testes/contract seams existentes ficam para o Chat 3 e não são evidência de `IMPLEMENTAÇÃO CONFIRMADA` neste documento.

## Pendências obrigatórias para o Chat 3

- revisar código vs. 10 dossiês;
- executar unit tests/contract tests do resolver, catálogo e migration v1→v2;
- validar compilação/build NeoForge `21.1.248` e fixture Ars `5.13.1`;
- A0101: physical+projectile positivo, magical projectile negativo, unknown modded fail-closed, dedup/composição;
- A0102: hostile magic positivo; self/attackerless/technical/resource-cost/Backlash fail-closed; provider/version boundaries;
- A0103: sete IDs exatos, exclusões e ausência de classifier implícito por Volcanoes atmosphere/pressure;
- A0104: crossing estrito, cinco pulsos, cancelamento one/root, rearme, restart/logout/death/dimension/respec/rules reload e persistência de cooldown;
- A0105: 3/80, duração 120, cooldown 400, no refresh, zero-base, modifier uniqueness e persistência de cooldown;
- A0106: threshold estrito, ordering, ×0,65, token único, clamp 1 HP, exclusions e persistência de cooldown;
- A0107–A0110: purchase fail-before-spend, effective rank zero e ausência dos fallbacks proibidos;
- GameTests/integrações pertinentes, JAR verification, dedicated-server smoke e CI.

## Retorno ao Chat 1

**Nenhum ponto deste lote exige redesign neste estado.** As limitações A0107–A0110 são o fail-closed já aprovado. Se o Chat 3 descobrir que a API real contradiz identidade, efeito, provider, gate, topologia ou authority, deverá então devolver especificamente o ponto afetado ao Chat 1.

## Estado de handoff

**A0101–A0110 — CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3.**

Chat 2 não declarou `IMPLEMENTAÇÃO CONFIRMADA`, não exigiu CI green, não fez merge e não iniciou A0111+.
