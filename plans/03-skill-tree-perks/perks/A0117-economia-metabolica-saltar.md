# A0117 — Economia Metabólica: Saltar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0115/P-0037/BodyCostResolver METABOLIC.  
**Notion:** https://app.notion.com/p/3c569db9f0db81c19ac1fe504cc2b3e2

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Economia Metabólica — Movimento; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate: Gateway SURVIVAL + A0115 ≥2 ranks efetivamente válidos + P-0037 METABOLIC.

## Contrato congelado

Reduz **3% por rank**, até **12%**, somente da parcela positiva de FoodData exhaustion causada por um **salto legítimo**. Economias METABOLIC compartilham teto global de **30% por evento**.

Sprint-jump usa o custo realmente produzido pelo vanilla/provider; não há valor hardcoded. Salto gratuito, teleporte, launch/impulso externo ou movimento sem debit metabólico não gera economia.

## Boundary e providers

O BodyCostResolver METABOLIC deve correlacionar uma `action_id` de salto server-side ao receipt positivo de exhaustion daquela mesma ação antes do débito final. ParCool `4.0.0.2` e Epic ParCool `21.0.0` são, no máximo, classificadores de saltos especiais **quando a própria ação produzir custo METABOLIC causal**; nunca providers de fome/exhaustion.

## Availability, causalidade e dedup

A0117 herda a availability de A0115. Enquanto A0115/P-0037 estiver indisponível, A0117 é não comprável e allocation legado vale 0 PP. Com o resolver presente, uma ação sem receipt causal apenas não recebe o benefício.

Uma action_id de salto recebe no máximo uma contribuição A0117. Sprint-jump não pode ser contado duas vezes como “corrida + salto” sobre o mesmo parcel; lanes/receipts precisam preservar o custo real sem double-discount.

## Projetos próprios / provider → árvore

Nenhum dos quatro projetos próprios adicionou novo hook para esta perk no delta atual. Volcanoes/Enshrouded forced/environment movement e Black Arcana casting não são salto metabólico.

## Nove eixos / 18 critérios

PASS após hardening: gate transitivo, custo real, action identity, cap, dedup, no heuristic movement e fail-closed. Topologia/PT-BR/Notion PASS; Especializações N/A; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0117-01` — unavailable transitivo A0115/P-0037 e purchase fail-before-spend.
- `P-A0117-02` — produzir/classificar action_id de salto legítimo sem usar animação/deslocamento como prova de custo.
- `P-A0117-03` — impedir double-discount sprint-jump e respeitar cap METABOLIC 30%.
- `P-A0117-04` — ParCool/Epic ParCool apenas por adapter versionado que prove ação + custo real; caso contrário omitidos.

## Testes exigidos ao Chat 3

Node indisponível com predecessor/provider ausente; salto normal, sprint-jump, salto sem exhaustion, teleport/knockback/launch excluídos; ParCool absent/present; callbacks duplicados; cap 30%; composição A0115; respec/reload/multiplayer; GameTests, build, JAR e dedicated-server smoke.