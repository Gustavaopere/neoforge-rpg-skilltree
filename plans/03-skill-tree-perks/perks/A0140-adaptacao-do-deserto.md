# A0140 — Adaptação do Deserto

## Estado Chat 1

**DESIGN APROVADO APÓS HARDENING / UNAVAILABLE_NODE no snapshot atual.**

## Contrato

- Keystone SURVIVAL/ACCLIMATION_HOT; 1 rank; 2 PP.
- Usa 0–5 cargas `ENVIRONMENTAL_HOT` sem consumi-las.
- Cada carga reduz em 3% somente o **surcharge HYDRATION ambiental quente real acima da base**, até −15%, dentro do cap HYDRATION compartilhado de 30%.
- Componente histórico `THERMAL_PHYSIOLOGY_HOT`: −4%/carga sobre uma penalidade fisiológica numérica específica, mas continua opcional/fail-closed até existir mapper provider-native seguro.
- Pré-requisitos: A0135 ≥2 + A0136 ≥2 + Gateway SURVIVAL.

## Authority e realidade do provider

- Cold Sweat 2.4.2 é autoridade térmica.
- Thirst Was Reclaimed 3.0.4 é owner de HYDRATION.
- No TWR 1.21.1 auditado, `PlayerThirst.updateExhaustion()` deriva a sede do delta de FoodData e `addExhaustion()` aplica `getExhaustionBiomeModifier(...)` internamente. Não existe hoje um receipt separado pronto de `HYDRATION_ENVIRONMENTAL_HOT_SURCHARGE`.
- `AcclimationLedger` também não está live na main.
- Volcanoes não é provider direto: sua futura contribuição térmica deve alimentar Cold Sweat; A0140 continua consumer indireto.

## Availability

Compra somente quando A0135/A0136 forem realmente adquiríveis + `AcclimationLedger(ENVIRONMENTAL_HOT)` estiver live + adapter ambiental Cold Sweat versionado + seam TWR capaz de isolar causalmente o surcharge hídrico quente. Enquanto isso, purchase falha antes do gasto e allocation legado vale 0 PP.

Depois que o componente HYDRATION principal estiver implementável, ausência de `THERMAL_PHYSIOLOGY_HOT` apenas omite esse componente opcional e **não** bloqueia a perk inteira.

## Ledger

- +1 carga após 10 min consecutivos em `ENVIRONMENTAL_HOT`, máximo 5;
- fora do estado: −1 após 20 min consecutivos;
- em `ENVIRONMENTAL_COLD`: −1 a cada 5 min;
- sem progresso offline;
- `ENVIRONMENTAL_HOT` é distinto de `ADVERSE_HOT` corporal.

## Exclusões

Não estimar surcharge por polling da barra, não escrever em thirst, WORLD/BODY/CORE/RATE/thresholds/resistências, não transformar Volcanoes em segundo sistema térmico.

## Testes Chat 3

- purchase fail-before-spend/0 PP atual;
- availability transitiva A0135→A0136→A0140;
- 0–5 cargas e temporizadores 10/20/5 min, sem progresso offline;
- ENVIRONMENTAL_HOT != ADVERSE_HOT;
- surcharge isolado causalmente e aplicado uma vez; cap 30%;
- ausência de mapper fisiológico omite apenas componente opcional;
- provider removal, respec, rules reload, dimension/logout/restart e multiplayer.
