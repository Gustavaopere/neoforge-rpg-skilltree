# A0118 — Conservação Hídrica: Saltar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0116/P-0037/BodyCostResolver HYDRATION + adapter TWR.  
**Notion:** https://app.notion.com/p/3c569db9f0db81e28d9aee8b32c6b38f

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Conservação Hídrica — Movimento; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate: Gateway SURVIVAL + A0116 ≥2 ranks efetivamente válidos + P-0037 HYDRATION + adapter TWR `3.0.4`.

## Contrato congelado

Reduz **3% por rank**, até **12%**, somente do parcel HYDRATION realmente atribuído a um salto legítimo; teto compartilhado HYDRATION de **30% por evento**. Desidratação basal, térmica e ambiental permanece intacta.

## Provider, boundary e ordering

Thirst Was Reclaimed `3.0.4` é owner de hidratação; Thirst Was Fixed `2.1.5` é compat/fix. A mesma `action_id` do salto atravessa o BodyCostResolver: após o lane METABOLIC, o adapter TWR deve produzir receipt com somente o custo hídrico daquela ação antes do debit. A0118 aplica uma vez nesse parcel.

ParCool `4.0.0.2`/Epic ParCool `21.0.0` apenas podem classificar ação especial quando houver custo real e receipt causal; animação não cria custo.

## Availability, dedup e fail-closed

A0118 herda A0116. A0116 indisponível, P-0037 ausente ou adapter TWR incompatível => A0118 não comprável e 0 PP legado. Depois do binding existir, ação sem receipt HYDRATION apenas omite o efeito.

Proibido: direct thirst writes, polling/delta da barra, reduzir custo térmico/ambiental, converter A0117 em hidratação ou double-discount da mesma action/lane.

## Projetos próprios / provider → árvore

Sem delta de capability: nenhum projeto próprio fornece hydration receipt. Volcanoes/Enshrouded hazards e Black Arcana costs permanecem fora.

## Nove eixos / 18 critérios

PASS após hardening: gate transitivo, provider-native owner, causalidade/action-id, ordering tipado, cap, dedup e fail-closed; topologia/PT-BR/Notion PASS; Especializações N/A; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0118-01` — unavailable transitivo A0116/P-0037/TWR e purchase fail-before-spend.
- `P-A0118-02` — validar adapter TWR 3.0.4; sem receipt pré-debit, manter fail-closed.
- `P-A0118-03` — mesma action_id de A0117, ordering METABOLIC→HYDRATION e one-lane/one-application.
- `P-A0118-04` — ParCool somente quando ação/custo reais forem provados.

## Testes exigidos ao Chat 3

Provider absent/version mismatch; availability transitiva; salto normal/sprint-jump/sem custo; A0117+A0118 sem duplicação; basal/térmico/ambiental intactos; cap 30%; direct-write/polling proibidos; multiplayer/respec/reload; GameTests, build, JAR e dedicated-server smoke.