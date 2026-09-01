# A0136 — Conservação Hídrica: Trabalhar Em Calor

## Estado Chat 1

**DESIGN APROVADO APÓS HARDENING / UNAVAILABLE_NODE.** Availability transitiva de A0135.

## Contrato

- 4 ranks; 1 PP/rank; −3%/rank da parcela HYDRATION real da mesma ação física sob `ADVERSE_HOT`, até −12%; cap HYDRATION 30%.
- A0135 ≥2 + Gateway SURVIVAL.
- Ativação futura: `ADVERSE_HOT` + carga ≥1 + receipt HYDRATION same-action do TWR.

## Authority

Thirst Was Reclaimed 3.0.4 é owner de HYDRATION; Cold Sweat 2.4.2 fornece somente estado térmico read-only. BodyCostResolver/AcclimationLedger e adapters versionados são obrigatórios.

## Availability

A0135 indisponível ou BodyCostResolver/AcclimationLedger/ADVERSE_HOT/TWR-HYDRATION ausente => purchase fail-before-spend e 0 PP legado. Depois da infraestrutura existir, um evento sem receipt apenas não proca.

## Exclusões

Não reduzir desidratação basal/ambiental, FIRE damage ou temperatura; não polling; não direct write em thirst/traits.

## Testes Chat 3

- transitividade A0135→A0136;
- provider absent/present;
- METABOLIC e HYDRATION na mesma action_id, caps independentes;
- zero/cancel/rollback;
- ledger lifecycle e provider removal.
