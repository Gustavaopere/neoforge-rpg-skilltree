# A0119 — Economia Metabólica: Nadar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0115/P-0037/BodyCostResolver METABOLIC.  
**Notion:** https://app.notion.com/p/3c569db9f0db8141976fc39574bb850b

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Economia Metabólica — Movimento; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate: Gateway SURVIVAL + A0115 ≥2 ranks efetivamente válidos + P-0037 METABOLIC.

## Contrato congelado

Reduz **3% por rank**, até **12%**, apenas da parcela positiva de FoodData exhaustion causada pela **natação ativa autopropelida**. Teto compartilhado METABOLIC: **30% por evento**.

Correntes, boiar, veículos, contraptions, knockback e simples estado `in_water` não recebem economia. A perk não altera oxigênio, resistência à água, temperatura, hidratação ou nutrição.

## Boundary, causalidade e dedup

BodyCostResolver METABOLIC deve correlacionar uma `action_id` de natação ativa a um receipt da parcela positiva de exhaustion realmente causada pelo deslocamento antes do debit final. Uma action_id recebe no máximo uma contribuição A0119.

A0119 não pode inferir custo por velocidade, distância ou estado aquático; deslocamento externo sem debit causal falha fechado.

## Availability e fallback

A0119 herda A0115. Enquanto A0115/P-0037 estiver indisponível, node não comprável e allocation legado 0 PP. Depois do resolver existir, trecho/evento sem receipt causal simplesmente não aplica.

Nenhuma redução global de exhaustion, polling ou compensação posterior é fallback válido.

## Projetos próprios / provider → árvore

Volcanoes água/pressão, Enshrouded hazards e Black Arcana não fornecem o receipt METABOLIC. Sem delta de capability neste ciclo.

## Nove eixos / 18 critérios

PASS após hardening: gate transitivo, custo real, self-propelled causality, dedup, cap, sem heurística e fail-closed. Topologia/PT-BR/Notion PASS; Especializações N/A; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0119-01` — unavailable transitivo A0115/P-0037 e purchase fail-before-spend.
- `P-A0119-02` — action identity de natação ativa e receipt METABOLIC pré-debit.
- `P-A0119-03` — excluir currents/floating/vehicles/forced motion sem depender apenas de delta de posição.
- `P-A0119-04` — one-action/one-contribution e cap METABOLIC 30%.

## Testes exigidos ao Chat 3

Availability; active swim vs float/current/vehicle/forced movement; `in_water` isolado não basta; 3/6/9/12%, cap 30%, callbacks duplicados, outras exhaustion intactas; respec/reload/multiplayer; GameTests, build, JAR e dedicated-server smoke.