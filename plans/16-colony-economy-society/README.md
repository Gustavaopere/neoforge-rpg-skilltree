# 16 — Economia e Sociedade da Colônia

## Objetivo

Transformar MineColonies e os sistemas próprios do RPG em uma economia simulada na qual dinheiro, salários, compras, propriedade, impostos, orçamento público, pesquisa, pobreza e classes sociais são fatos persistentes — não apenas buffs decorativos.

O Stage 16 é **econômico**, não governamental. O Stage 17 decide quais regras/regimes estão ativos; o Stage 16 executa as consequências financeiras e sociais dessas regras.

## Modelo geral

```text
EconomicActor
├── jogador
├── cidadão MineColonies
├── empresa/estabelecimento
├── tesouro da colônia
├── tesouro do realm
└── instituição pública

Transaction
├── payer
├── payee
├── amount + currencyId
├── reason
├── references (shop/job/property/law/research)
└── immutable receiptId
```

Toda transferência é atômica e auditável. Courier/Warehouse movimentam **mercadoria**; não fingem que transporte interno foi uma compra. A compra ocorre no ponto econômico real: loja, contrato, aluguel, salário, imposto, serviço ou outro evento declarado.

## Decisões canônicas

1. Cada cidadão possui carteira econômica persistente associada à identidade estável do cidadão, não ao UUID transitório da entidade renderizada.
2. Tesouro público é conta própria; dinheiro público não é “saldo do jogador”.
3. Salários saem de empregador/tesouro e podem gerar atraso/dívida quando não há caixa.
4. Preço é data-driven e pode responder a oferta/procura, impostos e política, com limites determinísticos.
5. Lojas executam troca real `dinheiro ↔ mercadoria`.
6. Itens modded são classificados automaticamente por tags/registries/adapters; não haverá catálogo manual eterno.
7. Propriedade, aluguel e patrimônio existem separadamente de renda.
8. Classe social é **derivada** de renda/patrimônio/propriedade e política vigente; não é etiqueta arbitrária fixa.
9. Construção/manutenção e pesquisa consomem orçamento real.
10. Pobreza, dívida, assistência e desigualdade têm impacto mensurável e alimentam Stage 20.
11. Singleplayer-first; nenhum desenho depende de mercado PvP.
12. Todo texto próprio em pt-BR.

## Integrações

- MineColonies: cidadãos, jobs, buildings, requests e logistics;
- Stage 15: distrito, zoning e contexto local;
- Stage 17: leis/regime/tax policy;
- Stage 18: lojas, bancos, prédios e empregos próprios;
- Stage 19: combustível/aquecimento e racionamento;
- Stage 20: tributos, guerra, vassalagem e descontentamento.

## Ordem

1. `01-money-treasury-citizen-wallets.md`
2. `02-salaries-work-and-employment.md`
3. `03-prices-taxes-subsidies-decrees.md`
4. `04-consumption-logistics-and-transactions.md`
5. `05-shops-goods-and-auto-classification.md`
6. `06-property-rent-wealth-and-social-class.md`
7. `07-construction-maintenance-and-public-budget.md`
8. `08-research-financing-and-patronage.md`
9. `09-poverty-debt-welfare-and-inequality.md`
10. `10-tests-performance-save-migration.md`

## Definition of Done

Uma colônia consegue pagar salários, operar lojas, cobrar impostos, registrar propriedade/aluguel, financiar prédio/pesquisa, classificar riqueza e enfrentar falta de caixa sem criar/destruir dinheiro silenciosamente; save/reload e mudanças de regime preservam o ledger.