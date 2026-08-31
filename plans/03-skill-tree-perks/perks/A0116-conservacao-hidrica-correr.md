# A0116 — Conservação Hídrica: Correr

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; P-0037/`BodyCostResolver` HYDRATION e adapter causal Thirst Was Reclaimed não estão materializados na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81b3a2e1e24d3179c77e

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Conservação Hídrica — Movimento; camada 1; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate: Gateway SURVIVAL + P-0037/BodyCostResolver HYDRATION + adapter versionado TWR.

## Contrato congelado

Reduz **3% por rank**, até **12%**, somente do consumo de hidratação causado pela corrida autopropelida. Economias HYDRATION compartilham teto de **30% por evento**.

Desidratação basal, calor/frio, exposição ambiental, efeitos de estado e outros custos hídricos permanecem provider-owned e intactos.

## Provider, boundary e authority

Owner de hidratação: **Thirst Was Reclaimed `3.0.4`**. Thirst Was Fixed `2.1.5` é compat/fix e não cria owner paralelo. Minecraft/NeoForge FoodData pode ser origem causal da ação, mas A0116 não é uma cópia de A0115.

O `BodyCostResolver` deve carregar a mesma `action_id` de corrida e, após a resolução METABOLIC, receber de um adapter TWR versionado um receipt contendo **apenas o parcel HYDRATION daquela ação** antes do débito do provider. A0116 atua uma vez nesse parcel. Nenhuma API específica de TWR é presumida sem prova de código/versão.

## Availability e fail-closed

Enquanto P-0037/BodyCostResolver HYDRATION ou o adapter TWR `3.0.4` estiver ausente/incompatível, A0116 é não comprável e allocation legado vale 0 PP. Depois que ambos existirem, receipt ausente para uma ação específica apenas omite o efeito naquela ação.

Proibido: escrita direta em thirst, polling/delta de barra, converter A0115 em hidratação, reduzir custos térmicos/ambientais ou usar Thirst Was Fixed como owner.

## Causalidade, dedup e composição

Identidade = `action_id + lane HYDRATION + provider receipt`. Uma aplicação por ação. A0115 e A0116 podem coexistir porque operam em lanes distintas; o mesmo debit não pode ser classificado duas vezes dentro da lane HYDRATION.

## Projetos próprios / provider → árvore

Volcanoes/Enshrouded hazards e Black Arcana resource costs não entram. O RPG apenas coordena o resolver; a hidratação permanece TWR-owned.

## Nove eixos / 18 critérios

PASS após hardening: gate de provider, ownership, custo real, causalidade, dedup, fallback, cap e separação METABOLIC/HYDRATION definidos. Topologia/PT-BR/Notion PASS; Especializações N/A; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0116-01` — unavailable/purchase fail-before-spend enquanto P-0037 ou TWR adapter faltar.
- `P-A0116-02` — validar API/código TWR 3.0.4 e implementar adapter somente se houver receipt causal pré-debit; nenhuma API deve ser inventada.
- `P-A0116-03` — propagar action_id e ordering METABOLIC → HYDRATION sem acoplar valores.
- `P-A0116-04` — cap HYDRATION 30%, one-action/one-contribution e provider mismatch fail-closed.

## Testes exigidos ao Chat 3

Provider absent/version mismatch; node indisponível sem binding; sprint causal vs basal/térmico/ambiental; coexistência A0115+A0116 sem duplicação; cap 30%; múltiplas actions no mesmo tick; nenhuma escrita direta/polling; respec/reload/multiplayer; GameTests, build, JAR e dedicated-server smoke.