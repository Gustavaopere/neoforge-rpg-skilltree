# 19.04 — Combustíveis, caldeiras e Generator Core

## Generator Core

Bloco/serviço próprio é a autoridade que converte consumo validado de combustível em `HeatSupply`. Pode existir dentro do blueprint da Central Térmica.

## Combustíveis

`FuelClassifier` data-driven:

1. explicit override;
2. tags de combustível/provider;
3. recipe/component evidence estável;
4. adapter específico;
5. desconhecido = não combustível, com diagnóstico opcional.

`FuelDefinition` declara heat value, form (`ITEM`, `FLUID` quando suportado), provider requirement, emissions/side effects somente se outro sistema realmente consumir isso.

Não manter lista manual eterna de todo combustível modded.

## Consumo

- retirar item/fluid de inventory/tank real;
- receipt operacional impede double consume/reward;
- supply existe somente para combustível efetivamente consumido;
- shutdown segura quando input acaba.

## Boilers

Caldeira/visual pode usar Create/provider, mas supply RPG não pode ser gerado só porque um shaft gira. O adapter precisa provar combustível + operação válida.

## Testes

- item fuel;
- modded tag;
- invalid fuel;
- empty storage;
- reload mid-burn;
- duplicate callback;
- provider absent.

## Acceptance

Cada unidade de calor produzida possui origem energética rastreável.