# 06.12 — Economy V2 — moeda, contas, comércio, banco e MineColonies

> **Status:** PLANO ARQUITETURAL / IMPLEMENTAÇÃO NÃO INICIADA.  
> **Baseline Git:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Authority física:** modlist 2026-09-08, 595 entradas; MineColonies `1.1.1381-1.21.1-snapshot`.  
> **Dependência existente:** 06.11 Economy V1 já está integrada e continua sendo a base macroeconômica da colônia.

## 1. Problema

A Economy V1 atual entrega um ledger virtual por colônia, emissão/retirada (`MINT`/`RETIRE`), persistência, idempotência, capacidade econômica `Q`, supply e indicadores de inflação/deflação.

Ela **não** entrega ainda:

- moeda física para o jogador;
- wallet pessoal;
- contas bancárias;
- pagamentos player↔player;
- vendors/lojas genéricas;
- compra/venda de itens;
- salários;
- impostos completos;
- business accounts;
- escrow;
- auction/market;
- inter-colony trade/FX.

Economy V2 fecha essa lacuna sem criar uma segunda autoridade monetária concorrente.

---

## 2. Decisão central: uma única autoridade econômica

O projeto terá um **Economy Core server-authoritative** com um transaction ledger comum. MineColonies, wallets, bancos, lojas e mercados serão projeções/contas sobre esse mesmo sistema.

```text
                   ECONOMY CORE
                        |
                Canonical Ledger
                        |
       +----------------+----------------+
       |                |                |
 Player Accounts   Colony Accounts   Business/Escrow
       |                |                |
    Wallets         Treasury           Vendors
       |                |                |
 Physical Cash      Wages/Taxes      Buy/Sell/Market
       \________________|________________/
                        |
                audited transactions
```

Não existirão dois balances canônicos para o mesmo dinheiro.

---

## 3. Relação com a Economy V1

A V1 não é descartada.

### 3.1 Preservar

- ledger server-side;
- `MINT`/`RETIRE` explícitos;
- replay protection/idempotência;
- persistence;
- supply e indicadores macroeconômicos;
- capacidade econômica da colônia derivada do MineColonies;
- fail-closed quando não existe hook seguro;
- separação entre MineColonies authority e RPG economy authority.

### 3.2 Evoluir

O balance econômico da colônia vira uma conta/treasury canônica dentro da camada ampliada. Transações de wage/tax/trade passam pelo mesmo ledger em vez de mutar balance paralelamente.

### 3.3 Revalidação obrigatória

Os testes provider-present históricos usaram MineColonies 1.1.1375/1376. A modlist física atual usa **1.1.1381**.

Antes de qualquer expansão sobre APIs/hooks MineColonies:

1. auditar source/API da build exata 1.1.1381;
2. repetir os smokes provider-present relevantes;
3. confirmar que queries de colônia/cidadão/job/storage continuam válidas;
4. atualizar a matriz de compatibilidade;
5. deixar fail-closed qualquer integração cuja superfície tenha mudado.

CI antigo não prova compatibilidade com 1.1.1381.

---

## 4. Pesquisa de referência antes de implementar

Economy V2 deve estudar projetos existentes para não reinventar problemas resolvidos, mas reutilização de código depende de licença e adequação arquitetural.

### 4.1 Lightman's Currency

Pontos fortes já identificados no source público:

- `MoneyAPI` e resource handlers;
- wallets;
- Bank API/accounts;
- traders;
- auction house;
- ownership;
- abstração que aceita dinheiro físico e tipos não físicos.

O projeto declara **Apache License 2.0** em seu build metadata. Antes de copiar qualquer unidade de código:

- confirmar `LICENSE`/headers da revisão exata escolhida;
- registrar arquivo/revisão de origem;
- preservar notices/atribuições exigidas;
- adaptar apenas componentes que não criem uma segunda monetary authority.

### 4.2 Create: Numismatics

Pontos fortes:

