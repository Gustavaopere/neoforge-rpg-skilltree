# 08.24 — Narrative Hardening, Migrations, Performance & Tests

## Goal
Fechar persistence, multiplayer, performance e compatibilidade antes de declarar o Narrative & Society Core utilizável em campanha longa.

## Persistence/migrations
- [ ] schema versions por store;
- [ ] forward migration explícita;
- [ ] backup/fail-safe para migration inválida;
- [ ] unknown content IDs preservados/diagnosticados sem crash quando possível;
- [ ] orphan actor/faction/settlement reconciliation;
- [ ] restart/reconnect não resetam consequences/timers/knowledge;
- [ ] opportunity/discovery/engagement/resolution sobrevivem a restart sem colapsar em um status único;
- [ ] death/return/identity-continuity records preservam histórico e provider origin.

## Performance
- [ ] nenhum global world/entity/population scan por tick;
- [ ] event-driven indexes;
- [ ] scheduler priority queue/indexado;
- [ ] social propagation budget/cadence;
- [ ] opportunity lifecycle atualizado por eventos/índices, nunca varrendo todas as quests por tick;
- [ ] bounds de grievance/favor/history caches derivados;
- [ ] profiling com colônia grande e ledger longo;
- [ ] save size/serialization benchmark.

## Unit tests
- IDs/codecs/validation;
- chronology;
- dedup/replay;
- knowledge propagation;
- opportunity/discovery state combinations;
- hidden-content visibility;
- autonomous resolution/invalidation;
- relationship clamps;
- death/return chronology;
- identity continuity reconciliation;
- law/governance resolution;
- consequence scheduling;
- epilogue conflict resolution;
- migrations.

## NeoForge GameTests/integration
- [ ] SIM/NÃO/ANTES/DEPOIS;
- [ ] objective pre-resolved;
- [ ] dead NPC fallback;
- [ ] secret witness propagation;

A sequência abaixo usa uma fixture genérica de pesquisador de necromancia, com identidade/IDs reservados de teste e sem binding com qualquer NPC canônico:
- [ ] pesquisador oculto → testemunha → clero → julgamento;
- [ ] oportunidade do pesquisador nunca desbloqueada;
- [ ] pesquisador `ELIGIBLE + UNKNOWN` nunca aparece no journal;
- [ ] oportunidade oferecida + recusada difere de nunca oferecida;
- [ ] oportunidade aceita + ignorada difere de abandono explícito;
- [ ] pesquisador investigado mas não encontrado preserva discovery/evidence parcial;
- [ ] pesquisador encontrado antes da quest reconcilia via alternate entry;
- [ ] situação resolvida por terceiros enquanto ainda `UNKNOWN`;
- [ ] player descobre apenas consequência posterior sem retroactive quest leak;
- [ ] Mobstein death → return preserves both ledger events;
- [ ] Mobstein returned NPC with partial memory does not satisfy memory-gated quest;
- [ ] Mobstein returned NPC may satisfy body/alive gate while legal/social identity remains disputed;
- [ ] repeated death/return does not duplicate reward, milestone or quest completion;
- [ ] two settlement governments produce different route availability;
- [ ] consequence survives restart/reload where harness permits;
- [ ] provider adapter missing/fail-soft;
- [ ] FTB reward idempotency;
- [ ] Easy NPC interaction anti-double-fire;
- [ ] MineColonies adapter smoke quando fixture/provider disponível;
- [ ] KubeJS replay protection.

## Dedicated server/multiplayer
- two players with different knowledge/discovery see different choices e journal entries;
- um jogador pode conhecer uma oportunidade enquanto outro permanece `UNKNOWN`;
- presenciar ressurreição não concede knowledge automático a jogadores ausentes;
- personal choice cannot mutate settlement/world scope without authority;
- team quest completion does not duplicate world reward;
- reconnect retains state;
- no client-only classloading on server.

## Compatibility matrix
Perfil completo:
- Easy NPC;
- FTB Quests;
- MineColonies;
- KubeJS.

Testar também jar base sem todos eles.

## Definition of Done
- Core/JUnit GREEN;
- NeoForge GameTests GREEN;
- validators GREEN;
- build/JAR verification GREEN;
- dedicated-server smoke GREEN;
- representative save migration test GREEN;
- documentation/authoring examples reconciliados;
- nenhum soft-lock conhecido no acceptance scenario genérico de pesquisador de necromancia;
- nenhuma exposição conhecida de conteúdo `UNKNOWN` ao player;
- retorno Mobstein não é tratado como restauração integral sem contrato/evidência;
- CI pós-merge confirmada na main.
