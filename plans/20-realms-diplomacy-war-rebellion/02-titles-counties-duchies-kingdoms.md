# 20.02 — Títulos, condados, ducados e reinos

## Objetivo

Representar hierarquia territorial/feudal quando o regime usar esse modelo.

## TitleDefinition

IDs extensíveis podem representar ranks como county/duchy/kingdom e títulos equivalentes. Não hardcodar apenas nomes europeus; o conteúdo/localization pode variar.

Campos:

- rank/order;
- holder Economic/PoliticalActor;
- liege title;
- territory/colony references;
- succession/appointment policy;
- tribute/service obligations;
- legal privileges.

## Constraints

- grafo de liege sem ciclos;
- território não pertence a dois peer titles incompatíveis;
- title não substitui district/region geometry;
- title inactive em regime não feudal pode ser preservado historicamente sem granting powers.

## Succession

A regra deve ser data-driven e determinística. Se não existir candidato válido, vacancy/interregnum é estado explícito, não escolha aleatória invisível.

## Testes

- hierarchy;
- cycle;
- title transfer;
- interregnum;
- regime transition disables privileges but preserves history;
- localization.

## Acceptance

Feudalismo consegue expressar lord/vassal/território sem misturar titles com MineColonies building ownership.