- moeda física e estética Create;
- Vendor;
- Bank Terminal;
- fluxo buying/selling;
- automação/inventários;
- UX adequada ao ecossistema Create.

O repositório público reporta **LGPL-3.0**. Reuso literal deve ser isolado e juridicamente compatível com a distribuição do RPG Skill Tree. Quando o boundary de licença ficar inconveniente, preferir estudo e reimplementação clean-room do comportamento.

### 4.3 Outros mods

Mods menores de economia podem ser estudados para UX, cartões, ATM, preços dinâmicos e lojas, mas só entram como fonte de código se licença e revisão forem verificadas.

### 4.4 Regra de procedência

Criar uma tabela de provenance antes do primeiro copy/port:

| Componente | Projeto | revisão | licença | ação | arquivos derivados |
|---|---|---|---|---|---|
| exemplo | Lightman's | SHA/tag | Apache-2.0 | adaptar | ... |

Sem essa linha, nenhuma cópia literal entra no repositório.

---

## 5. Unidades monetárias

### 5.1 Ledger unit

O ledger possui uma unidade canônica inteira/fixed-point. Não usar `float`/`double` para saldo monetário.

### 5.2 Denominações físicas

Moedas/notas/tokens físicos são **representações de valor**, não uma nova fonte de verdade capaz de duplicar supply.

Fluxo obrigatório:

```text
withdraw cash
account -X -> ledger transaction -> physical instruments +X

deposit cash
consume instruments X -> ledger transaction -> account +X
```

A soma não pode existir simultaneamente no balance digital e no item físico sem uma contrapartida registrada.

### 5.3 Emissão e retirada

Somente operações explícitas de política econômica podem alterar money supply:

- `MINT` cria supply;
- `RETIRE` destrói supply;
- transferências, salários, compras, taxes, bank deposits e cash withdrawal **movem** valor, não criam valor.

Validators/tests devem impedir paths que chamem `MINT` para pagar uma compra comum.

---

## 6. Contas

Tipos conceituais mínimos:

- `PLAYER` — pessoa/jogador;
- `COLONY` — treasury MineColonies;
- `BUSINESS` — loja/empresa;
- `ESCROW` — retenção temporária de mercado/auction/trade;
- `SYSTEM` — somente para operações de política explicitamente autorizadas.

Cada conta possui ID estável e owner/authority explícito.

### 6.1 Player account

- balance digital;
- transaction history resumida/paginada;
- transferências;
- deposit/withdraw cash;
- permissions para joint/team access somente se design posterior autorizar.

### 6.2 Colony account

- associada a colony ID canônico;
- preserva Economics V1;
- recebe impostos/taxas;
- paga salários e despesas;
- pode liquidar trades autorizados.

### 6.3 Business account

Vendor/loja não deve guardar saldo mágico invisível num block entity paralelo. A loja referencia uma business account ou owner account e todas as compras/vendas produzem transações.

---

## 7. Wallet e moeda física

Wallet é conveniência de inventário/acesso, não ledger.

Possíveis capacidades após protótipo:

- guardar denominações;
- auto-collect físico;
- mostrar valor total;
- depositar/sacar no banco;
- pagar usando composição de cash + balance quando a regra permitir;
- Curios integration somente se a API exata estiver confirmada.

Perda/destruição de cash físico deve continuar economicamente consistente: dinheiro físico perdido permanece fora de contas até ser encontrado/depositado; destruir cash por mecânica autorizada deve registrar `RETIRE` quando for realmente retirada monetária, não simplesmente apagar item sem accounting.

---

## 8. Vendor e compra/venda

### 8.1 Vendor contract

Um vendor declara ofertas, não autoridade monetária.

Uma compra atômica valida antes de commit:

- offer revision;
- preço;
- quantidade;
- stock/source real;
- capacidade de destination inventory;
- balance/payment sources;
- account permissions;
- taxes/fees;
- replay/request ID;
- provider availability.

