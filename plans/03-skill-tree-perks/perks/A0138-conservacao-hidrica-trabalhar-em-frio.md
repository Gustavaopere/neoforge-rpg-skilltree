# A0138 — Conservação Hídrica: Trabalhar Em Frio

## Estado Chat 1

**DESIGN APROVADO APÓS HARDENING / UNAVAILABLE_NODE.** Availability transitiva de A0137.

## Contrato

- 4 ranks; 1 PP/rank.
- −3%/rank somente da parcela HYDRATION real da mesma ação física elegível sob `ADVERSE_COLD`, até −12%.
- cap HYDRATION compartilhado: 30%.
- Pré-requisitos: A0137 ≥2 + Gateway SURVIVAL.
- Ativação futura: estado `ADVERSE_COLD` server-authoritative + `AcclimationLedger(ADVERSE_COLD) >= 1` + receipt HYDRATION positivo da mesma `action_id`.

## Authority

Thirst Was Reclaimed 3.0.4 é owner de HYDRATION. Cold Sweat 2.4.2 fornece apenas o estado térmico read-only. `BodyCostResolver`, `AcclimationLedger` e adapters versionados são bindings obrigatórios e ainda não estão live.

## Availability

A0137 indisponível ou ausência de BodyCostResolver/AcclimationLedger/ADVERSE_COLD/TWR-HYDRATION => purchase fail-before-spend e allocation legado 0 PP. Depois da infraestrutura existir, um evento sem estado/carga/receipt apenas não proca.

## Exclusões

Não reduzir desidratação basal/ambiental, temperatura ou ICE damage; não polling; não direct write em thirst/traits.

## Testes Chat 3

- transitividade A0137→A0138;
- provider absent/present;
- mesma action_id para METABOLIC/HYDRATION;
- caps independentes de 30%;
- zero/cancel/rollback;
- lifecycle da carga, respec, rules reload e provider removal.
