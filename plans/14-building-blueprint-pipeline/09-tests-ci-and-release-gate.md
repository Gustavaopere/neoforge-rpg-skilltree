# 14.09 — Testes, CI e gate de entrega

## Matriz mínima

### Core

- codecs de VoxelModel/BuildingSpec/Palette;
- determinismo e hashes;
- transformações;
- BOM/diffs;
- collision/constraint solver.

### Registry/runtime

- IDs vanilla e modded em registries 1.21.1;
- provider ausente/presente;
- block states direcionais;
- adapters de block entity.

### Export

- `.schem` parseável por leitor independente;
- reimport normalizado;
- Structurize/MineColonies import real quando ativado.

### Create/MineColonies

- classloading fail-soft;
- provider-present smoke;
- upgrade preserva anchors/markers;
- functional graph realmente opera no cenário certificado.

## CI

Adicionar validators determinísticos sem exigir cliente gráfico para o caminho core. Export fixtures pequenos entram no repositório apenas quando licença/proveniência permitir. Artefatos grandes são gerados na CI.

## Gate

Uma construção só recebe estado `APPROVED` quando:

1. spec valida;
2. VoxelModel valida;
3. preview e export compartilham hash;
4. BOM fecha;
5. provider requirements estão satisfeitos;
6. exporter alvo passou teste real;
7. licença/proveniência passou Stage 09.

## Acceptance

CI impede divergência entre preview e schematic e impede publicar blueprint que referencia blocos inexistentes ou provider incompatível.