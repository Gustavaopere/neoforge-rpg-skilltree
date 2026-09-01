# A0121 — Economia Metabólica: Escalar

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; `BodyCostResolver`/P-0037 e `METABOLIC_CLIMB` não existem na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81bebd7cd0a64234a1af

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Economia Metabólica — Mobilidade Avançada; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank; faixa Pequeno/Médio.
- Gate: A0115 ≥2 + A0117 ≥2 + Gateway SURVIVAL + capability corporal causal de escalada.

## Contrato congelado

A0121 reduz em **3% por rank**, até **12%**, somente a parcela `METABOLIC` positiva e causalmente atribuída a uma ação legítima de escalada. Todas as economias METABOLIC elegíveis do mesmo evento compõem sob **teto compartilhado de 30%**, não por descontos sequenciais que contornem o cap.

ParCool pode identificar/classificar a ação de escalada, mas não cria hunger/exhaustion. Epic Fight/ParCool Stamina é recurso distinto e nunca pode ser convertido em `FoodData`.

## Authority e pipeline

- Owner do custo corporal: Minecraft/NeoForge `FoodData` ou outro provider corporal explicitamente mapeado.
- ParCool **4.0.0.2** + Epic ParCool 21.0.0: somente identidade/classificação da escalada.
- RPG Skill Tree: `BodyCostResolver` futuro e aplicação da eficiência, sem inventar custo base.

Pipeline canônico futuro:

`action_id CLIMB server-side -> quote/receipt METABOLIC positivo do provider -> agregar eficiências METABOLIC -> cap 30% -> commit provider uma vez`.

A redução deve agir sobre custo realmente cotado/observável antes do commit final. Se a API real não oferecer fronteira segura para alterar o débito, a perk continua indisponível; não compensar por refund posterior ou polling.

## Availability / fail-closed

Enquanto `BodyCostResolver` ou `METABOLIC_CLIMB` não existirem de forma causal, compra nova falha antes do gasto e allocation legado vale **0 PP** para gates, permanecendo reembolsável/migrável. Classificar escalada sem custo corporal não satisfaz o gate.

Depois de existir binding global, ausência de receipt em uma ação específica apenas omite o proc daquele evento.

## Anti-abuso e dedup

- Uma `action_id` de escalada -> no máximo uma resolução A0121.
- Movimento forçado, veículo, corrente, contraption, teleport, knockback e animação sem custo corporal são inelegíveis.
- Não criar exhaustion artificial para tornar a perk funcional.

## Projetos próprios / provider → árvore

- RPG Skill Tree: owner permitido do resolver/availability; runtime ainda ausente.
- Volcanoes: pressão, atmosfera, calor e hazards não são `METABOLIC_CLIMB`.
- Enshrouded: Exposure/Shroud não classificam custo metabólico de escalada.
- Black Arcana: recursos/custos arcanos não são custo corporal desta perk.

## Pendências para Chat 2

- `P-A0121-01` — materializar `UNAVAILABLE_NODE` transitivo de A0115/A0117 e da capability `METABOLIC_CLIMB`.
- `P-A0121-02` — implementar somente se existir boundary real para `BodyCostResolver` METABOLIC com quote/receipt/commit causal; sem hook, manter indisponível.
- `P-A0121-03` — adapter ParCool 4.0.0.2/Epic ParCool apenas para identidade da ação, com exclusões forced/passive.
- `P-A0121-04` — dedup por `action_id`, cap compartilhado 30%, lifecycle/rules reload/respec.

## Testes exigidos ao Chat 3

Purchase fail-before-spend e PP legado 0; predecessor indisponível; provider absent/version mismatch; escalada legítima com/sem receipt; Stamina não convertida; forced/passive exclusions; uma action/um settlement; cap 30%; cancelamento/rollback; multiplayer; respec/rules reload/logout; NeoForge GameTests, build, JAR e dedicated-server smoke quando a implementação existir.
