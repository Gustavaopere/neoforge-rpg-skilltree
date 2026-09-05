# 16.06 — Propriedade, aluguel, patrimônio e classe social

## Propriedade

`PropertyRecord` representa ativo econômico identificável: residência, lote/distrito quando aplicável, estabelecimento ou outro bem cadastrado. Não tentar atribuir propriedade a cada bloco do mundo.

Campos principais: propertyId, owner actor, building/POI reference, district, valuation basis, occupancy/tenant, liens/debt references e revision.

## Aluguel

`LeaseContract` define landlord, tenant, property, periodic rent, deposit opcional, policy revision e arrears. Cobrança usa ledger comum. Falta de pagamento gera dívida/ação legal conforme Stage 17; não remove cidadão instantaneamente por regra hardcoded.

## Patrimônio

`netWealth` deriva de:

```text
cash + eligible property valuation + business equity/claims - recognized debt
```

Valuation é data-driven e bounded; não precisa recalcular preço de cada imóvel a cada tick.

## Classe social

Classe não é profissão nem caste fixa. É snapshot derivado de renda, patrimônio, propriedade e critérios do regime. Taxonomia inicial pode representar pobreza/baixa renda, trabalhadores, classe média, ricos/elites, mas IDs e thresholds são data-driven e traduzidos.

Stage 17 pode usar wealth/class para voto censitário ou privilégios, sem Stage 16 impor a política.

## Mobilidade

Mudanças econômicas podem mover um cidadão entre classes após janela/histerese para evitar flip-flop diário.

## Testes

- owner/tenant;
- rent paid/arrears;
- wealth with debt;
- class threshold/hysteresis;
- sale/transfer property;
- citizen identity survives reload.

## Acceptance

Voto censitário, tributação e desigualdade consultam fatos econômicos reproduzíveis, não tags arbitrárias.