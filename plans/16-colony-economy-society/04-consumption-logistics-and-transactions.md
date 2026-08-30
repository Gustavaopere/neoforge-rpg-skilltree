# 16.04 — Consumo, logística e transações

## Regra principal

**Courier/Warehouse não compram itens apenas por transportá-los.** MineColonies logistics movimenta estoque entre locais; o Stage 16 registra compra somente quando existe troca econômica real.

## Fronteiras econômicas

Eventos possíveis:

- cidadão compra em shop;
- instituição compra de business;
- treasury procurement;
- aluguel;
- salário;
- imposto;
- trade entre realms;
- consumo subsidiado/racionado.

Transferência interna de um warehouse para building da mesma entidade econômica é inventory movement, não market sale.

## TransactionCoordinator

Operações que envolvem item + dinheiro devem usar prepare/commit/rollback ou boundary equivalente:

1. validar estoque e preço;
2. reservar bem;
3. validar payer;
4. executar transferências monetárias/taxas;
5. transferir item;
6. publicar receipt.

Se provider não oferecer transação atômica, usar dedupe/compensation comprovada; nunca duplicar item ou moeda em crash/retry.

## Consumo

Consumo pessoal pode ser inferido apenas de eventos comprovados. Não subtrair item de inventário do cidadão só para satisfazer uma estatística abstrata sem integração validada.

## Testes

- warehouse transfer sem dinheiro;
- shop sale;
- crash/retry fixture;
- item reservado some antes do commit;
- tax + sale atomicidade;
- multiplayer attribution seguro apesar de singleplayer-first.

## Acceptance

Logística continua logística e todo movimento financeiro corresponde a uma causa econômica real.