Commit econômico e movimentação de item devem formar uma operação compensável/idempotente. Falha no item não pode cobrar o jogador; falha no pagamento não pode entregar o item.

### 8.2 Venda ao vendor

O vendor precisa ter funding real ou uma policy explícita de system buyer. Se for system buyer, a origem monetária deve declarar se move fundo de uma treasury ou se constitui `MINT` autorizado. Não esconder emissão atrás de “NPC compra tudo”.

### 8.3 Dynamic pricing

Preços dinâmicos podem consumir supply/demand/colony index, mas não devem reescrever a autoridade do ledger.

O preço usado numa transação é snapshotado/revisionado para impedir que duas etapas vejam valores diferentes.

---

## 9. Market e Auction

Implementar depois do Vendor básico.

Fluxo recomendado:

1. seller cria listing;
2. item/quantidade entra em escrow real;
3. listing recebe revision/expiry;
4. buyer paga para escrow;
5. settlement transfere valor menos fee/tax;
6. item é entregue;
7. replay não repete settlement.

Nenhuma auction entrega item sem escrow ou cobra sem garantia da oferta.

---

## 10. Bank

Banco é camada de acesso e serviços sobre accounts/ledger.

Escopo V2 mínimo:

- deposit/withdraw physical cash;
- transfer player↔player;
- player↔colony quando autorizado;
- extrato;
- permissions;
- UI server-validated.

Loans, interest, credit score e debt são **fase posterior**. Não adicionar crédito antes de o ledger/transfers/escrow estarem estáveis.

---

## 11. Wages e cidadãos MineColonies

Salário liga a Economy V2 ao futuro `CitizenVocationProgression`, mas são sistemas distintos.

### 11.1 Authority

- MineColonies continua authority de cidadão, job e work lifecycle;
- Economy Core é authority da transação monetária;
- o RPG Skill Tree não inventa que um citizen trabalhou apenas por tickar perto de um building.

### 11.2 Payroll

Payroll precisa de receipt/period key idempotente, por exemplo uma identidade de período econômico + citizen + colony + wage rule. Reprocessar o mesmo período não paga duas vezes.

O pagamento move valor de Colony Account para destino definido. Se a colônia não tem fundos, comportamento é explícito; não mintar automaticamente para cobrir folha.

### 11.3 Citizen wallet

Não implementar carteira individual de cidadão até existir uma necessidade de gameplay clara. Wage pode inicialmente produzir accounting/consumption agregado ou conta de citizen se e somente se a especificação futura exigir.

YAGNI: não criar milhões de microcontas sem uso.

---

## 12. Taxes

Taxas possíveis:

- transaction fee;
- market/auction fee;
- colony sales tax;
- business tax;
- player property/recurring tax somente se houver um evento autoritativo claro.

Toda taxa possui recipient explícito. “Taxa sumiu” só é permitida se a política disser `RETIRE`; caso contrário deve ser transferência para treasury/system account apropriada.

---

## 13. Comércio entre colônias

Fase posterior ao comércio local.

Requisitos:

- colony accounts estáveis;
- order/offer IDs;
- item/resource receipt;
- route/delivery authority definida;
- escrow;
- settlement idempotente;
- price/index snapshot;
- replay protection.

FX só entra se houver múltiplas moedas soberanas reais. Uma única moeda global **não precisa de câmbio artificial**.

---

## 14. Integração com Create

Create é o ecossistema tecnológico dominante atual e pode fornecer a UX física de automação, mas não ganha authority do dinheiro.

Possibilidades a estudar:

- vendor com inserção/extração automatizável;
- cash handling por belts/deployers/packages;
- bank/vault visuals Create-style;
- stock integration onde APIs reais permitirem;
- redstone/comparator output para estado, sem vazar balance indevido.

Inspirar-se no Numismatics é adequado; copiar implementation específica depende de decisão LGPL/provenance.

---

