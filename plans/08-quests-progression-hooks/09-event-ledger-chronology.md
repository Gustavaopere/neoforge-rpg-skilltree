# 08.09 — Event Ledger & Chronology

## Goal
Implementar histórico narrativo persistente, consultável e idempotente, capaz de responder ANTES/DEPOIS sem registrar spam contínuo.

## Entregas
- [ ] `NarrativeEventId` namespaced e estável.
- [ ] `NarrativeEventRecord` com logical time, actor, targets, scope, location/provider metadata e payload limitado.
- [ ] Índices para first/last/count e por actor/scope/event type.
- [ ] Queries `BEFORE`, `AFTER`, `NEVER`, `EVER`, `FIRST`, `LAST`.
- [ ] Dedup/replay key persistente.
- [ ] Coalescing de eventos repetitivos.
- [ ] Retention policy para eventos volumosos sem apagar milestones históricos.
- [ ] Migration/versioning do ledger.
- [ ] API read-only para quests/adapters.
- [ ] Emissão pós-commit, nunca antes de persistence confirmada.

## Anti-abuso/performance
- sem evento por tick de temperatura, Shroud, stamina, máquina ou exposição;
- transformar estados contínuos em transitions/milestones;
- queries bounded/indexadas;
- nenhum scan integral do ledger no hot path.

## Acceptance
1. evento A antes de B é consultado corretamente após save/restart;
2. replay da mesma key não duplica;
3. objetivo realizado antes da descoberta da quest é reconhecido como `PRE_RESOLVED` quando a definição permitir.