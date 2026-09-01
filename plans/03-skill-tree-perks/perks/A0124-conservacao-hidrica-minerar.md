# A0124 — Conservação Hídrica: Minerar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0123/P-0037 e exige adapter causal TWR 3.0.4.  
**Notion:** https://app.notion.com/p/3c569db9f0db8104b5d9ea43241f9963

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; ramo Conservação Hídrica — Mineração.
- 4 ranks; 1 PP/rank; função Ramo.
- Gate: A0123 ≥2 + Gateway SURVIVAL + `BodyCostResolver` HYDRATION + TWR 3.0.4 causal.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente a parcela HYDRATION positiva e causal da mesma quebra manual MINING. Teto compartilhado: **30% HYDRATION por evento**.

Não reduz calor subterrâneo, desidratação basal/climática, pressão, trabalho de máquinas ou qualquer custo não atribuído à action_id da quebra.

## Authority e ordem canônica

- METABOLIC: Minecraft/NeoForge FoodData, resolvido primeiro por A0123/outros reducers elegíveis.
- HYDRATION: Thirst Was Reclaimed **3.0.4**, owner exclusivo desta lane.
- TWF 2.1.5 é compat/fix, não owner.

`MINING action_id -> METABOLIC settlement -> TWR quote/receipt HYDRATION da mesma action -> agregar reducers HYDRATION -> cap 30% -> commit provider uma vez`.

Sem adapter capaz de correlacionar e alterar a cobrança de forma provider-native, não usar direct write, polling, barra, refund ou inferência por temperatura.

## Availability

A0123 indisponível, P-0037/BodyCostResolver ausente ou adapter TWR ausente/incompatível => A0124 não comprável, gasto zero e allocation legado = 0 PP efetivo/reembolsável. Após os bindings globais existirem, receipt ausente numa quebra específica só omite aquele proc.

## Dedup / anti-abuso

Uma action_id raiz produz no máximo um settlement hídrico; bulk/vein derived blocks sem custo corporal próprio não multiplicam economia. Automação/fake player sem débito corporal é inelegível.

## Projetos próprios

RPG Skill Tree é consumer/resolver futuro. Volcanoes não converte calor/pressão/geologia em HYDRATION MINING. Enshrouded e Black Arcana não são providers desta lane.

## Pendências Chat 2

- `P-A0124-01` — availability transitiva A0123 + P-0037 + adapter TWR.
- `P-A0124-02` — adapter TWR 3.0.4 causal same-action, sem direct write/polling.
- `P-A0124-03` — ordem METABOLIC→HYDRATION, dedup e cap 30%.
- `P-A0124-04` — lifecycle/respec/rules reload/provider removal.

## Testes Chat 3

Purchase/PP zero; A0123 indisponível; TWR absent/mismatch; manual mining same-action; calor/pressão excluídos; bulk dedup; TWF não-owner; cap 30%; zero/cancel rollback; multiplayer/lifecycle; GameTests/build/JAR/dedicated-server smoke.
