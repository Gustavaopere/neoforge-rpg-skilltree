# A0027 — Precisão com Martelos

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** presente no resolver crítico; confirmação depende das correções de família HAMMER de A0025.
- **Notion:** `3c569db9-f0db-816c-905b-c0b6df7a8fa7`.

## Contrato canônico

- A0025 ≥1 + gateway `epic_hammer`.
- 3 ranks, custo 1/rank.
- +3% de chance crítica com martelos por rank, máximo +9%.
- Hit direto do jogador, família HAMMER/heavy provider-native e uma única resolução crítica/root action.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS — crítico canônico único.
3. Identidade: PASS — precisão de martelos.
4. Topologia: PASS — camada 2.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary causal.
8. NeoVitae: PASS.
9. Providers: PASS de design; depende da classificação HAMMER corrigida.

## Evidência e boundaries

- `A0021A0040EpicFightHooks` reutiliza `A0001A0020CriticalService`.
- `ARCANE_BACKLASH` e ataques de allies/bodyguards Mobstein não entram no resolver nem herdam crítico do dono.
- Volcanoes/Enshrouded não fornecem critical receipt.

## Pendências

Nenhuma exclusiva de A0027. `P-A0025-01` deve ser resolvida para garantir que a família HAMMER usada aqui seja exclusivamente provider-native.
