# A0033 — Precisão com Maças

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252 após correção da família MACE.
- **Notion:** `3c569db9-f0db-8155-a97c-f4db0a54c59a`.

## Contrato canônico

- A0031 ≥1.
- +3% chance crítica MACE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- `ARCANE_BACKLASH`, proc terminal/secundário e dano de ally/bodyguard Mobstein são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(MACE)` mapeia A0033.
- `A0021A0040EpicFightHooks` reutiliza `A0001A0020RuntimeState.critical()` e correlaciona CriticalHitEvent/Epic Fight para evitar segunda rolagem.
- A resolução MACE da PR #252 não usa mais tag paralela: capability/categoria Epic Fight ou `Items.MACE` exato.
- Root action e critical service permanecem compartilhados com o pipeline canônico já mergeado; o lote A0031–A0040 preserva as regressões causais A0021–A0030.

## Provider→árvore

Black Arcana conserva authority de Backlash; Mobstein conserva ownership de companion damage. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Pendência Chat 2 / resolução Chat 3

A dependência de `P-A0031-01` foi encerrada. Nenhuma bridge específica adicional é necessária.

## Validação Chat 3 — PR #252

- Regressão transversal confirma preservação do pipeline crítico/root action ao reconciliar o lote com a `main` atual.
- `RPG Skill Tree CI` #2806: Core, JUnit 5, GameTests, runtime validations, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato A0033 validado; apta ao merge da PR #252.
