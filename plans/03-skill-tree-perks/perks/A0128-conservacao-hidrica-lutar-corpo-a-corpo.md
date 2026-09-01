# A0128 — Conservação Hídrica: Lutar Corpo a Corpo

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0127/P-0037 e exige adapter causal TWR 3.0.4.  
**Notion:** https://app.notion.com/p/3c569db9f0db81d19335e3265ae954fb

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Conservação Hídrica — Combate Corpo a Corpo; função Ramo.
- 4 ranks; 1 PP/rank.
- Gate: A0127 ≥2 + Gateway SURVIVAL + `BodyCostResolver` HYDRATION + TWR 3.0.4.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente HYDRATION positiva causal da mesma root melee válida. Teto compartilhado: **30% HYDRATION por evento**.

Não reduz Stamina Epic Fight, sede climática/basal, custos de DoT/proc/reflexão/summon nem qualquer parcela não correlacionada à ação melee raiz.

## Authority / pipeline

- METABOLIC: FoodData, resolvido primeiro por A0127/outros reducers elegíveis.
- HYDRATION: Thirst Was Reclaimed **3.0.4**; TWF 2.1.5 não é owner.
- Epic Fight 21.17.3.1 apenas classifica/identifica a root melee.

`melee action_id -> confirmed hostile outcome -> METABOLIC -> TWR same-action HYDRATION -> aggregate reducers -> cap 30% -> provider commit uma vez`.

Sem adapter causal modificável, direct writes, polling, barra e refunds são proibidos.

## Availability

A0127 indisponível, P-0037 ausente ou TWR adapter ausente/incompatível => A0128 não comprável, gasto zero e allocation legado 0 PP/reembolsável. Após bindings globais, receipt hídrico ausente em uma root específica apenas omite o proc.

## Dedup

Uma root melee pode gerar no máximo uma resolução A0128. DoT/proc/reflexão/summon/callback duplicado não cria nova conservação.

## Projetos próprios

RPG Skill Tree consumer futuro; Volcanoes/Enshrouded/Black Arcana não produzem HYDRATION melee por associação temática.

## Pendências Chat 2

- `P-A0128-01` — availability transitiva A0127 + P-0037 + TWR.
- `P-A0128-02` — adapter TWR 3.0.4 causal same-root, sem direct write/polling.
- `P-A0128-03` — root dedup, METABOLIC→HYDRATION e cap 30%.
- `P-A0128-04` — lifecycle/provider removal/respec/rules reload.

## Testes Chat 3

Purchase/PP zero; A0127/TWR absent; melee hit/miss; Stamina separada; DoT/proc/reflection/summon dedup; TWF não-owner; cap 30%; cancel/zero; multiplayer; lifecycle; GameTests/build/JAR/dedicated smoke.
