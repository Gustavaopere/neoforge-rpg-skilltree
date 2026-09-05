# 20.05 — Vassalagem, tributo e conquista

## VassalageTreaty

Define liege/vassal e obrigações concretas:

- periodic tribute money/goods;
- military support;
- diplomacy restrictions;
- autonomy scope;
- duration/break conditions;
- succession handling.

## Tribute

Dinheiro usa Stage 16 ledger; goods usam procurement/logistics contract. Falta de pagamento gera arrears/breach, não item/moeda negativo artificial.

## Conquista

Conquest é resultado de war goal + outcome, não clique administrativo. Transição registra:

- territory/colony transfer;
- treasury/property policy;
- government/law transition Stage 17;
- titles/vassal relations;
- occupation/resistance;
- citizens economic contracts preserved/migrated.

Não zerar wallets, properties ou jobs para facilitar takeover.

## Libertação/independência

Usa mesma máquina de transição inversa com records históricos, não restaura snapshot antigo cegamente.

## Testes

- tribute payment/arrears;
- breach;
- vassal independence;
- conquest transition;
- property/debt preservation;
- realm graph consistency.

## Acceptance

Vassalagem e conquista têm obrigações e migrações reais, sem renomear owner UUID e chamar isso de reino.