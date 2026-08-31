# A0132 — Conservação Hídrica: Conjurar

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
SURVIVAL/ARCANE_HYDRATION, 4 ranks, 1 PP/rank. Reduziria 3% por rank apenas `HYDRATION_CAST` causal, até 12%.

## Boundary
Exige receipt TWR da mesma action_id e, portanto, custo corporal de cast real. Recurso mágico não gera sede por conversão.

## Exclusões
Não usar mana, Source, Soul Energy, sangue, Arcane Strain, temperatura ou polling de thirst como substituto.

## Chat 2
Fail-closed e aquisição desabilitada enquanto `HYDRATION_CAST` não existir.