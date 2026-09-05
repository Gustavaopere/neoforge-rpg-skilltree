# 18.02 — Prédios comerciais e financeiros

## Objetivo

Criar pontos econômicos reais para transações do Stage 16.

## Perfis

O framework deve suportar, por definitions, funções como:

- mercado/market hall;
- loja geral;
- loja especializada por `GoodCategory`;
- banco/serviço financeiro;
- guilda/empresa/office comercial quando necessário.

Os nomes finais de conteúdo podem variar por style pack; a capability é a parte canônica.

## Shop

Building com `commerce` expõe estoque elegível, owner account, markup policy e terminal/worker de venda. A compra executa `TransactionCoordinator` do Stage 16. Courier reabastece estoque; não recebe preço por transportar.

## Banking

Building com `banking` pode fornecer UI/serviço para contas, debt contracts e treasury access autorizado. Saldo não fica armazenado fisicamente em chest como única autoridade.

## Ownership

Capitalismo pode permitir owner privado; regimes comunais podem tornar o building público. A transição altera `EconomicActorId` owner via Stage 17/16, não recria o prédio.

## Testes

- shop stock/sale;
- owner private/public;
- courier restock sem sale;
- bank unavailable sem Stage 16;
- district commercial zoning;
- upgrade aumenta capacity sem duplicar inventory.

## Acceptance

O jogador/cidadão compra em um estabelecimento real e consegue rastrear payer, seller, item, tax e receipt.