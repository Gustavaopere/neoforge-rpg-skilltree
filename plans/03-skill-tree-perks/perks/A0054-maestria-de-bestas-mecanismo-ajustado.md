# A0054 — Maestria de Bestas — Mecanismo Ajustado

## Estado

- **Design:** APROVADO após correção estrutural, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-814f-96e0-ee616a448f0d`.
- **Runtime:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. A janela/custo agora são transacionais, mas o node permanece `UNAVAILABLE_NODE` pela cadeia A0050→A0052/A0053; reload-speed extra continua omitido sem hook semântico seguro.

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

`armAdjustedMechanism(...)` não zera mais Cadência ao armar a janela. `tryAdjustedCrossbowShot(...)` reserva a ativação para um `rootActionId` sem consumir as 3 cargas; `commitAdjustedCrossbowShot(...)` só fecha a transação quando `EntityJoinLevelEvent` confirma um projectile CROSSBOW correlacionado ao `PendingLaunch` real.

Ausência de spawn/cancelamento deixa a reserva expirar bounded e libera o root sem debitar Cadência. Reconciliation por ranks limpa janela/reserva própria quando A0054 ou sua cadeia deixam de ser válidas. O bônus +15% de dano fica anexado apenas ao projectile que commitou a ação; siblings não repetem o consumo.

A parcela de reload acelerado permanece corretamente omitida por ausência de provider seguro.

## Pendências para Chat 2

- **RESOLVIDA P-A0054-01:** as 3 Cadências só são consumidas no commit do projectile materializado.
- **RESOLVIDA P-A0054-02:** availability A0050/A0052/A0053→A0054 propagada.
- **RESOLVIDA P-A0054-03:** `epicfight:crossbow` já é a ledger única na linha predecessora.
- **RESOLVIDA P-A0054-04:** reservation→commit/rollback implementado.
- **RESOLVIDA P-A0054-05:** launch receipt CROSSBOW confirmado é obrigatório.
- **RESOLVIDA P-A0054-06:** lifecycle de janela/reserva própria reconciliado; Cadência continua sob owner A0052.
- **PENDÊNCIA PROVIDER:** aceleração de reload permanece omitida até surgir hook semântico seguro; não exige redesign do efeito base e permanece fail-closed component-wise.

## Implementação Chat 2 — PR #386

- [x] Hook/launch provenance implementados.
- [x] Gate/availability fail-closed implementados.
- [x] Reservation→commit da janela e Cadência implementado.
- [x] Deduplicação por root/primeiro projectile especial implementada.
- [x] Lifecycle rank/respec/rules reload implementado.
- [x] Fallback sem reload-speed heurístico preservado.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/JUnit de arm/reserve/commit/expiry.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests de cancelamento/ausência de spawn/Multishot.
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

`ARCANE_BACKLASH`, spell/derived projectiles e companions Mobstein não armam/consomem o capstone.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e fail-closed** | A0052 ≥2 + A0053 ≥1 + Mastery `epicfight:crossbow` ≥80 + gateway; `UNAVAILABLE_NODE` preserva toda indisponibilidade herdada. |
| 2. Integração global | **PASS** | Cadência e crítico/dano usam pipelines próprios/canônicos; reload speed só existe com hook semântico real; nenhum recurso mágico/ambiental é usado como proxy. |
| 3. Qualidade e identidade | **PASS** | Capstone conclui a fantasia mecânica hit→reload→janela→disparo preparado, com decisão temporal e fallback que preserva identidade. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 4 terminal do ramo de Bestas, dependências convergentes e Mastery 80 compatíveis com posição de Capstone. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/BESTAS`; só satisfaz Gate C por mapeamento explícito e não vira classe/mod specialization automática. |
| 6. PT-BR | **PASS** | Nome, efeito e requisitos em PT-BR; IDs e hooks técnicos em inglês apenas onde necessário. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos; consumo tardio, reservation→commit, launch provenance e lifecycle re-fetched. |
| 8. NeoVitae | **PASS** | Nenhuma dependência residual. |
| 9. Cobertura modlist/providers | **PASS** | RPG/Epic Fight/WoM e providers globais foram classificados; Pufferfish projectile speed e Wayward Attributes foram rejeitados como substitutos de reload speed; own-projects/Mobstein não integram. |

Os critérios técnicos cumulativos permanecem satisfeitos no design; o código está presente em fail-closed até a cadeia CROSSBOW possuir A0050 seguro.

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram reservation→commit, launch provenance e lifecycle de rank/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.