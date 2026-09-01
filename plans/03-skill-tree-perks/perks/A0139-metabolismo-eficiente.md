# A0139 — Metabolismo Eficiente

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.** O tradeoff de Stamina é inseparável e o boundary de regeneração natural ainda não foi provado.

## Contrato

- Notable SURVIVAL/METABOLISM; 1 rank; custo 2 PP.
- Em `body_cost_event` elegível: −12% adicional no canal METABOLIC quando existir receipt corporal positivo e, para a mesma `action_id`, −12% no canal HYDRATION quando TWR produzir receipt correspondente.
- Cada canal permanece sob cap compartilhado de 30%.
- Tradeoff obrigatório: ×0,92 na **regeneração natural de Stamina** enquanto a perk estiver ativa.
- Pré-requisito: Gateway SURVIVAL + ≥3 nodes METABOLIC distintos capability-eligible, incluindo ≥1 profissional/climático da lista canônica. Nodes indisponíveis contam 0.

## Authority

- FoodData: origem METABOLIC quando causal.
- Thirst Was Reclaimed 3.0.4: owner de HYDRATION.
- Epic Fight 21.17.3.1: owner de Stamina.
- `STAMINA_NATURAL_REGEN_MODIFIABLE` não está provada no runtime auditado; não há autorização para polling/refund ou redução de outro recurso.

## Availability

Sem boundary versionado que module especificamente a regeneração natural **antes da aplicação nativa**, A0139 inteira fica indisponível/não comprável; allocation legado vale 0 PP e permanece migrável/reembolsável.

## Handoff Chat 2

Quando o provider existir, aplicar o tradeoff toda vez que a regen natural positiva for calculada, exatamente uma vez. Não afetar refunds, gains ativos, stamina de skills ou outros recursos. O benefício corporal e o tradeoff formam contrato all-or-nothing.

## Testes Chat 3

- purchase fail-before-spend/0 PP sem Stamina seam;
- nodes provider-gated não completam o requisito de três;
- METABOLIC e HYDRATION independentes, same action e caps 30%;
- tradeoff somente em natural regen, ×0,92 uma vez;
- sem efeito em refunds/gains ativos;
- all-or-nothing provider removal, respec, reload e multiplayer.
