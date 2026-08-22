# NeoForge RPG Skill Tree

Projeto privado para Minecraft **1.21.1 / NeoForge** que unifica progressão de personagem em uma árvore passiva grande e visualmente densa, inspirada em Path of Exile/Path of Exile 2, usando a interface e a engine do Passive Skill Tree como base.

## Objetivo

Construir uma única experiência de progressão que integre, sem duplicar bônus equivalentes:

- Passive Skill Tree / community port NeoForge 1.21.1
- Iron's Spells 'n Spellbooks e addons
- Ars Nouveau e addons
- Epic Fight / Battle Arts e compatibilidades
- atributos genéricos do pack quando fizer sentido

A árvore principal deve ser ampla e orgânica. Keystones podem desbloquear árvores especializadas. Exemplo: progressão arcana -> **Mestre do Fogo** -> árvore especializada de Fire, cujo XP é obtido usando magia de fogo.

## Princípios

1. **Uma UI principal:** a apresentação visual do Passive Skill Tree.
2. **Um atributo canônico por conceito:** evitar `spell damage`, `magic damage`, `generic spell power` etc. empilhados sem necessidade.
3. **Integração por capacidades/APIs:** reconhecer escolas do Iron's, glyphs/stats do Ars e categorias/movesets/skills do Epic Fight, em vez de hardcode por item quando possível.
4. **Progressão temática:** especialização deve exigir uso real da mecânica correspondente.
5. **Builds, não só números:** nodes pequenos dão incrementos; notables e keystones mudam comportamento, sinergias e restrições.
6. **Compatibilidade com addons:** novos conteúdos que respeitem as APIs principais devem herdar integrações automaticamente sempre que possível.

## Status

Fase atual: **arquitetura e inventário técnico**.

Documentos principais:

- `docs/specs/2026-08-22-unified-rpg-skill-tree-design.md`
- `docs/research/integration-inventory.md`
- `SOURCES.md`

A implementação será feita em branches e PRs separados; `main` permanece estável.
