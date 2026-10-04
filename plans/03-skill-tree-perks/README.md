# 03 — Skill Tree & Perks

Este diretório é o plano canônico do redesenho do catálogo de perks.

O catálogo histórico `A####` foi aposentado desta pasta. Os documentos técnicos concluídos do Stage 03 (`✅-01` a `✅-05`) permanecem como infraestrutura existente, mas não definem o novo catálogo de conteúdo.

## Estado atual

A criação massiva de perks está **congelada até a reconciliação da modlist e da matriz de capacidades**.

O plano operacional completo está em [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md). Ele define gates, entregáveis, ordem de execução, validações e decisões que não podem ser antecipadas.

O fluxo é:

`authority do pack -> matriz de capacidades -> identidade das 11 árvores -> budgets -> seleção curada de Abilities -> Standard Perks -> Transmutations -> especializações + Mecânicas Assinatura -> auditoria técnica -> runtime -> topologia/UX/wiki`.

## Estrutura

- [`01-internal-attributes/`](./01-internal-attributes/) — seis atributos fundamentais comuns.
- [`02-standard-perks/`](./02-standard-perks/) — perks normais das 11 árvores.
- [`03-transmutation-perks/`](./03-transmutation-perks/) — uma raiz `Txxxx` por Ability selecionada, com LEFT/RIGHT/METAMORPHOSIS.
- [`04-specialization-perks/`](./04-specialization-perks/) — especializações PURE/HYBRID organizadas pelo owner tree.
- [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md) — plano mestre de execução.
- [`DOSSIER-CONTRACT.md`](./DOSSIER-CONTRACT.md) — conteúdo obrigatório de cada P/T/S.
- [`SOURCES-AND-SELECTION-POLICY.md`](./SOURCES-AND-SELECTION-POLICY.md) — fontes e seleção curada das Abilities.
- [`SPECIALIZATION-CONTRACT.md`](./SPECIALIZATION-CONTRACT.md) — especializações e Mecânicas Assinatura.
- [`CATALOG-CONTRACT.md`](./CATALOG-CONTRACT.md) — paridade e ownership.
- [`NUMBERING.md`](./NUMBERING.md) — IDs e ranges.
- [`06-content-wiki-generation.md`](./06-content-wiki-generation.md) — fechamento de runtime/wiki.

## Regras curtas

Não criar perks porque um mod existe. Não criar especialização porque uma escola existe. Não selecionar Abilities para Transmutation antes da rubrica e quotas fecharem. Não implementar antes da auditoria técnica. Não desenhar topologia definitiva antes do runtime.

Diablo IV e outros jogos podem fornecer padrões de decisão e inspiração, nunca requisitos a copiar literalmente.
