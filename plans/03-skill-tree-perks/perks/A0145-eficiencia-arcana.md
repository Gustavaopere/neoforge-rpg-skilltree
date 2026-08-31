# A0145 — Eficiência Arcana

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL POR PROVIDER DE MANA.**

## Contrato
Tronco ARCANE/MANA_EFFICIENCY, 5 ranks, 1 PP/rank. −2% por rank exclusivamente sobre MANA nativa paga por cast, até −10%, respeitando piso/unidade mínima do provider.

## Boundary
Iron's 3.16.3 `SpellOnCastEvent` expõe custo original/ajustável de mana; Ars Nouveau 5.13.1 requer adapter equivalente. Custos compostos preservam parcelas não-MANA.

## Exclusões
Source, Soul Energy, sangue/HP, reagentes, charges e energia tecnológica não são mana.

## Chat 2
Aplicar uma vez antes da cobrança final; provider sem hook MANA fica fail-closed sem afetar os demais.