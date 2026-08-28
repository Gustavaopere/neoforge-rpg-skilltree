# 10.01 — Arquitetura, taxonomia e schema das entradas

## Objetivo

Congelar o contrato conceitual do Compêndio antes de criar loaders, UI ou conteúdo em massa. Uma entrada deve ser estável, endereçável por registry quando aplicável e capaz de representar fauna, flora, bioma, estrutura e recursos especiais sem campos ad hoc por mod.

## Arquivos previstos

Criar/alterar principalmente:

- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaCategory.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaEntryDefinition.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaTarget.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaSection.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryRule.java`
- `src/test/java/dev/gustavopere/rpgskilltree/core/EncyclopediaEntryDefinitionTest.java`

## Contrato de ID

- Toda entrada possui `ResourceLocation entryId` estável, por exemplo `rpgskilltree:fauna/alexsmobs/...`.
- O ID persistido não pode ser derivado de texto traduzido.
- Quando a entrada representa um registry object, armazenar também o alvo real (`entity_type`, `block`, `item`, `biome`, `structure` ou outra classe explicitamente suportada) como `ResourceLocation`.
- Um mesmo alvo pode possuir apenas uma entrada canônica por finalidade. Aliases devem ser declarados para migração, nunca silenciosamente duplicados.
- IDs removidos/renomeados entram no contrato de migração do subplano 05.

## Taxonomia inicial

Categorias canônicas de apresentação:

- `FAUNA`
- `HOSTIS`
- `CHEFES`
- `FLORA`
- `BIOMAS`
- `ESTRUTURAS`
- `RECURSOS`

Subcategorias são dados e não enums obrigatórios. Exemplos: mamíferos, aves, répteis, peixes, artrópodes, árvores, fungos, cultivos, ruínas, dungeons, templos e ambientes subterrâneos.

## Schema mínimo de uma entrada

Definir campos equivalentes a:

- `entryId`
- `category`
- `subcategory`
- `target`
- `providerModId`
- `titleKey`
- `summaryKey`
- `sectionKeys`
- `icon`/`renderHint`
- `tags`
- `relatedEntryIds`
- `discoveryRules`
- `visibilityPolicy`
- `sortOrder`

`providerModId` é metadado de origem e filtro de compatibilidade; não deve contaminar o texto editorial com frases automáticas do tipo “integração com mod X”.

## Seções semânticas

Uma entrada pode expor apenas seções compatíveis com sua categoria, mas os nomes de seção precisam ser canônicos:

- `VISAO_GERAL`
- `CLASSIFICACAO`
- `LOCALIZACAO`
- `ECOLOGIA`
- `COMPORTAMENTO`
- `ESTATISTICAS`
- `VARIANTES`
- `REPRODUCAO_DOMESTICACAO`
- `DROPS_RECURSOS`
- `PERIGOS`
- `OBSERVACOES`

Estruturas devem priorizar localização, ambiente, ameaças e características; flora deve priorizar ambiente, crescimento, colheita e usos; criaturas devem priorizar ecologia/comportamento/stats/drops.

## Regras de visibilidade

Definir pelo menos três estados:

1. `UNKNOWN` — entrada não revelada; pesquisa não vaza título específico se isso revelar conteúdo não descoberto.
2. `DISCOVERED` — visão geral e dados básicos liberados.
3. `STUDIED` — seções avançadas liberadas quando a entrada tiver regra de estudo adicional.

A primeira versão pode representar os níveis usando chaves distintas em `DiscoveryProgress`, desde que o formato seja documentado e testado. Não ampliar persistência ainda neste subplano.

## Conteúdo factual x runtime

Separar explicitamente:

- texto editorial estático e curado;
- relações/tags data-driven;
- dados runtime seguros, como atributos base observáveis de entidades;
- dados que não podem ser inferidos genericamente (drop tables condicionais, mecânicas de mods, variantes NBT etc.).

Nunca inventar um valor quando o registry/API não o expõe. Dados desconhecidos ficam ausentes, não recebem placeholders falsos.

## Testes

- rejeitar `entryId`, target ou translation key vazios;
- rejeitar categoria incompatível com target quando houver regra objetiva;
- rejeitar duplicate related IDs e self-link;
- aceitar entry sem target registry para conceitos especiais explicitamente suportados;
- provar imutabilidade/defensive copy de listas e sets;
- provar que display text não participa da identidade persistida.

## Acceptance

- [ ] O modelo de dados representa todas as categorias mínimas sem campo específico de um mod.
- [ ] IDs e targets usam `ResourceLocation` e têm regras de estabilidade documentadas.
- [ ] Visibilidade básica e estudada têm semântica definida.
- [ ] Testes unitários do contrato passam.
- [ ] Nenhuma persistência, loader ou UI foi acoplada prematuramente ao modelo.
