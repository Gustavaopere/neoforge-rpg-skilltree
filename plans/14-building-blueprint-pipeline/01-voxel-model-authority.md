# 14.01 — VoxelModel como autoridade geométrica

## Contrato

Representar cada construção como um modelo imutável por estágio:

```text
VoxelModel {
  schemaVersion
  sizeX,sizeY,sizeZ
  origin/anchor
  blocks[(x,y,z)] -> BlockStateRef
  blockEntities[(x,y,z)] -> dados permitidos
  markers[]
  connectors[]
  metadata
}
```

`BlockStateRef` mantém registry ID e propriedades normalizadas. NBT/block-entity data é whitelistada por tipo para impedir payload arbitrário.

## Regras

- coordenadas internas começam em uma origem local estável;
- ar explícito só é armazenado quando necessário para limpeza/substituição;
- ordem de serialização é determinística;
- hash lógico ignora metadata não geométrica e inclui todos os block states relevantes;
- markers não podem compartilhar IDs no mesmo estágio;
- anchor de upgrade deve permanecer estável entre níveis salvo migração declarada.

## Autoridade

Preview, BOM e exporters consomem o mesmo `VoxelModel`. Nenhum exporter possui uma segunda cópia da construção.

## Migração

Alteração do schema exige codec versionado. Modelo antigo nunca é reinterpretado silenciosamente com nova semântica.

## Testes

- round-trip determinístico;
- block state com propriedades;
- block entity permitido/rejeitado;
- hash idêntico entre duas serializações;
- hash muda quando um voxel muda;
- bounds negativos/fora do volume rejeitados;
- markers/anchors duplicados rejeitados.

## Acceptance

É possível comparar preview e export por um fingerprint do mesmo VoxelModel e provar que ambos representam exatamente a mesma construção.