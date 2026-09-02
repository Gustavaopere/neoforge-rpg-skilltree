# A0053 — Virote Perfurante

## Estado

- **Design:** APROVADO após correção de availability/provenance, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-811a-9656-f34ddd39f999`.
- **Runtime:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. O runtime implementa reservation→commit e launch provenance, mas o node permanece `UNAVAILABLE_NODE` pela cadeia A0050→A0052→A0053.

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

`tryPiercingBolt(...)` agora reserva a ação em `A0041A0060CombatState` sem debitar Cadência. `A0041A0060ProjectileEvents.onEntityJoin(...)` só chama `commitPiercingBolt(...)` quando existe `PendingLaunch.launchConfirmed` e um projectile CROSSBOW correlacionado; se o projectile não nasce, a reserva expira/é reconciliada sem consumo.

A reserva tem identidade `actor + rootActionId + A0053`, TTL bounded e remoção explícita em perda de ranks/pré-requisitos. O primeiro projectile especial correlacionado recebe a ação; siblings/impactos posteriores não recebem novo consumo. Penetration é aplicado no pipeline de damage/reduction existente; Impact continua component-wise fail-closed quando não há receipt provider-native seguro.

## Pendências para Chat 2

- **RESOLVIDA P-A0053-01:** availability A0050→A0052→A0053 propagada.
- **RESOLVIDA P-A0053-02:** custo virou reservation→commit na criação confirmada do projectile/root.
- **RESOLVIDA P-A0053-03:** launch receipt real exigido.
- **RESOLVIDA P-A0053-04:** reserva é descartada por TTL/lifecycle/reconciliation quando a progressão deixa de validar.
- Validação de cancelamento tardio, multi-pierce/ricochet/derived e dedup real permanece para Chat 3.
- A perk continua inalcançável enquanto A0050/A0052 estiverem indisponíveis; não há bypass nem rank no-op.

## Implementação Chat 2 — PR #386

- [x] Hook implementado.
- [x] Gate/availability fail-closed implementados.
- [x] Reservation→commit implementado.
- [x] Launch provenance implementada.
- [x] Deduplicação por root/primeiro projectile especial implementada.
- [x] Lifecycle/rollback bounded implementado.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** JUnit/unit tests do commit/rollback.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests cancelamento, multi-pierce/ricochet/derived.
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Provider→árvore

Nenhum dos projetos próprios ou Mobstein fornece penetration/impact CROSSBOW alternativo. Stage 11 itemization continua authority separada e `SEM HOOK SEGURO` para projetar seus rolls nesta perk.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e fail-closed** | A0052 ≥1 + `epic_crossbow`; availability herdada de A0050/A0052 impede bypass. |
| 2. Integração global | **PASS** | Consome somente Cadência própria; penetration/Impact usam providers canônicos quando seguros; magia, Shroud, hazards e companions não substituem componentes. |
| 3. Qualidade e identidade | **PASS** | Notable de gasto deliberado de Cadência para tiro de alto compromisso; muda decisão de combate e não é bônus plano permanente. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 após A0052 no ramo Cadência/Perfuração; custo e pré-requisito preservam progressão real. |
| 5. Especializações | **PASS** | Continua MARTIAL/BESTAS; não invade magia/tecnologia e não cria classe específica de mod. |
| 6. PT-BR | **PASS** | Texto de jogador em PT-BR; `penetration`, `Impact`, IDs e hooks técnicos aparecem apenas como termos de implementação quando necessário. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos; reservation→commit, launch provenance e lifecycle re-fetched após review. |
| 8. NeoVitae | **PASS** | Ausente de providers, gates e fallback. |
| 9. Cobertura modlist/providers | **PASS** | RPG/Epic Fight/Apothic/WoM quando aplicáveis e own-project/Mobstein boundaries foram dispostos; Stage 11 permanece `SEM HOOK SEGURO`. |

Os critérios técnicos cumulativos permanecem satisfeitos no design; o código está presente mas a confirmação final pertence ao Chat 3.

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram reservation→commit, launch provenance e lifecycle de reconciliação; re-fetch pós-review PASS em 2026-08-30.