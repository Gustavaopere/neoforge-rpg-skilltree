# A0135 — Economia Metabólica: Trabalhar Em Calor

## Estado Chat 1

**DESIGN APROVADO APÓS HARDENING / UNAVAILABLE_NODE no snapshot atual.**

## Contrato

- 4 ranks; 1 PP/rank; −3%/rank da parcela METABOLIC real de uma ação física elegível sob `ADVERSE_HOT`, até −12%; cap METABOLIC 30%.
- Pré-requisito: Gateway SURVIVAL + ≥2 ranks em um node METABOLIC físico realmente capability-eligible.
- Ativação futura: `ADVERSE_HOT` server-authoritative + `AcclimationLedger(ADVERSE_HOT) >= 1` + receipt METABOLIC positivo da mesma action_id.

## Authority

Cold Sweat 2.4.2 é authority térmica read-only. RPG-owned `BodyCostResolver` e `AcclimationLedger` são consumers/state necessários. Eles estão ausentes da main auditada.

`ADVERSE_HOT` não é WORLD/bioma, FIRE damage, BURNING_POINT nem `ENVIRONMENTAL_HOT`.

## Availability

Sem BodyCostResolver + AcclimationLedger + adapter Cold Sweat `ADVERSE_HOT` + predecessor capability-eligible, purchase falha antes do gasto e allocation legado vale 0 PP. Após esses bindings existirem, ausência de estado/carga/receipt num evento apenas omite o proc.

## Handoff Chat 2

Não implementar segundo sistema térmico, não escrever traits e não fabricar exhaustion por calor.

## Testes Chat 3

- purchase/PP com bindings ausentes;
- predecessor fail-closed não conta;
- hot/cold/environmental states não se confundem;
- 0/1 carga e lifecycle do ledger;
- mesma action_id + cap 30% + dedup;
- forced/provider removal/respec/rules reload.
