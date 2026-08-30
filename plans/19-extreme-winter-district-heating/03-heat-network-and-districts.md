# 19.03 — Rede de calor e distritos

## Modelo

Não usar “raio mágico” da usina. Persistir um graph lógico de infraestrutura:

```text
HeatGeneratorNode
HeatNetworkSegment
HeatSubstationNode
HeatEndpointNode
```

Edges possuem capacity/loss policy simplificada e status ativo/danificado. A geometria física pode vir de blocos próprios/Create, mas o graph é autoridade de distribuição RPG.

## Stage 15

Distritos agrupam endpoints e políticas. `districtAt` ajuda UI/prioridade, mas conexão depende do graph; pertencer ao distrito não basta para receber calor.

## Allocation

A cada período térmico relevante:

1. calcular supply por generators;
2. aplicar network capacity;
3. coletar demands;
4. ordenar por priority policy;
5. distribuir heat units;
6. publicar snapshots de building/district coverage.

## Performance

Graph updates incrementalmente quando bloco/network topology muda. Não revarrer colônia inteira a cada tick. Component IDs/cache por revision.

## Falhas

Segmento quebrado desconecta subgraph; overload limita throughput, não duplica calor.

## Testes

- connected/disconnected;
- bottleneck;
- two substations;
- district boundary sem conexão;
- network split/merge;
- save/reload.

## Acceptance

Heat chega por caminhos reais e capacity bottlenecks são observáveis.