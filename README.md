# NeoForge RPG Skill Tree

Projeto privado para Minecraft **1.21.1 / NeoForge** que unifica a progressão de personagem em uma árvore passiva ampla, inspirada em Path of Exile, usando o modelo visual/runtime do Passive Skill Tree como base.

## Objetivo

Construir uma única experiência de progressão que integre, sem duplicar bônus equivalentes:

- Passive Skill Tree / community port NeoForge 1.21.1;
- Iron's Spells 'n Spellbooks e addons;
- Ars Nouveau e addons;
- Epic Fight / Battle Arts;
- Create, Curios e demais sistemas do modpack quando houver integração mecânica real.

Classes surgem do investimento na árvore, sem escolha permanente nem bloqueio entre caminhos. Especializações combinadas, como Spellblade, Battlemage, Arcane Archer e Technomancer, têm prioridade quando seus requisitos são preenchidos.

## Alpha 1 — Core Architecture

Implementado neste checkpoint:

- resolução determinística de modificadores canônicos;
- 10 arquétipos/classes emergentes;
- requisitos de gateway e XP de mastery separados dos pontos da árvore;
- catálogo de atributos canônicos para evitar bônus equivalentes duplicados;
- contratos normalizados de ações de magia, combate e engenharia;
- proteção contra recursão de procs;
- políticas de mastery para Iron's, Ars, Epic Fight e Create;
- modelo seguro de redimensionamento/ejeção de Attunement;
- blueprint principal com **420 nós únicos e 664 conexões**;
- 15 gateways iniciais de subárvores.

## Princípios

1. **Uma UI principal:** apresentação visual baseada no Passive Skill Tree.
2. **Um atributo canônico por conceito:** evitar empilhamento acidental de atributos equivalentes.
3. **Integração por capacidades e APIs:** preferir escolas, glyphs, categorias e eventos registrados a hardcode por item.
4. **Progressão temática:** uso real da disciplina concede mastery.
5. **Builds com comportamento:** notables e keystones devem alterar mecânicas, sinergias ou restrições.
6. **Compatibilidade com addons:** conteúdos que respeitem as APIs principais devem herdar integrações quando possível.

## Verificação local

```bash
./scripts/test-core.sh
./scripts/validate-data.py
./scripts/generate-tree-skeleton.py
```

Resultados do Alpha 1:

- `CoreProgressionTest: PASS`
- `Data validation: PASS (10 archetypes, 15 tree gateways, 420 main-tree nodes budgeted)`
- layout: `420 unique nodes / 664 edges`

## Documentação

- `RELEASE_NOTES_ALPHA_1.md`
- `docs/ARCHITECTURE.md`
- `docs/MODPACK_SCOPE.md`
- `docs/integrations/`
- `docs/specs/2026-08-22-unified-rpg-skill-tree-design.md`
- `docs/research/integration-inventory.md`
- `SOURCES.md`

> Limitação conhecida: este Alpha 1 é um checkpoint de arquitetura e código-fonte. Ainda não produz um JAR instalável do mod.
