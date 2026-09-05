# 20.04 — Guerra, raids, cercos e ocupação

## WarRecord

Persistir attacker/defender, war goals, start, participants, fronts/targets, casualties/score summaries, truces e outcome.

## Materialização

Perto do jogador, ações militares podem usar entities reais/guards/raiders via adapters. Stage 02.06 classifica combatentes `COMBATANT_FULL`; civis continuam defensive/noncombatant conforme policy.

## Offscreen

Fora de área materializada, resolver engagements por modelo agregado do Stage 20.07, usando força/logística/morale/fortification snapshots. Não spawnar exércitos em chunks descarregados.

## Siege

Siege target referencia colony/POI/fortification. Dano a building/claim só ocorre por mecanismos compatíveis com protection e war state; não bypassar MineColonies protection genericamente.

## Occupation

Outcome pode criar `OccupationRecord` temporário com controller, tax/tribute policy e resistance. Colony membership/ownership só muda quando peace/conquest rules confirmarem.

## Testes

- declare war authority;
- raid vs war;
- materialized combat attribution;
- protection;
- offscreen determinism;
- occupation/release;
- save mid-war.

## Acceptance

Guerra tem estado persistente e outcome consistente sem griefing arbitrário do provider.