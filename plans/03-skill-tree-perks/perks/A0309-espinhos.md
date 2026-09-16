# A0309 — Espinhos

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183→A0307.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db8129b0fbd34ee842133c

## Contrato
Após o jogador sobreviver ao commit de um ataque melee direto hostil elegível, emitir no máximo uma retaliação derivada `NATURE_REACTIVE`: `damage_actually_received × (0.04×rank)`. Cap antes do coeficiente especial = 10% da vida máxima atual do defensor. Contra BOSS/PvP, retaliação final ×0,50. O atacante aplica sua própria mitigação.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0307≥1. Gate C A0183 e A0307 estão indisponíveis; compra fail-before-spend. Legacy unavailable = 0 PP em gates e reembolsável/migrável.

## Boundary requerido
`LivingDamageEvent.Post` + `DamageSource` fornecem primitives plausíveis para observar actual damage e entidades da fonte, mas ainda faltam `HOSTILE_DIRECT_MELEE_RECEIPT`, root-action/dedup, classifier BOSS/PvP universal e `DERIVED_COMBAT_OUTCOME_PIPELINE_V1`.

O receipt deve excluir projectile, DoT, environment, self/ally, thorns, derived, dano zero e dano fatal. Distância, swing animation, attacker type ou `DamageSource` genérico não bastam.

## Causalidade/dedup
Um único derived outcome por `incoming_melee_outcome_id`. Não usar `hurt()` ad hoc que recircule crítico/proc/Mastery/thorns. O cap é calculado antes do coeficiente BOSS/PvP e a base nunca inclui Absorption não perdida nem dano cancelado.

## Fallback
Sem direct-melee/hostility receipt, derived pipeline ou classifier seguro, retaliação = 0; node permanece indisponível por closure externa.

## Testes Chat 3
1. fail-before-spend A0183/A0307;
2. actual damage positivo e sobrevivência obrigatórios;
3. 4/8/12% com cap 10% max health;
4. BOSS/PvP ×0,50 após cap;
5. projectile/DoT/environment/self/ally/thorns/derived/zero/fatal excluídos;
6. um derived outcome por incoming outcome;
7. sem cascata crit/proc/Mastery/thorns;
8. multiplayer/dedicated server.

## Handoff Chat 2
Não criar retaliação via `hurt()` independente. Sem derived pipeline canônica e receipt causal, manter indisponível.