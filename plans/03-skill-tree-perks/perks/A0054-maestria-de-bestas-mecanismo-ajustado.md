# A0054 — Maestria de Bestas — Mecanismo Ajustado

## Estado

- **Design:** APROVADO após correção estrutural e reservation→commit.
- **Notion:** `3c569db9-f0db-814f-96e0-ee616a448f0d`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL / atualmente não adquirível pela cadeia A0050→A0052/A0053; o consumo da janela/Cadência ainda precisa ser transacional no lançamento real.

## Contrato canônico

- A0052 ≥2 + A0053 ≥1 + `epicfight:crossbow` ≥80 + gateway `epic_crossbow`.
- Todos os pré-requisitos precisam estar disponíveis/compráveis.
- Em 3 Cadências, recarga completa arma janela de 8/9/10 s.
- O **próximo disparo efetivamente materializado** na janela consome as 3 cargas e recebe +15% dano.
- A tentativa segue **reservation→commit**: reservar janela/recursos durante o lançamento, mas só commitá-los quando a criação do projectile/root for confirmada. Cancelamento tardio de `ArrowLooseEvent`, ausência de spawn ou falha equivalente faz rollback e preserva Cadência/janela.
- A recarga seguinte pode ser 15% mais rápida uma vez somente se surgir hook semântico seguro de reload/preparation speed.
- Sem esse hook, omitir apenas a aceleração; nunca usar projectile speed/timer heurístico.

## Evidência runtime

A policy possui `armAdjustedMechanismOnReload(...)` e `tryAdjustedCrossbowShot(...)`. Porém `A0041A0060CombatState.armAdjustedMechanism(...)` zera `cadence` no momento em que a janela é armada. O contrato corrigido exige que as 3 cargas permaneçam até o disparo que efetivamente consome a janela ou até a expiração; armar a janela não pode consumir antecipadamente as cargas se nenhum disparo ocorrer.

Há um segundo problema causal: `tryAdjustedCrossbowShot(...)` é chamado no `ArrowLooseEvent` antes de a criação do projétil estar confirmada e consome a janela naquele momento. Um listener posterior pode cancelar o lançamento, deixando a ativação perdida sem projectile/root. A implementação precisa reservar a ativação no evento inicial e commit/rollback somente quando o projétil correspondente for realmente criado.

A parcela de reload acelerado permanece corretamente omitida por ausência de provider seguro.

## Pendências para Chat 2

- **P-A0054-01:** mover consumo das 3 Cadências do arm/reload para o disparo que consome Mecanismo Ajustado; expiração sem disparo não deve simular consumo antecipado.
- **P-A0054-02:** propagar availability da cadeia A0050/A0052/A0053 e impedir compra enquanto os pré-requisitos forem indisponíveis.
- **P-A0054-03:** reconciliar a ledger `epicfight:crossbow` com o architecture catalog; `combat:crossbow` não pode atuar como ledger paralela.
- **P-A0054-04:** aplicar reservation→commit/rollback também à janela de Mecanismo Ajustado: cancelamento tardio/ausência de projectile spawn não pode queimar a ativação nem as Cadências.
- Herdar `P-A0049-01` e demais blockers da cadeia CROSSBOW; sem producer de Mastery legítimo e A0050 comprável, A0054 não é alcançável.

## Boundaries

`ARCANE_BACKLASH`, spell/derived projectiles e companions Mobstein não armam/consomem o capstone.

## Notion

Dependências, Gate, Hook, Fallback e Regra corrigidos no fechamento inicial. Após review da PR #249, `Hook`, `Fallback` e `Regra` passaram a exigir reservation→commit/rollback até a criação confirmada do projectile/root; re-fetch pós-review PASS em 2026-08-30.
