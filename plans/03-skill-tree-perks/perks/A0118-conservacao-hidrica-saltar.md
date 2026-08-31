# A0118 — Conservação Hídrica: Saltar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL com TWR.**

## Contrato
4 ranks; −3%/rank até −12% do parcel HYDRATION realmente derivado do mesmo salto; teto hídrico global 30%/evento.

## Hook
Mesma action-id de A0117 atravessa o BodyCostResolver; depois do lane METABOLIC, adapter Thirst Was Reclaimed 3.0.4 identifica a contribuição hídrica daquele receipt e aplica A0118 uma vez. Não reduzir sede basal/ambiental.

## Gate
Provider TWR ativo + Gateway SURVIVAL + receipt causal. Sem adapter/receipt, fail-closed para o evento.

## Chat 2
Testar sprint-jump, composição A0117+A0118 e ausência do provider.