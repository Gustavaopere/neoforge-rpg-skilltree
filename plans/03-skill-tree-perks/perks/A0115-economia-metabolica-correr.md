# A0115 — Economia Metabólica: Correr

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
Ramo SURVIVAL/METABOLISM, 4 ranks, 1 PP/rank. Reduz em 3% por rank somente a parcela positiva de FoodData exhaustion causada por corrida autopropelida, até 12%. Todas as economias METABOLIC compartilham teto de 30% por evento.

## Boundary
Minecraft possui custo real de sprint, mas NeoForge 1.21.1 não expõe evento geral de exhaustion. `BodyCostResolver` deve instrumentar/correlacionar a chamada server-side de `Player#causeFoodExhaustion` com uma action identity de corrida; sem receipt causal, não aplicar.

## Exclusões
Movimento forçado/passivo, metabolismo basal, clima, cura natural e outras fontes de exhaustion não entram. Não alterar saturation/nutrition/hydration diretamente.

## Chat 2
Implementar receipt antes do débito final, uma aplicação por action-id e teto compartilhado de 30%; sem consumer instalado, acquisition fica fail-closed até o runtime existir.