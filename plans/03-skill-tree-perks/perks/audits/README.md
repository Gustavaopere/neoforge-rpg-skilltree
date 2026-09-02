# Auditorias de Perks

Esta pasta concentra as auditorias históricas e por lote do sistema de perks. Os dossiês individuais `Axxxx-*.md`, `STATUS.md`, critérios e regras operacionais permanecem na raiz de `perks/`.

## Auditorias de implementação — Chat 2

- `AUDITORIA-A0001-A0010-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`
- `AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`

## Auditorias pós-merge e pendências técnicas — Chat 3

- `AUDITORIA-CHAT3-A0001-A0010-PENDENCIAS-TECNICAS.md`
- `AUDITORIA-CHAT3-A0011-A0020-PENDENCIAS-TECNICAS.md`
- `AUDITORIA-CHAT3-A0021-A0030-PENDENCIAS-TECNICAS.md`

## Auditoria consolidada histórica

- `AUDITORIA-A0001-A0020.md`

## Auditorias retroativas provider→árvore — Chat 1

- `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`
- `AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`

## Auditorias de fechamento por lote — Chat 1

- `AUDITORIA-A0061-A0070.md` — fechamento de design A0061–A0070, incluindo correção fail-closed/unavailable de A0067, cobertura BOSS de A0070 e gate de delta dos quatro projetos próprios.
- `AUDITORIA-A0071-A0080.md` — fechamento de design A0071–A0080, incluindo availability transitiva, reservation→commit, posturas server-authoritative, sustain all-or-nothing, movement/stationary e dodge-success fail-closed.
- `AUDITORIA-A0081-A0090.md` — fechamento de design A0081–A0090, incluindo sustain/vampirismo multi-provider, native lifesteal dedup, availability magic/element/DoT, body tradeoffs e fundação VITALITY.
- `AUDITORIA-A0200-A0209.md` — checkpoint histórico inicial Eldritch/Ender, preservado para rastreabilidade.
- `AUDITORIA-A0200-A0299.md` — auditoria especial autorizada de 100 perks, com tracker individual A0200–A0299, Notion 100/100, providers, projetos próprios, blockers e handoff fail-closed.
- `AUDITORIA-A0191-A0199.md` — fechamento excepcional do bloco residual explicitamente autorizado entre a sequência normal A0181–A0190 e a faixa A0200+ já auditada; BLOOD/ELDRITCH provider-native, 7 unavailable, A0193/A0194 defensivas implementáveis e contratos all-or-nothing preservados.
- `STATUS-A0191-A0199.md` — tracker complementar autoritativo deste bloco residual enquanto a cadeia documental predecessor permanece em PRs abertas.

### Sequência concorrente A0141–A0190

As auditorias abaixo permanecem em PRs de Chat 1 abertas e não são copiadas para esta branch, evitando sobreposição destrutiva:

- `AUDITORIA-A0141-A0150.md` — PR #361;
- `AUDITORIA-A0151-A0160.md` — PR #366;
- `AUDITORIA-A0161-A0170.md` + tracker complementar — PR #368;
- `AUDITORIA-A0171-A0180.md` + tracker complementar — PR #375;
- `AUDITORIA-A0181-A0190.md` + tracker complementar — PR #380.

A reconciliação futura deve preservar A0141–A0199 em ordem e manter intacta a auditoria especial A0200+ já mergeada. Até lá, `STATUS.md` raiz não deve ser sobrescrito por uma cópia derivada da `main` incompleta.

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