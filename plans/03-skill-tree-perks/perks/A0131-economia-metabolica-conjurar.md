# A0131 — Economia Metabólica: Conjurar

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

No snapshot auditado, não existe `METABOLIC_CAST` causal. Compra deve falhar antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável.

## Contrato

- Árvore/domínio: SURVIVAL; ponte SURVIVAL↔ARCANE.
- Máx. 4 ranks; 1 PP/rank.
- Efeito reservado: −3%/rank da parcela METABOLIC corporal real gerada especificamente por conjuração legítima, até −12%.
- Cap compartilhado METABOLIC: 30% por `body_cost_event`.
- Pré-requisitos: Gateway SURVIVAL + Gateway ARCANE efetivamente desbloqueados.

## Authority e provider

- Iron's Spells 'n Spellbooks 3.16.3, Ars Nouveau 5.13.1 e demais providers mágicos aprovados classificam cast/recurso; não são owner de FoodData.
- Black Arcana pode futuramente classificar cast concluído por receipt próprio. `ArcanaGatePreflight` atual é apenas projeção read-only parcial e não prova sucesso do cast.
- Owner corporal: Minecraft/NeoForge FoodData + futuro `BodyCostResolver`.

## Boundary futuro

`cast root/action_id confirmado -> receipt METABOLIC_CAST positivo e causal -> BodyCostResolver -> reducers METABOLIC -> cap 30% -> commit uma vez`.

Mana, Source, Soul Energy, sangue/HP, cooldown, Arcane Strain, temperatura ou Stamina nunca substituem METABOLIC.

## Handoff Chat 2

- Manter node indisponível até `BodyCostResolver` + producer `METABOLIC_CAST` real.
- Não criar exhaustion artificial para dar utilidade à perk.
- Bridge PP conta para no máximo um threshold puro, nunca SURVIVAL e ARCANE simultaneamente.

## Testes Chat 3

1. purchase fail-before-spend sem provider;
2. allocation legado = 0 PP;
3. cast sem exhaustion corporal não proca;
4. recursos mágicos não são convertidos em FoodData;
5. um cast/root gera no máximo um settlement;
6. cancelamento/falha de cast não gera custo ou benefício fantasma;
7. provider removal/rules reload/respec mantêm fail-closed.
