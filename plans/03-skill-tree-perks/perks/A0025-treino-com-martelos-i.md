# A0025 — Treino com Martelos I

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** VALIDADA EM CI na PR #242; `P-A0025-01` e `P-A0025-02` resolvidas.
- **Notion:** `3c569db9-f0db-81db-a6bd-c81a093c0e44`.

## Contrato canônico

- Root de Martelos; nível 10 + gateway `epic_hammer` + mastery `epicfight:heavy` ≥70.
- 3 ranks, custo 1/rank; +3% dano de martelo/rank, máximo +9%.
- Família exclusivamente provider-native HAMMER/heavy. Categoria desconhecida = fail-closed.
- Proibido `rpgskilltree:hammers`, nome, material, aparência, impacto, dano ou velocidade como classificação paralela.
- Mastery `epicfight:heavy`: +10 uma única vez por tipo hostil inédito atingido por hit direto provider-native HAMMER/heavy, persistido em `DiscoveryProgress`; hits repetidos não concedem XP. Gate 70 = 7 tipos; A0030 mastery 80 = 8 tipos.

## Auditoria — 9 eixos

1. Gates: PASS após correção da semântica de mastery.
2. Integração: PASS — provider-native first.
3. Identidade: PASS como fundamento exterior.
4. Topologia: PASS — root camada 1.
5. Especializações: PASS — `FUNDAMENTO_EXTERIOR: MARTELOS`.
6. PT-BR: PASS.
7. Notion: PASS após remoção do fallback paralelo e anti-farm.
8. NeoVitae: PASS.
9. Providers: PASS — runtime corrigido na PR #242.

## Evidência e pendências

- `CombatPerkTreeModel` exige nível 10, mastery 70 e `epic_hammer`.
- `A0021A0040EpicFightHooks.categoryFamily(...)` classifica HAMMER apenas pela categoria Epic Fight explícita.
- A rota `rpgskilltree:hammers` foi removida do resolver de tags do adapter; o arquivo de tag HAMMER também foi removido, impedindo reativação por datapack externo.
- `A0021A0040MasteryPolicy` exclui HAMMER do award repetível de 3 XP/hit.
- `A0021A0040MasteryHooks` usa `DiscoveryProgress` persistente e concede +10 `epicfight:heavy` somente no primeiro hit direto contra cada tipo hostil distinto.
- `ARCANE_BACKLASH` e companions Mobstein nunca concedem Mastery ao jogador.

`P-A0025-01`: **RESOLVIDA na PR #242**.
`P-A0025-02 / P-MASTERY-HEAVY`: **RESOLVIDA na PR #242**.

## Chat 2 — implementação e regressão — PR #242

- Regressão JUnit valida +10 XP por novo tipo hostil, 0 XP para repetição, rejeição de hit indireto e de família não HAMMER.
- Discovery key estável: `mastery/epicfight:heavy/entity_type/<registry-id>`.
- A mutação de Mastery + Discovery usa `PlayerProgressionRuntime.awardMasteryAndDiscoveries(...)` no boundary persistente canônico.
- MACE/SCYTHE não foram redesenhadas nem corrigidas antecipadamente neste lote.
- CI #2192 validou JUnit, GameTests, build, JAR e dedicated-server smoke.
