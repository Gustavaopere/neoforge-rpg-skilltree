# A0137 — Economia Metabólica: Trabalhar Em Frio

## Estado Chat 1

**DESIGN APROVADO APÓS HARDENING / UNAVAILABLE_NODE no snapshot atual.**

## Contrato

- 4 ranks; 1 PP/rank; −3%/rank da parcela METABOLIC real de uma ação física elegível sob `ADVERSE_COLD`, até −12%; cap METABOLIC 30%.
- Gateway SURVIVAL + ≥2 ranks em node METABOLIC físico capability-eligible.
- Ativação futura: `ADVERSE_COLD` server-authoritative + `AcclimationLedger(ADVERSE_COLD) >= 1` + receipt METABOLIC positivo da mesma action_id.

## Authority

Cold Sweat 2.4.2 é authority térmica read-only. BodyCostResolver/AcclimationLedger são serviços RPG-owned necessários e não existem na main auditada.

`ADVERSE_COLD` não é WORLD/bioma, ICE damage, FREEZING_POINT nem `ENVIRONMENTAL_COLD`.

## Availability

Sem BodyCostResolver + AcclimationLedger + adapter `ADVERSE_COLD` + predecessor capability-eligible, purchase falha antes do gasto e allocation legado vale 0 PP. Depois da infraestrutura existir, ausência de estado/carga/receipt apenas omite o proc daquele evento.

## Testes Chat 3

- fail-before-spend/0 PP;
- predecessor morto não conta;
- distinção ADVERSE_COLD/ENVIRONMENTAL_COLD;
- carga 0/1 e lifecycle;
- action_id/cap/dedup;
- provider removal/respec/rules reload.
