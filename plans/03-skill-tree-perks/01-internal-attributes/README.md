# 01 — Internal Attributes

**Ponto 0 do plano do Codex.** A árvore interna dos atributos é compartilhada por todos os personagens e **não** entra nas quotas P/S/T.

A enum canônica `AttributeId` possui:

- `I0001` STRENGTH
- `I0002` CONSTITUTION
- `I0003` AGILITY
- `I0004` INTELLIGENCE
- `I0005` DETERMINATION
- `I0006` CHARISMA

`AttributeRanks` armazena ranks uncapped; custo de ranks é definido pela Core policy `AttributeRankCostPolicy`, não por esta pasta. Codex deve conferir fórmulas/efeitos reais antes de completá-los, sem inventar bônus e sem criar segunda economia de pontos.

**Requisito de perk:** ex.: `INTELLIGENCE >= 20` é *limiar não consumível*. O gasto em rank de atributo ocorreu no Core, e a compra de uma perk não debita esse atributo de novo. `NodeAccessRequirement` **não implementa mínimos de atributo atualmente**: contrato da compra fica em `PROVIDER-EVIDENCE-AND-COSTS.md` para implementação posterior.
