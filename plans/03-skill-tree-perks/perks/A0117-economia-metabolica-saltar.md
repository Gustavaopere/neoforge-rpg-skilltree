# A0117 — Economia Metabólica: Saltar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
4 ranks; −3%/rank até −12% da FoodData exhaustion realmente atribuída ao salto. Sprint-jump usa o custo que vanilla/provider efetivamente produzir; não hardcodar valores e não duplicar A0115 pela mesma action.

## Hook
BodyCostResolver/METABOLIC correlaciona action-id de salto server-side com receipt positivo em `causeFoodExhaustion`, aplica uma vez e respeita cap global 30%.

## Exclusões
Knockback, queda, launch, voo e movimento forçado não contam. ParCool só participa se a ação real terminar em débito FoodData causal.

## Chat 2
Cobrir salto normal, sprint-jump, callback duplicado e ação sem exhaustion.