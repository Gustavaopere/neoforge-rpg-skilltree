# A0130 — Conservação Hídrica: Usar Arco/Besta

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0129/`METABOLIC_RANGED` e `HYDRATION_RANGED` causal estão ausentes.  
**Notion:** https://app.notion.com/p/3c569db9f0db812bb302ed4cbb6a60c8

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Conservação Hídrica — Combate à Distância; função Ramo.
- 4 ranks; 1 PP/rank.
- Gate: A0129 ≥2 + Gateway SURVIVAL + TWR 3.0.4 + parcela HYDRATION positiva causal do mesmo disparo.

## Contrato congelado

Se um disparo legítimo futuramente produzir custo corporal e uma parcela hídrica real correlacionada, A0130 reduz essa HYDRATION em **3% por rank**, até **12%**, sob teto compartilhado de **30% por evento**.

Hoje não existe `METABOLIC_RANGED` comprovado; portanto também não existe `HYDRATION_RANGED` causal derivada que possa ser economizada.

## Authority / pipeline

- Thirst Was Reclaimed **3.0.4** é owner exclusivo da HYDRATION.
- Thirst Was Fixed 2.1.5 permanece compat/fix.
- Minecraft/NeoForge/Epic Fight 21.17.3.1 apenas classificam/identificam disparos.

`ranged root action_id -> METABOLIC_RANGED real -> TWR HYDRATION_RANGED same-root -> aggregate HYDRATION reducers -> cap 30% -> provider commit uma vez`.

Não escrever thirst diretamente, não inferir consumo por barra/polling e não substituir por Stamina, munição, Focus/Cadence, mana ou draw/reload time.

## Availability

A0129 indisponível, BodyCostResolver/P-0037 ausente, TWR adapter causal ausente ou HYDRATION_RANGED inexistente => A0130 não comprável, gasto zero, allocation legado 0 PP e reembolsável/migrável.

## Dedup

Multishot e projectiles irmãos compartilham uma root para o custo corporal/hídrico. Projectile derivado sem launch provenance não cria settlement.

## Projetos próprios

RPG Skill Tree é consumer/resolver futuro; Volcanoes, Enshrouded e Black Arcana não criam HYDRATION_RANGED por associação temática.

## Pendências Chat 2

- `P-A0130-01` — availability transitiva A0129 + P-0037 + TWR + `HYDRATION_RANGED`.
- `P-A0130-02` — adapter TWR 3.0.4 same-root somente quando houver METABOLIC_RANGED real.
- `P-A0130-03` — launch/root/Multishot dedup, ordem METABOLIC→HYDRATION e cap 30%.
- `P-A0130-04` — lifecycle/provider removal/respec/rules reload.

## Testes Chat 3

Purchase/PP zero; A0129 indisponível; ranged action sem body cost não gera hydration; TWR absent/mismatch; sem direct writes/polling; Stamina/ammo/Focus/Cadence/mana intactos; Multishot dedup; projectile derivado; future provider present/absent; cap 30%; multiplayer/lifecycle; GameTests/build/JAR/dedicated smoke.
