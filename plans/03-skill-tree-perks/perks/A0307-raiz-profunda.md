# A0307 — Raiz Profunda

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183→A0304.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db8149a7edd3cea3bb001c

## Contrato
+5% knockback resistance por rank (+5/+10/+15%) somente enquanto o jogador estiver `onGround` e a superfície física efetiva que o sustenta estiver classificada `NATURAL_GROUND`. Uma única instância transitória por ID estável, removida imediatamente ao perder a condição.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0304≥1. Gate C A0183/A0304 fechados; compra fail-before-spend. Legacy unavailable = 0 PP em gates e reembolsável/migrável.

## Support context
A tag planejada `rpgskilltree:natural_ground` não existe na `main`. Ainda faltam `GROUND_SUPPORT_CONTEXT_V1` e classifier data-driven `NATURAL_GROUND`. Em sublevels móveis, a posição deve ser resolvida no espaço authoritative correto antes da consulta. Sable/Aeronautics fornecem transformação/contexto, mas não semântica natural.

Não inferir naturalidade por dirt/grass visual, biome, namespace, dimensão, bloco aproximado abaixo do player ou parent Level.

## Runtime permitido quando desbloqueada
A primitive genérica de `AttributeModifier` transitório por ID estável já existe; pode ser reutilizada para a resistência depois que o support context for seguro. Reconciliar idempotentemente em mudança de suporte, login, dimensão e respec.

## Fallback
Sem support context/classifier inequívocos, bônus = 0. Node permanece indisponível por closure externa.

## Testes Chat 3
1. fail-before-spend A0183/A0304;
2. onGround + support natural positivo;
3. ar/veículo/surface não natural = 0;
4. sublevel usa espaço correto e não parent-level approximation;
5. modifier único/idempotente e cleanup imediato;
6. tag/classifier reload reconciliado;
7. login/dimensão/respec;
8. multiplayer/dedicated server.

## Handoff Chat 2
Não usar bloco abaixo aproximado. Sem support-context authoritative, manter indisponível.