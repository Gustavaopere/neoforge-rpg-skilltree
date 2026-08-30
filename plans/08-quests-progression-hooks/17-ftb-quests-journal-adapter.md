# 08.17 — FTB Quests Journal Adapter

## Goal
Usar FTB Quests como diário/apresentação do estado narrativo, não como storage canônico da campanha.

## Baseline
Versão pública NeoForge 1.21.1 verificada em 2026-08-30: `2101.1.34`.

## Entregas
- [ ] Optional adapter e classloading seguro.
- [ ] Mapear Narrative beat/arc state para capítulos/tasks/visibility quando a API real permitir.
- [ ] Conditions de RPG/Narrative consultam APIs server-authoritative.
- [ ] Rewards de FTB chamam mutation/reward services idempotentes.
- [ ] Journal pode mostrar missão `PRE_RESOLVED`, `TRANSFORMED`, `FAILED/PRODUCTIVE_FAILURE` etc. sem reescrever história.
- [ ] Quest completada no FTB não autoriza estado se o Core não validou a causa.
- [ ] Reconciliation login/reload para refletir estado existente.
- [ ] Proteção contra team completion duplicando reward individual/world.
- [ ] PT-BR e texto narrativo externo ao código.

## Fail-soft
Sem FTB Quests, o Core continua funcionando; apenas diário/UX fica ausente. Nenhuma progressão narrativa é automaticamente marcada como concluída.

## Acceptance
Uma ação feita antes de a quest aparecer já está no ledger. Ao abrir/instalar o journal adapter, o FTB mostra a resolução apropriada sem conceder reward novamente.