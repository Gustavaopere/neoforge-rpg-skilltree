# A0142 — Digestão Frugal

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COMO TRADEOFF ATÔMICO.**

## Contrato
Notable SURVIVAL/NUTRITION, 1 rank, 2 PP. Ao comer: saturação creditada ao FoodData ×0,92, sem alterar hunger/nutrientes/hidratação/efeitos. Em repouso fisiológico, magnitude do decremento nutricional real do Nutritional Balance 7.0.3 por queda de saturação ×0,85.

## Boundary
Nutritional Balance expõe `processSaturationChange` e lista/status nutricional publicamente. O adapter deve modular o decremento causal no ponto real; o lado de custo atua apenas sobre saturation positiva creditada.

## Exclusões
Não fixar cinco nutrientes, nomes ou thresholds; não reduzir hunger/hydration e não conceder benefício se o custo não puder ser aplicado.

## Chat 2
Implementar custo+benefício inseparáveis e repouso server-side (≥200 ticks sem dano hostil e sem body_cost_event físico no tick). Falha de qualquer metade desativa a perk inteira.