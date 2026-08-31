# A0131 — Economia Metabólica: Conjurar

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Ponte SURVIVAL_ARCANE_BRIDGE/METABOLIC, 4 ranks, 1 PP/rank. Reduziria 3% por rank somente custo corporal `METABOLIC_CAST` real, até 12%.

## Boundary
Iron's 3.16.3, Ars Nouveau 5.13.1 e outros providers identificam casts/recursos, mas mana/Source/Soul Energy/HP/cooldown não são FoodData exhaustion. Provider corporal de `METABOLIC_CAST` está ausente.

## Exclusões
Não inventar exhaustion por conjuração e não converter recursos mágicos em fome.

## Chat 2
Manter compra desabilitada; só habilitar por adapter que prove custo corporal positivo e causal da mesma action_id.