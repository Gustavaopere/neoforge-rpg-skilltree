# A0022 — Ritmo das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** PARCIAL; `P-A0022-01`, `P-A0022-02` e `P-A0022-03` abertas.
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
9. Providers: PASS de design; implementação parcial nos receipts/lifecycle abaixo.

## Evidência e pendências

- `A0021A0040CombatState` implementa cap, expiração e idle decay em nível de policy/state, mas o adapter ainda não satisfaz integralmente todos os caminhos canônicos.
- `A0021A0040EpicFightHooks.onDodge` arma reposicionamento via `DodgeEvent`.
- **P-A0022-01:** o adapter A0021–A0040 não registra hoje `ON_STUNNED` nem remove 2 Fluxo por stagger forte hostil. Chat 2 deve usar o receipt Epic Fight provider-native, análogo ao lote A0001–A0020, restrito a `LONG/KNOCKDOWN/NEUTRALIZE` com fonte hostil. Dano genérico não é substituto.
- **P-A0022-02:** o fallback geométrico canônico (≥1,5 blocos de deslocamento horizontal + ≥60° de mudança angular antes do hit) ainda não é armado quando não há `DodgeEvent`; `A0021A0040EpicFightHooks.onEpicFightTick` não deve transformar câmera, teleport ou knockback em reposicionamento, mas precisa correlacionar server-side a geometria elegível com o próximo hit direto de adaga. Sem isso, essa rota permanece não implementada e A0024 também não pode depender dela como se estivesse disponível.
- **P-A0022-03:** o decay canônico após 3 s sem deslocamento horizontal relevante não é aplicado pelo adapter quando não existe alvo hostil vivo no `PlayerPatch`; nesse caso `tickFlow(..., false, ...)` retorna antes da perda de 1 Fluxo/s e o estado apenas expira em 5/7 s. Chat 2 deve reconciliar o adapter com o contrato de lifecycle sem tornar alvo hostil uma condição artificial para o decay.
- `ARCANE_BACKLASH` e companions Mobstein não geram/renovam Fluxo.

**P-A0022-01/02/03 bloqueiam IMPLEMENTAÇÃO CONFIRMADA, não o design.**
