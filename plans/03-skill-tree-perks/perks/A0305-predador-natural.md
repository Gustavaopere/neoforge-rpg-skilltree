# A0305 — Predador Natural

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db81709934e46a31950a69

## Contrato
+5% de dano por rank (+5/+10/+15%) contra alvo hostil explicitamente classificado `NATURAL_HOSTILE`. A condição é target-specific e aplicada uma vez por `outcome_id + target_uuid`. Contra BOSS, apenas a contribuição desta perk recebe coeficiente 0,50: +2,5/+5/+7,5%.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0301≥1 ou A0304≥1. Gate C A0183 está indisponível. Compra fail-before-spend; legacy unavailable = 0 PP para gates e reembolsável/migrável.

## Classifiers requeridos
A tag planejada `rpgskilltree:natural_hostiles` não existe na `main`. É necessário `NATURAL_HOSTILE_CLASSIFIER_V1` data-driven/allowlisted e classificação BOSS/PvP inequívoca. `BossIdentity` existente pode alimentar adapter apenas onde houver mapping runtime comprovado; não equivale a `isBoss(target)` universal.

Mob class, animalidade, neutralidade, biome, namespace, aparência ou origem em mod de fauna não definem `NATURAL_HOSTILE`.

## Fallback
Sem classifier natural, contribuição = 0. Sem boss/PvP classifier seguro, fail-closed no ramo dependente do coeficiente especial. Node permanece indisponível enquanto A0183 estiver fechado.

## Testes Chat 3
1. fail-before-spend A0183;
2. allowlist/tag positiva e negativa;
3. nenhum fallback por Mob/namespace/biome;
4. target-specific, sem snapshot herdado para outro alvo;
5. BOSS reduz somente contribuição A0305;
6. dedup por outcome+target;
7. reload de tags/classifier reconciliado;
8. multiplayer/dedicated server.

## Handoff Chat 2
Não criar heurística de fauna. O classifier deve ser explícito e data-driven.