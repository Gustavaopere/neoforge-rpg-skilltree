# 15.01 — Domínio poligonal e persistência

## Modelo

`DistrictRecord` deve persistir fatos, não render data:

- `districtId` namespaced;
- owner `colonyId` e opcional `realmId`;
- nome/localization reference;
- lista ordenada de vértices XZ;
- `VerticalPolicy`;
- zoning IDs;
- policy/service references;
- revision;
- created/modified provenance administrativa.

## Geometria

- mínimo de 3 vértices únicos;
- polígonos côncavos permitidos;
- auto-intersecção rejeitada;
- segmentos degenerados rejeitados;
- orientação clockwise/counter-clockwise normalizada;
- limite máximo de vértices configurável para performance;
- coordenadas inteiras/block-space como autoridade.

`VerticalPolicy` padrão cobre toda a coluna jogável. Políticas futuras por faixa Y são explicitamente versionadas; não devem alterar distritos antigos automaticamente.

## Overlap

Distritos pares da mesma camada administrativa não podem se sobrepor por padrão. Sobreposição só existe via hierarquia explícita (por exemplo subdistrito), com `parentDistrictId` e prioridade determinística. Empate inválido é rejeitado.

## Persistência

Usar `SavedData` ou boundary canônico equivalente do mundo, com schema version e índice reconstruível. Cache espacial não é persistido como autoridade.

## Testes

- convex/concave;
- self-intersection;
- border point;
- huge coordinates;
- save/load;
- schema migration;
- overlap/hierarchy.

## Acceptance

A geometria autoritativa sobrevive restart e gera sempre a mesma resposta de pertencimento.