# 18.01 — Framework de prédios próprios

## Modelo

`CivicBuildingDefinition` deve declarar:

- ID namespaced;
- family/tags;
- MineColonies bridge ID quando houver;
- blueprint/style references do Stage 14;
- max level;
- required jobs/seats;
- service capabilities;
- district/zoning requirements;
- provider requirements;
- localization;
- persistence/migration policy.

## Runtime

Manter `BuildingServiceRecord` próprio referenciado por colony/building identity. O record contém somente fatos necessários aos sistemas RPG; não duplica todo estado interno MineColonies.

## Capability/service IDs

Exemplos extensíveis: `commerce`, `banking`, `taxation`, `assembly`, `court`, `religion`, `health`, `research`, `engineering`, `arcane_research`, `heat_generation`, `heat_distribution`.

## Lifecycle

- construção detectada → registrar/ligar serviço;
- upgrade → reconciliar capacity/markers;
- destroy/deactivate → suspender serviço sem apagar ledger/contratos históricos;
- provider missing → record preservado e capability indisponível.

## Proteção

Toda interação respeita `ProtectedAreaService`/MineColonies protection; perks/máquinas não podem contornar claim.

## Testes

- register/upgrade/destroy;
- provider absent;
- duplicate event idempotence;
- save/load;
- unknown definition quarantine;
- protection boundary.

## Acceptance

Prédios próprios possuem identidade estável independente da entidade/block instance que os representa.