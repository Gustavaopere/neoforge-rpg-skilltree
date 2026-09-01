# A0134 — Conservação Hídrica: Carregar Peso

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.** Availability transitiva de A0133 e de um provider hídrico causal.

## Contrato

- 4 ranks; 1 PP/rank.
- −3%/rank somente do surcharge HYDRATION adicional causado por `player_encumbrance` real, até −12%.
- cap HYDRATION compartilhado: 30%.
- A0133 ≥2 + Gateway SURVIVAL + Thirst Was Reclaimed 3.0.4 + provider versionado de encumbrance.

## Authority

TWR é owner de HYDRATION; o provider de encumbrance apenas classifica carga e causalidade. Massa de contraption, inventário, Armor ou heurísticas visuais não qualificam.

## Boundary futuro

`player_encumbrance + action_id -> METABOLIC settlement -> adapter TWR same-action -> HYDRATION_LOAD adicional -> A0134 uma vez`.

## Handoff Chat 2

Manter indisponível enquanto A0133/provider/adapter hídrico estiver ausente. Não escrever em thirst e não criar surcharge artificial.

## Testes Chat 3

- availability transitiva;
- purchase fail-before-spend/0 PP legado;
- mesma action_id entre carga, METABOLIC e HYDRATION;
- heurísticas excluídas;
- cap/dedup/rollback/lifecycle.
