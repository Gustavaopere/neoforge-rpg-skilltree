# A0025 — Treino com Martelos I

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** NÃO CONFIRMADA; `P-A0025-01` e `P-A0025-02` abertas.
- **Notion:** `3c569db9-f0db-81db-a6bd-c81a093c0e44`.

## Contrato canônico

- Root de Martelos; nível 10 + gateway `epic_hammer` + mastery `epicfight:heavy` ≥70.
- 3 ranks, custo 1/rank; +3% dano de martelo/rank, máximo +9%.
- Família exclusivamente provider-native HAMMER/heavy. Categoria desconhecida = fail-closed.
- Proibido `rpgskilltree:hammers`, nome, material, aparência, impacto, dano ou velocidade como classificação paralela.
- Mastery `epicfight:heavy`: +10 uma única vez por tipo hostil inédito atingido por hit direto provider-native HAMMER/heavy, persistido em `DiscoveryProgress`; hits repetidos não concedem XP. Gate 70 = 7 tipos; A0030 mastery 80 = 8 tipos.

## Auditoria — 9 eixos

1. Gates: PASS após correção da semântica de mastery.
2. Integração: PASS de design — provider-native first.
3. Identidade: PASS como fundamento exterior.
4. Topologia: PASS — root camada 1.
5. Especializações: PASS — `FUNDAMENTO_EXTERIOR: MARTELOS`.
6. PT-BR: PASS.
7. Notion: PASS após remoção do fallback paralelo e anti-farm.
8. NeoVitae: PASS.
9. Providers: PASS de design; runtime requer correção.

## Evidência e pendências

- `CombatPerkTreeModel` exige nível 10, mastery 70 e `epic_hammer`.
- **P-A0025-01:** `A0021A0040EpicFightHooks.family(...)` ainda aceita fallback paralelo para HAMMER via tag RPG. Chat 2 deve remover/desativar esse fallback para HAMMER e manter classificação Epic Fight explícita.
- **P-A0025-02 / P-MASTERY-HEAVY:** `A0021A0040MasteryPolicy` concede hoje 3 XP por hit confirmado e `A0021A0040MasteryHooks` permite farm repetitivo. Chat 2 deve substituir o award de HAMMER/heavy pela política finita `DiscoveryProgress`: +10 por tipo hostil inédito, uma vez por tipo.
- `ARCANE_BACKLASH` e companions Mobstein nunca concedem Mastery ao jogador.

Ambas as pendências bloqueiam `IMPLEMENTAÇÃO CONFIRMADA`, não o design.
