# A0022 — Ritmo das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** VALIDADA EM CI na PR #242; `P-A0022-01`, `P-A0022-02` e `P-A0022-03` resolvidas.
- **Notion:** `3c569db9-f0db-8135-a327-f367995a0091`.

## Contrato canônico

- A0021 ≥ 2 + gateway `epic_dagger`.
- Hit direto de adaga até 2,5 s após dodge válido ou reposicionamento seguro gera 1 Fluxo, cap 4.
- Fallback geométrico: deslocamento horizontal ≥1,5 blocos + mudança angular ≥60° antes do hit; câmera, teleport, knockback e deslocamento sem hit não qualificam.
- Rank 1/2: estado expira 5/7 s após o último ganho; perdas não renovam.
- 3 s sem deslocamento horizontal relevante inicia perda de 1 Fluxo/s.
- Stagger forte hostil `LONG`, `KNOCKDOWN` ou `NEUTRALIZE` remove 2 Fluxo.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS — Fluxo é estado RPG, dodge/reposicionamento vêm do provider.
3. Identidade: PASS — mobilidade ativa, sem farm frontal.
4. Topologia: PASS — Notable de adagas.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary causal.
8. NeoVitae: PASS.
9. Providers: PASS — receipts/lifecycle exigidos foram implementados pelo Chat 2.

## Evidência e pendências

- `A0021A0040CombatState` implementa cap, expiração e idle decay sem depender de alvo hostil vivo.
- `A0021A0040EpicFightHooks.onDodge` continua armando reposicionamento via `DodgeEvent`.
- `A0022RuntimeHooks` registra `ON_STUNNED` e remove 2 Fluxo somente para `LONG`, `KNOCKDOWN` ou `NEUTRALIZE` com fonte hostil.
- `A0022RuntimeHooks` amostra geometria server-side e `A0021A0040CombatState.sampleFallbackReposition(...)` exige deslocamento horizontal ≥1,5 e mudança angular ≥60°; câmera não entra no cálculo.
- `EntityTeleportEvent` e `LivingKnockBackEvent` invalidam a rota geométrica antes do hit.
- Movimento sozinho apenas arma o receipt; Fluxo continua sendo concedido somente no hit direto confirmado de adaga.
- `ARCANE_BACKLASH` e companions Mobstein não geram/renovam Fluxo.

`P-A0022-01`, `P-A0022-02` e `P-A0022-03`: **RESOLVIDAS na PR #242**.

## Chat 2 — implementação e regressão — PR #242

- Regressão JUnit cobre decay após 3 s mesmo sem target vivo.
- Regressão JUnit cobre perda exata de 2 Fluxo no receipt forte normalizado.
- Regressão JUnit cobre baseline geométrica, deslocamento/ângulo elegível e invalidação do receipt.
- O adapter permanece server-authoritative e não usa câmera, dano genérico, animação ou velocidade como substitutos.
- Estado definitivo passa a `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #242 e confirmação da `main`.
