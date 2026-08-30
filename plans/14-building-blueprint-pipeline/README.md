# 14 — Pipeline de Construções, Blueprints e Schematics

## Objetivo

Criar uma única fonte geométrica capaz de produzir **preview fiel**, `.schem` para visualização externa, blueprints compatíveis com a cadeia MineColonies/Structurize quando suportada e estruturas de upgrade, sem desenhar uma imagem e depois tentar reconstruí-la manualmente.

A decisão central é:

```text
BuildingSpec + Palette + FunctionalGraph
               ↓
          VoxelModel canônico
          ↙       ↓        ↘
      Preview   .schem   Blueprint/export
```

O preview aprovado pelo usuário e o arquivo exportado devem ter o mesmo hash lógico de voxels. Não existe “preview conceitual” que use uma construção diferente.

## Escopo

- gerador paramétrico de construções;
- paletas vanilla + modded, com foco real em Create quando o projeto pedir Create;
- níveis/estágios de construção, especialmente níveis 1–5;
- markers funcionais, inventories, acessos e zonas de manutenção;
- exporters versionados;
- Bill of Materials (BOM);
- validação de rotação, espelhamento, bounding boxes e blocos inválidos;
- style packs MineColonies;
- preview web/offline derivado do modelo voxel;
- integração direta com Stage 19 para Central Térmica.

## Invariantes

1. VoxelModel é autoridade; screenshots não são autoridade.
2. Blocos são `ResourceLocation`s reais do registry-alvo.
3. Paleta declarada “Create” não pode ser silenciosamente substituída por pedra/ferro vanilla.
4. Exporter que não conhece um bloco falha com diagnóstico; não o troca escondido.
5. Toda transformação é determinística.
6. Upgrade preserva anchors/conectores obrigatórios ou declara migração física explícita.
7. Estrutura funcional diferencia bloco decorativo de componente operacional.
8. Assets externos respeitam Stage 09.09.
9. O pipeline funciona sem MineColonies/Create instalados quando seus adapters não são requeridos.
10. Não há escrita arbitrária no mundo como efeito colateral de gerar arquivo.

## Ordem

1. `01-voxel-model-authority.md`
2. `02-block-palettes-and-provider-assets.md`
3. `03-parametric-building-generator.md`
4. `04-preview-rendering-and-review.md`
5. `05-schem-structurize-export.md`
6. `06-minecolonies-stylepacks-upgrades.md`
7. `07-create-functional-machinery.md`
8. `08-validation-material-bom.md`
9. `09-tests-ci-and-release-gate.md`

## Definition of Done

Um teste vertical deve gerar uma construção multiestágio com blocos Create reais, produzir preview e `.schem` da mesma matriz voxel, reimportar/inspecionar o resultado, validar BOM e transforms, e produzir o artefato MineColonies/Structurize somente quando o formato/API da versão 1.21.1 estiver comprovado.