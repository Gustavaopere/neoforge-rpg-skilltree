# Instruções Operacionais do Projeto

Este diretório de primeiro nível é a **fonte canônica de instruções operacionais** do projeto `neoforge-rpg-skilltree`.

A organização existe para evitar cópias concorrentes espalhadas por `plans/`, anexos de chats e diretórios temáticos. Código, status de implementação e dossiês individuais permanecem em seus diretórios próprios; somente instruções, guias e governança de catálogo/modlist ficam centralizados aqui.

## Estrutura

- [`perks/`](./perks/) — critérios obrigatórios e protocolos dos Chats 1, 2 e 3 para lotes de perks.
- [`guides/`](./guides/) — quatro guias completos consolidados e as árvores editoriais de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios.
- [`modlist/`](./modlist/) — navegação e regras de autoridade para modlist, auditoria e catálogo de mods.

## Autoridades

### Presença, JAR e versão instalada

A **modlist física/runtime mais recente** é a autoridade primária. Dependências `jarjar` embutidas não contam como mods top-level independentes.

Snapshot reconciliado em 2026-09-07: **612 entradas top-level incluindo NeoForge**, isto é, NeoForge + 611 JARs.

### Catálogo e auditoria

A **Auditoria Mestre da Modlist — NeoForge 1.21.1** no Notion mantém o estado curatorial e de auditoria do catálogo. O GitHub não deve criar uma segunda cópia manual concorrente dessa base; os guias e deltas versionados registram o recorte técnico necessário ao repositório.

Notion: https://app.notion.com/p/34e80068b1ae402d8e32db7456044bab?v=fbaad1c305a344e4b1ac4b8dc2e1c6f2

### Estado de implementação

`plans/STATUS.md` continua sendo a autoridade de estado dos planos/implementações e **não é movido para este diretório**.

### Dossiês individuais de perks

Os dossiês por perk continuam em `plans/03-skill-tree-perks/perks/`. Este diretório contém os protocolos e guias usados para produzir e validar esses dossiês, não os substitui.

## Regra de manutenção

1. Atualizações de instruções operacionais devem ser feitas aqui.
2. Os quatro guias consolidados devem permanecer sincronizados com suas árvores editoriais e com a modlist física atual.
3. `CURRENT-MODLIST.md` e o delta mais recente prevalecem sobre versões históricas embutidas em capítulos quando a questão for presença/JAR/runtime.
4. Nenhuma mudança de organização deste diretório autoriza alterar silenciosamente código, semântica de perk, `plans/STATUS.md` ou decisões já mergeadas por outros trabalhos.
5. Movimentos de documentação devem preservar o histórico e não reverter commits posteriores da `main`.
