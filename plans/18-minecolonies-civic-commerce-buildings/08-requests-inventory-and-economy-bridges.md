# 18.08 — Requests, inventário e bridge econômica

## Regra principal

MineColonies request/logistics é reutilizado para necessidades físicas. Stage 16 decide quando aquisição é compra.

## Fluxos

### Estoque interno

```text
Warehouse → Courier → Building
```

Sem transferência monetária quando owner econômico é o mesmo e não existe contrato de compra.

### Procurement externo

```text
Building precisa item
→ request não encontra estoque interno
→ procurement policy autoriza mercado
→ Stage 16 reserva orçamento
→ shop/business vende
→ item entra na logística
```

A compra deve ocorrer uma vez e possuir receiptId ligado ao request/procurement ID.

## Inventários

Nunca duplicar inventory interno MineColonies em um segundo storage RPG. Guardar apenas references/reservations necessárias.

## Falhas

- request cancelado: liberar reservation;
- item entregue após timeout: dedupe pelo procurement ID;
- warehouse cheio: dinheiro não some sem compensação;
- business sem estoque: request permanece pendente/alternate supplier conforme policy.

## Testes

- internal transfer;
- external purchase;
- cancellation;
- retry;
- insufficient treasury;
- duplicate callback;
- save/reload mid-procurement.

## Acceptance

Supply chain e ledger convergem sem item/moeda duplicados.