# 03 — Skill Tree & Perks

Estado: **EM ANDAMENTO**.

## Base atual

A árvore principal possui **512 nós JSON materializados**. `generated/main-tree-layout.json` comprova `target_node_count = 512`, `actual_node_count = 512` e `node_budget_satisfied = true`.

Isso não significa que existam 512 efeitos mecânicos distintos finalizados. Os arquivos de skill definem a estrutura da árvore e vários têm `bonuses: []`; efeitos mecânicos também são declarados separadamente em `data/rpgskilltree/node_effects` e handlers runtime.

## Objetivo

Manter uma árvore extensa, legível e data-driven, com validação forte de IDs, dependências e efeitos.

## Inventário da árvore principal

- Core: 28
- Regiões principais: 420
- Bridges híbridas: 48
- Keystones externos: 16
- Total: 512

## Critérios de aceite

- [x] orçamento estrutural 512/512 no layout gerado;
- [ ] todo efeito declarado referencia contratos válidos;
- [ ] requisitos formam grafo válido;
- [ ] nenhum ID duplicado;
- [ ] desbloqueio é server-authoritative;
- [ ] respec não deixa atributos/efeitos órfãos;
- [ ] catálogo da wiki pode ser regenerado automaticamente a partir dos dados.