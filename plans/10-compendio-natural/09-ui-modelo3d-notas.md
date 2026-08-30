# 10.09 — UI, pesquisa, modelo 3D e notas pessoais

## Objetivo

Criar uma interface única, legível e escalável para centenas/milhares de entradas, com pesquisa, filtros, navegação por relações, modelo 3D de entidades e notas pessoais.

Toda apresentação própria do mod deve usar **pt-BR** como idioma canônico entregue pelo projeto.

## Estrutura de navegação

Tela principal planejada:

```text
Compêndio Natural
├── Fauna
├── Flora
├── Árvores
├── Cultivos
├── Biomas
├── Estruturas
├── Dimensões
├── Descobertas
└── Favoritos/Notas
```

Filtros adicionais:

- mod/namespace;
- descoberto/não descoberto;
- categoria;
- dimensão;
- biome;
- hostilidade;
- domesticável/reprodutível;
- boss;
- cobertura (`AUTO`, `CURATED`, `ADAPTER` em modo debug).

## Plano

### A — Tela principal e lista virtualizada

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreen.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumListView.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumSearchIndex.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumFilterState.java
```

- [ ] não renderizar milhares de rows completas fora da viewport;
- [ ] pesquisa local sobre snapshot já sincronizado;
- [ ] normalizar acentos para pesquisa sem destruir display pt-BR;
- [ ] buscar por nome localizado, ID técnico opcional, mod e aliases;
- [ ] preservar filtro ao abrir/voltar da página;
- [ ] suportar teclado, mouse e resolução/UI scale variável.

### B — Página de entrada

Layout conceitual:

```text
[Cabeçalho: nome | mod | descoberta]
[Preview/Modelo] [Resumo]

Abas/Seções:
- Visão geral
- Estatísticas
- Habitat
- Ecologia
- Loot/Usos
- Reprodução/Domesticação
- Variantes
- Relações
- Notas
```

Somente seções com dados devem aparecer. Não preencher UI com `N/A` repetitivo.

### C — Modelo 3D de entidade

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/render/CompendiumEntityPreview.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/render/EntityPreviewFactory.java
```

Requisitos:

- [x] client-only;
- [x] instância de preview nunca participa do mundo real;
- [x] não dispara AI, loot, sounds, particles ou side effects;
- [x] rotação/zoom controlados;
- [x] fallback para ícone/texto se renderer falhar;
- [x] blacklist/adapter para entidades que não podem ser construídas de forma segura;
- [x] crash de renderer modded não deve derrubar catálogo inteiro;
- [ ] variantes podem ser selecionadas quando houver representação segura.

Implementação inicial de segurança:

- entidades `minecraft:*` vivas podem usar construção vanilla destacada por padrão;
- entidades de mods terceiros ficam **fail-closed** até adapter explícito declarar construção segura;
- nenhuma instância de preview é adicionada ao `ClientLevel` ou recebe tick pelo Compêndio;
- blacklist explícita sobrepõe adapter;
- falha de construção ou renderer coloca o tipo em quarentena durante a sessão do cliente e usa fallback pt-BR;
- adapters de preview têm contrato de retornar entidade viva destacada, sem adicionar/tickar a entidade no mundo.

Bloqueio atual de variantes:

- `DiscoveryRecord` já persiste `variantIds`, e o runtime de fauna consegue produzir snapshots da variante da instância observada;
- o snapshot cliente atual (`CompendiumClientSnapshot`) **não transporta** os `variantIds` descobertos;
- os feeds genéricos de descoberta ainda publicam sinais sem `variantId`;
- por isso, a UI **não deve** construir uma lista de variantes a partir do registry/catálogo completo: isso poderia revelar variantes ainda não descobertas;
- o seletor de variantes permanece fail-closed até o contrato de snapshot/protocolo do 10.13 projetar somente variantes autorizadas para aquele jogador.

### D — Preview de flora/árvore/estrutura

- flora/árvore: item/block rendering quando disponível;
- estrutura: ícone/screenshot próprio somente se houver asset/proveniência; não tentar renderizar estrutura inteira 3D na primeira versão;
- bioma/dimensão: ícones e metadata; screenshots são opcionais e precisam de pipeline/asset próprio.

Implementação atual:

- [x] `FLORA`, `TREE`, `CROP` e `BLOCK_FEATURE` usam uma política pura de preview por registry;
- [x] o resolver compartilhado procura o `Block` canônico e usa `Block#asItem()` quando existe representação segura em item;
- [x] blocos sem item (`Items.AIR`) e IDs ausentes usam fallback pt-BR, sem inventar asset ou representação 3D;
- [x] o renderer físico cliente usa `GuiGraphics.renderItem(ItemStack, x, y)` e contém falhas de renderização localmente;
- [x] `STRUCTURE`, `BIOME` e `DIMENSION` permanecem metadata-only enquanto não houver asset próprio/proveniência válida;
- [x] nenhuma estrutura inteira é renderizada em 3D nesta versão;
- [x] o lookup de registry fica em `runtime/compendium`, sem dependência client-only, e é verificado em ambiente NeoForge bootstrapped por GameTest.

O renderer direto de `BlockState` fica deliberadamente adiado até existir API 1.21.1 confirmada e matriz de segurança suficiente para blocos modded; a ausência de item não autoriza um renderer arbitrário.

### E — Notas pessoais

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/notes/CompendiumNotesModel.java
```

Decisão de armazenamento deve ser fechada no 10.13. Requisitos funcionais:

- [ ] nota por `CompendiumEntryId`;
- [ ] texto pt-BR livre do jogador;
- [ ] limite de tamanho;
- [ ] não executar formatting/comandos arbitrários;
- [ ] notas privadas por padrão;
- [ ] não sincronizar para outros jogadores sem feature explícita futura;
- [ ] conteúdo ausente do modpack preserva a nota enquanto o save mantiver o ID.

### F — Favoritos e histórico leve

- [x] favoritar entradas;
- [x] opcionalmente manter lista limitada de últimas entradas abertas;
- [x] não criar histórico ilimitado;
- [x] favoritos não interferem na descoberta.

### G — Tooltip de proveniência/debug

Em modo avançado/debug, permitir visualizar:

- [x] `ResourceLocation`;
- [x] source mod;
- [x] `FactSource`;
- [x] provider/origem técnico registrado na proveniência da entrada;
- [x] coverage status.

- [x] No modo survival normal, esses detalhes não poluem a leitura; a exibição é opt-in e local ao cliente.

### H — Acesso

Planejar:

- keybind configurável para abrir o Compêndio;
- botão opcional em UI do RPG quando fizer sentido;
- item/livro físico é opcional e não deve ser a única forma de acesso;
- dedicated server não registra classes client-only.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumSearchIndexTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumFilterStateTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumPageModelTest.java
```

Verificação manual/client test matrix:

- [ ] 1.000+ entradas sem travamento perceptível ao scroll;
- [ ] pesquisa com/sem acento;
- [ ] nome duplicado de mods diferentes;
- [ ] entrada desconhecida/oculta;
- [ ] renderer 3D vanilla e modded;
- [ ] renderer com falha usa fallback;
- [ ] UI scale 1–4;
- [ ] resolução pequena e ultrawide;
- [ ] navegação por teclado/mouse;
- [ ] notas persistem conforme contrato do 10.13.

## Acceptance

O subplano fecha quando o jogador consegue pesquisar e navegar o catálogo de forma responsiva, visualizar entidades com fallback seguro e manter notas sem expor detalhes administrativos por padrão.
