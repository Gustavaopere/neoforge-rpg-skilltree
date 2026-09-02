# A0303 — Inoculação

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db81d38004e4fbe51a3b1a

## Contrato
+6% de resistência a dano POISON por rank (+6/+12/+18%). A contribuição entra uma única vez no bucket `RPG_POISON_RESISTANCE`. Não reduz duração de efeitos, não amplia para NATURE genérico e não cria imunidade.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE`; Gate C A0183 está fechado. Compra fail-before-spend. Legacy unavailable = 0 PP em gates/thresholds e permanece reembolsável/migrável.

## Boundary e authority
`LivingDamageEvent.Pre` é boundary mutável adequado para mitigação server-side, mas ainda falta classifier canônico inequívoco de componente POISON e uma lane compartilhada de mitigação. Adapters apenas classificam; não reduzem dano em listener próprio.

## Deduplicação
Contribuições semanticamente iguais agregam no mesmo `canonical_modifier_id`; mitigadores semanticamente distintos compõem conforme o resolver. Generic MAGIC, Wither, DoT ou NATURE não são POISON por fallback.

## Fallback
Sem classifier POISON seguro, aquele componente recebe 0 mitigação A0303. Node permanece indisponível enquanto A0183 estiver fechado.

## Testes Chat 3
1. fail-before-spend com A0183 fechado;
2. +6/+12/+18% uma única vez;
3. mesma família não reduz em listeners duplicados;
4. generic MAGIC/Wither/NATURE não classifica POISON;
5. mixed components preservam apenas parcela POISON;
6. provider absent/mismatch fail-closed;
7. multiplayer/dedicated server.

## Handoff Chat 2
Quando/Se a closure abrir, estender o resolver compartilhado; não criar reducer POISON concorrente.