# 10.09 — Contrato funcional da UI, previews e notas do Compêndio

## Fronteira

A apresentação foi separada para `../textura/03-compendio-ui-modelos-previews.md`.

Este arquivo continua sendo o contrato de engenharia do Stage 10.09: read models, pesquisa/filtros, segurança de previews, autorização de variantes, notas/favoritos, acesso client-only e testes funcionais.

## Objetivo

Fornecer ao cliente um modelo navegável e seguro para centenas/milhares de entradas, sem expor dados não descobertos e sem permitir que renderer/UI alterem authority do Compêndio.

## A — Browser, pesquisa e navegação

Implementação atual relevante:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumBrowserModel.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumSearchIndex.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumFilterState.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenLayout.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumScreenSession.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/client/CompendiumScreen.java
```

- [x] não materializar milhares de rows fora da viewport;
- [x] pesquisa local sobre snapshot já sincronizado;
- [x] normalizar acentos para pesquisa sem destruir display pt-BR;
- [x] buscar por nome localizado, ID técnico opcional, mod e aliases;
- [x] preservar query, filtro, scroll e seleção ao abrir/voltar da página;
- [x] suportar teclado/mouse e geometria responsiva como contrato funcional do cliente.

A composição visual desses estados pertence a `../textura/03-compendio-ui-modelos-previews.md`.

Evidência automatizada já registrada:

- `CompendiumBrowserModelTest` cobre viewport limitada com 1.505 entradas, scroll, pesquisa/filtros, teclado, clique e preservação de contexto;
- `CompendiumSearchIndexTest` cobre busca sem acento por nome, alias, mod e ID técnico sem alterar display;
- `CompendiumScreenLayoutTest` cobre geometria compacta, wide e ultrawide;
- `CompendiumScreenSessionTest` cobre tradução de eventos de navegação para o modelo puro.

## B — Page model e visibilidade

- [x] cabeçalho recebe nome localizado, origem e estado de descoberta somente quando permitido;
- [x] entradas ocultas permanecem shell sem fatos secretos;
- [x] somente fatos que sobreviveram ao filtro de visibilidade chegam ao cliente;
- [x] notas e favoritos são ligados à entrada canônica;
- [x] proveniência administrativa permanece opt-in/debug;
- [x] `entryRelations` autorizadas podem ser projetadas em `CompendiumPageModel`;
- [x] relações cujo target não existe no `CompendiumClientSnapshot` autorizado ficam fail-closed sem expor ID ausente;
- [x] navegação por relação preserva contexto do browser;
- [ ] seletor de variantes permanece bloqueado até o Stage 10.13 transportar somente variantes descobertas/autorizadas.

Layout, tabs/painéis e hierarquia visual dessa página pertencem a Textura.

## C — Preview de entidade: segurança

Contratos existentes:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/render/CompendiumEntityPreview.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/render/EntityPreviewFactory.java
```

- [x] client-only;
- [x] instância de preview nunca participa do mundo real;
- [x] não dispara AI, loot, sounds, particles ou side effects;
- [x] fallback técnico quando renderer/construção não puder ser usado;
- [x] blacklist/adapter para entidades que não podem ser construídas de forma segura;
- [x] falha de renderer modded não derruba o catálogo inteiro;
- [ ] variantes só podem ser selecionadas quando houver representação segura e autorização no snapshot.

Política atual:

- entidades `minecraft:*` vivas podem usar construção vanilla destacada por padrão;
- entidades de terceiros ficam fail-closed até adapter explícito declarar construção segura;
- preview não é adicionado ao `ClientLevel` nem recebe tick pelo Compêndio;
- blacklist explícita vence adapter;
- falha de construção/render coloca o tipo em quarentena durante a sessão e usa fallback;
- adapters de preview devem retornar entidade viva destacada sem adicioná-la/tická-la no mundo.

Câmera, enquadramento, background, controles visuais e fallback gráfico pertencem a Textura.

## D — Variantes: boundary anti-vazamento

Estado conhecido:

- `DiscoveryRecord` persiste `variantIds`;
- runtime de fauna consegue produzir snapshot da variante observada;
- o `CompendiumClientSnapshot` atual não transporta a lista autorizada de `variantIds` descobertos;
- feeds genéricos de descoberta ainda podem publicar sinais sem `variantId`.

