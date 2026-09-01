# A0127 — Economia Metabólica: Lutar Corpo a Corpo

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; P-0037/`BodyCostResolver` ausente.  
**Notion:** https://app.notion.com/p/3c569db9f0db81c58fbcf563b8586965

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Esforço Profissional — Combate Corpo a Corpo; função Ponte.
- 4 ranks; 1 PP/rank.
- Gate: Gateway SURVIVAL + acesso MARTIAL real + `BodyCostResolver` METABOLIC.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente a parcela positiva de FoodData exhaustion realmente cobrada pela ação melee elegível que acerta alvo hostil válido. Ataque no vazio, DoT, reflexão, summon, proc derivado e callback duplicado não geram novo custo A0127.

Todas as eficiências METABOLIC da mesma ação compartilham cap de **30%**.

## Authority / causalidade

Minecraft/NeoForge FoodData é owner do custo corporal. Epic Fight **21.17.3.1** serve apenas para identificar/classificar ações melee integradas; Stamina é recurso independente e não pode substituir METABOLIC.

Pipeline: `melee root action_id -> hit hostil válido -> quote/receipt METABOLIC positivo da mesma root -> aggregate reducers -> cap 30% -> commit uma vez`.

Usar o débito real da ação; não inferir custo por dano, animação, attack speed ou arma. Se o provider só expuser débito pós-fato sem seam transacional seguro, a perk continua indisponível — sem refund heurístico.

## Availability

Sem P-0037/BodyCostResolver: compra falha antes do gasto; allocation legado = 0 PP/reembolsável. Após o binding global existir, outcome sem receipt só não recebe economia.

## Dedup / atribuição

Uma root melee -> no máximo um settlement. Target deve ser hostil válido e autoria direta do jogador. Proc secundário não abre nova action metabólica.

## Projetos próprios

RPG Skill Tree: resolver futuro. Volcanoes hazards não são melee cost. Enshrouded/Black Arcana damages ou custos próprios não entram como FoodData melee.

## Pendências Chat 2

- `P-A0127-01` — unavailable enquanto P-0037 ausente.
- `P-A0127-02` — binding transacional causal FoodData para melee confirmado; sem seam, fail-closed.
- `P-A0127-03` — Epic Fight 21.17.3.1 classifier/root attribution sem converter Stamina.
- `P-A0127-04` — dedup root, hostilidade, cap 30%, lifecycle/rules reload.

## Testes Chat 3

Purchase/PP zero; melee hit vs miss; target inválido; Stamina separada; DoT/proc/reflection/summon dedup; vanilla/Epic Fight convergence; cap 30%; cancel/zero; multiplayer attribution; respec/reload; GameTests/build/JAR/dedicated smoke.
