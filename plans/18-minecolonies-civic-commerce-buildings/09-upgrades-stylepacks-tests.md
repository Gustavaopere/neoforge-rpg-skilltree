# 18.09 — Upgrades, style packs e gate de integração

## Upgrades

Cada building funcional mapeia seus níveis para VoxelModels Stage 14. Mudanças de capacity/jobs/service são publicadas somente depois do upgrade físico ser confirmado pelo provider.

## Style packs

Styles podem mudar arquitetura/paleta sem alterar capability contract. Provider dependencies são declaradas por style.

## Testes provider-present

- construir nível 1;
- atribuir worker;
- request/fulfill;
- upgrade sequencial até max level disponível;
- inventories/worker/service references preservados;
- demolish/rebuild recovery;
- rotation/style variant;
- proteção MineColonies.

## Core-only

Nenhuma classe MineColonies/Create é carregada no dedicated server sem providers. Definitions dependentes ficam unavailable com diagnóstico.

## Economia/governo

Testar um vertical slice:

```text
shop construído
→ merchant assigned
→ citizen recebe salário
→ estoque chega via courier
→ citizen compra item
→ imposto vai ao treasury
→ save/reload
→ nenhum receipt duplica
```

## Gate

Só declarar integração suportada após executar contra a versão MineColonies/Structurize alvo em NeoForge 1.21.1.

## Acceptance

Prédios são construíveis, operacionais, migráveis e financeiramente conectados sem assumir internals não testados.