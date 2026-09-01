# A0126 — Conservação Hídrica: Cortar Madeira

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0125/P-0037 e exige adapter causal TWR 3.0.4.  
**Notion:** https://app.notion.com/p/3c569db9f0db813b9030f836474056a8

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Conservação Hídrica — Silvicultura; função Ramo.
- 4 ranks; 1 PP/rank.
- Gate: A0125 ≥2 + Gateway SURVIVAL + BodyCostResolver HYDRATION + TWR 3.0.4.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente HYDRATION positiva causada pela mesma ação manual legítima de Silvicultura. Teto compartilhado de **30% HYDRATION por evento**.

Calor, desidratação basal/climática, automação e ações derivadas sem custo corporal próprio ficam fora.

## Authority / pipeline

HYDRATION pertence ao Thirst Was Reclaimed **3.0.4**. Thirst Was Fixed 2.1.5 é compat/fix. A mesma `action_id` de forestry é resolvida em METABOLIC primeiro; somente então um adapter TWR pode expor a parcela HYDRATION causal e modificável antes do commit.

`FORESTRY action_id -> METABOLIC -> TWR HYDRATION quote/receipt -> aggregate reducers -> cap 30% -> one commit`.

Sem essa fronteira, direct thirst writes, polling, refunds e inferência por barra são proibidos.

## Availability

A0125 indisponível, P-0037 ausente ou TWR causal incompatível => A0126 não comprável, gasto zero, allocation legado 0 PP/reembolsável. Com bindings globais presentes, receipt ausente em um evento específico apenas omite o benefício.

## Dedup

Tree-felling/bulk derivados não criam parcels extras sem custo real; uma action raiz produz no máximo uma resolução hídrica.

## Projetos próprios

RPG Skill Tree consumer futuro; Volcanoes não converte ambiente em HYDRATION forestry; Enshrouded/Black Arcana N/A.

## Pendências Chat 2

- `P-A0126-01` — availability transitiva A0125 + P-0037 + TWR.
- `P-A0126-02` — adapter causal TWR 3.0.4 same-action.
- `P-A0126-03` — METABOLIC→HYDRATION, dedup e cap 30%.
- `P-A0126-04` — lifecycle/provider removal/respec/rules reload.

## Testes Chat 3

Purchase fail-before-spend; PP 0; A0125/TWR absent; manual forestry; bulk/tree-felling dedup; calor/clima excluídos; TWF não-owner; cap 30%; cancel/zero; multiplayer/lifecycle; GameTests/build/JAR/dedicated smoke.
