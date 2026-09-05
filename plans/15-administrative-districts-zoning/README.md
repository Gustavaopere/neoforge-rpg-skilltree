# 15 — Distritos Administrativos e Zoneamento

## Objetivo

Adicionar territórios **administrativos criados pelo jogador** sem confundir com as regiões geográficas/semânticas do Stage 13. Um distrito é um polígono persistente usado por leis, impostos, aquecimento, serviços, zoneamento, economia e análise social.

## Decisão central

Distritos não são chunks nem retângulos obrigatórios. O domínio suporta polígonos côncavos traçados no mundo.

```text
AdministrativeDistrict
├── districtId
├── colony/realm owner
├── polygonXZ
├── verticalPolicy
├── zoning
├── policy overrides
├── service links
└── socioeconomic snapshot references
```

Consulta comum:

```text
districtAt(BlockPos) -> Optional<DistrictRef>
```

Essa é a fronteira reutilizada pelos Stages 16–20.

## Autoridades

- Stage 13: região natural, POI e descoberta;
- Stage 15: jurisdição administrativa;
- MineColonies: claim/colony protection quando aplicável;
- Stage 17: leis e policy resolver;
- Stage 19: rede térmica.

Nenhum deles reinterpreta silenciosamente o estado do outro.

## UX

Uma ferramenta de topografia permite marcar vértices, visualizar linhas/fechamento e confirmar. Client renderiza beams/wireframe, mas servidor valida geometria e persiste o resultado.

## Invariantes

- IDs namespaced/estáveis;
- polígonos simples, sem auto-intersecção;
- consulta espacial bounded;
- alteração de fronteira é transação versionada;
- overlap possui regra explícita e determinística;
- nenhuma coordenada de distrito depende do JourneyMap;
- pt-BR first;
- renderer opcional/fail-soft.

## Ordem

1. `01-polygon-domain-and-persistence.md`
2. `02-surveyor-tool-and-trace-mode.md`
3. `03-markers-beams-and-client-wireframes.md`
4. `04-point-in-polygon-spatial-index.md`
5. `05-zoning-types.md`
6. `06-district-policy-overrides.md`
7. `07-socioeconomic-profile.md`
8. `08-map-rendering-and-stage13-bridge.md`
9. `09-tests-performance-migration.md`

## Definition of Done

O jogador traça um distrito côncavo, confirma no servidor, salva/recarrega, consulta `districtAt`, visualiza no mapa e consegue aplicar políticas/serviços sem que isso altere a região natural do Stage 13.