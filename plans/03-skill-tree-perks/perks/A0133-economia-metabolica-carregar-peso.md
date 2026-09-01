# A0133 — Economia Metabólica: Carregar Peso

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.** Não existe provider aprovado de `player_encumbrance` no runtime auditado.

## Contrato

- 4 ranks; 1 PP/rank.
- −3%/rank somente do custo METABOLIC adicional provocado por `player_encumbrance` real, até −12%.
- cap METABOLIC compartilhado: 30%.
- Gateway SURVIVAL + provider versionado de encumbrance.

## Authority e exclusions

`BodyCostResolver` recebe apenas a parcela adicional causal publicada pelo provider. Massa de contraption/Create Aeronautics, Sable Weight, quantidade de slots, inventário, Armor, movement speed e aparência do equipamento não classificam `player_encumbrance`.

## Boundary futuro

`provider de player_encumbrance -> surcharge METABOLIC_LOAD positivo + action_id -> BodyCostResolver -> A0133 uma vez`.

## Handoff Chat 2

Manter purchase bloqueado até existir provider real. A perk nunca cria o surcharge que pretende reduzir.

## Testes Chat 3

- provider absent/present;
- purchase fail-before-spend e allocation legado 0 PP;
- contraption/inventário/Armor não qualificam;
- reduzir apenas o surcharge confirmado;
- cap, dedup, action identity e lifecycle.