## 15. Segurança, idempotência e anti-abuso

Toda mutação econômica relevante precisa de:

- request/transaction ID estável;
- replay detection;
- atomic validation before debit;
- no negative balances, salvo futuro credit system explícito;
- no integer overflow;
- authorization/ownership;
- server-side price/cost;
- durable persistence;
- audit trail suficiente;
- compensation/recovery para operação item+money interrompida;
- rate limits quando o endpoint puder ser spamado.

Dupe tests são gate de release, não melhoria opcional.

---

## 16. UI

Telas previstas:

- wallet summary;
- bank/account;
- transfer;
- vendor buy/sell;
- business/vendor configuration;
- market/auction;
- colony treasury summary quando houver surface segura.

A UI nunca calcula saldo/preço final como authority. Server response inclui revision, breakdown e erro legível.

Se MineColonies não oferecer ponto de extensão seguro para Town Hall na build exata, a UI permanece própria/externa e o Town Hall integration fica fail-closed.

---

## 17. Migração e compatibilidade

- preservar dados V1 de colônias;
- migrar cada colony ledger para a representação nova sem alterar supply;
- verificar invariant `supply_before == supply_after` na migração, salvo transação explícita de política;
- manter schema versionado;
- backup/recovery testável;
- nenhuma currency item antiga é convertida automaticamente sem regra de valor explícita.

---

## 18. Ordem de implementação

### V2.0 — research/provenance

- MineColonies 1.1.1381 revalidation;
- Lightman's architecture audit;
- Numismatics architecture audit;
- license/provenance matrix;
- ADR de reuse versus clean-room por componente.

### V2.1 — common accounts/ledger

- generalizar V1 para accounts;
- preservar colony state;
- transfers;
- transaction query/history;
- invariants/supply tests.

### V2.2 — cash + wallet + bank

- denomination registry;
- deposit/withdraw;
- wallet;
- bank UI;
- dupe/crash recovery tests.

### V2.3 — vendor buy/sell

- offer schema;
- stock/payment atomicity;
- business accounts;
- server-side UI;
- Create automation spike.

### V2.4 — colony payroll/tax

- wage receipt/idempotency;
- treasury payments;
- taxes/fees;
- economic index integration.

### V2.5 — market/auction

- listing;
- item escrow;
- monetary escrow;
- settlement/expiry;
- fees.

### V2.6 — inter-colony trade

- only after all earlier invariants are stable.

---

## 19. Testes obrigatórios

- V1 migration preserves exact supply;
- duplicate request never pays twice;
- concurrent transfers cannot overspend;
- withdrawal cannot duplicate cash + digital balance;
- deposit consumes cash exactly once;
- chunk unload/crash between item and money phases recovers deterministically;
- vendor cannot sell nonexistent stock;
- vendor cannot charge and fail delivery;
- sale cannot pay without consuming item;
- market escrow prevents double sale;
- expired listing settles/refunds once;
- payroll period pays citizen/target once;
- insufficient colony funds do not mint silently;
- tax has explicit recipient or RETIRE policy;
- player cannot set price/cost field trusted by server;
- unauthorized account access is denied;
- MineColonies 1.1.1381 provider-present lane passes;
- dedicated server works without client UI classes;
- reload/restart preserves ledger and idempotency state required by contract.

---

## 20. Acceptance

Economy V2 só é considerada pronta quando moeda física, digital balance, colony treasury, vendors e banco movimentam valor sob uma única authority; nenhuma compra/salário/taxa cria moeda silenciosamente; V1 é migrada sem mudar supply; MineColonies 1.1.1381 é revalidado; e qualquer código externo reutilizado possui licença/provenance registrada.

**Não instalar Lightman's Currency ou Numismatics como dependency por padrão.** Eles são referências/possíveis fontes licenciadas. Uma dependência externa só entra se uma ADR demonstrar vantagem sobre o core próprio e preservar uma única monetary authority.