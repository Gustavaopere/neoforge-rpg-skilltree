# 07 — Data, Network & UI

Carregar dados com validação forte e apresentar progressão no cliente sem transferir autoridade de gameplay para a interface.

Ordem: schemas → reload/snapshots → protocolo → sync do jogador → tela da árvore → localização/acessibilidade.

## 07.05 — Skill Tree UI

`05-skill-tree-ui.md` é o plano canônico da tela unificada de progressão. Ele cobre o cosmograma completo **Árvore 1 (Atributos) → Árvore 2 (Perks Principais / 11 domínios) → constelações das classes emergentes → Árvore 3 (Especialistas)**, incluindo:

- as 23 classes atualmente materializadas e as identidades-alvo Ranger/Hunter e Death Knight sem fingir runtime inexistente;
- as 25 especializações atualmente materializadas e seu mapeamento para formas simbólicas;
- subtrees dedicadas de Technomancer, Warlock, Druid e Metamorph;
- shapes data-driven, layout determinístico, bridges compartilhadas e prevenção de duplicação visual;
- pan/zoom, LOD, viewport culling, busca, filtros, breadcrumbs, tooltips e acessibilidade;
- fail-closed visual, autoridade server-side, validators, TDD, performance, build e dedicated-server safety.

A UI nunca pode criar node, classe, especialização, requisito, recurso ou unlock que não exista no estado/catálogo autoritativo.
