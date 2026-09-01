# A0125 — Economia Metabólica: Cortar Madeira

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; P-0037/`BodyCostResolver` ausente.  
**Notion:** https://app.notion.com/p/3c569db9f0db810f98cffbd4ba02aa18

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Esforço Profissional — Silvicultura; função Ponte.
- 4 ranks; 1 PP/rank.
- Gate: Gateway SURVIVAL + corredor Silvicultura real + `BodyCostResolver` METABOLIC.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente a exhaustion/FoodData positiva realmente cobrada por quebra manual classificada como Silvicultura. Todas as eficiências METABOLIC do mesmo evento respeitam teto compartilhado de **30%**.

A origem natural/colocada do bloco não é critério de reward: se a ação manual paga custo corporal real, pode receber a economia. Porém tree-felling/bulk breaks derivados não fabricam parcelas extras e automação sem débito corporal do jogador é inelegível.

## Pipeline

`manual forestry action_id -> classificação explícita -> quote/receipt METABOLIC positivo -> aggregate reducers -> cap 30% -> commit provider uma vez`.

Nunca inferir custo por número de toras derrubadas, tempo da animação, dureza ou durabilidade da ferramenta. Mods de árvore podem classificar a ação, não possuir automaticamente FoodData.

## Availability / fail-closed

Sem P-0037/BodyCostResolver com hook seguro, A0125 é não comprável; compra falha antes do gasto; allocation legado = 0 PP e permanece reembolsável/migrável. Depois do binding global, ação sem receipt apenas não recebe o benefício.

## Projetos próprios

RPG Skill Tree: resolver/availability futuro. Volcanoes: vegetação/hazards não criam custo. Enshrouded/Black Arcana: N/A.

## Pendências Chat 2

- `P-A0125-01` — unavailable enquanto P-0037 ausente.
- `P-A0125-02` — boundary causal FoodData para forestry manual; sem seam, manter fail-closed.
- `P-A0125-03` — classifier Silvicultura explícito, bulk/tree-felling dedup, automação/fake player exclusions.
- `P-A0125-04` — cap METABOLIC 30%, uma action/uma cobrança, lifecycle.

## Testes Chat 3

Purchase/PP zero; manual log break; placed log com custo real; tree-felling/bulk sem multiplicação; automação; custo zero/cancel; classifier absent; cap 30%; respec/reload/multiplayer; GameTests/build/JAR/dedicated smoke.
