# A0120 — Conservação Hídrica: Nadar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0116/P-0037/BodyCostResolver HYDRATION + adapter TWR.  
**Notion:** https://app.notion.com/p/3c569db9f0db81fbad53cdda8f43da73

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Conservação Hídrica — Movimento; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate: Gateway SURVIVAL + A0116 ≥2 ranks efetivamente válidos + P-0037 HYDRATION + adapter TWR `3.0.4`.

## Contrato congelado

Reduz **3% por rank**, até **12%**, somente do consumo HYDRATION realmente atribuído à **natação ativa autopropelida**. Teto compartilhado HYDRATION: **30% por evento**.

Estar submerso, boiar, corrente, veículo ou água ao redor não gera economia nem hidratação. Desidratação basal, térmica/ambiental, qualidade da água e oxigênio permanecem em canais próprios.

## Provider, boundary e authority

Thirst Was Reclaimed `3.0.4` é owner de hidratação; Thirst Was Fixed `2.1.5` é compat/fix. Após o lane METABOLIC, o BodyCostResolver deve propagar a mesma `action_id` de natação a um adapter TWR versionado que produza receipt somente do parcel hídrico daquela ação antes do debit.

A0119 não cria automaticamente A0120: METABOLIC e HYDRATION são lanes independentes que podem compartilhar causalidade, não valor.

## Availability, causalidade e dedup

A0120 herda A0116. A0116 indisponível, P-0037 ausente ou adapter TWR incompatível => node não comprável e allocation legado 0 PP. Com bindings presentes, evento sem receipt HYDRATION apenas não recebe efeito.

Proibido: escrever thirst diretamente, polling/bar delta, inferir custo por `in_water`, hidratar por água ambiente ou reduzir custos térmicos/ambientais.

Identidade: `action_id + HYDRATION receipt`; no máximo uma aplicação A0120 por ação/lane.

## Projetos próprios / provider → árvore

Volcanoes pressure/water, Enshrouded environment e Black Arcana não são hydration provider desta perk. Sem delta de capability no ciclo.

## Nove eixos / 18 critérios

PASS após hardening: availability transitiva, provider owner, action identity, typed lanes, cap, dedup, no-free-hydration e fail-closed. Topologia/PT-BR/Notion PASS; Especializações N/A; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0120-01` — unavailable transitivo A0116/P-0037/TWR e purchase fail-before-spend.
- `P-A0120-02` — validar adapter TWR 3.0.4; sem receipt causal pré-debit, não implementar redução.
- `P-A0120-03` — propagar mesma action_id de A0119 com lanes independentes e one-application.
- `P-A0120-04` — excluir float/current/vehicle/forced motion e preservar basal/térmico/environment/quality.

## Testes exigidos ao Chat 3

Provider absent/version mismatch; availability; active swim vs float/current/vehicle; água ao redor não hidrata; coexistência A0119+A0120 sem duplicação; cap 30%; basal/térmico/ambiental intactos; no direct writes/polling; respec/reload/multiplayer; GameTests, build, JAR e dedicated-server smoke.