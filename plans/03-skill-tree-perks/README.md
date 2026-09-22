# 03 — Skill Tree & Perks

Este diretório é o plano canônico do redesenho do catálogo de perks.

O catálogo histórico `A####` foi aposentado desta pasta. Os documentos técnicos concluídos do Stage 03 (`✅-01` a `✅-05`) permanecem como infraestrutura existente, mas não definem o novo catálogo de conteúdo.

## Estado atual

A criação massiva de perks está **congelada até a reconciliação da modlist e da matriz de capacidades**.

O fluxo canônico está em [`00-EXECUTION-ROADMAP.md`](./00-EXECUTION-ROADMAP.md). A ordem é:

`modlist física -> capacidades -> distribuição nas 11 árvores -> especializações -> catálogo conceitual -> auditoria técnica por provider -> implementação -> topologia visual`.

Não criar perks a partir de um mod apenas porque ele existe, e não escolher especializações copiando estruturas antigas do runtime.

## Estrutura

- [`01-internal-attributes/`](./01-internal-attributes/) — seis atributos fundamentais comuns.
- [`02-standard-perks/`](./02-standard-perks/) — perks normais das 11 árvores.
- [`03-transmutation-perks/`](./03-transmutation-perks/) — uma raiz `Txxxx` por Ability e modificadores `Txxxx.n`.
- [`04-specialization-perks/`](./04-specialization-perks/) — especializações reconstruídas do zero depois da matriz de capacidades.
- [`CATALOG-CONTRACT.md`](./CATALOG-CONTRACT.md) — paridade, ownership, stacking e regras de catálogo.
- [`NUMBERING.md`](./NUMBERING.md) — códigos editoriais e ranges.
- [`06-content-wiki-generation.md`](./06-content-wiki-generation.md) — fechamento posterior do catálogo, runtime e wiki.

O desenho visual/topológico definitivo não é fixado nesta etapa. Primeiro fecha-se o conteúdo e sua taxonomia; depois a topologia será construída sobre um catálogo estável.
