# Auditorias de Perks

Esta pasta concentra as auditorias históricas e por lote do sistema de perks. Os dossiês individuais `Axxxx-*.md`, `STATUS.md`, critérios e regras operacionais permanecem na raiz de `perks/`.

## Auditorias de implementação — Chat 2

- `AUDITORIA-A0001-A0010-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`

## Auditorias pós-merge e pendências técnicas — Chat 3

- `AUDITORIA-CHAT3-A0001-A0010-PENDENCIAS-TECNICAS.md`
- `AUDITORIA-CHAT3-A0011-A0020-PENDENCIAS-TECNICAS.md`

## Auditoria consolidada histórica

- `AUDITORIA-A0001-A0020.md`

## Auditorias retroativas provider→árvore — Chat 1

- `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0061-A0070.md` — fechamento Chat 1 das fundações MARTIAL, incluindo availability de A0067 e taxonomia de bosses de A0070.

## Auditorias de delta da modlist — Chat 1

Reauditoria especial acumulada A0001–A0050 contra Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated Simply Swords 1.4.0, Simply Swords: Cataclysm 1.0.2, Simply Tooltips 0.1.5 e Epic Fight Compat 1.1.0. O trabalho foi mantido em cinco sublotes exatos de 10 dentro da mesma PR para um único merge final.

- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0001-A0050.md` — **tracker canônico deste ciclo especial para resultado e handoffs Simply** (`P-SIMPLY-A0001-50-01`, `P-SIMPLY-A0006-01`, `P-SIMPLY-ALPHA-01`). Chat 2 deve consultá-lo em conjunto com `STATUS.md`; esses handoffs não podem ser considerados ausentes apenas porque não pertencem ao índice histórico geral.
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0001-A0010.md`
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0011-A0020.md`
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0021-A0030.md`
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0031-A0040.md`
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0041-A0050.md`

Novos lotes devem ser criados aqui, nunca novamente na raiz de `perks/`.

## Convenção

1. Um arquivo por lote/ciclo quando a auditoria é operacionalmente distinta.
2. Não consolidar tudo em um único arquivo gigante: isso piora diffs, revisão e rastreabilidade de PRs.
3. `STATUS.md` é o índice de estado geral. Quando uma auditoria especial declarar explicitamente um tracker canônico complementar no índice desta pasta, esse tracker também é fonte obrigatória para os handoffs daquele ciclo.
4. Chat 1, Chat 2 e Chat 3 devem procurar a auditoria correspondente em `plans/03-skill-tree-perks/perks/audits/`; para o delta Simply A0001–A0050, o consolidado acima é leitura obrigatória junto ao `STATUS.md`.
5. Dossiês `Axxxx-*.md` permanecem fora desta pasta para navegação direta por perk.
