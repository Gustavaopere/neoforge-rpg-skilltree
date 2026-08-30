# 16.01 — Dinheiro, tesouro e carteiras

## Autoridade

Criar um ledger server-authoritative com moeda identificada por `ResourceLocation`. A implementação inicial pode possuir uma moeda padrão do RPG, mas o schema não deve fechar o domínio a um enum.

## Identidades econômicas

`EconomicActorId` deve suportar, no mínimo:

- player;
- MineColonies citizen via identidade estável de colônia+cidadão;
- business/institution;
- colony treasury;
- realm treasury.

Nunca usar somente UUID da entidade MineColonies como chave econômica permanente.

## Ledger

Cada transação possui `receiptId`, payer, payee, amount, currency, reason, timestamp lógico/revision e referências de origem. Operação é atômica: ou débito e crédito publicam juntos ou nada muda.

Saldos não podem ficar negativos salvo quando uma conta/contrato explicitamente aceita crédito/dívida. Dívida é registro separado, não saldo mágico abaixo de zero por acidente.

## Fontes e sumidouros monetários

Qualquer criação/destruição monetária exige `MonetarySourceReason`/`MonetarySinkReason` data-driven e auditável, por exemplo emissão inicial, reward administrativo aprovado ou sink de sistema. Transferências normais conservam soma dos saldos.

## Tesouro

Recebe impostos, tributos e receitas públicas; paga salários públicos, obras, manutenção, welfare e pesquisa. O jogador pode governar o tesouro via leis/permissões, mas não é dono automático do saldo.

## Performance

Persistir saldos + journal bounded. Receipts antigos podem ser compactados em checkpoints auditáveis; nunca manter evento por tick indefinidamente.

## Testes

- transferência normal;
- fundos insuficientes;
- receipt idempotente/replay;
- conservation invariant;
- cidadão unload/reload mantém carteira;
- remoção/migração de citizen ID;
- duas moedas independentes;
- corrupção de journal com recovery.

## Acceptance

Dinheiro possui origem, destino e dono inequívocos, sem duplicação por retry/reload.