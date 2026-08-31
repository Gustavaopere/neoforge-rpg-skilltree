# A0130 — Conservação Hídrica: Usar Arco/Besta

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
SURVIVAL/RANGED_HYDRATION, 4 ranks, 1 PP/rank. Reduziria 3% por rank somente `HYDRATION_RANGED` real até 12%.

## Boundary
Exige receipt TWR causal da mesma action_id do disparo. Sem `METABOLIC_RANGED` real, a bridge hídrica correspondente também não existe.

## Exclusões
Munição, Stamina, mana, Focus/Cadence ou simples ocorrência do disparo não são HYDRATION.

## Chat 2
Fail-closed integral e aquisição desabilitada enquanto `HYDRATION_RANGED` estiver ausente.