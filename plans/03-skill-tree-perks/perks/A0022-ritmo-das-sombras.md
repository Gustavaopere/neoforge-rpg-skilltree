# A0022 — Ritmo das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** PARCIAL; `P-A0022-01` aberta.
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
9. Providers: PASS de design; implementação parcial no receipt de stagger.

## Evidência e pendência

- `A0021A0040CombatState` implementa cap, expiração e idle decay.
- `A0021A0040EpicFightHooks.onDodge` arma reposicionamento e o tick evita fallback de câmera.
- **P-A0022-01:** o adapter A0021–A0040 não registra hoje `ON_STUNNED` nem remove 2 Fluxo por stagger forte hostil. Chat 2 deve usar o receipt Epic Fight provider-native, análogo ao lote A0001–A0020, restrito a `LONG/KNOCKDOWN/NEUTRALIZE` com fonte hostil. Dano genérico não é substituto.
- `ARCANE_BACKLASH` e companions Mobstein não geram/renovam Fluxo.

**P-A0022-01 bloqueia IMPLEMENTAÇÃO CONFIRMADA, não o design.**
