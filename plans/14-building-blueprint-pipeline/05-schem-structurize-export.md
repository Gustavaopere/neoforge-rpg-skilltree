# 14.05 — Export `.schem` e Structurize/MineColonies

## Objetivo

Separar o modelo interno dos formatos de terceiros.

## Exporters

Definir interface conceitual:

```text
BlueprintExporter
- supports(targetVersion)
- validate(VoxelModel)
- export(VoxelModel, options)
- produceManifest()
```

Implementações planejadas:

- Sponge/`.schem` na revisão efetivamente aceita pelo viewer usado;
- formato Structurize/MineColonies compatível com a versão 1.21.1 do modpack, somente após validar seu contrato real.

## Regras

- nenhum exporter inventa bloco ausente;
- NBT perigoso/irrelevante é filtrado;
- entities não são exportadas por padrão;
- block entities são explicitamente suportadas por adapter;
- palette/index compression é detalhe do exporter;
- metadata própria via manifest não deve quebrar consumidores externos.

## Compatibilidade

A implementação deve testar o arquivo em pelo menos um leitor independente do próprio exporter. Para MineColonies/Structurize, validar import real na versão suportada antes de declarar compatibilidade.

## Testes

- arquivo parseável;
- dimensões e anchor;
- estados direcionais;
- block entities permitidos;
- IDs modded;
- round-trip voxel;
- erro claro para formato/API incompatível.

## Acceptance

O usuário pode abrir o `.schem` em viewer compatível e observar a mesma geometria aprovada; o artefato MineColonies só é rotulado compatível após import real.