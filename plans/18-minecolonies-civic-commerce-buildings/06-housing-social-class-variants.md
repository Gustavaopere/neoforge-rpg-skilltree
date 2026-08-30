# 18.06 — Habitação, aluguel e variantes socioeconômicas

## Objetivo

Representar diferenças de moradia sem transformar classe social em caste física automática.

## HousingProfile

Metadados de residência podem declarar:

- capacity;
- valuation tier;
- rent baseline;
- comfort/service requirements;
- district/zoning tags;
- style/upgrade level;
- heating demand profile.

## Classe social

Stage 16 deriva classe econômica. O perfil da casa influencia valor/aluguel e pode refletir riqueza, mas mudança de classe não teleporta cidadão nem expulsa morador automaticamente.

Law pode criar requisitos, habitação pública, rent cap ou segregation fictícia somente se design futuro explicitamente autorizar; o baseline não hardcoda segregação.

## MineColonies residence

Quando possível, reutilizar residences/occupancy existentes e anexar economic property references em vez de duplicar todo sistema residencial.

## Upgrade

Nível físico altera valuation/capacity/heat demand conforme definition. Stage 14 fornece BOM; Stage 16 registra financiamento/propriedade.

## Testes

- owner vs tenant;
- rent;
- housing upgrade;
- class change sem forced move;
- heat demand;
- residence provider bridge.

## Acceptance

Habitação participa de patrimônio, aluguel e aquecimento sem substituir a lógica residencial do MineColonies.