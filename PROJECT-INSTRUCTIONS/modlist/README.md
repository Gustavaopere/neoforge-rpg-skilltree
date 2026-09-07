# Modlist — autoridade, auditoria e documentação

Este diretório define **onde consultar e como reconciliar** a modlist do projeto. Ele não é uma cópia manual da base inteira do Notion.

## Ordem de autoridade

1. **Modlist física/runtime mais recente** — autoridade para presença, JAR e versão realmente carregada.
2. **Auditoria Mestre da Modlist — NeoForge 1.21.1 (Notion)** — catálogo, decisão curatorial, pesquisa, riscos, compatibilidade e histórico.
3. **Guias versionados deste repositório** — documentação técnica/editorial necessária a perks, integrações e manutenção.

Notion: https://app.notion.com/p/34e80068b1ae402d8e32db7456044bab?v=fbaad1c305a344e4b1ac4b8dc2e1c6f2

## Snapshot reconciliado

Em 2026-09-07, a modlist física reconciliada possui **612 entradas top-level incluindo NeoForge**: NeoForge + **611 JARs**. Dependências `jarjar` embutidas não contam como mods top-level independentes.

## Documentação no GitHub

- Delta físico atual: [`../guides/MODLIST-DELTA-2026-09-07.md`](../guides/MODLIST-DELTA-2026-09-07.md)
- Gameplay/Sistemas: [`../guides/gameplay/CURRENT-MODLIST.md`](../guides/gameplay/CURRENT-MODLIST.md)
- Magia: [`../guides/magic/CURRENT-MODLIST.md`](../guides/magic/CURRENT-MODLIST.md)
- Tecnologia: [`../guides/technology/CURRENT-MODLIST.md`](../guides/technology/CURRENT-MODLIST.md)
- Projetos próprios: [`../guides/projects/`](../guides/projects/)

Os mods que o trabalho de auditoria da modlist documenta devem ser incorporados às árvores de guia pertinentes e aos deltas versionados quando houver mudança física. O estado vivo completo permanece no Notion; não duplique centenas de linhas manualmente neste diretório.

## Regra para divergência

Se Notion, guia histórico e runtime divergirem sobre presença/JAR/versão, **o runtime/modlist física prevalece** e as demais fontes devem ser reconciliadas. Não inferir mod top-level a partir de dependência interna jar-in-jar.
