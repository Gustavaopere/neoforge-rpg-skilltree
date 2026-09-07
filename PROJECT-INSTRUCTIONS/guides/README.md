# Guias Canônicos do Modpack

Este diretório concentra os **quatro guias operacionais canônicos** usados nas auditorias, design de perks, integrações e manutenção técnica do modpack.

## Snapshot operacional atual

Referência reconciliada em **2026-09-07**:

- **612 entradas top-level**, incluindo NeoForge;
- NeoForge + **611 JARs** físicos;
- dependências `jarjar` embutidas não são contadas como mods top-level;
- para presença, filename e runtime, `CURRENT-MODLIST.md` + [`MODLIST-DELTA-2026-09-07.md`](./MODLIST-DELTA-2026-09-07.md) prevalecem sobre versões históricas dentro dos capítulos.

## Guias completos consolidados

1. [`GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`](./GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md)
2. [`GUIA-COMPLETO-MODS-DE-MAGIA.md`](./GUIA-COMPLETO-MODS-DE-MAGIA.md)
3. [`GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`](./GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md)
4. [`GUIA-COMPLETO-PROJETOS-PROPRIOS.md`](./GUIA-COMPLETO-PROJETOS-PROPRIOS.md)

## Árvores editoriais/fontes

- [`gameplay/`](./gameplay/) — Gameplay e Sistemas.
- [`magic/`](./magic/) — Mods de Magia.
- [`technology/`](./technology/) — Mods de Tecnologia.
- [`projects/`](./projects/) — Projetos próprios: RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e integração cruzada.

Os arquivos completos acima são a entrada operacional compacta. As árvores editoriais preservam capítulos, inventários `CURRENT-MODLIST.md`, fontes e material de auditoria detalhado.

## Autoridade e manutenção

- A modlist física atual é autoridade de presença/JAR/runtime.
- O Notion mantém a Auditoria Mestre e o estado curatorial do catálogo.
- Update de versão não prova continuidade de hook/API.
- Biblioteca, UI, VFX, bridge ou compat não se tornam provider de perk automaticamente.
- Providers e bridges devem seguir provider-native first, authority explícita, gates, deduplicação, causalidade, fallback e fail-closed conforme os protocolos em [`../perks/`](../perks/).
- Projetos próprios exigem reconciliação de capability delta, provider → árvore e perk → provider antes de fechar design quando pertinentes.

Este diretório substitui como localização operacional o antigo `plans/03-skill-tree-perks/guides/`. Não mantenha uma segunda árvore editorial ativa naquele caminho.
