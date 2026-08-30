# 15.04 — `districtAt` e índice espacial

## API conceitual

```text
Optional<DistrictRef> districtAt(ResourceKey<Level>, BlockPos)
List<DistrictRef> districtsIntersecting(AABB/ChunkPos)
```

A implementação de point-in-polygon deve lidar corretamente com bordas e polígonos côncavos.

## Performance

Não percorrer todos os distritos em toda consulta. Manter índice reconstruível por bounding boxes/chunk buckets ou estrutura equivalente. Atualizar somente districts cuja revision mudou.

Hot paths possíveis: consumo econômico, temperatura, leis e entrada de entidade. A API deve permitir cache por chunk/revision quando semanticamente seguro.

## Bordas

Definir ponto sobre segmento como **dentro** do distrito dono. Em fronteira compartilhada explicitamente válida, hierarquia/prioridade resolve a resposta; peers sobrepostos são inválidos.

## Persistência

Índice é derivado e pode ser reconstruído do `DistrictRecord`; corrupção do cache nunca altera autoridade.

## Testes

- ray-casting/winding fixtures;
- bordas/vértices;
- concavidade;
- negativos;
- centenas/milhares de districts sintéticos;
- update de revision remove buckets antigos;
- benchmark comparativo sem inventar budget antes de medir.

## Acceptance

Stages consumidores usam uma única consulta determinística e bounded, sem reimplementar geometria.