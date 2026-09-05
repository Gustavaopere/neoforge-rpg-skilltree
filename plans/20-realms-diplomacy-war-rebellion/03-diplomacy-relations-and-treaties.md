# 20.03 — Diplomacia, relações e tratados

## RelationState

Manter relação bilateral com fatores explicáveis: tratados, guerra recente, comércio, tributo, casualties, doctrine/government compatibility quando definida, espionage incidents e eventos de quest.

Score agregado pode existir para IA, mas UI deve mostrar fatores principais; não depender de número opaco único.

## Treaties

`TreatyRecord` com parties, clauses, start/end, ratification/authority e breach state.

Cláusulas extensíveis:

- peace/non-aggression;
- alliance/defense;
- trade;
- tribute;
- access;
- vassalage;
- research/resource agreement;
- truce after war.

Cada clause possui enforcement real ou fica indisponível.

## Singleplayer NPC decisions

AI avalia interesses/constraints em períodos diplomáticos, não a cada tick. Seeded randomness pode desempatar opções, mas state/factors são persistidos para evitar reroll por reload.

## Testes

- sign/expire;
- breach;
- incompatible clauses;
- authority office;
- NPC deterministic reload;
- trade/tribute bridge.

## Acceptance

Tratado é record executável e não apenas texto de quest.