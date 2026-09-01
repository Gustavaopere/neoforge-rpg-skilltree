# A0115 — Economia Metabólica: Correr

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; P-0037/`BodyCostResolver` METABOLIC não existe na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81d0b17de81fe45b332b

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Economia Metabólica — Movimento; camada 1; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Baixo.
- Gate estrutural: Gateway SURVIVAL + P-0037/BodyCostResolver METABOLIC canônico.

## Contrato congelado

Reduz **3% por rank**, até **12%**, exclusivamente da parcela positiva de `FoodData` exhaustion causada pela corrida **autopropelida**. Economias METABOLIC compartilham teto global de **30% por evento**.

Metabolismo basal, clima/temperatura, cura natural, fome passiva, saturação/nutrição e custos de outras ações permanecem intactos.

## Boundary e authority

Minecraft/NeoForge continua owner de FoodData/exhaustion. RPG Skill Tree poderá possuir o `BodyCostResolver`, mas somente se ele produzir uma `action_id`/receipt server-authoritative que isole o custo positivo da própria corrida **antes da cobrança final**. A contribuição A0115 é aplicada uma vez nesse parcel e não reescreve FoodData por compensação posterior.

A `main@66fcec7...` não contém `BodyCostResolver` nem P-0037. Logo o binding obrigatório ainda não existe.

## Availability, causalidade e dedup

Enquanto P-0037 estiver ausente/incompatível, A0115 é não comprável e allocation legado vale 0 PP para gates. Depois que o resolver existir, um evento sem receipt causal apenas falha fechado para aquele evento.

Proibido: estimar por tempo/distância correndo, polling de hunger/saturation, delta posterior de exhaustion, reduzir exhaustion global ou tratar movimento forçado/passivo como corrida.

Identidade: uma action_id de corrida e um parcel METABOLIC; exatamente uma contribuição A0115 por evento/resolução.

## Projetos próprios / provider → árvore

- RPG Skill Tree: futuro owner do resolver/availability; ainda ausente.
- Volcanoes: calor/pressão/respiração permanecem canais próprios e não viram sprint exhaustion.
- Enshrouded/Black Arcana: hazards/resource costs não são METABOLIC de corrida.

## Nove eixos / 18 critérios

PASS em design após o hardening: provider gate real, identidade mecânica, topologia SURVIVAL, owner de FoodData preservado, causalidade, dedup, teto compartilhado, no-free-resource e fail-closed. Especialização N/A; Notion corrigido/re-fetched; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0115-01` — materializar `UNAVAILABLE_NODE`/purchase fail-before-spend enquanto P-0037 faltar.
- `P-A0115-02` — implementar/validar `BodyCostResolver` METABOLIC somente se houver boundary server-side causal antes do débito; se a implementação real exigir mudança semântica, devolver ao Chat 1.
- `P-A0115-03` — action identity, one-event/one-contribution e cap compartilhado 30%.
- `P-A0115-04` — classificar corrida autopropelida sem incluir forced/passive movement.

## Testes exigidos ao Chat 3

Estado atual: não comprável/0 PP legado. Com P-0037: 0/1/múltiplos receipts no mesmo tick, 3/6/9/12%, cap 30%, sprint vs forced movement, outras exhaustion sources intactas, sem compensação posterior, dedup, respec/reload/multiplayer, GameTests, build, JAR e dedicated-server smoke.