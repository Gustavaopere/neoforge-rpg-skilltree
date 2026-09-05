# A0142 — Digestão Frugal

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

O tradeoff é inseparável e o Nutritional Balance 7.0.3 não expõe hoje um seam público/causal seguro para modular o decremento nutricional durante repouso antes da aplicação. Compra deve falhar antes do gasto; allocation legado vale 0 PP.

## Contrato

- Domínio SURVIVAL; ramo Nutrição — Metabolismo; camada 4; Notable.
- 1 rank; 2 PP.
- Pré-requisitos: Gateway SURVIVAL + ≥2 ranks em dois nodes METABOLIC distintos e capability-eligible + Nutritional Balance 7.0.3.
- Custo: no consumo de alimento elegível, saturação positiva creditada ao `FoodData` ×0,92.
- Benefício inseparável: durante repouso fisiológico, magnitude do decremento nutricional real do Nutritional Balance ×0,85.
- Hunger, nutrientes creditados pelo alimento, hidratação, efeitos e intoxicação permanecem nativos.

## Evidência exata do provider

Snapshot: `dannydjdk/Nutritional-Balance@fce213e966b395b16ae30a801a19a37f6a73da50`.

`INutritionalBalancePlayer` expõe `getPlayerNutrients()`, `getStatus()` e `processSaturationChange(float)`. Em `DefaultNutritionalBalancePlayer.processSaturationChange(...)`, quando `currentSaturation < savedSaturation`, o provider calcula:

`decrementer = NUTRIENT_DECAY_RATE × (currentSaturation - savedSaturation) / N`

e aplica `changeValue(decrementer)` a cada nutriente. O método não recebe contexto do jogador e não publica evento/reducer público para alteração causal pré-aplicação.

## Availability

Exige capability completa `A0142_NUTRITION_DECAY_REDUCER`: os dois lados do tradeoff devem ser implementáveis de forma causal e provider-native. Enquanto o segundo lado não possuir seam seguro, a perk inteira é `UNAVAILABLE_NODE`.

É proibido entregar somente preservação nutricional ou somente a penalidade de saturação.

## Boundary futuro

`food consumption real → saturação positiva a creditar → ×0,92 → FoodData commit`

`queda real de saturação → Nutritional Balance calcula decremento → player em repouso elegível → magnitude ×0,85 → changeValue uma vez`.

Repouso fisiológico: ≥200 ticks sem causar/receber dano hostil elegível e sem `body_cost_event` físico ativo no tick, conforme contrato do catálogo.

## Exclusões

- não hardcodear quantidade/nome de nutrientes;
- não inferir decay por polling de barras;
- não compensar nutrientes depois do fato;
- não alterar hunger/hydration;
- não usar mixin implícito não documentado como se fosse API aprovada;
- não duplicar `processSaturationChange()`.

## Handoff Chat 2

Manter `UNAVAILABLE_NODE` até existir seam completo. Se um futuro adapter exigir alteração essencial do tradeoff ou authority, devolver ao Chat 1 em vez de redesenhar silenciosamente.

## Testes Chat 3

1. purchase fail-before-spend e legacy PP 0;
2. nodes METABOLIC fail-closed não satisfazem o gate;
3. custo e benefício ativam/desativam juntos;
4. saturação ×0,92 sem tocar hunger/nutrients/hydration/effects;
5. decay ×0,85 somente sobre decremento real e durante repouso;
6. lista de nutrientes dinâmica, inclusive N diferente de 5;
7. no polling/refund/replay/double-processing;
8. provider removal/reload/respec/logout/restart/multiplayer.