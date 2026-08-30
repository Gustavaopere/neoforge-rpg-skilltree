# 20.07 — Assentamentos abstratos/offscreen

## Objetivo

Evitar dezenas de MineColonies completas, citizens e chunks ativos para representar o mundo político.

## AbstractSettlement

Persistir agregados:

- settlementId/realmId;
- population bands;
- wealth/production stocks;
- military strength;
- fortification;
- government/service status;
- strategic resources;
- location/region reference;
- lastSimulatedPeriod/revision.

Não persistir inventário individual de centenas de NPCs abstratos.

## Simulation step

Usar balanços agregados, deterministic seed e períodos coarse. Produção/consumo/growth são bounded e auditáveis. Catch-up após longo offline possui cap/batching; não iterar cada hora perdida indefinidamente.

## Materialização

Se um settlement precisar virar conteúdo visitável, usar pipeline explícito de materialização (estrutura/POI ou colony real) com reconciliation de aggregated state. Não spawnar automaticamente MineColonies completa só por aproximar-se, até esse contrato ser implementado/testado.

## War

Offscreen battle consome snapshots agregados e produz casualties/resource deltas; quando combate materializa, não aplicar o mesmo outcome duas vezes.

## Testes

- long offline catch-up;
- deterministic seed;
- resource conservation constraints;
- materialization idempotence fixture;
- war dedupe;
- thousands synthetic settlements performance.

## Acceptance

O mundo pode ter muitos realms/settlements sem custo equivalente a muitas colônias carregadas.