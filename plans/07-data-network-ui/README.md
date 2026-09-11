# 07 — Data, Network & UI

Carregar dados com validação forte e apresentar progressão no cliente sem transferir autoridade de gameplay para a interface.

Ordem: schemas → reload/snapshots → protocolo → sync do jogador → tela da árvore → localização/acessibilidade.

## 07.05 — Skill Tree UI

`05-skill-tree-ui.md` é o plano canônico do **alvo visual** da progressão: cosmograma **Árvore 1 (Atributos) → Árvore 2 (Perks Principais / 11 domínios) → constelações das classes emergentes → Árvore 3 (Especialistas)**.

O plano cobre:

- as 23 classes atualmente definidas e as identidades-alvo Ranger/Hunter e Death Knight sem fingir runtime inexistente;
- as 25 specialization definitions atuais e seu mapeamento para emblemas simbólicos;
- subtrees dedicadas atuais de Technomancer, Warlock, Druid e Metamorph;
- shape fitting que só usa nodes/edges reais quando uma subtree existe;
- prevenção de fake specialist nodes e de duplicação de especializações compartilhadas;
- layout determinístico, pan/zoom, LOD, busca, filtros, breadcrumbs, tooltips e acessibilidade;
- fail-closed visual, autoridade server-side, validators, TDD, profiling, build e dedicated-server safety.

**Gate obrigatório:** `docs/decisions/README.md` mantém D001 (Passive Skill Tree versus custom UI) aberta. O vertical slice exigido pelo `docs/MASTER_PLAN.md` e a ADR D001 devem ser concluídos **antes** da implementação integral de 07.05. O plano descreve o resultado de produto; ele não escolhe silenciosamente a engine.

A UI nunca pode criar node, classe, especialização, requisito, recurso ou unlock que não exista no estado/catálogo autoritativo.
