# Auditorias de Perks

Esta pasta concentra as auditorias históricas e por lote do sistema de perks. Os dossiês individuais `Axxxx-*.md`, `STATUS.md`, critérios e regras operacionais permanecem na raiz de `perks/`.

## Auditorias de implementação — Chat 2

- `AUDITORIA-A0001-A0010-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`

## Auditorias pós-merge e pendências técnicas — Chat 3

- `AUDITORIA-CHAT3-A0001-A0010-PENDENCIAS-TECNICAS.md`

## Auditoria consolidada histórica

- `AUDITORIA-A0001-A0020.md`

## Auditorias retroativas provider→árvore — Chat 1

- `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`

Novos lotes devem ser criados aqui, nunca novamente na raiz de `perks/`.

## Convenção

1. Um arquivo por lote/ciclo quando a auditoria é operacionalmente distinta.
2. Não consolidar tudo em um único arquivo gigante: isso piora diffs, revisão e rastreabilidade de PRs.
3. `STATUS.md` é o índice de estado; esta pasta guarda a evidência detalhada.
4. Chat 1, Chat 2 e Chat 3 devem procurar a auditoria correspondente em `plans/03-skill-tree-perks/perks/audits/`.
5. Dossiês `Axxxx-*.md` permanecem fora desta pasta para navegação direta por perk.
