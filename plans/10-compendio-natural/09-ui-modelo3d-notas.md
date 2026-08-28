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

- [ ] client-only;
- [ ] instância de preview nunca participa do mundo real;
- [ ] não dispara AI, loot, sounds, particles ou side effects;
- [ ] rotação/zoom controlados;
- [ ] fallback para ícone/texto se renderer falhar;
- [ ] blacklist/adapter para entidades que não podem ser construídas de forma segura;
- [ ] crash de renderer modded não deve derrubar catálogo inteiro;
- [ ] variantes podem ser selecionadas quando houver representação segura.

### D — Preview de flora/árvore/estrutura

- flora/árvore: item/block rendering quando disponível;
- estrutura: ícone/screenshot próprio somente se houver asset/proveniência; não tentar renderizar estrutura inteira 3D na primeira versão;
- bioma/dimensão: ícones e metadata; screenshots são opcionais e precisam de pipeline/asset próprio.

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

- [ ] favoritar entradas;
- [ ] opcionalmente manter lista limitada de últimas entradas abertas;
- [ ] não criar histórico ilimitado;
- [ ] favoritos não interferem na descoberta.

### G — Tooltip de proveniência/debug

Em modo avançado/debug, permitir visualizar:

- `ResourceLocation`;
- source mod;
- `FactSource`;
- provider que forneceu o dado;
- coverage status.

No modo survival normal, esses detalhes não devem poluir a leitura.

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
