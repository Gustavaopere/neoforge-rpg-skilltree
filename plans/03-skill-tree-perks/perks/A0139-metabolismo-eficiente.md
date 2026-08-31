# A0139 — Metabolismo Eficiente

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Notable SURVIVAL/METABOLISM, 1 rank, 2 PP. Em body_cost_event suportado: −12% adicional METABOLIC e, quando houver receipt correspondente, −12% HYDRATION; cada canal respeita teto 30%. Custo inseparável: −8% de regeneração natural de Stamina.

## Boundary
Benefícios usam BodyCostResolver/TWR. O custo exige `STAMINA_NATURAL_REGEN_MODIFIABLE` do Epic Fight 21.17.3.1. A auditoria atual não provou boundary versionado para modificar especificamente a regeneração natural; a integração Epic Fight permanece future-provider.

## Exclusões
Não aplicar benefício sem custo, não aproximar regen por polling e não substituir Stamina por FoodData, mana ou movement speed.

## Chat 2
Manter aquisição desabilitada e allocation legado = 0 PP para Gate B até o adapter de regen natural ser provado. Quando existir, aplicar custo e benefícios como contrato inseparável.