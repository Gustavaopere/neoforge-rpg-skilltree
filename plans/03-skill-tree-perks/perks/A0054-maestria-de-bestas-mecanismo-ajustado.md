# A0054 — Maestria de Bestas — Mecanismo Ajustado

## Estado

- **Design:** APROVADO após correção estrutural, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-814f-96e0-ee616a448f0d`.
- **Runtime:** **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO**. Janela/custo transacionais e launch provenance foram validados pelo Chat 3, mas o node permanece `UNAVAILABLE_NODE` pela cadeia A0050→A0052/A0053; reload-speed extra continua corretamente omitido sem hook semântico seguro.

## Contrato canônico

- A0052 ≥2 + A0053 ≥1 + `epicfight:crossbow` ≥80 + gateway `epic_crossbow`.
- Todos os pré-requisitos precisam estar disponíveis/compráveis.
- Em 3 Cadências, recarga completa arma janela de 8/9/10 s.
- O **próximo disparo efetivamente materializado** na janela consome as 3 cargas e recebe +15% dano.
- A tentativa segue **reservation→commit**: reservar janela/recursos durante o lançamento, mas só commitá-los quando criação do projectile/root correlacionado a launch receipt CROSSBOW for confirmada. Cancelamento tardio, ausência de spawn ou falha equivalente faz rollback e preserva Cadência/janela.
- A recarga seguinte pode ser 15% mais rápida uma vez somente se surgir hook semântico seguro de reload/preparation speed.
- Sem esse hook, omitir apenas aceleração; nunca usar projectile speed/timer heurístico.
- Rank loss/respec/rules reload que invalide A0054 limpa janela/reservas do capstone; Cadência base pertence a A0052 e só é limpa quando A0052/pré-requisitos forem invalidados.

## Evidência runtime

`armAdjustedMechanism(...)` não zera Cadência ao armar a janela. `tryAdjustedCrossbowShot(...)` reserva a ativação para um `rootActionId` sem consumir as 3 cargas; `commitAdjustedCrossbowShot(...)` só fecha a transação quando `EntityJoinLevelEvent` confirma um projectile CROSSBOW correlacionado ao `PendingLaunch` real.

Ausência de spawn/cancelamento deixa a reserva expirar bounded e libera o root sem debitar Cadência. Reconciliation por ranks limpa janela/reserva própria quando A0054 ou sua cadeia deixam de ser válidas. O bônus +15% de dano fica anexado apenas ao projectile que commitou a ação; siblings não repetem o consumo.

A parcela de reload acelerado permanece corretamente omitida por ausência de provider seguro.

## Pendências técnicas

- **RESOLVIDA P-A0054-01:** as 3 Cadências só são consumidas no commit do projectile materializado.
- **RESOLVIDA P-A0054-02:** availability A0050/A0052/A0053→A0054 propagada.
- **RESOLVIDA P-A0054-03:** `epicfight:crossbow` é a ledger única na linha predecessora.
- **RESOLVIDA P-A0054-04:** reservation→commit/rollback implementado.
- **RESOLVIDA P-A0054-05:** launch receipt CROSSBOW confirmado obrigatório.
- **RESOLVIDA P-A0054-06:** lifecycle de janela/reserva própria reconciliado; Cadência continua sob owner A0052.
- **PENDÊNCIA PROVIDER NÃO BLOQUEANTE:** aceleração de reload permanece omitida até surgir hook semântico seguro.
- **PENDÊNCIA HERDADA NÃO BLOQUEANTE:** a cadeia permanece indisponível enquanto A0050 não possuir binding real.

## Implementação e validação

- [x] Hook/launch provenance implementados.
- [x] Gate/availability fail-closed implementados.
- [x] Reservation→commit da janela e Cadência implementado.
- [x] Deduplicação por root/primeiro projectile especial implementada.
- [x] Lifecycle rank/respec/rules reload implementado.
- [x] Fallback sem reload-speed heurístico preservado.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit de arm/reserve/commit/expiry e regressões do lote.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** fail-closed/component fallback confirmados.
- [ ] **IMPLEMENTAÇÃO CONFIRMADA JOGÁVEL:** bloqueada pela cadeia A0050/A0052/A0053.

## Boundaries

`ARCANE_BACKLASH`, spell/derived projectiles e companions Mobstein não armam/consomem o capstone.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e fail-closed** | A0052 ≥2 + A0053 ≥1 + Mastery `epicfight:crossbow` ≥80 + gateway; `UNAVAILABLE_NODE` preserva indisponibilidade herdada. |
| 2. Integração global | **PASS** | Cadência/dano usam pipelines canônicos; reload speed só com hook semântico real. |
| 3. Qualidade e identidade | **PASS** | Capstone hit→reload→janela→disparo preparado. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 4 terminal de Bestas. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/BESTAS`. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Providers globais classificados; substitutes incorretos de reload speed rejeitados. |

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento de design; re-fetch pós-review PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

O pipeline transacional foi validado no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`, que passou JUnit 5, NeoForge adapter/GameTests, build, JAR verification e dedicated-server smoke. A0054 permanece **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO** pela availability herdada; nenhuma aceleração heurística foi adicionada.