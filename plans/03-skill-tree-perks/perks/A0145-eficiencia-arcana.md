# A0145 — Eficiência Arcana

## Estado Chat 1

**DESIGN APROVADO COM COBERTURA ARS DIRETA / IRON'S FAIL-CLOSED.**

A perk só pode reduzir custo quando o provider permite aplicar a mesma regra de forma coerente na elegibilidade/admissão e na cobrança final. Ars Nouveau 5.13.1 satisfaz o contrato. Iron's 3.16.3 não satisfaz no snapshot atual.

## Contrato

- Domínio/árvore: ARCANE / Principal — ARCANE.
- Ramo: Fundamentos — Eficiência de Mana; camada 1; Tronco.
- Até 5 ranks; 1 PP/rank.
- Gateway ARCANE obrigatório.
- Redução da parcela MANA nativa: ×0,98 / ×0,96 / ×0,94 / ×0,92 / ×0,90.
- Custos não-MANA permanecem integralmente cobrados.
- Custo nativo 0 continua 0; custo inteiro positivo não pode virar gratuito: `max(1, floor(custo_before × fator))`, salvo piso mais restritivo do provider.

## Tipagem de recursos

MANA é separada de:

- Ars Source;
- Goety Soul Energy;
- Malum spirits/souls;
- HP/sangue;
- reagentes, cargas e itens;
- energia tecnológica.

A0145 não converte, reduz ou subsidia esses recursos.

## Ars Nouveau 5.13.1 — hook aprovado

Snapshot: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

`SpellCostCalcEvent.Post` expõe o custo final mutável do `SpellContext`. O mesmo pipeline de resolução de custo alimenta a validação e cobrança do cast, tornando possível manter coerência gate→debit.

Boundary:

`SpellCostCalcEvent.Post → currentCost final positivo → A0145 uma vez → quantização/piso → pipeline nativo de admissão/cobrança`.

O adapter deve preservar caster/context/action identity quando disponível e não tocar em Source ou outros custos.

## Iron's 3.16.3 — evidência negativa

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`AbstractSpell.canBeCastedBy()` calcula `hasEnoughMana` usando `getManaCost(spellLevel)` **antes** de `SpellOnCastEvent`. Embora `SpellOnCastEvent#setManaCost(int)` altere a quantia posteriormente debitada, ele não retroage para o gate de mana suficiente.

Consequência: usar apenas `setManaCost` permitiria pagar menos mana, mas continuaria bloqueando casts que deveriam ser possíveis com o custo reduzido. Isso viola a identidade da perk.

Portanto, o canal Iron's é fail-closed até existir `MANA_COST_REDUCER_COMPLETE` que preserve conjuntamente admissão e cobrança.

## Fallback

Fail-closed por provider. Ars permanece funcional; Iron's recebe efeito 0. Não aplicar desconto somente no debit e não substituir a perk por regen, max mana, refund ou geração gratuita.

Se nenhum provider com capability completa estiver presente, compra deve falhar antes do gasto e legacy allocation vale 0 PP.

## Handoff Chat 2

- implementar Ars somente via `SpellCostCalcEvent.Post`;
- não usar `SpellOnCastEvent#setManaCost` isoladamente para Iron's;
- se surgir seam Iron's alternativo, provar coerência gate→debit antes de habilitar;
- manter recursos não-MANA intactos;
- deduplicar um cálculo/cast para uma única aplicação.

## Testes Chat 3

1. ranks ×0,98/0,96/0,94/0,92/0,90;
2. custo 0 permanece 0 e custo inteiro positivo respeita piso;
3. Ars gate e debit usam custo reduzido coerentemente;
4. Source e custos compostos não-MANA permanecem inalterados;
5. Iron's 3.16.3 recebe zero efeito enquanto capability completa estiver ausente;
6. teste negativo provando que `setManaCost` isolado não é implementação válida;
7. provider absent/removed/respec/reload mantém fail-closed;
8. nenhum refund/top-up/regen substituto;
9. uma aplicação por cast/context.