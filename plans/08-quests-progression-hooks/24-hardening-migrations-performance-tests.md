# 08.24 — Narrative Hardening, Migrations, Performance & Tests

## Goal
Fechar persistence, multiplayer, performance e compatibilidade antes de declarar o Narrative & Society Core utilizável em campanha longa.

## Persistence/migrations
- [ ] schema versions por store;
- [ ] forward migration explícita;
- [ ] backup/fail-safe para migration inválida;
- [ ] unknown content IDs preservados/diagnosticados sem crash quando possível;
- [ ] orphan actor/faction/settlement reconciliation;
- [ ] restart/reconnect não resetam consequences/timers/knowledge.

## Performance
- [ ] nenhum global world/entity/population scan por tick;
- [ ] event-driven indexes;
- [ ] scheduler priority queue/indexado;
- [ ] social propagation budget/cadence;
- [ ] bounds de grievance/favor/history caches derivados;
- [ ] profiling com colônia grande e ledger longo;
- [ ] save size/serialization benchmark.

## Unit tests
- IDs/codecs/validation;
- chronology;
- dedup/replay;
- knowledge propagation;
- relationship clamps;
- law/governance resolution;
- consequence scheduling;
- epilogue conflict resolution;
- migrations.

## NeoForge GameTests/integration
- [ ] SIM/NÃO/ANTES/DEPOIS;
- [ ] objective pre-resolved;
- [ ] dead NPC fallback;
- [ ] secret witness propagation;
- [ ] Severin hidden → witness → clergy → trial;
- [ ] two settlement governments produce different route availability;
- [ ] consequence survives restart/reload where harness permits;
- [ ] provider adapter missing/fail-soft;
- [ ] FTB reward idempotency;
- [ ] Easy NPC interaction anti-double-fire;
- [ ] MineColonies adapter smoke quando fixture/provider disponível;
- [ ] KubeJS replay protection.

## Dedicated server/multiplayer
- two players with different knowledge see different choices;
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
- nenhum soft-lock conhecido no acceptance scenario de Severin;
- CI pós-merge confirmada na main.