# 19.02 — Modelo térmico de edifícios

## Objetivo

Calcular demanda sem simular termodinâmica por bloco/tick.

## `BuildingThermalProfile`

Inputs possíveis:

- building type/level;
- volume/bounds aproximados;
- occupancy;
- insulation rating derivada de blueprint/material tags quando disponível;
- exposed surface proxy;
- service criticality;
- climate severity;
- current heat allocation.

Output:

- demand units;
- thermal state (`HEATED`, `UNDERHEATED`, `CRITICAL`, etc.);
- comfort/exposure factor.

## Atualização

Recalcular quando severity, building revision, occupancy band ou allocation muda; não a cada bloco/tick.

## Integração Stage 14/18

Blueprint pode fornecer thermal metadata validada. MineColonies building desconhecido recebe perfil genérico por categoria/tamanho com diagnóstico, não zero demand.

## Limites

Insulation não substitui heat infinito; apenas reduz demanda dentro de caps. Prédio sem endpoint/network permanece sem allocation.

## Testes

- house small/large;
- hospital critical;
- insulated vs uninsulated;
- upgrade changes demand;
- unknown modded building fallback;
- reload determinism.

## Acceptance

Demanda é explicável e barata o suficiente para centenas de prédios.