Logo, a UI não pode montar lista de variantes pelo registry/catálogo completo. O seletor permanece fail-closed até o 10.13 projetar somente variantes permitidas.

## E — Flora, árvores, crops, estruturas e worldgen previews

- [x] `FLORA`, `TREE`, `CROP` e `BLOCK_FEATURE` usam política pura de preview por registry;
- [x] resolver procura `Block` canônico e usa `Block#asItem()` quando existe representação segura;
- [x] blocos sem item (`Items.AIR`) e IDs ausentes usam fallback sem inventar representação;
- [x] renderer cliente usa representação segura em item e contém falha localmente;
- [x] `STRUCTURE`, `BIOME` e `DIMENSION` permanecem metadata-only enquanto não houver asset próprio/proveniência válida;
- [x] nenhuma estrutura inteira é renderizada em 3D nesta versão;
- [x] lookup de registry permanece fora do boundary client-only e é validado em ambiente NeoForge bootstrapped.

A decisão futura de renderer direto de `BlockState` continua bloqueada até existir API 1.21.1 confirmada e matriz de segurança suficiente. Asset/screenshot/ícone futuro é problema de Textura depois desse boundary técnico.

## F — Notas pessoais

Modelo funcional existente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/CompendiumNotesModel.java
```

- [x] nota indexada por `CompendiumEntryId` canônico;
- [x] texto livre armazenado literalmente;
- [x] limite de 4.096 code points no modelo;
- [x] editor client-only usa limite compatível;
- [x] não interpretar/executar formatting, comandos ou markup da nota;
- [x] notas privadas/client-local por padrão neste estágio;
- [x] nenhum envio a outros jogadores;
- [x] IDs ausentes do catálogo atual continuam preserváveis durante a vida do modelo;
- [x] troca de entrada não mistura textos;
- [x] foco no editor não dispara navegação da lista;
- [ ] persistência entre reconnect/restart/save pertence ao 10.13;
- [ ] política para notas de conteúdo removido depende do armazenamento do 10.13.

A indicação visual de sessão atual, painel e layout do editor pertencem a Textura; a persistência continua sem promessa até o 10.13 fechar o contrato.

## G — Favoritos, recentes e debug

- [x] favoritar entradas;
- [x] histórico recente pode ser limitado;
- [x] favoritos não interferem na descoberta;
- [x] modo avançado pode expor `ResourceLocation`, source mod, `FactSource`, provider/origem e coverage status quando autorizados;
- [x] modo survival normal não depende desses IDs técnicos.

A apresentação desses controles e tooltips fica em Textura.

## H — Acesso e side

- [x] keybind configurável `key.rpgskilltree.open_compendium`, padrão `J`;
- [x] registro e abertura da tela ficam em subscriber `Dist.CLIENT`;
- [x] dedicated-server smoke continua obrigatório;
- [ ] botão adicional na UI do RPG é decisão de apresentação e deve ser especificado em Textura se adotado;
- [ ] população real do `ClientCompendiumState` por pipeline de produção ainda depende do snapshot/protocolo do 10.13; sem isso o shell pode abrir com snapshot vazio.

## Testes funcionais relevantes

Já existentes:

```text
CompendiumBrowserModelTest
CompendiumSearchIndexTest
CompendiumFilterStateTest
CompendiumPageModelFactoryTest
CompendiumScreenLayoutTest
CompendiumScreenSessionTest
CompendiumRelationNavigationTest
CompendiumRelationPanelStateTest
CompendiumNotesModelTest
```

Continuam necessários, conforme o runtime evoluir:

- injected renderer failure comprovando fallback funcional;
- variantes apenas quando autorizadas pelo 10.13;
- persistência de notas conforme contrato futuro;
- dedicated-server/client boundaries sem classloading indevido.

Validação de estética, clipping, contraste, UI scale e qualidade do preview pertence ao companion de Textura e não substitui estes testes.

## Acceptance funcional

O cliente consegue pesquisar/navegar um snapshot autorizado, construir previews somente pelos caminhos seguros, manter estado local de notas/favoritos conforme o contrato atual e falhar fechado para variantes/renderer inseguros sem transformar apresentação em authority.
