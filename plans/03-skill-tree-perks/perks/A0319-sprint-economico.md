# A0319 — Sprint Econômico

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por ausência de cost hook causal/precommit seguro.
- **Fonte canônica:** Notion `A0319` — https://app.notion.com/3c569db9f0db81b8b262dc6cd6341dae
- **Correção/persistência:** Provider/Mods, Hook e Regra corrigidos e re-fetched em 2026-09-05.

## Contrato aprovado

Cada **débito real e causal de sprint** pode receber duas lanes independentes, somente quando a authority correspondente expuser precommit mutável:

### STAMINA

- rank1: custo ×0,97;
- rank2: ×0,94;
- rank3: ×0,91;
- rank4: ×0,88.

Equivale a −3% por rank, máximo −12%.

### METABOLIC / exhaustion

- rank1: custo ×0,98;
- rank2: ×0,96;
- rank3: ×0,94;
- rank4: ×0,92.

Equivale a −2% por rank, máximo −8%.

Cada fórmula atua apenas sobre sua lane, uma vez e **antes** do commit. Zero permanece zero. A0319 não cria custo ausente, não converte STAMINA em hunger/exhaustion e não toca HYDRATION.

## Gate e disponibilidade

Pré-requisito estrutural: A0318 Passo Leve ≥1. Porém a identidade de A0319 exige pelo menos uma lane de custo de sprint causal/precommit tecnicamente modificável. Na `main` auditada não existe `BodyCostResolver`, stamina-cost modifier ou sprint/exhaustion receipt seguro; por isso o node inteiro permanece `UNAVAILABLE_NODE` e compra falha antes do gasto.

## Providers e versões atuais

- **Epic Fight 21.17.3.1:** candidato à lane STAMINA apenas se adapter versionado expuser o débito nativo específico e mutável precommit.
- **ParCool 4.0.0.3** e **Epic ParCool 21.0.0:** presentes na modlist de 2026-08-30. Podem compartilhar/encaminhar stamina, mas presença/animação/compat não provam hook de débito.
- **Minecraft/NeoForge 1.21.1 FoodData/exhaustion:** authority metabólica; ainda falta boundary que isole somente a parcela causal de sprint antes do commit.

Se Epic Fight/ParCool/Epic ParCool convergirem na mesma pool de stamina, o mesmo débito é **um receipt** e recebe no máximo uma contribuição A0319.

## Anti-abuso e proibições

Não implementar polling, distância percorrida, timer paralelo, cancel/readd, reembolso posterior, regeneração maior, food/stamina injection ou aproximação do custo. Não reduzir outros débitos de stamina/exhaustion sem provenance de sprint.

## Fallback

Sem hook precommit seguro: `UNAVAILABLE_NODE`. Allocation legado indisponível = 0 PP para gates/thresholds e continua reembolsável/migrável. Não converter a perk em movement speed ou regen.

## Testes obrigatórios para Chat 3

1. snapshot atual: purchase fail-before-spend mesmo com A0318;
2. legacy unavailable = 0 PP e migrável/reembolsável;
3. futuro STAMINA resolver: ×0,97/0,94/0,91/0,88 exatos;
4. futuro METABOLIC resolver: ×0,98/0,96/0,94/0,92 exatos;
5. zero cost permanece zero e outros custos não-sprint não mudam;
6. shared stamina pool recebe contribuição uma única vez entre Epic Fight/ParCool/Epic ParCool;
7. nenhuma restituição pós-commit/injection/polling/distância;
8. uma lane ausente não bloqueia artificialmente a outra quando o design for reaberto, mas não cria custo substituto;
9. provider absent/version mismatch/provenance ambígua falha fechado;
10. server-authoritative, multiplayer e dedicated server.

## Handoff Chat 2

No snapshot atual, preservar availability fail-closed. Não fabricar receipt de sprint nem aproximar custo por movimento observado.