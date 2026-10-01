# A0308 — Seiva Arcana

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183→A0304.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db81bf8678eeada5cad489

## Contrato
+4% de regeneração nativa positiva de MANA por rank (+4/+8/+12%) enquanto o jogador estiver em território explicitamente classificado `NATURAL_TERRITORY`. Multiplica apenas a taxa nativa positiva/modificável do mesmo provider; zero/bloqueada permanece zero.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0304≥2 + provider de MANA com regen modificável. Gate C A0183/A0304 fechados; compra fail-before-spend. Legacy unavailable = 0 PP em gates e reembolsável/migrável.

## Territory/resource authority
A tag planejada `rpgskilltree:natural_biomes` não existe na `main`. Ainda faltam `NATURAL_TERRITORY_CONTEXT_V1` e `MANA_REGEN_MODIFIER_V1` ou boundary provider-native equivalente. Em sublevels, território deve ser resolvido no level/espaço authoritative atual; Sable/Aeronautics não autorizam herdar o biome do parent Level.

Ars Source, Malum Soul/Spirit, FE e outras energias não são MANA. Não criar recurso, timer ou regen paralela.

## Fallback
Sem território natural seguro ou regen MANA positiva/modificável, bônus = 0. Node permanece indisponível por closure externa.

## Testes Chat 3
1. fail-before-spend A0183/A0304/provider;
2. ×1.04/×1.08/×1.12 somente sobre regen positiva;
3. taxa zero/bloqueada continua zero;
4. Source/Soul/Spirit/FE não entram;
5. sublevel usa territory authoritative, não parent biome;
6. provider/config/tag reload reconciliado;
7. dimensão/respec/login;
8. multiplayer/dedicated server.

## Handoff Chat 2
Não fabricar `MANA` nem usar outro recurso como fallback. Sem territory + native regen boundary, manter indisponível.