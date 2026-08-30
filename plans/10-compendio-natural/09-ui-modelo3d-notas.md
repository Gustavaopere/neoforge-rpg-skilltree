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

Implementação atual:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumBrowserModel.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumSearchIndex.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumFilterState.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenLayout.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenSession.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/client/CompendiumScreen.java
```

- [x] não renderizar milhares de rows completas fora da viewport;
- [x] pesquisa local sobre snapshot já sincronizado;
- [x] normalizar acentos para pesquisa sem destruir display pt-BR;
- [x] buscar por nome localizado, ID técnico opcional, mod e aliases;
- [x] preservar query, filtro, scroll e seleção ao abrir/voltar da página;
- [x] suportar teclado, mouse e geometria responsiva baseada na resolução escalada do cliente.

Evidência automatizada:

- `CompendiumBrowserModelTest` verifica viewport limitada com **1.505 entradas**, scroll, composição de pesquisa/filtros, teclado, clique e preservação do contexto da lista;
- `CompendiumSearchIndexTest` verifica pesquisa sem acento por nome localizado, alias, mod e ID técnico sem alterar o texto exibido;
- `CompendiumScreenLayoutTest` cobre layouts compacto, wide e ultrawide, limites mínimos e capacidade de rows;
- `CompendiumScreenSessionTest` cobre tradução dos eventos de navegação para o modelo puro.

A matriz manual de UI scale 1–4 e percepção de fluidez continua separada abaixo; os testes automatizados não substituem essa validação visual/client real.

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

Estado atual:

- [x] cabeçalho mostra nome localizado, mod de origem e estado de descoberta;
- [x] páginas ocultas permanecem shell/sem detalhes até a política de descoberta permitir;
- [x] somente fatos confirmados e seções que sobreviveram ao filtro de visibilidade chegam à página do cliente;
- [x] preview 3D de entidades e preview estático seguro são integrados à página;
- [x] notas pessoais e favoritos são acessíveis pela própria página;
- [x] proveniência administrativa permanece opt-in via debug;
- [ ] `entryRelations` já são projetadas em `CompendiumPageModel`, mas ainda precisam de apresentação/navegação clicável na UI;
- [ ] seletor de variantes depende da projeção segura de variantes descobertas no 10.13.

A primeira versão física usa seções contínuas em vez de criar abas vazias. Abas reais podem ser introduzidas se a densidade final exigir, mas não devem gerar painéis `N/A` sem conteúdo.

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

Modelo existente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumNotesModel.java
```

A persistência e qualquer protocolo de sincronização continuam pertencendo ao 10.13. O 10.09 fecha somente o modelo funcional e a edição local no cliente.

Requisitos funcionais e estado atual:

- [x] nota indexada pelo `CompendiumEntryId` canônico;
- [x] texto livre do jogador armazenado literalmente;
- [x] limite de 4.096 code points no modelo;
- [x] editor multiline client-only com limite conservador compatível com o modelo;
- [x] strings próprias da UI em pt-BR;
- [x] não interpretar nem executar formatting, comandos ou markup inseridos na nota;
- [x] notas privadas/client-local por padrão neste estágio;
- [x] nenhum envio de notas para outros jogadores;
- [x] IDs ausentes do catálogo atual continuam aceitos e preservados enquanto a instância do modelo existir;
- [x] botão/painel de notas por entrada na página do Compêndio;
- [x] trocar de entrada recarrega a nota correspondente ao ID, sem misturar textos entre entradas;
- [x] `Esc` fecha primeiro o painel de notas antes da navegação compacta voltar à lista;
- [x] foco no editor não dispara navegação por setas/Enter da lista;
- [ ] persistência de notas entre reconnect/restart/save — contrato do 10.13;
- [ ] preservação persistente de notas de conteúdo removido do modpack — depende do armazenamento definido no 10.13.

A indicação visual `Notas pessoais — sessão atual` é deliberada enquanto não existe persistência. O 10.09 não deve prometer durabilidade que o 10.13 ainda não implementou.

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

Estado atual:

- [x] keybind configurável `key.rpgskilltree.open_compendium`, registrado no menu de controles e com tecla padrão `J`;
- [ ] botão adicional na UI do RPG continua opcional e só deve ser incluído se melhorar a navegação sem duplicar controles;
- [x] item/livro físico continua opcional e não é a única forma de acesso, pois o keybind abre diretamente o Compêndio;
- [x] registro do keybind e abertura da tela ficam em subscriber `Dist.CLIENT`; dedicated-server smoke permanece parte obrigatória do CI.

## Testes previstos

Já existentes:

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumBrowserModelTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumSearchIndexTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumFilterStateTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumPageModelFactoryTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenLayoutTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenSessionTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumNotesModelTest.java
```

Verificação manual/client test matrix:

- [ ] 1.000+ entradas sem travamento perceptível ao scroll — viewport de 1.505 entradas é coberta automaticamente, mas percepção de fluidez exige cliente real;
- [x] pesquisa com/sem acento — cobertura automatizada do índice;
- [ ] nome duplicado de mods diferentes — comportamento de ordenação/filtro precisa de caso explícito/manual;
- [x] entrada desconhecida/oculta — cobertura automatizada de shell e políticas de visibilidade;
- [ ] renderer 3D vanilla e modded — requer teste visual/client real;
- [x] renderer com falha usa fallback — contrato automatizado de fail-soft; validação visual ainda é desejável;
- [ ] UI scale 1–4 — requer cliente real;
- [x] resolução pequena e ultrawide — geometria coberta automaticamente; validação visual continua desejável;
- [x] navegação por teclado/mouse — modelo/sessão cobertos automaticamente; smoke visual continua desejável;
- [ ] notas persistem conforme contrato do 10.13.

## Acceptance

O subplano fecha quando o jogador consegue pesquisar e navegar o catálogo de forma responsiva, visualizar entidades com fallback seguro e manter notas sem expor detalhes administrativos por padrão.

Pendências funcionais conhecidas do 10.09 que não dependem de teste manual:

1. apresentação e navegação clicável das `entryRelations` já projetadas no `CompendiumPageModel`;
2. seletor seguro de variantes, bloqueado até o snapshot/protocolo do 10.13 transportar somente variantes descobertas/autorizadas;
3. persistência das notas, explicitamente pertencente ao 10.13.
