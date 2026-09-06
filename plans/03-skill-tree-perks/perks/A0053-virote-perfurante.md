# A0053 — Virote Perfurante

## Estado

- **Design:** APROVADO após correção de availability/provenance, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-811a-9656-f34ddd39f999`.
- **Runtime:** **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO**. Reservation→commit, launch provenance, rollback e deduplicação estão implementados/validados, mas o node permanece `UNAVAILABLE_NODE` pela cadeia A0050→A0052→A0053.

## Contrato canônico

- A0052 ≥1 + gateway `epic_crossbow`; availability de A0052 é obrigatória.
- Com 2 Cadências, disparo CROSSBOW totalmente carregado pode consumir 2 para +10%/+15% penetration e +15%/+25% impact.
- Exige launch receipt CROSSBOW server-authoritative e projectile/root correlacionado.
- O custo segue **reservation→commit**: tentativa pode reservar 2 Cadências, mas commit só ocorre quando criação do projectile/root correlacionado é confirmada. Cancelamento tardio, ausência de spawn, perda do rank/pré-requisito ou rules reload que invalide a ação libera a reserva sem consumo.
- Componentes são independentes; aplicar apenas os semanticamente seguros.
- Primeiro impacto elegível do mesmo projectile/root recebe o efeito uma vez.
- Ricochetes, perfurações posteriores, derivados, dano periódico, Backlash ou projectile de companion não reaplicam.
- Reservas pendentes não sobrevivem a rank loss/respec/rules reload que invalide A0053 ou seus pré-requisitos.

## Evidência runtime

`tryPiercingBolt(...)` reserva a ação em `A0041A0060CombatState` sem debitar Cadência. `A0041A0060ProjectileEvents.onEntityJoin(...)` só chama `commitPiercingBolt(...)` quando existe `PendingLaunch.launchConfirmed` e um projectile CROSSBOW correlacionado; se o projectile não nasce, a reserva expira/é reconciliada sem consumo.

A reserva possui identidade `actor + rootActionId + A0053`, TTL bounded e remoção explícita em perda de ranks/pré-requisitos. O primeiro projectile especial correlacionado recebe a ação; siblings/impactos posteriores não recebem novo consumo. Penetration usa o pipeline de damage/reduction existente; Impact continua component-wise fail-closed quando não há receipt provider-native seguro.

## Pendências técnicas

- **RESOLVIDA P-A0053-01:** availability A0050→A0052→A0053 propagada.
- **RESOLVIDA P-A0053-02:** custo reservation→commit na criação confirmada do projectile/root.
- **RESOLVIDA P-A0053-03:** launch receipt real exigido.
- **RESOLVIDA P-A0053-04:** reserva descartada por TTL/lifecycle/reconciliation quando a progressão deixa de validar.
- **PENDÊNCIA NÃO BLOQUEANTE:** a perk continua inalcançável enquanto A0050/A0052 estiverem indisponíveis; Impact permanece component-wise fail-closed sem receipt provider-native seguro.

## Implementação e validação

- [x] Hook implementado.
- [x] Gate/availability fail-closed implementados.
- [x] Reservation→commit implementado.
- [x] Launch provenance implementada.
- [x] Deduplicação por root/primeiro projectile especial implementada.
- [x] Lifecycle/rollback bounded implementado.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit do commit/rollback e regressões do lote.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** fail-closed atual confirmado.
- [ ] **IMPLEMENTAÇÃO CONFIRMADA JOGÁVEL:** bloqueada pela cadeia A0050/A0052.

## Provider→árvore

Nenhum dos projetos próprios ou Mobstein fornece penetration/impact CROSSBOW alternativo. Stage 11 itemization continua authority separada e `SEM HOOK SEGURO` para projetar seus rolls nesta perk.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e fail-closed** | A0052 ≥1 + `epic_crossbow`; availability herdada de A0050/A0052 impede bypass. |
| 2. Integração global | **PASS** | Consome somente Cadência própria; penetration/Impact usam providers canônicos quando seguros. |
| 3. Qualidade e identidade | **PASS** | Notable de gasto deliberado de Cadência. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 após A0052 no ramo Cadência/Perfuração. |
| 5. Especializações | **PASS** | MARTIAL/BESTAS. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Providers/boundaries pertinentes dispostos; Stage 11 segue `SEM HOOK SEGURO`. |

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento de design; re-fetch pós-review PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

O pipeline transacional A0053 foi revisado contra o contrato e passou a bateria real do lote no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`. A perk permanece **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO** por herdar a indisponibilidade de A0050/A0052; nenhum bônus substituto foi inventado.