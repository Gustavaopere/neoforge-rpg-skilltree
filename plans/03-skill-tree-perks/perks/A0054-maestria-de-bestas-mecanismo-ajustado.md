# A0054 — Maestria de Bestas — Mecanismo Ajustado

## Estado

- **Design:** APROVADO após correção estrutural.
- **Notion:** `3c569db9-f0db-814f-96e0-ee616a448f0d`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL / atualmente não adquirível pela cadeia A0050→A0052/A0053.

## Contrato canônico

- A0052 ≥2 + A0053 ≥1 + `epicfight:crossbow` ≥80 + gateway `epic_crossbow`.
- Todos os pré-requisitos precisam estar disponíveis/compráveis.
- Em 3 Cadências, recarga completa arma janela de 8/9/10 s.
- O **próximo disparo** na janela consome as 3 cargas e recebe +15% dano.
- A recarga seguinte pode ser 15% mais rápida uma vez somente se surgir hook semântico seguro de reload/preparation speed.
- Sem esse hook, omitir apenas a aceleração; nunca usar projectile speed/timer heurístico.

## Evidência runtime

A policy possui `armAdjustedMechanismOnReload(...)` e `tryAdjustedCrossbowShot(...)`. Porém `A0041A0060CombatState.armAdjustedMechanism(...)` zera `cadence` no momento em que a janela é armada. O contrato corrigido exige que as 3 cargas permaneçam até o disparo que efetivamente consome a janela ou até a expiração; armar a janela não pode consumir antecipadamente as cargas se nenhum disparo ocorrer.

A parcela de reload acelerado permanece corretamente omitida por ausência de provider seguro.

## Pendências para Chat 2

- **P-A0054-01:** mover consumo das 3 Cadências do arm/reload para o disparo que consome Mecanismo Ajustado; expiração sem disparo não deve simular consumo antecipado.
- **P-A0054-02:** propagar availability da cadeia A0050/A0052/A0053 e impedir compra enquanto os pré-requisitos forem indisponíveis.
- **P-A0054-03:** reconciliar a ledger `epicfight:crossbow` com o architecture catalog; `combat:crossbow` não pode atuar como ledger paralela.

## Boundaries

`ARCANE_BACKLASH`, spell/derived projectiles e companions Mobstein não armam/consomem o capstone.

## Notion

Dependências, Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS.
