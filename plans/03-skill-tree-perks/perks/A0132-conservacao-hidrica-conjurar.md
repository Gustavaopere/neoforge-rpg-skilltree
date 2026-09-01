# A0132 — Conservação Hídrica: Conjurar

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

Sem `METABOLIC_CAST` e sem `HYDRATION_CAST` causal, o node não pode ser comprado. Availability é transitiva de A0131.

## Contrato

- Máx. 4 ranks; 1 PP/rank.
- Efeito reservado: −3%/rank da parcela HYDRATION corporal real atribuível ao esforço da conjuração, até −12%.
- Cap HYDRATION compartilhado: 30% por evento.
- Pré-requisitos: Gateway SURVIVAL + A0131 ≥2 + provider hídrico real.

## Authority e provider

- Thirst Was Reclaimed 3.0.4 é owner de HYDRATION.
- Providers mágicos classificam cast/resource_id; recursos mágicos não viram hidratação.
- `BodyCostResolver` deve correlacionar o receipt HYDRATION à mesma `action_id` após a resolução METABOLIC.

## Boundary futuro

`cast confirmado -> METABOLIC settlement -> adapter TWR same-action -> HYDRATION_CAST positivo -> reducers HYDRATION -> cap 30% -> commit uma vez`.

## Handoff Chat 2

- Manter indisponível enquanto A0131 ou qualquer binding corporal/hídrico estiver ausente.
- Proibido direct write em thirst, polling de barra ou inferência a partir de Mana/Source/Soul Energy/HP/cooldown.

## Testes Chat 3

1. availability transitiva A0131→A0132;
2. purchase fail-before-spend sem HYDRATION_CAST;
3. zero PP legado;
4. mesmo `action_id` METABOLIC/HYDRATION;
5. cast sem custo corporal não proca;
6. cancelamento/rollback sem sede fantasma;
7. cap HYDRATION 30% independente do METABOLIC.
